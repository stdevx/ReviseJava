package com.revise.volatile_example.scenario;

public class MyThreadGroup extends ThreadGroup {

    public MyThreadGroup() {
        super(NAME);
    }

    static String NAME = "Location ThreadGroup";

}
