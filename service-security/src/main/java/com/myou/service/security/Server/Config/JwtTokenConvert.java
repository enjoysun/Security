package com.myou.service.security.Server.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/*
 * @Time    : 2019/9/26 3:54 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : JwtTokenConvert.java
 * @Software: IntelliJ IDEA
 */
@Configuration
public class JwtTokenConvert {
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("123");
        return accessTokenConverter;
    }
}
