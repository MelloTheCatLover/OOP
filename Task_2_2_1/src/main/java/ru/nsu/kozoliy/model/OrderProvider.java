package ru.nsu.kozoliy.model;


import java.util.List;
import ru.nsu.kozoliy.entities.Pizza;


/**
 * Интерфейс для создания заказов.
 * Этот интерфейс определяет метод для создания заказа на основе списка пицц.
 */
public interface OrderProvider {

    /**
     * Создает заказ на основе списка пицц.
     *
     * @param pizzas список пицц для заказа
     * @throws InterruptedException если поток был прерван во время создания заказа
     */
    void makeOrder(List<Pizza> pizzas) throws InterruptedException;
}
