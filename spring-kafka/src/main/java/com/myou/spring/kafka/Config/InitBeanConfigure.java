package com.myou.spring.kafka.Config;

import com.myou.spring.kafka.Service.MessageBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @Time    : 2020/1/31 6:01 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : InitBeanConfigure.java
 * @Software: IntelliJ IDEA
 */
@Configuration
public class InitBeanConfigure {
    @Bean(initMethod = "init")
    public MessageBean messageBean() {
        return new MessageBean();
    }
}
