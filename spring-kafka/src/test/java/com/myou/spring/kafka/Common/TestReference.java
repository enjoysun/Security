package com.myou.spring.kafka.Common;

/*
 * @Time    : 2020/2/13 9:15 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : TestReference.java
 * @Software: IntelliJ IDEA
 */
public class TestReference {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        int[] s = arr;
        System.out.println(s);
        System.out.println(arr);
        s = new int[]{4, 5};
        System.out.println(s);
    }
}
