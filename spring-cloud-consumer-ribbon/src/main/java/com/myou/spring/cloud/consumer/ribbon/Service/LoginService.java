package com.myou.spring.cloud.consumer.ribbon.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
 * @Time    : 2019/9/14 10:55 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : loginService.java
 * @Software: IntelliJ IDEA
 */
@Service
public class LoginService {
    @Autowired
    private RestTemplate restTemplate;

    public String login(String message) {
        return restTemplate.getForObject(String.format("http://SERVICE-CLIENT/service/welcome?message=%s", message),
                String.class
        );
    }
}
