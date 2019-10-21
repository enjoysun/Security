package com.myou.gateway.security.oauth.Common.BaseSourceConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/*
 * @Time    : 2019/10/14 11:37 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : PrimaryLettuceFactory.java
 * @Software: IntelliJ IDEA
 */
/**
 * PropertySource加载属性源:
 * 1.PropertySource+Environment
 * 2.PropertySource+@Value
 * */
@Configuration
@PropertySource("classpath:redis.properties")
@ConfigurationProperties(prefix = "redis1")
public class PrimaryLettuceFactory {

    @Autowired
    private Environment environment;

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                environment.getProperty("redis1.host"),
                environment.getProperty("redis1.port", Integer.class));
        redisStandaloneConfiguration.setDatabase(environment.getProperty("redis1.database", Integer.class));
        redisStandaloneConfiguration.setPassword(environment.getProperty("redis1.password"));
        return redisStandaloneConfiguration;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory(redisStandaloneConfiguration());
    }

}
