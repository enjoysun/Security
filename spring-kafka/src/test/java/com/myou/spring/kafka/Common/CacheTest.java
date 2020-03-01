package com.myou.spring.kafka.Common;

import com.myou.spring.kafka.Common.Service.AnnotationTest;
import com.myou.spring.kafka.Common.Service.DistributedLockable;
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
    @AnnotationTest(name = "tony")
//    @DistributedLockable(key = "jk", value = "2", timeout = 60, paras = "哦哦哦")
    public void cacheTest() throws Throwable {
//        boolean set = redisCliService.set("java:name", "you");
//        System.out.println(set);
        System.out.println("ok");
    }
}
