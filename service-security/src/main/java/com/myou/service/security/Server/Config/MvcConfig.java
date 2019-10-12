package com.myou.service.security.Server.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * @Time    : 2019/10/12 4:59 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MvcConfig.java
 * @Software: IntelliJ IDEA
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/confirm_access").setViewName("oauth_approval");
    }
}
