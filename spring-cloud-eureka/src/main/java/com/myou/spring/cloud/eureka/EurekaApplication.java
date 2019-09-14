package com.myou.spring.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/*
 * @Time    : 2019/9/11 4:26 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : EurekaApplication.java
 * @Software: IntelliJ IDEA
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
