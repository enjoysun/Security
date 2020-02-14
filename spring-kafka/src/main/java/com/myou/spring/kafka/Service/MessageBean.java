package com.myou.spring.kafka.Service;

/*
 * @Time    : 2020/1/31 5:57 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageBean.java
 * @Software: IntelliJ IDEA
 */
public class MessageBean {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    private String message;
    private String toUser;

    private void init() {
        System.out.println("init");
        this.message = "default";
        this.toUser = "default";
    }

    private void destroy() {
        System.out.println("destroy");
    }
}
