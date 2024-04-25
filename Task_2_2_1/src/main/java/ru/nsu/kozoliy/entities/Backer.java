package ru.nsu.kozoliy.entities;

import java.util.logging.Logger;
import ru.nsu.kozoliy.interfaces.Ibacker;
import ru.nsu.kozoliy.model.OrderGetter;
import ru.nsu.kozoliy.model.OrderQueueManager;
import ru.nsu.kozoliy.storage.Storage;


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
    private final OrderQueueManager orderQueueManager;
    private Order order = null;

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
        this.orderQueueManager = new OrderQueueManager();
    }

    /**
     * Переопределенный метод toString для получения строкового представления пекаря.
     *
     * @return строковое представление пекаря
     */
    @Override
    public String toString() {
        return "Backer{"
                + "name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", id=" + id
                + ", workingTimeMs=" + workingTimeMs
                + '}';
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
        logger.info(name + " " + surname + " is baking a "
                + pizza.getSize() + " cm pizza of type "
                + pizza.getType() + ".");
        Thread.sleep(workingTimeMs);
        pizza.setCooked(true);
        logger.info(name + " has finished baking the "
                + pizza.getType() + " pizza.");
    }

    /**
     * Метод запуска потока приготовления пиццы.
     * Пекарь бесконечно выпекает пиццу согласно заказам и добавляет их на склад.
     * Если поток пекаря был прерван во время приготовления, метод завершается.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                order = orderGetter.getOrder();
            } catch (InterruptedException e) {
                logger.info("Повара: " + name + " "
                        + surname + " прервали, пока брал заказ.");
                break;
            }

            logger.info("Повар: " + name + " " + surname + " взял заказ: " + order);
            for (Pizza pizza : order.getPizzas()) {
                try {
                    makePizza(pizza);
                } catch (InterruptedException e) {
                    logger.info("Повара: " + name + " " + surname
                            + " прервали, пока готовил одну из пицц");
                    break;
                }
            }


            try {
                Storage storage = Storage.getInstance();
                storage.addOrder(order);
            } catch (InterruptedException e) {
                logger.info("Повара: " + name + " " + surname
                        + " прервали, пока стоял у склада.");
                break;
            }
            logger.info("Повар: " + name + " " + surname + " выполнил заказ: " + order);
        }

        logger.info("Повар: " + name + " " + surname + " закончил смену");

        if (order != null) {
            orderQueueManager.addOrder(order);
            order.getPizzas().clear();
        }

    }
}