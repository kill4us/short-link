package com.kill4us.shortlink.project.common.constant;

/**
 * Redis Key常量类
 */
public class RedisKeyConstant {

    /**
     * 短链接跳转key前缀
     */
    public static final String GOTO_SHORT_LINK_KEY = "short-link_goto_%s";

    /**
     * 短链接跳转锁前缀
     */
    public static final String LOCK_GOTO_SHORT_LINK_KEY = "short-link_lock_goto_%s";
}
