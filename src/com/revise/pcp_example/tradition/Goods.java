package com.revise.pcp_example.tradition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Goods {
    private int number = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void increment() throws Exception {
        try {
            lock.lock();

            while (number != 0) {
                condition.await();
            }

            number += 1;
            System.out.println(Thread.currentThread().getName() + "\t number=" + number);
            condition.signalAll();

        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws Exception {
        try {
            lock.lock();

            while (number == 0) {
                condition.await();
            }

            number -= 1;
            System.out.println(Thread.currentThread().getName() + "\t number=" + number);
            condition.signalAll();

        } finally {
            lock.unlock();
        }
    }
}
