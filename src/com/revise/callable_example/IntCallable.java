package com.revise.callable_example;

import java.util.concurrent.Callable;

public class IntCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t enter call()");
        return 10;
    }
}
