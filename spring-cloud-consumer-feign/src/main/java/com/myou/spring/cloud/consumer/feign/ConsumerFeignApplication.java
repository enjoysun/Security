package com.myou.spring.cloud.consumer.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
 * @Time    : 2019/9/15 6:40 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : ConsumerFeignApplication.java
 * @Software: IntelliJ IDEA
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConsumerFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerFeignApplication.class, args);
    }
}
