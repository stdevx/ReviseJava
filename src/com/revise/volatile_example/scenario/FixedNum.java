package com.revise.volatile_example.scenario;

import java.util.concurrent.atomic.AtomicInteger;

public class FixedNum {

    private final AtomicInteger times;

    public FixedNum(int times) {
        this.times = new AtomicInteger(times);
    }

    // compareAndSwapInt
    public void addTimes() {
        times.getAndIncrement();
    }

    public int getTimes() {
        return times.get();
    }

}
