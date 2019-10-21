package com.myou.gateway.security.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/*
 * @Time    : 2019/10/14 11:20 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : SecurityOauthApplication.java
 * @Software: IntelliJ IDEA
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.myou.gateway.security.oauth.Mapper")
public class SecurityOauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityOauthApplication.class, args);
    }
}
