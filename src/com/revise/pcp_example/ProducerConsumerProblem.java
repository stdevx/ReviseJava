package com.revise.pcp_example;

import com.revise.pcp_example.blocking_queue.SharedData;
import com.revise.pcp_example.tradition.Goods;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ProducerConsumerProblem {
    public void show() {
        printlnDivider();
        systemPrintln("生产者消费者问题 开始");

//        tradition();
        blockingQueueWay();

        systemPrintln("生产者消费者问题 结束");
    }

    private void blockingQueueWay() {
        systemPrintln("阻塞队列方法，容量=3 开始");
        int capacity = 3;
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(capacity);
        SharedData<String> sharedData = new SharedData<>(blockingQueue);

        new Thread(() -> {
            systemPrintln(Thread.currentThread().getName() + "\t启动");
            try {
                String data = String.valueOf(10);
                sharedData.produce(data);
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Producer").start();

        new Thread(() -> {
            systemPrintln(Thread.currentThread().getName() + "\t启动");
            try {
                sharedData.consume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Consumer").start();

        systemPrintln(Thread.currentThread().getName() + "\t进入睡眠5秒");
        try {
            TimeUnit.SECONDS.sleep(5);
            systemPrintln(Thread.currentThread().getName() + "\t结束睡眠");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sharedData.stop();
        systemPrintln(Thread.currentThread().getName() + "\t结束生产消费");

        while (Thread.activeCount() > 2) {

        }
        systemPrintln("阻塞队列方法，容量=3 结束");
    }

    private void tradition() {
        systemPrintln("传统方法 开始");
        Goods goods = new Goods();

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    goods.increment();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    goods.decrement();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();

        while (Thread.activeCount() > 2) {

        }
        systemPrintln("传统方法 结束");
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
