package com.revise.pcp_example.blocking_queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/*
* while (FLAG) 循环用于模拟运行情况
* */
public class SharedData<T> {
    private volatile boolean FLAG = true; //默认开启，进行生产和消费
    private final BlockingQueue<T> blockingQueue;
    private final int TIMEOUT = 2;

    public SharedData(BlockingQueue<T> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void produce(T data) throws Exception {
        boolean retValue;
        while (FLAG) {
            retValue = blockingQueue.offer(data, TIMEOUT, TimeUnit.SECONDS);
            TimeUnit.SECONDS.sleep(1);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "失败");
            }
        }

        System.out.println(Thread.currentThread().getName() + "\tFLAG=false 生产暂停");
    }

    public void consume() throws Exception {
        T result;
        while (FLAG) {
            result = blockingQueue.poll(TIMEOUT, TimeUnit.SECONDS);
            TimeUnit.SECONDS.sleep(1);
            if (result != null) {
                System.out.println(Thread.currentThread().getName() + "\t消费队列" + result + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t消费队列失败");
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t消费超时，消费暂停执行FLAG=false");
                return;
            }
        }

        System.out.println(Thread.currentThread().getName() + "\tFLAG=false 消费暂停");
    }

    public void stop() {
        FLAG = false;
    }
}
