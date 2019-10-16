package com.myou.gateway.security.oauth.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * @Time    : 2019/10/16 4:15 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : CustomLoginAndAuthor.java
 * @Software: IntelliJ IDEA
 */
@Controller
public class CustomLogin {
    @GetMapping("/auth/login")
    public String loginPage(Model model) {
        model.addAttribute("loginProcessUrl", "/auth/authorize");
        return "login";
    }
}
