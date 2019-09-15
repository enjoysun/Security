package com.myou.spring.cloud.consumer.feign.Service.Interface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * @Time    : 2019/9/15 6:46 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Login.java
 * @Software: IntelliJ IDEA
 */
// 注解需要声明调用eureka注册的服务名
@FeignClient("SERVICE-CLIENT")
public interface Login {

    // feign提供了与mvc的声明式接口耦合，但是调用时需要声明参数获取方式和rest方法类型，参数名需与服务暴露的名称保持一致
    @RequestMapping(value = "/service/welcome", method = RequestMethod.GET)
    String login(@RequestParam("message") String message);
}
