package com.revise.volatile_example.scenario;

import java.util.Objects;
import java.util.UUID;

public class Merchandise {
    private final UUID id;
    private final String name;
    // 测试可见性
    private volatile int prices;

    public Merchandise(String name, int prices) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.prices = prices;
    }

    public int getPrices() {
        return prices;
    }

    public void setPrices(int prices) {
        this.prices = prices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Merchandise)) return false;
        Merchandise that = (Merchandise) o;
        return prices == that.prices && id.equals(that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, prices);
    }

    @Override
    public String toString() {
        return "Merchandise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prices=" + prices +
                '}';
    }
}
