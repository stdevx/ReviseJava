package com.revise.thread_pool_example;

import java.util.concurrent.*;

/*
* CPU密集型：
* 一般情况下核心数设置：机器核心数+1
* IO密集型：
* 按生产环境测试调配。（粗略可以按机器核心数*2）
* 一般情况：机器核心数/1 - 阻塞系数   （阻塞系数0.8~0.9）
* */
public class CustomThreadPool {

    private final int corePoolSize = 2;
    private final int maximumPoolSize = 4;
    private final int queueCapacity = 2;
    private final long keepAliveTime = 100L;

    /*
    * Abort policy causes the executor to throw a RejectedExecutionException
    * */
    public void abortPolicyDemo() {
        systemPrintln("abortPolicy 10个任务 开始");

        ThreadPoolExecutor abortPool = abortPolicyVersion();

        for (int i = 0; i < 10; i++) {
            try {
                abortPool.execute(
                        () -> systemPrintln(Thread.currentThread().getName() + " 执行任务成功")
                );
            } catch (Exception e) {
                systemPrintln(Thread.currentThread().getName() + " 执行任务被抛弃");
            }
        }

        abortPool.shutdown();
        while (Thread.activeCount() > 2) {

        }
        systemPrintln("abortPolicy 10个任务 结束");
    }

    /*
    * Instead of running a task asynchronously in another thread,
    * this policy makes the caller thread execute the task.
    * The caller-runs policy makes it easy to implement a simple form of throttling.
    * That is, a slow consumer can slow down a fast producer to control the task submission flow.
    * */
    public void callerRunsPolicyDemo() {
        systemPrintln("callerRunsPolicy 10个任务 开始");

        ThreadPoolExecutor callerRunsPolicyPool = callerRunsPolicyVersion();

        for (int i = 0; i < 10; i++) {
            try {
                callerRunsPolicyPool.execute(() -> {
                            try {
                                TimeUnit.SECONDS.sleep(1);
                                systemPrintln(Thread.currentThread().getName() + " 执行任务成功");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                );
            } catch (Exception e) {
                systemPrintln(Thread.currentThread().getName() + " 执行任务被抛弃");
            }
        }

        callerRunsPolicyPool.shutdown();
        while (Thread.activeCount() > 2) {

        }
        systemPrintln("callerRunsPolicy 10个任务 结束");
    }

    /*
    * The discard policy silently discards the new task when it fails to submit it
    * */
    public void discardPolicyDemo() {
        systemPrintln("discardPolicy 10个任务 开始");

        ThreadPoolExecutor abortPool = discardPolicyVersion();

        for (int i = 0; i < 10; i++) {
            try {
                abortPool.execute(
                        () -> systemPrintln(Thread.currentThread().getName() + " 执行任务成功")
                );
            } catch (Exception e) {
                systemPrintln(Thread.currentThread().getName() + " 执行任务被抛弃");
            }
        }

        abortPool.shutdown();
        while (Thread.activeCount() > 2) {

        }
        systemPrintln("discardPolicy 10个任务 结束");
    }

    /*
    * The discard-oldest policy first removes a task from the head of the queue, then re-submits the new task
    *
    *
    * WARNING:
    * The discard-oldest policy and priority queues don't play well together.
    * Because the head of a priority queue has the highest priority, we may simply lose the most important task.
    * */
    public void discardOldestPolicyDemo() {
        systemPrintln("discardPolicy 10个任务 开始");

        ThreadPoolExecutor abortPool = discardOldestPolicyVersion();

        for (int i = 0; i < 10; i++) {
            try {
                abortPool.execute(
                        () -> {
                            try {
                                TimeUnit.SECONDS.sleep(3);
                                systemPrintln(Thread.currentThread().getName() + " 执行任务成功");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                );
            } catch (Exception e) {
                systemPrintln(Thread.currentThread().getName() + " 执行任务被抛弃");
            }
        }

        abortPool.shutdown();
        while (Thread.activeCount() > 2) {

        }
        systemPrintln("discardPolicy 10个任务 结束");
    }

    // AbortPolicy为默认策略
    private ThreadPoolExecutor abortPolicyVersion() {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueCapacity);
        return new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS,
                queue,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    private ThreadPoolExecutor callerRunsPolicyVersion() {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueCapacity);
        return new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS,
                queue,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    private ThreadPoolExecutor discardPolicyVersion() {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueCapacity);
        return new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS,
                queue,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }

    private ThreadPoolExecutor discardOldestPolicyVersion() {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueCapacity);
        return new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS,
                queue,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
    }

    private void systemPrintln(String msg) {
        System.out.println(msg);
    }
}
