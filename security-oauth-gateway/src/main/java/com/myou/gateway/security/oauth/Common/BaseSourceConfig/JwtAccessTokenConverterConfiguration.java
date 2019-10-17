package com.myou.gateway.security.oauth.Common.BaseSourceConfig;

import com.myou.gateway.security.oauth.Grant.Model.UserDetailExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;

/*
 * @Time    : 2019/10/15 4:34 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : JwtAccessTokenConverterConfig.java
 * @Software: IntelliJ IDEA
 */

/**
 * @EnableConfigurationProperties注解的作用是：使用 @ConfigurationProperties 注解的类生效。
 * 说明：
 * 如果一个配置类只配置@ConfigurationProperties注解，而没有使用@Component，那么在IOC容器中是获取不到properties 配置文件转化的bean。说白了 @EnableConfigurationProperties 相当于把使用 @ConfigurationProperties 的类进行了一次注入。
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class JwtAccessTokenConverterConfiguration {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenEnhance() {
        SecurityProperties.JwtProperties securityPropertiesJwt = securityProperties.getJwt();
        KeyPair keyPair = keyPair(securityPropertiesJwt, keyStoreKeyFactory(securityPropertiesJwt));
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                UserDetailExtension user = (UserDetailExtension) authentication.getUserAuthentication().getPrincipal();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("phone", user.getPhone());
                hashMap.put("email", user.getEmail());
                hashMap.put("roles", user.getRoles());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(hashMap);
                // 设置过期时间
//                GregorianCalendar calendar = new GregorianCalendar();
//                calendar.add(Calendar.MILLISECOND, 20);
//                ((DefaultOAuth2AccessToken) accessToken).setExpiration(calendar.getTime());
                return super.enhance(accessToken, authentication);
            }
        };
        jwtAccessTokenConverter.setKeyPair(keyPair);
        return jwtAccessTokenConverter;
    }

    private KeyPair keyPair(SecurityProperties.JwtProperties jwtProperties, KeyStoreKeyFactory keyStoreKeyFactory) {
        return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(), jwtProperties.getKeyPairPassword().toCharArray());
    }

    private KeyStoreKeyFactory keyStoreKeyFactory(SecurityProperties.JwtProperties jwtProperties) {
        return new KeyStoreKeyFactory(jwtProperties.getKeyStore(), jwtProperties.getKeyStorePassword().toCharArray());
    }
}
