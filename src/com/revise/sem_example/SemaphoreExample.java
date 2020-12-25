package com.revise.sem_example;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample {
    public void show() {
        printlnDivider();
        systemPrintln("Semaphore例子开始");

        int total = 3;
        Semaphore semaphore = new Semaphore(total);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    systemPrintln(Thread.currentThread().getName() + "\tgot permit.");
                    TimeUnit.SECONDS.sleep(1);
                    semaphore.release();
                    systemPrintln(Thread.currentThread().getName() + "\trelease permit.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "t" + i).start();
        }

        // 仅用于等待所有子线程结束
        while (Thread.activeCount() > 2) {

        }
        systemPrintln("Semaphore例子结束");
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
