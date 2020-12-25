package com.revise.lock_example.scenario;

// 精确唤醒
public class MultipleCondition {

    public void show() {
        systemPrintln("多个Condition控制线程唤醒例子 开始");

        SharedResource sharedResource = new SharedResource();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                sharedResource.printThree();
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                sharedResource.printFour();
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                sharedResource.printFive();
            }
        }, "CC").start();

        while (Thread.activeCount() > 2) {

        }
        systemPrintln("多个Condition控制线程唤醒例子 结束");
    }

    private void systemPrintln(String msg) {
        System.out.println(msg);
    }

}
