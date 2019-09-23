package com.myou.service.security.Common.Config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/*
 * @Time    : 2019/9/20 3:29 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RedisLettuceFactory.java
 * @Software: IntelliJ IDEA
 */
@Configuration
public class RedisLettuceFactory {
    @Data
    @Configuration
    @PropertySource("classpath:redis.properties")
    @ConfigurationProperties(prefix = "redis1")
    public class FactoryModel {
        private String host;
        private int port;
        private int database;
        private String password;

        public RedisStandaloneConfiguration redisStandaloneConfiguration() {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
            redisStandaloneConfiguration.setPassword(password);
            redisStandaloneConfiguration.setDatabase(database);
            return redisStandaloneConfiguration;
        }
    }

    @Autowired
    private FactoryModel factoryModel;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory(factoryModel.redisStandaloneConfiguration());
    }
}
