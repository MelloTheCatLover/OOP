package ru.nsu.kozoliy.interfaces;

/**
 * Интерфейс, представляющий курьера.
 * Курьер умеет доставлять пиццу.
 */
public interface Icourier extends Runnable {

    /**
     * Доставляет пиццу.
     *
     * @throws InterruptedException если поток был прерван во время доставки пиццы
     */
    void deliverPizza() throws InterruptedException;
}
