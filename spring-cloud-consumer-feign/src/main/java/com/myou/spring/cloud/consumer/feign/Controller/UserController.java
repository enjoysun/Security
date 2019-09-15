package com.myou.spring.cloud.consumer.feign.Controller;

import com.myou.spring.cloud.consumer.feign.Service.Interface.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Time    : 2019/9/15 7:03 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : UserController.java
 * @Software: IntelliJ IDEA
 */
@RestController
public class UserController {
    @Autowired
    private Login login;


    @GetMapping("/login")
    public String Login(String msg){
        System.out.println("control"+msg);
        return login.login(msg);
    }
}
