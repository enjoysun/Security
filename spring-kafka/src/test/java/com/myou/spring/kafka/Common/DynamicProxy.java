package com.myou.spring.kafka.Common;

/*
 * @Time    : 2020/2/12 9:49 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : DynamicProxy.java
 * @Software: IntelliJ IDEA
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author myoueva@gmail.com
 * @Description //动态代理
 * @Date 9:49 PM 2020/2/12
 * @Param
 * @return
 **/
interface Pen {
    String color(String color);
}

class Blue implements Pen {
    @Override
    public String color(String color) {
        return String.format("蓝笔:%s", color);
    }
}

class DynamicPattern implements InvocationHandler {

    private Object aClass;

    public DynamicPattern(Class<?> aClass) throws IllegalAccessException, InstantiationException {
        this.aClass = aClass.newInstance();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("增强前");
        Object object = method.invoke(aClass, args);
        System.out.println("增强后");
        return object;
    }
}

public class DynamicProxy {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Pen instance = (Pen) Proxy.newProxyInstance(Pen.class.getClassLoader(), new Class[]{Pen.class}, new DynamicPattern(Blue.class));
        System.out.println(instance.color("蓝色"));
    }
}
