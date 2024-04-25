package ru.nsu.kozoliy.entities;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Logger;
import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;
import ru.nsu.kozoliy.interfaces.Icourier;
import ru.nsu.kozoliy.storage.Storage;

/**
 * Class representing a courier in the pizza delivery system.
 * The courier picks up pizza orders from the warehouse and delivers them to customers.
 */
public class Courier implements Icourier {
    private static final Logger logger = Logger.getLogger(Courier.class.getName());

    private final String name;
    private final String surname;
    private final int id;
    private final Queue<Order> bag;
    private final int baggageSize;
    private final int deliveryTime;

    /**
     * Constructor for creating an instance of a courier.
     *
     * @param name         the name of the courier
     * @param surname      the surname of the courier
     * @param id           the unique identifier of the courier
     * @param baggageSize  the size of the courier's baggage
     * @param deliveryTime the time it takes to deliver one pizza
     */
    public Courier(String name, String surname, int id, int baggageSize, int deliveryTime) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.bag = new ArrayDeque<>();
        this.baggageSize = baggageSize;
        this.deliveryTime = deliveryTime;
    }

    /**
     * Method for delivering pizza to customers.
     * The courier picks up pizza orders from the warehouse and delivers them to customers.
     * After successful delivery, the courier removes the order from his bag.
     *
     * @throws InterruptedException if the courier thread was interrupted during delivery
     */
    @Override
    public void deliverPizza() throws InterruptedException {
        Storage storage = Storage.getInstance();
        while (!isBagFull()) {
            Order pizzaOrder = storage.getOrder();
            bag.add(pizzaOrder);
            logger.info("Courier " + name + " "
                    + surname + " received pizza " + pizzaOrder.toString());
        }
        for (Order pizzaOrder : bag) {
            Thread.sleep(deliveryTime);
            pizzaOrder.getUser().run();
            bag.remove(pizzaOrder);
        }
    }

    /**
     * Method to start the pizza delivery thread to customers.
     * When the method is called, an infinite loop is started,
     * in which the courier delivers pizza.
     * If the courier thread was interrupted during delivery, the method exits.
     */
    @Override
    public void run() {
        while (true) {
            try {
                deliverPizza();
            } catch (InterruptedException e) {
                logger.severe("Courier interrupted while delivering pizza: "
                        + e.getMessage());
                return;
            }
        }
    }

    /**
     * Method to check if the courier's bag is full.
     *
     * @return true if the courier's bag is full, false otherwise
     */
    private boolean isBagFull() {
        return bag.size() == baggageSize;
    }

    /**
     * Overridden toString method to get a string representation of the courier.
     *
     * @return string representation of the courier
     */
    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Courier{"
                + "name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", id=" + id
                + ", baggageSize=" + baggageSize
                + ", deliveryTime=" + deliveryTime + '}';
    }
}
