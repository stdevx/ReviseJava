package com.revise.lock_example.scenario;

import java.util.concurrent.atomic.AtomicReference;

public class DiySpinLock {
    AtomicReference<Thread> threadAtomicReference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        systemPrintln(thread.getName() + "\t自制自旋锁 got in");
        while (!threadAtomicReference.compareAndSet(null, thread)) {

        }
        systemPrintln(thread.getName() + "\t自制自旋锁 locked");
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        threadAtomicReference.compareAndSet(thread, null);
        systemPrintln(thread.getName() + "\t自制自旋锁 unlocked");
    }

    private void systemPrintln(String msg) {
        System.out.println(msg);
    }
}
