package com.myou.spring.kafka.Common;

/*
 * @Time    : 2020/2/12 9:15 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Decorator.java
 * @Software: IntelliJ IDEA
 */

/**
 * @Author myoueva@gmail.com
 * @Description //装饰模式
 * @Date 9:16 PM 2020/2/12
 * @Param
 * @return
 **/
interface Animal {
    String call();
}

class Dog implements Animal {
    @Override
    public String call() {
        return "汪汪";
    }
}

class DogEnhance implements Animal {
    private Animal dog;

    public DogEnhance(Animal dog) {
        this.dog = dog;
    }

    @Override
    public String call() {
        return String.format("阿黄叫:%s", dog.call());
    }
}

public class Decorator {
    public static void main(String[] args) {
        DogEnhance dogEnhance = new DogEnhance(new Dog());
        String call = dogEnhance.call();
        System.out.println(call);
    }
}
