package com.revise.cb_example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    public void show() {
        printlnDivider();
        systemPrintln("CyclicBarrier例子开始");
        int total = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(
                total,
                () -> systemPrintln(Thread.currentThread().getName() + "\t所有子线程已开始")
        );

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    systemPrintln(Thread.currentThread().getName() + "\t开始");
                    cyclicBarrier.await();
                    systemPrintln(Thread.currentThread().getName() + "\t结束");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "t" + i).start();
        }

        // 仅用于等待所有子线程结束
        while (Thread.activeCount() > 2) {

        }
        systemPrintln("CyclicBarrier例子结束");
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
