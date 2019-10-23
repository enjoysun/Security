package com.myou.gateway.security.oauth.Service;

import org.springframework.stereotype.Service;

/*
 * @Time    : 2019/10/23 9:47 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : OauthClientUtil.java
 * @Software: IntelliJ IDEA
 */
public interface OauthClientUtil {
    String createClientID(String userName);

    String createSecret(String userName, String clientID);
}
