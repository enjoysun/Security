package com.myou.spring.cloud.service.controller;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/service")
public class demo {

    @GetMapping("/welcome")
    public String eurekaDemo(String message, @Value("${server.port}") int port) throws InterruptedException {
        log.info(String.format("端口%d进行重试", port));
//        Thread.sleep(1000);
        return String.format("%s:%d", message, port);
    }
}
