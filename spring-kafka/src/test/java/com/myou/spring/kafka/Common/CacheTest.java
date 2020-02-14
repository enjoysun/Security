package com.myou.spring.kafka.Common;

import com.myou.spring.kafka.Common.Service.RedisCliService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/*
 * @Time    : 2020/2/12 8:12 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : CacheTest.java
 * @Software: IntelliJ IDEA
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {
    @Autowired
    private RedisCliService redisCliService;

    @Autowired
    private RedisLock redisLock;

    @Test
    public void cacheTest() {
//        boolean set = redisCliService.set("java:name", "you");
//        System.out.println(set);
        boolean trylock = redisLock.trylock("java:lock1", "1", 60);
        System.out.println(trylock);
    }
}
