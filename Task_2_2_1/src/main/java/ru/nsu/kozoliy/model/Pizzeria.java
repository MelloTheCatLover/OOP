package ru.nsu.kozoliy.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.Queue;
import ru.nsu.kozoliy.dto.BackerDto;
import ru.nsu.kozoliy.dto.CourierDto;
import ru.nsu.kozoliy.entities.Backer;
import ru.nsu.kozoliy.entities.Courier;
import ru.nsu.kozoliy.entities.Order;
import ru.nsu.kozoliy.entities.Pizza;
import ru.nsu.kozoliy.interfaces.Ibacker;
import ru.nsu.kozoliy.interfaces.Icourier;
import ru.nsu.kozoliy.interfaces.Ipizzeria;
import ru.nsu.kozoliy.parsing.Configuration;
import ru.nsu.kozoliy.services.BackerService;
import ru.nsu.kozoliy.services.CourierService;
import ru.nsu.kozoliy.services.UserService;
import ru.nsu.kozoliy.storage.Storage;

/**
 * Класс, представляющий пиццерию.
 * Этот класс управляет процессом приготовления
 * и доставки пиццы, используя пекарей, курьеров и пользователей.
 */
public class Pizzeria implements Ipizzeria {
    private static final Logger logger = Logger.getLogger(Pizzeria.class.getName());

    private final OrderQueueManager orderQueueManager;
    private final Queue<Order> orders;
    private final Storage storage = Storage.getInstance();
    private final UserService userService;
    private final BackerService backerService;
    private final CourierService courierService;
    private int orderNumber = 0;

    /**
     * Создает новый экземпляр пиццерии на основе конфигурации.
     *
     * @param configuration конфигурация пиццерии
     */
    public Pizzeria(Configuration configuration) {
        Storage.getInstance(configuration.storage().capacity());
        orderQueueManager = new OrderQueueManager();
        List<Icourier> couriers = new ArrayList<>();
        for (CourierDto courierDto : configuration.couriers()) {
            Icourier courier = new Courier(courierDto.name(),
                    courierDto.surname(), courierDto.id(),
                    courierDto.baggageSize(),
                    courierDto.deliveryTime());
            couriers.add(courier);
        }
        List<Ibacker> backers = new ArrayList<>();
        for (BackerDto backerDto : configuration.backers()) {
            Ibacker backer = new Backer(backerDto.name(),
                    backerDto.surname(),
                    backerDto.id(),
                    this,
                    backerDto.workingTimeMs());
            backers.add(backer);
        }
        orders = new ArrayDeque<>();
        backerService = new BackerService(backers);
        courierService = new CourierService(couriers);
        userService = new UserService(this);
    }

    /**
     * Запускает процессы пиццерии.
     */
    @Override
    public void run() {
        backerService.run();
        courierService.run();
        userService.run();
    }

    /**
     * Останавливает работу пиццерии.
     */
    public synchronized void stopWorking() {
        userService.stopService();
    }

    /**
     * Завершает работу пиццерии.
     */
    public synchronized void endWorking() {
        userService.stopService();
        backerService.stopService();
        courierService.stopService();
        notifyAll();
    }

    /**
     * Получает заказ из очереди.
     *
     * @return заказ из очереди
     * @throws InterruptedException если поток был прерван во время ожидания заказа
     */
    @Override
    public synchronized Order getOrder() throws InterruptedException {
        return orderQueueManager.getOrder();
    }

    /**
     * Проверяет, есть ли заказы в очереди.
     *
     * @return true, если в очереди нет заказов, иначе false
     */
    @Override
    public boolean isNoOrders() {
        return orderQueueManager.isNoOrders();
    }

    /**
     * Создает заказ на основе списка пицц.
     *
     * @param pizzas список пицц для заказа
     */
    @Override
    public synchronized void makeOrder(List<Pizza> pizzas) {
        Order order = new Order(orderNumber++, pizzas);
        order.setUser(() ->
                logger.info("Заказ номер: " + order.getId() + " был получен"));
        orderQueueManager.addOrder(order);
        logger.info("Поступил заказ под номером:  " + order.getId());
    }

    /**
     * Возвращает сервис пекарей.
     *
     * @return сервис пекарей
     */
    public BackerService getBackerService() {
        return backerService;
    }

    /**
     * Возвращает сервис курьеров.
     *
     * @return сервис курьеров
     */
    public CourierService getCourierService() {
        return courierService;
    }
}
