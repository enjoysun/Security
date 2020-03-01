package com.myou.spring.kafka.Common.Service;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLockable {
    String key() default "java:lock";

    String value() default "1";

    long timeout() default 60;

    String paras() default "参数";
}
