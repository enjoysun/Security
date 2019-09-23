package com.myou.service.security.Common.Config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/*
 * @Time    : 2019/9/20 5:04 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : PrimaryDataSource.java
 * @Software: IntelliJ IDEA
 */
@Primary
@Configuration
public class PrimaryDataSource {
    @Bean
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
