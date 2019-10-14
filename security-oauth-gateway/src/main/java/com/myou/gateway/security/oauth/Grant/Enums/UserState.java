package com.myou.gateway.security.oauth.Grant.Enums;

/**
 * 账户状态
 * */
public enum UserState {
    NORMAL(0),
    LOCKED(1),
    FORBIDDEN(2);

    private int code;

    UserState(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
