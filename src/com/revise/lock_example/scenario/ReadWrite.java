package com.revise.lock_example.scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWrite {

    private final List<String> data = new ArrayList<>();

    public void readFreeWriteMutual() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);

        Thread t1 = new Thread(() -> {
            reentrantReadWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + "\tadding str");
            data.add("Tony");
            System.out.println(Thread.currentThread().getName() + "\tfinished adding");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reentrantReadWriteLock.writeLock().unlock();
        }, "t1");
        Thread t2 = new Thread(() -> {
            reentrantReadWriteLock.readLock().lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\tread:" + data);
            reentrantReadWriteLock.readLock().unlock();
        }, "t2");
        Thread t3 = new Thread(() -> {
            reentrantReadWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + "\tadding str");
            data.add("Ross");
            System.out.println(Thread.currentThread().getName() + "\tfinished adding");
            reentrantReadWriteLock.writeLock().unlock();
        }, "t3");
        Thread t4 = new Thread(() -> {
            reentrantReadWriteLock.readLock().lock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\tread:" + data);
            reentrantReadWriteLock.readLock().unlock();
        }, "t4");
        Thread t5 = new Thread(() -> {
            reentrantReadWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + "\tread:" + data);
            reentrantReadWriteLock.readLock().unlock();
        }, "t5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }

}
