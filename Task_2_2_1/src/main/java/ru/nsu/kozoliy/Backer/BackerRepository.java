package ru.nsu.kozoliy.Backer;

import ru.nsu.kozoliy.Order.Order;
import ru.nsu.kozoliy.Order.Pizza;
import ru.nsu.kozoliy.PizzeriaLogic.OrderGetter;
import ru.nsu.kozoliy.Storage.IStorageRepository;

public class BackerRepository implements IBackerRepository{
    private final int workingTime;
    private final OrderGetter orderGetter;
    private final IStorageRepository warehouse;

    public BackerRepository(IStorageRepository warehouse, OrderGetter orderGetter, int workingTime) {
        this.warehouse = warehouse;
        this.orderGetter = orderGetter;
        this.workingTime = workingTime;
    }

    @Override
    public Pizza makePizza(Order order) throws InterruptedException {
        Thread.sleep(workingTime);
        Pizza pizza = new Pizza();
        System.out.println("Pizza man " + this + " creates pizza for order " + order.getId());
        return pizza;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Hello from backer " + this);
                Order order = orderGetter.getOrder();
                Pizza pizza = makePizza(order);
                order.setPizza(pizza);
                warehouse.addPizza(order);
            } catch (InterruptedException e) {
                System.out.println("Backer thread was interrupted");
                return;
            }
        }
    }
}
