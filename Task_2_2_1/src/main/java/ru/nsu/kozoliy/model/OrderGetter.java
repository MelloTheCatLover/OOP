package ru.nsu.kozoliy.model;

import ru.nsu.kozoliy.entities.Order;

/**
 * Интерфейс для получения заказов.
 * Этот интерфейс определяет методы для получения заказов и проверки наличия заказов.
 */
public interface OrderGetter {

    /**
     * Получает заказ из источника.
     *
     * @return заказ, полученный из источника
     * @throws InterruptedException если поток был прерван во время ожидания заказа
     */
    Order getOrder() throws InterruptedException;

    /**
     * Проверяет, есть ли доступные заказы.
     *
     * @return true, если нет доступных заказов, иначе false
     */
    boolean isNoOrders();
}
