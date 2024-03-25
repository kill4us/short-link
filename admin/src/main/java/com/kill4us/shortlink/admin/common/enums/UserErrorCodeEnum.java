package com.kill4us.shortlink.admin.common.enums;

import com.kill4us.shortlink.admin.common.convention.errorcode.IErrorCode;

public enum UserErrorCodeEnum implements IErrorCode {

    USER_NOT_EXISTS("B000200", "用户查询不存在"),
    USER_EXISTS("B000201", "用户已存在");

    private final String code;

    private final String message;

    UserErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
