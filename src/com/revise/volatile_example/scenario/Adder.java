package com.revise.volatile_example.scenario;

public class Adder extends Thread {

    private final Num num;

    public Adder(ThreadGroup group, String name, Num num) {
        super(group, name);
        this.num = num;
    }

    @Override
    public void run() {
        super.run();
        System.out.println(currentThread().getName() + "\tstarted.");
        for (int i = 0; i < 10000; i++) {
            num.addTimes();
        }
        System.out.println(currentThread().getName() + "\tfinished.");
    }
}
