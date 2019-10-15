package com.myou.gateway.security.oauth.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Time    : 2019/10/15 3:05 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Test.java
 * @Software: IntelliJ IDEA
 */
@RestController
public class Test {
    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        //for debug
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id :" + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        //for debug
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "order id :" + id;
    }
}
