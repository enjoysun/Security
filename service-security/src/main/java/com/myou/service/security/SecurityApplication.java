package com.myou.service.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*
 * @Time    : 2019/9/19 10:06 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : SecurityApplication.java
 * @Software: IntelliJ IDEA
 */
@SpringBootApplication
@EnableEurekaClient
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
