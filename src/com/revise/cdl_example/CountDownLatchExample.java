package com.revise.cdl_example;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public void show() {
        printlnDivider();
        systemPrintln("CountDownLatch例子开始");
        systemPrintln(Thread.currentThread().getName() + "\t开始等待所有子线程");
        int total = 5;
        CountDownLatch countDownLatch = new CountDownLatch(total);

        for (int i = 1; i <= total; i++) {
            new Thread(() -> {
                systemPrintln(Thread.currentThread().getName() + "\t开始");
                countDownLatch.countDown();
                systemPrintln(Thread.currentThread().getName() + "\t结束");
            }, "t" + i).start();
        }

        try {
            countDownLatch.await();
            systemPrintln(Thread.currentThread().getName() + "\t等待所有子线程完成");
            systemPrintln("CountDownLatch例子结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
