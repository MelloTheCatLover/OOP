package ru.nsu.kozoliy.modelInterfaces;

import ru.nsu.kozoliy.entities.Pizza;

/**
 * Интерфейс, представляющий пекаря.
 * Пекарь умеет готовить пиццу.
 */
public interface IBacker extends Runnable {

    /**
     * Готовит пиццу.
     *
     * @param pizza пицца, которую нужно приготовить
     * @throws InterruptedException если поток был прерван во время приготовления пиццы
     */
    void makePizza(Pizza pizza) throws InterruptedException;
}
