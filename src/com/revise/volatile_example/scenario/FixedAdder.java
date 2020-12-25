package com.revise.volatile_example.scenario;

public class FixedAdder extends Thread {
    private final FixedNum times;

    public FixedAdder(ThreadGroup group, String name, FixedNum times) {
        super(group, name);
        this.times = times;
    }

    @Override
    public void run() {
        super.run();
        System.out.println(currentThread().getName() + "\tstarted.");
        for (int i = 0; i < 10000; i++) {
            times.addTimes();
        }
        System.out.println(currentThread().getName() + "\tfinished.");
    }
}
