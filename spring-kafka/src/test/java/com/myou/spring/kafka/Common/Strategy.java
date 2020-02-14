package com.myou.spring.kafka.Common;

/*
 * @Time    : 2020/2/12 9:04 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Strategy.java
 * @Software: IntelliJ IDEA
 */
/**
 * @Author myoueva@gmail.com
 * @Description //策略设计模式
 * @Date 9:15 PM 2020/2/12
 * @Param
 * @return
 **/
interface Algorithm {
    int operator(int num1, int num2);
}

class Sum implements Algorithm {
    @Override
    public int operator(int num1, int num2) {
        return num1 + num2;
    }
}

class Avg implements Algorithm {
    @Override
    public int operator(int num1, int num2) {
        return (num1 + num2) / 2;
    }
}

class Pattern implements Algorithm {
    private Algorithm algorithm;

    public Pattern(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public int operator(int num1, int num2) {
        return algorithm.operator(num1, num2);
    }
}

public class Strategy {

    public static void main(String[] args) {
        Pattern patternSum = new Pattern(new Sum());
        Pattern patternAvg = new Pattern(new Avg());
        System.out.println(patternSum.operator(1, 2));
        System.out.println(patternAvg.operator(2, 2));
    }
}
