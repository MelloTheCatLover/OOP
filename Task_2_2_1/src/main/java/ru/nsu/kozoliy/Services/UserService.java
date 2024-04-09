package ru.nsu.kozoliy.Services;

import ru.nsu.kozoliy.Entities.Pizza;
import ru.nsu.kozoliy.Entities.User;
import ru.nsu.kozoliy.Model.Pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserService implements UserGeneratorService, Runnable{

    private final Pizzeria pizzeria;
    private final int MIN_CUSTOMER_COUNT = 3;
    private final List<Runnable> customers;
    private final ExecutorService executorService;

    public UserService(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
        customers = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(MIN_CUSTOMER_COUNT);
    }


    @Override
    public void run() {
        generate();
        customers.forEach(executorService::execute);
    }

    @Override
    public List<Runnable> generate() {
        int customerCount = new Random().nextInt(7) + MIN_CUSTOMER_COUNT;
        for (int i = 0; i < customerCount; i++) {
            int pizzaCountInOrder = new Random().nextInt(3) + 1;
            ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
            Pizza.PizzaType[] pizzaTypes = Pizza.PizzaType.values();
            for(int j = 0; j < pizzaCountInOrder; j++){
                int size = new Random().nextInt(4) * 5 + 20;
                Pizza.PizzaType type = pizzaTypes[new Random().nextInt(pizzaTypes.length)];
                pizzas.add(new Pizza(size, type));
            }
            Runnable customer = new User(pizzeria, pizzas);
            customers.add(customer);
        }
        return customers;
    }


    public void stopService() {
        executorService.shutdownNow();
    }
}
