package com.revise.callable_example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableExample {

    public void show() {
        printlnDivider();
        systemPrintln("Callable接口使用例子 开始");

        // 面向接口，adapter pattern
        FutureTask<Integer> futureTask = new FutureTask<>(new IntCallable());

        // 创建新线程执行特定任务，且可获得执行结果（增强并发性能）
        new Thread(futureTask, "t1").start();
        new Thread(futureTask, "t2").start(); // 同一个任务对象不重复执行

        try {
            // get() 如果提前调用则会阻塞主线程
            systemPrintln(Thread.currentThread().getName() + " 子线程的返回结果：" + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        while (Thread.activeCount() > 2) {

        }
        systemPrintln("Callable接口使用例子 结束");
    }

    private void systemPrintln(String msg) {
        System.out.println(msg);
    }

    private void printlnDivider() {
        for (int i = 0; i < 50; i++) {
            System.out.print("-");
        }
        systemPrintln("");
    }
}
