package com.kill4us.shortlink.admin.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kill4us.shortlink.admin.common.convention.exception.ClientException;
import com.kill4us.shortlink.admin.dao.entity.UserDO;
import com.kill4us.shortlink.admin.dao.mapper.UserMapper;
import com.kill4us.shortlink.admin.dto.req.UserLoginReqDTO;
import com.kill4us.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.kill4us.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.kill4us.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.kill4us.shortlink.admin.dto.resp.UserRespDTO;
import com.kill4us.shortlink.admin.service.GroupService;
import com.kill4us.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.kill4us.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static com.kill4us.shortlink.admin.common.enums.UserErrorCodeEnum.*;

/**
 * 用户接口实现层
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;  //  注入布隆过滤器
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;
    private final GroupService groupService;

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return
     */
    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        UserRespDTO result = new UserRespDTO();
        if (userDO == null) {
            throw new ClientException(USER_NOT_EXISTS.message());
        }
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    @Override
    public boolean hasUsername(String username) {
        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    /**
     * 用户注册
     * @param requestParam 注册用户请求参数
     */
    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if (!hasUsername(requestParam.getUsername())) {
            throw new ClientException(USER_NAME_EXISTS);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
        try {
            if (lock.tryLock()) {  //  认为获取到锁的用户一定能注册成功，只有trylock成功了，才注册
                int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
                if (inserted < 1) {
                    throw new ClientException(USER_SAVE_ERROR);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                groupService.saveGroup("默认分组");
                return;
            }
            throw new ClientException(USER_NAME_EXISTS);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 更新用户信息
     * @param requestParam 用户修改信息请求参数
     */
    @Override
    public void update(UserUpdateReqDTO requestParam) {
        LambdaUpdateWrapper<UserDO> updateWrapper = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam, UserDO.class), updateWrapper);
    }


    /**
     * 用户登录
     * @param requestParam username, password
     * @return token
     */
    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername())
                .eq(UserDO::getPassword, requestParam.getPassword())
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException("用户不存在");
        }
        Boolean hasLogin = stringRedisTemplate.hasKey("login_" + requestParam.getUsername());
        if (hasLogin != null && hasLogin) {
            throw new ClientException("用户已登录");
        }

        String uuid = UUID.randomUUID().toString();

        stringRedisTemplate.opsForHash().put("login_" + requestParam.getUsername(), uuid, JSON.toJSONString(userDO));
        stringRedisTemplate.expire("login_" + requestParam.getUsername(),30L, TimeUnit.MINUTES);
        return new UserLoginRespDTO(uuid);
    }

    /**
     * 检查用户是否登录
     * @param token
     * @return 存在返回true，否则返回false
     */
    @Override
    public boolean checkLogin(String username, String token) {
        Object remoteToken = stringRedisTemplate.opsForHash().get("login_" + username, token);
        return remoteToken != null;
    }

    @Override
    public void logout(String username, String token) {
        if (checkLogin(username, token)) {
            stringRedisTemplate.delete("login_" + username);  //  用redis中删除key
            return;
        }
        throw new ClientException("用户Token不存在或用户未登录");
    }
}
