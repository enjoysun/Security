package com.myou.spring.kafka.Common.Service;

import com.myou.spring.kafka.Common.AcquireHandler;

/*
 * @Time    : 2020/2/16 9:22 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : CacheLock.java
 * @Software: IntelliJ IDEA
 */
public interface CacheLock {
    <T> T tryLock(String key, String value, long timeout, AcquireHandler acquireHandler) throws Throwable;
}
