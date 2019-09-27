package com.myou.service.security.Common.States;

public enum UserState {
    NORMAL(0),
    DISABLE(1);

    private int state;

    UserState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
