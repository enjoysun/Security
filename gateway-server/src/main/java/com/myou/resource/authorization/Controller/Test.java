package com.myou.resource.authorization.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Time    : 2019/10/21 4:36 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Test.java
 * @Software: IntelliJ IDEA
 */
@RestController
public class Test {
    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
