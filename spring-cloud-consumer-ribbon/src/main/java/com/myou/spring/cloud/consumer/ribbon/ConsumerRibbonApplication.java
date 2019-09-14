package com.myou.spring.cloud.consumer.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
 * @Time    : 2019/9/14 10:43 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : ConsumerRibbonApplication.java
 * @Software: IntelliJ IDEA
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerRibbonApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerRibbonApplication.class, args);
    }
}
