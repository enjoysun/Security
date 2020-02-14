package com.myou.spring.cloud.zipkin;

import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.zip.CRC32;

/*
 * @Time    : 2019/11/14 5:07 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Test.java
 * @Software: IntelliJ IDEA
 */
public class Test {
    public volatile int num = 0;
    private static final String URL="http://78re52.com1.z0.glb.clouddn.com/resource/flower.jpg";

    public synchronized void add() {
        int ids = 0;
        while (ids++ < 10000) {
            num++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        final Test test = new Test();
//        Thread thread = new Thread(() -> {
//            test.add();
//        });
//        Thread thread1 = new Thread(() -> {
//            test.add();
//        });
//        thread.start();
//        thread1.start();
//        thread.join();
//        thread1.join();
//        System.out.println(test.num);
//        CRC32
        System.out.println(URL.getBytes(Charset.forName("UTF-8")));
    }
}
