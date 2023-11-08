package com.example.common.enums;

public enum ResultCodeEnum {
    SUCCESS("200", "成功"),

    PARAM_ERROR("400", "参数异常"),
    TOKEN_INVALID_ERROR("401", "无效的token"),
    TOKEN_CHECK_ERROR("401", "token验证失败，请重新登录"),
    PARAM_LOST_ERROR("4001", "params lost error"),

    SYSTEM_ERROR("500", "系统异常"),
    USER_EXIST_ERROR("5001", "user already exist"),
    USER_NOT_LOGIN("5002", "用户未登录"),
    USER_ACCOUNT_ERROR("5003", "username or password wrong"),
    USER_NOT_EXIST_ERROR("5004", "user not exist"),
    PARAM_PASSWORD_ERROR("5005", "原密码输入错误"),
    USER_ROLE_ERROR("5005", "user role inconsistent"),
    ;

    public String code;
    public String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
