package ru.nsu.kozoliy.entities;

import java.util.ArrayDeque;
import java.util.logging.Logger;
import java.util.Queue;
import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;
import ru.nsu.kozoliy.interfaces.Icourier;
import ru.nsu.kozoliy.storage.Storage;

public class Courier implements Icourier {
    private static final Logger logger = Logger.getLogger(Courier.class.getName());

    private final String name;
    private final String surname;
    private final int id;
    private final Queue<Order> bag;
    private final int baggageSize;
    private final int deliveryTime;

    public Courier(String name, String surname, int id, int baggageSize, int deliveryTime) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.bag = new ArrayDeque<>();
        this.baggageSize = baggageSize;
        this.deliveryTime = deliveryTime;
    }

    @Override
    public void deliverPizza() throws InterruptedException {
        Storage storage = Storage.getInstance();
        while (!isBagFull()) {
            Order pizzaOrder = storage.getOrder();
            bag.add(pizzaOrder);
            logger.info("Courier " + name + " " + surname + " received pizza " + pizzaOrder.toString());
        }
        for (Order pizzaOrder : bag) {
            Thread.sleep(deliveryTime);
            pizzaOrder.getUser().run();
            bag.remove(pizzaOrder);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                deliverPizza();
            } catch (InterruptedException e) {
                logger.severe("Courier interrupted while delivering pizza: " + e.getMessage());
                return;
            }
        }
    }

    private boolean isBagFull() {
        return bag.size() == baggageSize;
    }

    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Courier{"
                + "name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", id=" + id
                + ", baggageSize="
                + baggageSize + ", deliveryTime="
                + deliveryTime + '}';
    }
}
