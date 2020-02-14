package com.myou.spring.kafka.Common;

/*
 * @Time    : 2020/2/12 9:22 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Proxy.java
 * @Software: IntelliJ IDEA
 */

/**
 * @Author myoueva@gmail.com
 * @Description //静态代理模式
 * @Date 9:22 PM 2020/2/12
 * @Param
 * @return
 **/
interface Car {
    String style();
}

class Mazda implements Car {
    @Override
    public String style() {
        return "阿特兹";
    }
}

class ProxyMazda implements Car {
    private Car mazda;

    public ProxyMazda() {
        this.mazda = new Mazda();
    }

    @Override
    public String style() {
        return String.format("我的%s", mazda.style());
    }
}

public class Proxy {
    public static void main(String[] args) {
        ProxyMazda mazda = new ProxyMazda();
        System.out.println(mazda.style());
    }
}
