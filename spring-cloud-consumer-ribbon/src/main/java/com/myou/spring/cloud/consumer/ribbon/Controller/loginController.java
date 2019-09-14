package com.myou.spring.cloud.consumer.ribbon.Controller;

import com.myou.spring.cloud.consumer.ribbon.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Time    : 2019/9/14 10:59 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : loginController.java
 * @Software: IntelliJ IDEA
 */
@RestController
@RequestMapping("/login")
public class loginController {
    @Autowired
    private LoginService loginService;

    @GetMapping()
    public String login(String msg) {
        return loginService.login(msg);
    }
}
