package ru.nsu.kozoliy.model;

import java.util.ArrayDeque;
import java.util.Queue;
import ru.nsu.kozoliy.entities.Order;

/**
 * Менеджер очереди заказов.
 * Этот класс управляет очередью заказов, предоставляя методы для добавления заказов,
 * извлечения заказов и проверки наличия заказов в очереди.
 */
public class OrderQueueManager {
    private final Queue<Order> orders;

    /**
     * Конструктор класса OrderQueueManager.
     * Создает новый экземпляр менеджера очереди заказов с использованием ArrayDeque.
     */
    public OrderQueueManager() {
        this.orders = new ArrayDeque<>();
    }

    /**
     * Добавляет заказ в очередь.
     *
     * @param order добавляемый заказ
     */
    public synchronized void addOrder(Order order) {
        orders.add(order);
        notifyAll();
    }

    /**
     * Получает заказ из очереди.
     *
     * @return заказ из очереди
     * @throws InterruptedException если поток был прерван во время ожидания заказа
     */
    public synchronized Order getOrder() throws InterruptedException {
        while (orders.isEmpty()) {
            wait();
        }
        return orders.poll();
    }

    /**
     * Проверяет, пуста ли очередь заказов.
     *
     * @return true, если очередь пуста, иначе false
     */
    public boolean isNoOrders() {
        return orders.isEmpty();
    }
}
