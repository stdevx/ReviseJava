package com.revise.volatile_example;

import com.revise.volatile_example.scenario.*;

import java.util.concurrent.TimeUnit;

/*
 * volatile
 * 1. 内存可见性
 * 2. 不保证原子性
 * 3. 禁止指令重排 // memory barrier CPU指令（保证有序性，强制刷出CPU缓存数据）
 * */
public class VolatileExample {

    public void show() {
        visibility();
        notAtomic();
        atomicFixed();
        guaranteeOrder();
    }

    public void visibility() {
        printlnDivider();
        System.out.println("volatile内存可见性例子开始");

        String threadName = Thread.currentThread().getName();

        Merchandise merchandise = new Merchandise("Apple", 100);
        MyThreadGroup myThreadGroup = new MyThreadGroup();
        Bidder bidderA = new Bidder(myThreadGroup,"Tony", merchandise);

        bidderA.start();

        try {
            bidderA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // waiting other thread to change prices
        systemPrintln(threadName + "\tstart waiting prices changed.");
        while (merchandise.getPrices() == 100) {

        }
        systemPrintln(threadName + "\tfinished prices changed.");

        systemPrintln(threadName + "\tvolatile内存可见性：");
        systemPrintln(threadName + "\tmerchandise.prices=" + merchandise.getPrices());
        System.out.println("volatile内存可见性例子结束");
    }

    public void notAtomic() {
        printlnDivider();
        System.out.println("volatile不保证原子性例子开始");

        String threadName = Thread.currentThread().getName();

        Num num = new Num(0);
        MyThreadGroup myThreadGroup = new MyThreadGroup();
        Adder adderA = new Adder(myThreadGroup,"Adder A", num);
        Adder adderB = new Adder(myThreadGroup,"Adder B", num);
        Adder adderC = new Adder(myThreadGroup,"Adder C", num);

        adderA.start();
        adderB.start();
        adderC.start();

        try {
            adderA.join();
            adderB.join();
            adderC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        systemPrintln(threadName + "\tvolatile不保证原子性（times正确结果应为30000）");
        systemPrintln(threadName + "\tnum.times=" + num.getTimes());
        System.out.println("volatile不保证原子性例子结束");
    }

    public void atomicFixed() {
        printlnDivider();
        System.out.println("原子性保证例子开始（AtomicInteger的使用）");

        String threadName = Thread.currentThread().getName();

        FixedNum fixedNum = new FixedNum(0);
        MyThreadGroup myThreadGroup = new MyThreadGroup();
        FixedAdder fixedAdderA = new FixedAdder(myThreadGroup,"fixedAdder A", fixedNum);
        FixedAdder fixedAdderB = new FixedAdder(myThreadGroup,"fixedAdder B", fixedNum);
        FixedAdder fixedAdderC = new FixedAdder(myThreadGroup,"fixedAdder C", fixedNum);

        fixedAdderA.start();
        fixedAdderB.start();
        fixedAdderC.start();

        try {
            fixedAdderA.join();
            fixedAdderB.join();
            fixedAdderC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        systemPrintln(threadName + "\t修复原子性（times为AtomicInteger）");
        systemPrintln(threadName + "\tfixedNum.times=" + fixedNum.getTimes());
        System.out.println("原子性保证例子结束（AtomicInteger的使用）");
    }

    public void guaranteeOrder() {
        printlnDivider();
        System.out.println("禁止重排特性例子开始（volatile在多线程中单例模式的运用）");
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\tstarted.");
                // 构造器有输出语句
                UniqueApple apple = UniqueApple.getInstance();
                System.out.println(Thread.currentThread().getName() + "\tfinished.");
            }).start();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("禁止重排特性例子结束（volatile在多线程中单例模式的运用）");
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
