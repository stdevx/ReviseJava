package com.revise.lock_example.scenario;

import java.util.concurrent.locks.ReentrantLock;

/*
* ReentrantLock（可重入锁，即递归锁）
* */
public class Reentrant {

    public void featureDemo1() {
        printlnDivider();
        systemPrintln("可重入演示例子1开始（synchronized修饰）");
        User user = new User();
        Thread t1 = new Thread(user::getPhone, "t1");
        Thread t2 = new Thread(user::getPhone, "t2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        systemPrintln("可重入演示例子1结束（synchronized修饰）");
    }

    public void featureDemo2() {
        printlnDivider();
        systemPrintln("可重入演示例子2开始（ReentrantLock）");

        ReentrantLock unfair = new ReentrantLock();

        Thread t3 = new Thread(() -> {
            numOperation(unfair);
        }, "t3");
        Thread t4 = new Thread(() -> {
            numOperation(unfair);
        }, "t4");

        t3.start();
        t4.start();

        try {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        systemPrintln("可重入演示例子2结束（ReentrantLock）");
    }

    private void numOperation(ReentrantLock unfair) {
        unfair.lock();
        System.out.println(Thread.currentThread().getName() + "\tgetNum");
        {
            unfair.lock();
            System.out.println(Thread.currentThread().getName() + "\tsendNum");
            unfair.unlock();
        }
        unfair.unlock();
    }

    private void systemPrintln(String msg) {
        System.out.println(msg);
    }

    private void printlnDivider() {
        for (int i = 0; i < 50; i++) {
            System.out.print("-");
        }
        systemPrintln("");
    }
}
