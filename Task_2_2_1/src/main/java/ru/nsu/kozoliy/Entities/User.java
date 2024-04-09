package ru.nsu.kozoliy.Entities;

import ru.nsu.kozoliy.Model.OrderProvider;

import java.util.List;
import java.util.Random;

public class User implements Runnable{
    private final OrderProvider provider;
    private final List<Pizza> pizzas;


    public User(OrderProvider provider, List<Pizza> pizzas) {
        this.provider = provider;
        this.pizzas = pizzas;

    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Hello from customer " + this);
            try {
                provider.makeOrder(pizzas);
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
