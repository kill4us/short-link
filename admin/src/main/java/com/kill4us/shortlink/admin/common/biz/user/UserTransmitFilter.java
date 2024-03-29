package com.kill4us.shortlink.admin.common.biz.user;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.Objects;


/**
 * 用户信息传输过滤器
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();
        if (!Objects.equals(requestURI, "/api/short-link/v1/user/login") && !Objects.equals(requestURI, "/api/short-link/v1/user")) {
            String token = httpServletRequest.getHeader("token");
            String username = httpServletRequest.getHeader("username");
            Object userInfoJSONStr = stringRedisTemplate.opsForHash().get("login_" + username, token);
            if (userInfoJSONStr != null) {
                UserInfoDTO userInfoDTO = JSON.parseObject(userInfoJSONStr.toString(), UserInfoDTO.class);
                UserContext.setUser(userInfoDTO);
            }
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
}