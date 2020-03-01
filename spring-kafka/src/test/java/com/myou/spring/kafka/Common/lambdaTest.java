package com.myou.spring.kafka.Common;

/*
 * @Time    : 2020/2/16 8:33 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : lambdaTest.java
 * @Software: IntelliJ IDEA
 */
@FunctionalInterface
interface AcquireTeatHandler<T> {
    T acquire(T t) throws Throwable;
}


public class lambdaTest {
    static class Dos {
        static <T> T Run(AcquireTeatHandler<T> acquireHandler, T t) throws Throwable {
            System.out.println("开始执行函数");
            return acquireHandler.acquire(t);
        }
    }

    public static void main(String[] args) throws Throwable {
        String run = Dos.Run((String string) -> {
            return string;
        }, "爱字谜得");
        System.out.println("运行结束");
    }
}
