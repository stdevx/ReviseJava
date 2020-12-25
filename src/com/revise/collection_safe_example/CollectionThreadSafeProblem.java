package com.revise.collection_safe_example;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionThreadSafeProblem {

    public void show() {
        problemCreate();
        problemSolveByVector();
        problemSolveByCollections();
        problemSolveByCopyOnWrite();
    }

    public void problemCreate() {
        printlnDivider();
        systemPrintln("容器线程安全问题演示例子开始");

        List<String> list = new ArrayList<>();
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                try {
                    list.add(UUID.randomUUID().toString().substring(0, 6));
                    systemPrintln(list.toString());
                } catch (Exception exception) {
                    systemPrintln(exception.toString() + "，容器线程安全问题发生");
                }
            });
            t.start();
            threadList.add(t);
        }

        try {
            for (Thread t : threadList) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        systemPrintln("容器线程安全问题演示例子开始");
    }

    public void problemSolveByVector() {
        printlnDivider();
        systemPrintln("容器线程安全问题解决例子开始（Vector）");

        List<String> list = new Vector<>();
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 6));
                systemPrintln(list.toString());
            });
            t.start();
            threadList.add(t);
        }

        try {
            for (Thread t : threadList) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        systemPrintln("容器线程安全问题解决例子开始（Vector）");
    }

    public void problemSolveByCollections() {
        printlnDivider();
        systemPrintln("容器线程安全问题解决例子开始（Collections.synchronizedList）");
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 6));
                systemPrintln(list.toString());
            });
            t.start();
            threadList.add(t);
        }

        try {
            for (Thread t : threadList) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        systemPrintln("容器线程安全问题解决例子开始（Collections.synchronizedList）");
    }

    public void problemSolveByCopyOnWrite() {
        printlnDivider();
        systemPrintln("容器线程安全问题解决例子开始（CopyOnWrite）");

        List<String> list = new CopyOnWriteArrayList<>();
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 6));
                systemPrintln(list.toString());
            });
            t.start();
            threadList.add(t);
        }

        try {
            for (Thread t : threadList) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        systemPrintln("容器线程安全问题解决例子开始（CopyOnWrite）");
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
