package com.myou.spring.cloud.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Time    : 2019/9/11 5:15 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : demo.java
 * @Software: IntelliJ IDEA
 */
@RestController
@RequestMapping("/eureka")
public class demo {

    @GetMapping("/welcome")
    public String eurekaDemo(String message, @Value("${server.port}") int port) {
        return String.format("%s:%d", message, port);
    }
}
