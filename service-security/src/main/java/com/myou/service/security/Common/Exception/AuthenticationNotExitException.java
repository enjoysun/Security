package com.myou.service.security.Common.Exception;

import java.nio.file.AccessDeniedException;

/*
 * @Time    : 2019/10/11 10:10 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : AuthenticationNotExitException.java
 * @Software: IntelliJ IDEA
 */
public class AuthenticationNotExitException extends AccessDeniedException {
    private static final long serialVersionUID = 8531575451064410763L;

    public AuthenticationNotExitException(String file) {
        super(file);
    }

    public AuthenticationNotExitException(String file, String other, String reason) {
        super(file, other, reason);
    }
}
