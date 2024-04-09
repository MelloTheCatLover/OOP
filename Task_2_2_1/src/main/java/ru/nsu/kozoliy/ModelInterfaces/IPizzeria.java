package ru.nsu.kozoliy.ModelInterfaces;

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
