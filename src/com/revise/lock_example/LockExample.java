package com.revise.lock_example;

import com.revise.lock_example.scenario.DiySpinLock;
import com.revise.lock_example.scenario.MultipleCondition;
import com.revise.lock_example.scenario.ReadWrite;

import java.util.concurrent.TimeUnit;

public class LockExample {
    public void show() {
//        Reentrant reentrant = new Reentrant();
//        reentrant.featureDemo1();
//        reentrant.featureDemo2();
//        diySpinLockDemo();
//        ReadWrite readWrite = new ReadWrite();
//        readWrite.readFreeWriteMutual();
        MultipleCondition multipleCondition = new MultipleCondition();
        multipleCondition.show();
    }

    private void diySpinLockDemo() {
        printlnDivider();
        systemPrintln("自制自旋锁例子开始");
        DiySpinLock diySpinLock = new DiySpinLock();
        Thread t1 = new Thread(() -> {
            diySpinLock.lock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            diySpinLock.unlock();
        }, "t1");
        t1.start();

        Thread t2 = new Thread(
                () -> {
                    diySpinLock.lock();
                    diySpinLock.unlock();
                },
                "t2"
        );
        t2.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        systemPrintln("自制自旋锁例子结束");
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
