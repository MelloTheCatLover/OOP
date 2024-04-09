package ru.nsu.kozoliy.modelInterfaces;


import ru.nsu.kozoliy.model.OrderGetter;
import ru.nsu.kozoliy.model.OrderProvider;

/**
 * Интерфейс, представляющий пиццерию.
 * Пиццерия умеет принимать и обрабатывать заказы, а также работать в качестве отдельного потока.
 */
public interface IPizzeria extends OrderGetter, OrderProvider, Runnable {

    /**
     * Останавливает прием заказов.
     */
    void stopWorking();

    /**
     * Завершает работу пиццерии.
     */
    void endWorking();
}
