package com.revise.volatile_example.scenario;

public class UniqueApple {
    public static volatile UniqueApple instance = null;

    public static UniqueApple getInstance() {
        if (instance == null) {
            synchronized (UniqueApple.class) {
                if (instance == null) {
                    instance = new UniqueApple();
                }
            }
        }
        return instance;
    }

    private UniqueApple() {
        System.out.println("UniqueApple init.");
    }
}
