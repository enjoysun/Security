package com.myou.spring.kafka.Common;

import com.myou.spring.kafka.Common.Service.CacheLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;


/*
 * @Time    : 2020/2/13 9:57 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RedisLock.java
 * @Software: IntelliJ IDEA
 */

@Component
public class RedisLock implements CacheLock {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public <T> T tryLock(String key, String value, long timeout, AcquireHandler acquireHandler) throws Throwable {
        Boolean execute = (Boolean) redisTemplate.execute((RedisConnection redisConnection) -> {
            RedisStringCommands redisStringCommands = redisConnection.stringCommands();
            Boolean aBoolean = redisStringCommands.set(
                    key.getBytes(),
                    value.getBytes(),
                    Expiration.seconds(timeout),
                    RedisStringCommands.SetOption.SET_IF_ABSENT);
            return aBoolean;
        });
        if (execute) {
            acquireHandler.handler();
        }
        return null;
    }
}
