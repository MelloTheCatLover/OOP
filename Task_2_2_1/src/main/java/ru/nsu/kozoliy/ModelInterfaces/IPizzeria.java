package ru.nsu.kozoliy.ModelInterfaces;


import ru.nsu.kozoliy.Model.OrderGetter;
import ru.nsu.kozoliy.Model.OrderProvider;

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
