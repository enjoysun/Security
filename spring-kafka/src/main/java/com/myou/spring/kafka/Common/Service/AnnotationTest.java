package com.myou.spring.kafka.Common.Service;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AnnotationTest {
    String name() default "jack";
}
