package com.kill4us.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kill4us.shortlink.admin.dao.entity.UserDO;
import com.kill4us.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.kill4us.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户返回实体
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 查询用户名是否已存在
     * @param username 用户名
     * @return 用户名存在，不返回true，存在返回false
     */
    boolean hasUsername(String username);

    /**
     * 用户注册
     * @param requestParam 注册用户请求参数
     */
    void register(UserRegisterReqDTO requestParam);
}
