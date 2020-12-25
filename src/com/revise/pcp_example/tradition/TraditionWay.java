package com.revise.pcp_example.tradition;

public class TraditionWay {
    private void show() {
        systemPrintln("传统方法 开始");
        Goods goods = new Goods();

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    goods.increment();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    goods.decrement();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();

        while (Thread.activeCount() > 2) {

        }
        systemPrintln("传统方法 结束");
    }

    private void systemPrintln(String msg) {
        System.out.println(msg);
    }
}
