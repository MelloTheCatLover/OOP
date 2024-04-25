package ru.nsu.kozoliy.interfaces;


import ru.nsu.kozoliy.model.OrderGetter;
import ru.nsu.kozoliy.model.OrderProvider;

/**
 * Интерфейс, представляющий пиццерию.
 * Пиццерия умеет принимать и обрабатывать заказы, а также работать в качестве отдельного потока.
 */
public interface Ipizzeria extends OrderGetter, OrderProvider, Runnable {

    /**
     * Останавливает прием заказов.
     */
    void stopWorking();

    /**
     * Завершает работу пиццерии.
     */
    void endWorking();
}
