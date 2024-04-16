package ru.nsu.kozoliy.entities;


import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import ru.nsu.kozoliy.model.OrderProvider;


/**
 * Класс, представляющий пользователя системы - заказчика пиццы.
 */
public class User implements Runnable {
    private static final Logger logger = Logger.getLogger(User.class.getName());

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
     * Пользователь периодически делает заказы через провайдера
     * и засыпает на случайный промежуток времени.
     */
    @Override
    public void run() {
        while (true) {
            logger.info("Hello from customer " + this);
            try {
                // Делаем заказ через провайдера
                provider.makeOrder(pizzas);

                // Генерируем случайное время ожидания перед следующим заказом
                int minSleepTime = 1000;
                int sleepTime = new Random().nextInt(10000) + minSleepTime;
                Thread.sleep(sleepTime);

            } catch (InterruptedException e) {
                // Если поток был прерван, выводим сообщение и завершаем его работу
                logger.warning("User " + this + " was interrupted: " + e.getMessage());
                return;
            }
        }
    }
}