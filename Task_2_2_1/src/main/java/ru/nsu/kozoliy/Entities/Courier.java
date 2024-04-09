package ru.nsu.kozoliy.Entities;

import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;
import ru.nsu.kozoliy.ModelInterfaces.ICourier;
import ru.nsu.kozoliy.Storage.Storage;

import java.util.ArrayDeque;
import java.util.Queue;

public class Courier implements ICourier {
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

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }


    private boolean isBagFull() {
        return bag.size() == baggageSize;
    }

    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Courier{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", id=" + id +
                ", baggageSize=" + baggageSize +
                ", deliveryTime=" + deliveryTime +
                '}';
    }

    @Override
    public void deliverPizza() throws InterruptedException {
        Storage storage = Storage.getInstance();
        while (!isBagFull()) {
            Order pizzaOrder = storage.getOrder();
            bag.add(pizzaOrder);
            System.out.println("Курьер " + name + " " + surname + " получил пиццу " + pizzaOrder.toString());
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
                System.out.println("Курьера прервали");
                return;
            }
        }
    }

}
