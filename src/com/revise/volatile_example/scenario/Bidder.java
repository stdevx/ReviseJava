package com.revise.volatile_example.scenario;

import java.util.concurrent.TimeUnit;

public class Bidder extends Thread {

    private final Merchandise merchandise;

    public Bidder(
            ThreadGroup group,
            String clientName,
            Merchandise merchandise
    ) {
        super(group, clientName);
        this.merchandise = merchandise;
    }

    @Override
    public void run() {
        super.run();
        System.out.println(currentThread().getName() + "\tstarted.");
        /*
        * 如果注释掉睡眠1秒，线程在退出时候会执行清理动作，即同步所有等待当前对象的线程。
        * （其他线程完成修改且退出，此时如果主线程还没执行到while语句前，就算merchandise对象里没有加volatile的成员也会被刷新，
        *  即主线程也会直接完成。）
        * */
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int prices = merchandise.getPrices();
        prices += 10;
        merchandise.setPrices(prices);
        System.out.println(currentThread().getName() + "\tfinished.");
    }


}