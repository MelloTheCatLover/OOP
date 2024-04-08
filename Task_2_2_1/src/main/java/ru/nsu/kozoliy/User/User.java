package ru.nsu.kozoliy.User;

import ru.nsu.kozoliy.PizzeriaLogic.OrderProvider;

import java.util.Random;

public class User implements Runnable {
    private final OrderProvider provider;
    private final int pizzaCountInOrder;


    public User(OrderProvider provider, int pizzaCount) {
        this.provider = provider;
        this.pizzaCountInOrder = pizzaCount;

    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Hello from customer " + this);
            try {
                provider.makeOrder(pizzaCountInOrder);
                int MIN_SLEEP_TIME = 1000;
                int sleepTime = new Random().nextInt(10000) + MIN_SLEEP_TIME;
                Thread.sleep(sleepTime);

            } catch (InterruptedException e) {
                System.out.println("User " + this + " was interrupted");
                return;
            }
        }
    }
}
