package com.revise.thread_pool_example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {

    public void show() {
        printlnDivider();
        systemPrintln("线程池例子 开始");

//        fixedThreadPool();
//        singleThreadExecutor();
//        cachedThreadPool();
        customThreadPool();

        systemPrintln("线程池例子 结束");
    }

    // 工作线程为固定数量（队列类型：LinkedBlockingQueue）
    private void fixedThreadPool() {
        systemPrintln("fixedThreadPool例子 工作线程数量为3 开始");
        int total = 3;
        ExecutorService service = Executors.newFixedThreadPool(total);

        for (int i = 0; i < 10; i++) {
            service.submit(() -> systemPrintln(Thread.currentThread().getName() + "\t 执行"));
        }

        service.shutdown();
        while (Thread.activeCount() > 2) {

        }
        systemPrintln("fixedThreadPool例子 工作线程数量为3 结束");
    }

    // 工作线程为一个（队列类型：LinkedBlockingQueue）
    private void singleThreadExecutor() {
        systemPrintln("singleThreadExecutor例子 工作线程数量为1 开始");
        ExecutorService service = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 10; i++) {
            service.submit(() -> systemPrintln(Thread.currentThread().getName() + "\t 执行"));
        }

        service.shutdown();
        while (Thread.activeCount() > 2) {

        }
        systemPrintln("singleThreadExecutor例子 工作线程数量为1 结束");
    }

    // 工作线程数量不固定，按需自动扩张（队列类型：SynchronousQueue）
    private void cachedThreadPool() {
        systemPrintln("cachedThreadPool例子 工作线程数量按需自动扩张 开始");
        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            service.submit(() -> systemPrintln(Thread.currentThread().getName() + "\t 执行"));
        }

        service.shutdown();
        while (Thread.activeCount() > 2) {

        }
        systemPrintln("cachedThreadPool例子 工作线程数量按需自动扩张 结束");
    }

    // 自定义线程池（4种拒绝策略演示）
    private void customThreadPool() {
        systemPrintln("自定义线程池例子 开始");
        systemPrintln("核心工作线程数量2，最大为4。队列容量为2。空闲降容为0.1秒");

        CustomThreadPool customThreadPool = new CustomThreadPool();
        customThreadPool.abortPolicyDemo();
        customThreadPool.callerRunsPolicyDemo();
        customThreadPool.discardPolicyDemo();
        customThreadPool.discardOldestPolicyDemo();

        systemPrintln("自定义线程池例子 结束");
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
