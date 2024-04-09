package ru.nsu.kozoliy.ModelInterfaces;

/**
 * Интерфейс, представляющий курьера.
 * Курьер умеет доставлять пиццу.
 */
public interface ICourier extends Runnable {

    /**
     * Доставляет пиццу.
     *
     * @throws InterruptedException если поток был прерван во время доставки пиццы
     */
    void deliverPizza() throws InterruptedException;
}
