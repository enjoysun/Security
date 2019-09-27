package com.myou.service.security.Common.Exception;

/*
 * @Time    : 2019/9/27 4:55 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : UserExitsException.java
 * @Software: IntelliJ IDEA
 */
public class UserExitsException extends Exception {
    public UserExitsException() {
    }

    public UserExitsException(String message) {
        super(message);
    }
}
