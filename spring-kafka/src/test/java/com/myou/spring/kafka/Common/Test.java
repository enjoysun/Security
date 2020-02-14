package com.myou.spring.kafka.Common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * @Time    : 2019/11/27 4:46 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Test.java
 * @Software: IntelliJ IDEA
 */
public class Test {
    private static int sum = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    sum += 1;
                }
            }
        });
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    sum += 1;
                }
            }
        });
        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
        System.out.println(sum);
    }
}
