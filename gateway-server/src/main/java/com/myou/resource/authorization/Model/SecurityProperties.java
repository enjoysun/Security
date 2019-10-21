package com.myou.resource.authorization.Model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

/*
 * @Time    : 2019/10/21 4:48 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : SecurityProperties.java
 * @Software: IntelliJ IDEA
 */

/**
 * jwt解码配置
 * */
@ConfigurationProperties("security")
public class SecurityProperties {
    private JwtProperties jwt;

    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }

    public static class JwtProperties {

        private Resource publicKey;

        public Resource getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(Resource publicKey) {
            this.publicKey = publicKey;
        }
    }
}
