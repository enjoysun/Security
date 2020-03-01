package com.myou.spring.kafka.Common;

import com.myou.spring.kafka.Common.Service.AnnotationTest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/*
 * @Time    : 2020/2/17 9:29 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : AnnotationTestAspect.java
 * @Software: IntelliJ IDEA
 */
@Component
@Aspect
public class AnnotationTestAspect {
    @Pointcut("@annotation(com.myou.spring.kafka.Common.Service.AnnotationTest)")
    private void annotationTestable() {
    }

    @Around("annotationTestable()")
    public Object doAroundTestCheck(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("执行前");
        Object proceed = pjp.proceed();
        System.out.println("执行后");
        return proceed;
    }

}
