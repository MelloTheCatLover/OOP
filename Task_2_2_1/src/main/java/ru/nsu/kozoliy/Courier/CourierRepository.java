package ru.nsu.kozoliy.Courier;

import ru.nsu.kozoliy.Order.Order;
import ru.nsu.kozoliy.Storage.IStorageRepository;

import java.util.ArrayDeque;
import java.util.Queue;

public class CourierRepository implements ICourierRepository, Runnable {
    private final IStorageRepository warehouse;
    private final Queue<Order> bag;

    private final int baggageCount;

    private final int deliveryTimeMs;


    public CourierRepository(IStorageRepository warehouse, int baggageCount, int deliveryTimeMs) {
        this.warehouse = warehouse;
        this.baggageCount = baggageCount;
        this.bag = new ArrayDeque<>();
        this.deliveryTimeMs = deliveryTimeMs;
    }

    @Override
    public void deliverPizza() throws InterruptedException {
        while (!isBagFull()) {
            Order pizzaOrder = warehouse.getPizza();
            bag.add(pizzaOrder);
            System.out.println("Courier " + this + " received pizza " + pizzaOrder.toString());
        }
        for (Order pizzaOrder : bag) {
            Thread.sleep(deliveryTimeMs);
            pizzaOrder.getConsumer().run();
            bag.remove(pizzaOrder);
        }
    }


    @Override
    public void run() {
        while (true) {
            try {
                deliverPizza();
            } catch (InterruptedException e) {
                System.out.println("Courier thread was interrupted");
                return;
            }
        }
    }

    private boolean isBagFull() {
        return bag.size() == baggageCount;
    }
}