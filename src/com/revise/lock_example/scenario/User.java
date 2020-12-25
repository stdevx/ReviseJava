package com.revise.lock_example.scenario;

public class User {
    public synchronized void getPhone() {
        System.out.println(Thread.currentThread().getName() + "\tgetPhone");
        sendMsg();
    }
    public synchronized void sendMsg() {
        System.out.println(Thread.currentThread().getName() + "\tsendMsg");
    }
}
