package com.myou.spring.cloud.zuul.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Time    : 2019/9/17 5:44 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : ZuulInnerController.java
 * @Software: IntelliJ IDEA
 */
@RestController
public class ZuulInnerController {

    @RequestMapping(value = "/Inner", method = RequestMethod.GET)
    public String test(String msg) {
        return String.format("inner:%s", msg);
    }
}
