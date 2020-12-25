package com.revise.lock_example.scenario;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedResource {
    private int number = 1;
    private final Lock lock = new ReentrantLock();
    private final Condition condition1 = lock.newCondition();
    private final Condition condition2 = lock.newCondition();
    private final Condition condition3 = lock.newCondition();

    public void printThree() {
        lock.lock();
        try {
            while (number != 1) {
                condition1.await();
            }

            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }

            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printFour() {
        lock.lock();
        try {
            while (number != 2) {
                condition2.await();
            }

            for (int i = 0; i < 4; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }

            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printFive() {
        lock.lock();
        try {
            while (number != 3) {
                condition3.await();
            }

            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }

            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
