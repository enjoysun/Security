package com.myou.resource.authorization.Config;

import com.myou.resource.authorization.Model.SecurityProperties;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.nio.charset.Charset;
/*
 * @Time    : 2019/10/21 5:04 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : TokenConfiguration.java
 * @Software: IntelliJ IDEA
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class TokenConfiguration {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() throws IOException {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        String verifierKey = IOUtils.toString(securityProperties.getJwt().getPublicKey().getInputStream(), Charset.forName("UTF-8"));
        tokenConverter.setVerifierKey(verifierKey);
        return tokenConverter;
    }

    @Bean
    public TokenStore tokenStore() throws IOException {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
}
