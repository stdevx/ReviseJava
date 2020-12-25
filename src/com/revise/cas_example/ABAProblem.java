package com.revise.cas_example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABAProblem {

    public void show() {
        abaProblemCreate();
        abaProblemSolve();
    }

    private void abaProblemCreate() {
        printlnDivider();
        systemPrintln("ABA问题演示开始");
        AtomicReference<Integer> atomicReference = new AtomicReference<>(1);
        Thread t1;
        Thread t2;
        t1 = new Thread(() -> {
            atomicReference.compareAndSet(1, 2);
            systemPrintln(Thread.currentThread().getName() +
                    " atomicReference当前值：" +
                    atomicReference.get()
            );
            atomicReference.compareAndSet(2, 1);
            systemPrintln(Thread.currentThread().getName() +
                    " atomicReference当前值：" +
                    atomicReference.get()
            );
        }, "t1");
        t1.start();
        t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicReference.compareAndSet(1, 3);
            systemPrintln(Thread.currentThread().getName() +
                    " atomicReference当前值：" +
                    atomicReference.get()
            );
        }, "t2");
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        systemPrintln("ABA问题演示结束");
    }

    private void abaProblemSolve() {
        printlnDivider();
        systemPrintln("ABA问题解决开始（AtomicStampedReference）");
        AtomicStampedReference<Integer> atomicStampedReference =
                new AtomicStampedReference<>(1, 1);
        Thread t1;
        Thread t2;
        t1 = new Thread(() -> {
            int currentStamp = atomicStampedReference.getStamp();
            systemPrintln(Thread.currentThread().getName() +
                    " stamp初始值：" +
                    currentStamp
            );
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(
                    1,
                    2,
                    currentStamp,
                    currentStamp + 1
            );
            systemPrintln(Thread.currentThread().getName() +
                    " 第一次修改后，stamp当前值：" +
                    atomicStampedReference.getStamp()
            );
            atomicStampedReference.compareAndSet(
                    2,
                    1,
                    atomicStampedReference.getStamp(),
                    atomicStampedReference.getStamp() + 1
            );
            systemPrintln(Thread.currentThread().getName() +
                    " 第二次修改后，stamp当前值：" +
                    atomicStampedReference.getStamp()
            );
        }, "t1");
        t1.start();
        t2 = new Thread(() -> {
            int currentStamp = atomicStampedReference.getStamp();
            systemPrintln(Thread.currentThread().getName() +
                    " stamp初始值：" +
                    atomicStampedReference.getStamp()
            );
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(
                    1,
                    3,
                    currentStamp,
                    currentStamp + 1
            );
            systemPrintln(Thread.currentThread().getName() +
                    " 第三次修改后，修改结果：" + result +
                    "，atomicStampedReference当前值：" +
                    atomicStampedReference.getReference() +
                    "，stamp当前值：" +
                    atomicStampedReference.getStamp()
            );
        }, "t2");
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        systemPrintln("ABA问题解决结束（AtomicStampedReference）");
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
