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

public class Pizzeria implements IPizzeria {
    private final Queue<Order> orders;
    private final Storage storage = Storage.getInstance();
    private final UserService userService;
    private final BackerService backerService;
    private final CourierService courierService;
    private int orderNumber = 0;

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

    @Override
    public void run() {
        backerService.run();
        courierService.run();
        userService.run();
    }


    synchronized public void stopWorking() {
        userService.stopService();
    }

    synchronized public void endWorking() {
        userService.stopService();
        backerService.stopService();
        courierService.stopService();
        notifyAll();
    }

    @Override
    synchronized public Order getOrder() throws InterruptedException {
        while (orders.isEmpty()) {
            wait();
        }
        return orders.poll();
    }

    @Override
    public boolean isNoOrders() {
        return orders.isEmpty();
    }



    @Override
    synchronized public void makeOrder(List<Pizza> pizzas) {
        Order order = new Order(orderNumber++, pizzas);
        order.setUser(() ->
                System.out.println("Заказ номер: " + order.getId() + " был получен"));
        orders.add(order);
        System.out.println("поступил заказ под номером:  " + order.getId());
        notifyAll();
    }

    public BackerService getBackerService() {
        return backerService;
    }

    public CourierService getCourierService() {
        return courierService;
    }
}
