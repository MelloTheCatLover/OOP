package ru.nsu.kozoliy.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import ru.nsu.kozoliy.entities.Pizza;
import ru.nsu.kozoliy.entities.User;
import ru.nsu.kozoliy.model.Pizzeria;


/**
 * Сервис для генерации пользовательских задач.
 */
public class UserService implements UserGeneratorService, Runnable {

    private final Pizzeria pizzeria;
    private final int minCostomerCount = 3;
    private final List<Runnable> customers;
    private final ExecutorService executorService;

    /**
     * Конструктор класса UserService.
     *
     * @param pizzeria объект класса Pizzeria
     */
    public UserService(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
        customers = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(minCostomerCount);
    }

    /**
     * Запускает сервис.
     */
    @Override
    public void run() {
        generate();
        customers.forEach(executorService::execute);
    }

    /**
     * Генерирует список пользовательских задач.
     *
     * @return список пользовательских задач
     */
    @Override
    public List<Runnable> generate() {
        int customerCount = new Random().nextInt(7) + minCostomerCount;
        for (int i = 0; i < customerCount; i++) {
            int pizzaCountInOrder = new Random().nextInt(3) + 1;
            ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
            Pizza.PizzaType[] pizzaTypes = Pizza.PizzaType.values();
            for (int j = 0; j < pizzaCountInOrder; j++) {
                int size = new Random().nextInt(4) * 5 + 20;
                Pizza.PizzaType type = pizzaTypes[new Random().nextInt(pizzaTypes.length)];
                pizzas.add(new Pizza(size, type));
            }
            Runnable customer = new User(pizzeria, pizzas);
            customers.add(customer);
        }
        return customers;
    }

    /**
     * Останавливает выполнение сервиса.
     */
    public void stopService() {
        executorService.shutdownNow();
    }
}
