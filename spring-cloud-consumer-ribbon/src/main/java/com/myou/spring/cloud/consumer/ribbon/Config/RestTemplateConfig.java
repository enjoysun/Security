package com.myou.spring.cloud.consumer.ribbon.Config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/*
 * @Time    : 2019/9/14 10:53 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RestTemplateConfig.java
 * @Software: IntelliJ IDEA
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
