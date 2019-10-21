package com.myou.gateway.security.oauth.Grant.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * @Time    : 2019/10/15 3:13 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : PasswordEncoderConfig.java
 * @Software: IntelliJ IDEA
 */

/**
 * 密码加密接入
 * */
@Configuration
public class PasswordEncoderConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
