package com.myou.spring.cloud.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*
 * @Time    : 2019/9/11 5:07 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : ServiceApplication.java
 * @Software: IntelliJ IDEA
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
