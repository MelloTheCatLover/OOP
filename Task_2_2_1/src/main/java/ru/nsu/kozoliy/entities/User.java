package ru.nsu.kozoliy.entities;

import ru.nsu.kozoliy.model.OrderProvider;

import java.util.List;
import java.util.Random;

/**
 * Класс, представляющий пользователя системы - заказчика пиццы.
 */
public class User implements Runnable {
    private final OrderProvider provider; // Провайдер заказов
    private final List<Pizza> pizzas; // Список пицц, которые пользователь может заказать

    /**
     * Конструктор для создания пользователя с указанным провайдером заказов и списком пицц.
     *
     * @param provider провайдер заказов
     * @param pizzas   список пицц, которые пользователь может заказать
     */
    public User(OrderProvider provider, List<Pizza> pizzas) {
        this.provider = provider;
        this.pizzas = pizzas;
    }

    /**
     * Метод, который запускается при запуске потока пользователя.
     * Пользователь периодически делает заказы через провайдера и засыпает на случайный промежуток времени.
     */
    @Override
    public void run() {
        while (true) {
            System.out.println("Hello from customer " + this);
            try {
                // Делаем заказ через провайдера
                provider.makeOrder(pizzas);

                // Генерируем случайное время ожидания перед следующим заказом
                int MIN_SLEEP_TIME = 1000;
                int sleepTime = new Random().nextInt(10000) + MIN_SLEEP_TIME;
                Thread.sleep(sleepTime);

            } catch (InterruptedException e) {
                // Если поток был прерван, выводим сообщение и завершаем его работу
                System.out.println("User " + this + " was interrupted");
                return;
            }
        }
    }
}
