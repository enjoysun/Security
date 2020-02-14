package com.myou.spring.kafka;


import com.myou.spring.kafka.Service.MessageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/*
 * @Time    : 2019/11/21 3:49 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : KafkaApplication.java
 * @Software: IntelliJ IDEA
 */
@SpringBootApplication
public class KafkaApplication {
//    @Override
//    public void run(String... args) throws Exception {
//        Class<?> aClass = Class.forName("sun.misc.Unsafe");
//        Field theUnsafe = aClass.getDeclaredField("theUnsafe");
//        theUnsafe.setAccessible(true);
//        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
//        System.out.println("结果:" + messageBean.getMessage() + ":User:" + messageBean.getToUser());
//        Field message = messageBean.getClass().getDeclaredField("message");
//        long l = unsafe.objectFieldOffset(message);
//        System.out.println(l);
//    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

//    @Autowired
//    private MessageBean messageBean;
}
