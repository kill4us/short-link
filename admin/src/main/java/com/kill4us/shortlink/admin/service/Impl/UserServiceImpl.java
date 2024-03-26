package com.kill4us.shortlink.admin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kill4us.shortlink.admin.common.convention.exception.ClientException;
import com.kill4us.shortlink.admin.dao.entity.UserDO;
import com.kill4us.shortlink.admin.dao.mapper.UserMapper;
import com.kill4us.shortlink.admin.dto.resp.UserRespDTO;
import com.kill4us.shortlink.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

import static com.kill4us.shortlink.admin.common.enums.UserErrorCodeEnum.USER_NOT_EXISTS;

/**
 * 用户接口实现层
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

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
}
