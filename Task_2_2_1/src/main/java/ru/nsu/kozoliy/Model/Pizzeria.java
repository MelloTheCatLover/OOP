package ru.nsu.kozoliy.Model;

import ru.nsu.kozoliy.Dto.BackerDto;
import ru.nsu.kozoliy.Dto.CourierDto;
import ru.nsu.kozoliy.Entities.Backer;
import ru.nsu.kozoliy.Entities.Courier;
import ru.nsu.kozoliy.Entities.Order;
import ru.nsu.kozoliy.Entities.Pizza;
import ru.nsu.kozoliy.ModelInterfaces.IBacker;
import ru.nsu.kozoliy.ModelInterfaces.ICourier;
import ru.nsu.kozoliy.ModelInterfaces.IPizzeria;
import ru.nsu.kozoliy.Parsing.Configuration;
import ru.nsu.kozoliy.Services.BackerService;
import ru.nsu.kozoliy.Services.CourierService;
import ru.nsu.kozoliy.Services.UserService;
import ru.nsu.kozoliy.Storage.Storage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Класс, представляющий пиццерию.
 * Этот класс управляет процессом приготовления и доставки пиццы, используя пекарей, курьеров и пользователей.
 */
public class Pizzeria implements IPizzeria {
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
        List<ICourier> couriers = new ArrayList<>();
        for (CourierDto courierDto : configuration.couriers()) {
            ICourier courier = new Courier(courierDto.name(), courierDto.surname(), courierDto.id() ,courierDto.baggageSize(), courierDto.deliveryTime());
            couriers.add(courier);
        }
        List<IBacker> backers = new ArrayList<>();
        for (BackerDto backerDto : configuration.backers()) {
            IBacker backer = new Backer(backerDto.name(), backerDto.surname(), backerDto.id(), this, backerDto.workingTimeMs());
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
    synchronized public void stopWorking() {
        userService.stopService();
    }

    /**
     * Завершает работу пиццерии.
     */
    synchronized public void endWorking() {
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
    synchronized public Order getOrder() throws InterruptedException {
        while (orders.isEmpty()) {
            wait();
        }
        return orders.poll();
    }

    /**
     * Проверяет, есть ли заказы в очереди.
     *
     * @return true, если в очереди нет заказов, иначе false
     */
    @Override
    public boolean isNoOrders() {
        return orders.isEmpty();
    }

    /**
     * Создает заказ на основе списка пицц.
     *
     * @param pizzas список пицц для заказа
     */
    @Override
    synchronized public void makeOrder(List<Pizza> pizzas) {
        Order order = new Order(orderNumber++, pizzas);
        order.setUser(() ->
                System.out.println("Заказ номер: " + order.getId() + " был получен"));
        orders.add(order);
        System.out.println("Поступил заказ под номером:  " + order.getId());
        notifyAll();
    }

    public BackerService getBackerService() {
        return backerService;
    }

    public CourierService getCourierService() {
        return courierService;
    }
}
