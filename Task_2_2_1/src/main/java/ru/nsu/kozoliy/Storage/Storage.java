package ru.nsu.kozoliy.Storage;

import ru.nsu.kozoliy.Entities.Order;
import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;

import java.util.ArrayDeque;
import java.util.Queue;

public class Storage {
    private static Storage instance;
    private final Queue<Order> orders;
    private final int capacity;

    private Storage(int capacity) {
        this.capacity = capacity;
        orders = new ArrayDeque<>(capacity);
    }



    public static synchronized Storage getInstance(int capacity) {
        if (instance == null) {
            instance = new Storage(capacity);
        }
        return instance;
    }

    public static synchronized Storage getInstance() {
        return instance;
    }


    synchronized public void addOrder(Order order) throws InterruptedException {
        while (this.isFull()) {
            this.wait();
        }
        orders.add(order);
        notifyAll();
    }


    synchronized public Order getOrder() throws InterruptedException {
        while (isEmpty()) {
            this.wait();
        }
        notifyAll();
        return orders.poll();

    }


    public boolean isFull() {
        return orders.size() == capacity;
    }


    public boolean isEmpty() {
        return orders.isEmpty();
    }


    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Storage{" +
                "pizzas=" + orders +
                ", capacity=" + capacity +
                '}';
    }

    public int getCapacity() {
        return capacity;
    }
}


