package ru.nsu.kozoliy.entities;

import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;
import ru.nsu.kozoliy.interfaces.Ibacker;
import ru.nsu.kozoliy.model.OrderGetter;
import ru.nsu.kozoliy.storage.Storage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс, представляющий пекаря в пиццерии.
 * Пекарь выпекает пиццу согласно заказам и добавляет их на склад.
 */
public class Backer implements Ibacker {
    private static final Logger logger = Logger.getLogger(Backer.class.getName());

    private final String name;
    private final String surname;
    private final int id;
    private final OrderGetter orderGetter;
    private final int workingTimeMs;

    /**
     * Конструктор для создания экземпляра пекаря.
     *
     * @param name          имя пекаря
     * @param surname       фамилия пекаря
     * @param id            уникальный идентификатор пекаря
     * @param orderGetter   получатель заказов для пекаря
     * @param workingTimeMs время приготовления одной пиццы в миллисекундах
     */
    public Backer(String name, String surname, int id, OrderGetter orderGetter, int workingTimeMs) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.orderGetter = orderGetter;
        this.workingTimeMs = workingTimeMs;
    }

    /**
     * Переопределенный метод toString для получения строкового представления пекаря.
     *
     * @return строковое представление пекаря
     */
    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Backer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", id=" + id +
                ", workingTimeMs=" + workingTimeMs +
                '}';
    }

    /**
     * Метод приготовления пиццы.
     * Пекарь выпекает пиццу заданного размера и типа.
     *
     * @param pizza пицца, которую нужно приготовить
     * @throws InterruptedException если поток пекаря был прерван во время приготовления
     */
    @Override
    public void makePizza(Pizza pizza) throws InterruptedException {
        logger.info(name + " " + surname + " is baking a " + pizza.getSize() + " cm pizza of type " + pizza.getType() + ".");
        Thread.sleep(workingTimeMs);
        pizza.setCooked(true);
        logger.info(name + " has finished baking the " + pizza.getType() + " pizza.");
    }

    /**
     * Метод запуска потока приготовления пиццы.
     * Пекарь бесконечно выпекает пиццу согласно заказам и добавляет их на склад.
     * Если поток пекаря был прерван во время приготовления, метод завершается.
     */
    @Override
    public void run() {
        while (true) {
            try {
                logger.info(name + " " + surname + " started the shift");
                Order order = orderGetter.getOrder();
                for (Pizza pizza : order.getPizzas()) {
                    if (Thread.currentThread().isInterrupted()) {
                        logger.info(name + " " + surname + " was interrupted while baking " + pizza.getType() + " pizza.");
                        Thread.interrupted();
                        return;
                    }
                    makePizza(pizza);
                }
                Storage storage = Storage.getInstance();
                storage.addOrder(order);
            } catch (InterruptedException e) {
                logger.log(Level.INFO, "The baker thread was interrupted", e);
                return;
            }
        }
    }
}
