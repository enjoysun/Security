package com.myou.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/*
 * @Time    : 2019/10/23 4:31 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : GatewayApplication.java
 * @Software: IntelliJ IDEA
 */
@SpringBootApplication
@RestController
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * RouteLocatorBuilder:
     * 1.创建路由
     * 2.制定匹配规则
     * 3.添加过滤器至路由
     *
     * 参考文献:https://spring.io/guides/gs/gateway/(quick started&& how to write test(applicationTest))
     */
    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes().route(
                p -> p.path("/service1/**")
                        .filters(f -> f.addRequestHeader("hello", "word"))
                        .uri("http://localhost:8091")
        ).route(p -> p
                .path("/service/**")
                .filters(f -> f.hystrix(c -> c.setName("myHystrix").setFallbackUri("forward:/fallback"))) //限流器
                .uri("http://localhost:8091")
        )
                .build();
    }

    @GetMapping("/fallback")
    public Mono<String> hy() {
        return Mono.just("丢失服务");
    }
}
