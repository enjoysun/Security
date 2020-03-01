package com.myou.spring.kafka.Common;

import com.myou.spring.kafka.Common.Service.DistributedLockable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * @Time    : 2020/2/16 10:10 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : DistributedLockableAspect.java
 * @Software: IntelliJ IDEA
 */
@Component
@Aspect
public class DistributedLockableAspect {
    @Pointcut(value = "@annotation(com.myou.spring.kafka.Common.Service.DistributedLockable)")
    public void distributedLockable() {
    }

    @Autowired
    private RedisLock redisLock;

    @Around(value = "distributedLockable() && @annotation(lockable)")
    public Object tryLock(ProceedingJoinPoint proceedingJoinPoint, DistributedLockable lockable) throws Throwable {
        return redisLock.tryLock(lockable.key(), lockable.value(), lockable.timeout(), () -> {
            proceedingJoinPoint.proceed();
        });
//        return proceedingJoinPoint.proceed();
    }
}
