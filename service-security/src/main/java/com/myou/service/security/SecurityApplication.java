package com.myou.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.HashMap;

/*
 * @Time    : 2019/9/19 10:06 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : SecurityApplication.java
 * @Software: IntelliJ IDEA
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.myou.service.security.Mapper")
public class SecurityApplication implements ApplicationRunner{
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("pass="+bCryptPasswordEncoder.encode("12345"));
        System.out.println("secret="+bCryptPasswordEncoder.encode("app_secret"));
    }
}
