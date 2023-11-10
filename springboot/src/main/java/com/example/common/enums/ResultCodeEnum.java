package com.example.common.enums;

public enum ResultCodeEnum {
    SUCCESS("200", "success"),

    PARAM_ERROR("400", "params error"),
    TOKEN_INVALID_ERROR("401", "invalid token"),
    TOKEN_CHECK_ERROR("401", "token validation error，please login again"),
    PARAM_LOST_ERROR("4001", "params lost error"),

    SYSTEM_ERROR("500", "system error"),
    USER_EXIST_ERROR("5001", "user already exist"),
    USER_NOT_LOGIN("5002", "user doesn't login"),
    USER_ACCOUNT_ERROR("5002", "username or password wrong"),
    USER_NOT_EXIST_ERROR("5003", "user not exist"),
    PARAM_PASSWORD_ERROR("5004", "password wrong"),
    USER_ROLE_ERROR("5005", "user role inconsistent"),
    NO_AUTH("5006", "no authentication")
    ;

    public String code;
    public String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
