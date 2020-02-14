package com.myou.spring.kafka.Common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/*
 * @Time    : 2020/2/11 10:12 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RedisProperties.java
 * @Software: IntelliJ IDEA
 */
@Data
@Component
@PropertySource("classpath:redis.properties")
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    private Integer database;
    private String host;
    private Integer port;
    private Boolean ssl;
    private String password;
    private Long connTimeout;
    private Integer maxActive;
    private Integer maxIdle;
    private Integer minIdle;
    private Integer maxWait;
}
