package ru.nsu.kozoliy.Storage;

import ru.nsu.kozoliy.Order.Order;

import java.util.ArrayDeque;
import java.util.Queue;

public class StorageRepository implements IStorageRepository {
    private final Queue<Order> pizzas;
    private final int capacity;

    public StorageRepository(int capacity) {
        this.capacity = capacity;
        pizzas = new ArrayDeque<>(capacity);
    }

    @Override
    synchronized public void addPizza(Order order) throws InterruptedException {
        while (this.isFull()) {
            this.wait();
        }
        pizzas.add(order);
        notifyAll();

    }

    @Override
    synchronized public Order getPizza() throws InterruptedException {
        while (isEmpty()) {
            this.wait();
        }
        notifyAll();
        return pizzas.poll();

    }

    @Override
    public boolean isFull() {
        return pizzas.size() == capacity;
    }

    @Override
    public boolean isEmpty() {
        return pizzas.size() == 0;
    }


}