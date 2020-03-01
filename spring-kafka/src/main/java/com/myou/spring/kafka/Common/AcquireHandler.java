package com.myou.spring.kafka.Common;

/*
 * @Time    : 2020/2/16 9:13 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : AcquireHandler.java
 * @Software: IntelliJ IDEA
 */
@FunctionalInterface
public interface AcquireHandler {
    void handler() throws Throwable;
}
