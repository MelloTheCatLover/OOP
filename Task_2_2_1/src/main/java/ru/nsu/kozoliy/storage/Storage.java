package ru.nsu.kozoliy.storage;

import java.util.ArrayDeque;
import java.util.Queue;
import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;
import ru.nsu.kozoliy.entities.Order;



/**
 * Класс, представляющий хранилище заказов.
 */
public class Storage {
    private static Storage instance;
    private final Queue<Order> orders;
    private final int capacity;

    /**
     * Конструктор класса Storage.
     *
     * @param capacity вместимость хранилища
     */
    private Storage(int capacity) {
        this.capacity = capacity;
        orders = new ArrayDeque<>(capacity);
    }

    /**
     * Получить единственный экземпляр класса Storage.
     *
     * @param capacity вместимость хранилища
     * @return единственный экземпляр класса Storage
     */
    public static synchronized Storage getInstance(int capacity) {
        if (instance == null) {
            instance = new Storage(capacity);
        }
        return instance;
    }

    /**
     * Получить единственный экземпляр класса Storage.
     *
     * @return единственный экземпляр класса Storage
     */
    public static synchronized Storage getInstance() {
        return instance;
    }

    /**
     * Добавить заказ в хранилище.
     *
     * @param order заказ
     * @throws InterruptedException если произошла ошибка во время ожидания
     */
    public synchronized void addOrder(Order order) throws InterruptedException {
        while (this.isFull()) {
            this.wait();
        }
        orders.add(order);
        notifyAll();
    }

    /**
     * Получить заказ из хранилища.
     *
     * @return заказ
     * @throws InterruptedException если произошла ошибка во время ожидания
     */
    public synchronized Order getOrder() throws InterruptedException {
        while (isEmpty()) {
            this.wait();
        }
        notifyAll();
        return orders.poll();
    }

    /**
     * Проверить, полное ли хранилище.
     *
     * @return true, если хранилище полное, иначе false
     */
    public boolean isFull() {
        return orders.size() == capacity;
    }

    /**
     * Проверить, пустое ли хранилище.
     *
     * @return true, если хранилище пустое, иначе false
     */
    public boolean isEmpty() {
        return orders.isEmpty();
    }

    /**
     * Получить вместимость хранилища.
     *
     * @return вместимость хранилища
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Переопределение метода toString.
     *
     * @return строковое представление объекта
     */
    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Storage{" +
                "orders=" + orders +
                ", capacity=" + capacity +
                '}';
    }
}
