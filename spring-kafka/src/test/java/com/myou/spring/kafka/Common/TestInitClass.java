package com.myou.spring.kafka.Common;

import java.util.concurrent.ArrayBlockingQueue;

/*
 * @Time    : 2020/2/6 5:05 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : TestInitClass.java
 * @Software: IntelliJ IDEA
 */
public class TestInitClass {

    public static void main(String[] args) {
        String binaryString = Integer.toBinaryString(47);
        System.out.println(binaryString);
    }

    private int num = 2;

    private void cry() {
        System.out.println("miao");
    }

    private static class Dog {
        private int num = 4;

        public void cry() {
//            System.out.println(TestInitClass.this.num);
            System.out.println("wang");
        }
    }
}
