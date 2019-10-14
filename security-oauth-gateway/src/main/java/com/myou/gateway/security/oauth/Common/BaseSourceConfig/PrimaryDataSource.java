package com.myou.gateway.security.oauth.Common.BaseSourceConfig;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/*
 * @Time    : 2019/10/14 11:35 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : PrimaryDataSource.java
 * @Software: IntelliJ IDEA
 */
@Configuration
public class PrimaryDataSource {
    @Bean
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
