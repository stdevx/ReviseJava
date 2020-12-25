package com.revise.blocking_queue_example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueExample {

    public void show() {
        printlnDivider();
        systemPrintln("阻塞队列例子开始");

//        api();
        synchronous();

        // 仅用于等待所有子线程结束
        while (Thread.activeCount() > 2) {

        }
        systemPrintln("阻塞队列例子结束");
    }

    private void api() {
        addRemoveElement();
        offerPullPeek();
        putTake();
        offerPollTimeout();
        synchronous();
    }

    private void addRemoveElement() {
        systemPrintln("addAndTake api，队列容量为3 开始");
        int capacity = 3;
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(capacity);
        blockingQueue.add("one");
        blockingQueue.add("two");
        blockingQueue.add("three");
        try {
            blockingQueue.add("four");
        } catch (Exception e) {
            systemPrintln("add 队列已满异常\t" + e.toString());
        }

        systemPrintln("element() 查看头部\t" + blockingQueue.element());

        blockingQueue.remove();
        blockingQueue.remove();
        blockingQueue.remove();

        try {
            blockingQueue.remove();
        } catch (Exception e) {
            systemPrintln("remove() 队列已空异常\t" + e.toString());
        }
        systemPrintln("addAndTake api，队列容量为3 结束");
    }

    private void offerPullPeek() {
        systemPrintln("addAndTake api，队列容量为3 开始");
        int capacity = 3;
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(capacity);
        systemPrintln("one 添加结果\t"+ blockingQueue.offer("one"));
        systemPrintln("two 添加结果\t"+ blockingQueue.offer("two"));
        systemPrintln("three 添加结果\t"+ blockingQueue.offer("three"));
        systemPrintln("four 添加结果\t"+ blockingQueue.offer("four"));

        systemPrintln("peek() 查看头部\t" + blockingQueue.peek());

        systemPrintln("删除头部结果\t" + blockingQueue.poll());
        systemPrintln("删除头部结果\t" + blockingQueue.poll());
        systemPrintln("删除头部结果\t" + blockingQueue.poll());
        systemPrintln("删除头部结果\t" + blockingQueue.poll());

        systemPrintln("addAndTake api，队列容量为3 结束");
    }

    private void putTake() {
        systemPrintln("putTake api，队列容量为2 开始");
        int capacity = 2;
        BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(capacity);
        new Thread(() -> {
            try {
                systemPrintln(Thread.currentThread().getName() + "\t开始添加one");
                arrayBlockingQueue.put("one");
                systemPrintln(Thread.currentThread().getName() + "\t完成添加one");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                systemPrintln(Thread.currentThread().getName() + "\t开始添加two");
                arrayBlockingQueue.put("two");
                systemPrintln(Thread.currentThread().getName() + "\t完成添加two");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
        new Thread(() -> {
            try {
                systemPrintln(Thread.currentThread().getName() + "\t开始添加three");
                arrayBlockingQueue.put("three");
                systemPrintln(Thread.currentThread().getName() + "\t完成添加three");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3").start();
        new Thread(() -> {
            try {
                systemPrintln(Thread.currentThread().getName() + "\t开始take()，且进入睡眠2秒");
                TimeUnit.SECONDS.sleep(2);
                arrayBlockingQueue.take();
                systemPrintln(Thread.currentThread().getName() + "\t完成take()");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t4").start();

        while (Thread.activeCount() > 2) {

        }
        systemPrintln("putTake api，队列容量为2 结束");
    }

    private void offerPollTimeout() {
        systemPrintln("offerPollTimeout api，队列容量为2，超时为1秒 开始");
        int capacity = 2;
        BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(capacity);
        new Thread(() -> {
            try {
                systemPrintln(Thread.currentThread().getName() + "\t开始添加one");
                boolean result = arrayBlockingQueue.offer("one", 1, TimeUnit.SECONDS);
                systemPrintln(Thread.currentThread().getName() + "\t添加one结果：" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                systemPrintln(Thread.currentThread().getName() + "\t开始添加two");
                boolean result = arrayBlockingQueue.offer("two", 1, TimeUnit.SECONDS);
                systemPrintln(Thread.currentThread().getName() + "\t添加two结果：" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
        new Thread(() -> {
            try {
                systemPrintln(Thread.currentThread().getName() + "\t开始添加three");
                boolean result = arrayBlockingQueue.offer("three", 1, TimeUnit.SECONDS);
                systemPrintln(Thread.currentThread().getName() + "\t完成1秒等待");
                systemPrintln(Thread.currentThread().getName() + "\t添加three结果：" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3").start();
        new Thread(() -> {
            try {
                systemPrintln(Thread.currentThread().getName() + "\t开始take()，且进入睡眠3秒");
                TimeUnit.SECONDS.sleep(3);
                arrayBlockingQueue.take();
                systemPrintln(Thread.currentThread().getName() + "\t完成take()");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t4").start();

        while (Thread.activeCount() > 2) {

        }
        systemPrintln("offerPollTimeout api，队列容量为2 结束");
    }

    // 有消费才生产
    private void synchronous() {
        systemPrintln("SynchronousQueue开始");

        BlockingQueue<String> synchronousQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                systemPrintln(Thread.currentThread().getName() + "\t开始添加one");
                synchronousQueue.put("one");
                systemPrintln(Thread.currentThread().getName() + "\t完成添加one");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                systemPrintln(Thread.currentThread().getName() + "\t开始添加two");
                synchronousQueue.put("two");
                systemPrintln(Thread.currentThread().getName() + "\t完成添加two");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
        new Thread(() -> {
            try {
                systemPrintln(Thread.currentThread().getName() + "\t开始take()");
                systemPrintln(Thread.currentThread().getName() + "\t开始take()，且进入睡眠2秒");
                TimeUnit.SECONDS.sleep(2);
                synchronousQueue.take();
                systemPrintln(Thread.currentThread().getName() + "\t完成take()");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3").start();
        new Thread(() -> {
            try {
                systemPrintln(Thread.currentThread().getName() + "\t开始take()");
                systemPrintln(Thread.currentThread().getName() + "\t开始take()，且进入睡眠1秒");
                TimeUnit.SECONDS.sleep(1);
                synchronousQueue.take();
                systemPrintln(Thread.currentThread().getName() + "\t完成take()");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t4").start();

        while (Thread.activeCount() > 2) {

        }
        systemPrintln("SynchronousQueue结束");
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
