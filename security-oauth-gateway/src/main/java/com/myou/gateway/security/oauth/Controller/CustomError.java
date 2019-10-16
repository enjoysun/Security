package com.myou.gateway.security.oauth.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Time    : 2019/10/16 11:37 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : CustomError.java
 * @Software: IntelliJ IDEA
 */
@RestController
public class CustomError implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String handlerError() {
        return "自定义错误处理";
    }
}
