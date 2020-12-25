package com.revise.volatile_example.scenario;

import java.util.Objects;

public class Num {
    // 测试volatile不保证原子性
    private volatile int times;

    public Num(int times) {
        this.times = times;
    }

    public void addTimes() {
        // 大概率会覆盖其他线程的赋值结果
        times += 1;
    }

    public int getTimes() {
        return times;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Num)) return false;
        Num num = (Num) o;
        return times == num.times;
    }

    @Override
    public int hashCode() {
        return Objects.hash(times);
    }

    @Override
    public String toString() {
        return "Num{" +
                "bidTimes=" + times +
                '}';
    }
}
