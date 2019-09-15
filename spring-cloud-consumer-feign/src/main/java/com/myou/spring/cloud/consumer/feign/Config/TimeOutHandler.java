package com.myou.spring.cloud.consumer.feign.Config;

import com.myou.spring.cloud.consumer.feign.Service.Interface.Login;
import org.springframework.stereotype.Component;

/*
 * @Time    : 2019/9/15 10:50 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : TimeOutHandler.java
 * @Software: IntelliJ IDEA
 */
@Component
public class TimeOutHandler implements Login {
    @Override
    public String login(String message) {
        return String.format("方法超时:%s", message);
    }
}
