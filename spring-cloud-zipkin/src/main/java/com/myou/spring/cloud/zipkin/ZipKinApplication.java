package com.myou.spring.cloud.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.internal.EnableZipkinServer;

/*
 * @Time    : 2019/9/18 5:07 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : ZipKinApplication.java
 * @Software: IntelliJ IDEA
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZipkinServer
public class ZipKinApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipKinApplication.class, args);
    }
}
