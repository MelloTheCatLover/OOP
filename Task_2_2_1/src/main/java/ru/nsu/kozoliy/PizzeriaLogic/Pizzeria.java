package ru.nsu.kozoliy.PizzeriaLogic;

import ru.nsu.kozoliy.Backer.BackerDto;
import ru.nsu.kozoliy.Backer.BackerRepository;
import ru.nsu.kozoliy.Backer.IBackerRepository;
import ru.nsu.kozoliy.Courier.CourierDto;
import ru.nsu.kozoliy.Courier.CourierRepository;
import ru.nsu.kozoliy.Courier.ICourierRepository;
import ru.nsu.kozoliy.Model.BackerService;
import ru.nsu.kozoliy.Model.CourierService;
import ru.nsu.kozoliy.Model.CustomerService;
import ru.nsu.kozoliy.Order.Order;
import ru.nsu.kozoliy.Parsing.Configuration;
import ru.nsu.kozoliy.Storage.IStorageRepository;
import ru.nsu.kozoliy.Storage.StorageRepository;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Pizzeria implements IPizzeria {
    private final Queue<Order> orders;
    private final IStorageRepository storage;
    private final CustomerService customerService;
    private final BackerService backerService;
    private final CourierService courierService;
    private int orderNumber = 0;

    public Pizzeria(Configuration configuration) {
        storage = new StorageRepository(configuration.storage().capacity());
        List<ICourierRepository> couriers = new ArrayList<>();
        for (CourierDto courierDto : configuration.couriers()) {
            ICourierRepository courier = new CourierRepository(storage, courierDto.baggageCount(), courierDto.deliveryTimeMs());
            couriers.add(courier);
        }
        List<IBackerRepository> backers = new ArrayList<>();
        for (BackerDto backerDto : configuration.backers()) {
            IBackerRepository backer = new BackerRepository(storage, this, backerDto.workingTimeMs());
            backers.add(backer);
        }
        orders = new ArrayDeque<>();
        backerService = new BackerService(backers);
        courierService = new CourierService(couriers);
        customerService = new CustomerService(this);
    }


    @Override
    synchronized public void makeOrder(int count) {
        Order order = new Order(orderNumber++, count);
        order.setConsumer(() ->
                System.out.println("Order " + order.getId() + " was successfully delivered"));
        orders.add(order);
        System.out.println("Pizzeria received order with number " + order.getId());
        notifyAll();
    }

    @Override
    public void run() {
        backerService.run();
        courierService.run();
        customerService.run();
    }


    synchronized public void stopWorking() {
        customerService.stopService();
    }

    synchronized public void endWorking() {
        customerService.stopService();
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

    public IStorageRepository getWarehouse() {
        return storage;
    }

    public BackerService getBackerService() {
        return backerService;
    }

    public CourierService getCourierService() {
        return courierService;
    }
}
