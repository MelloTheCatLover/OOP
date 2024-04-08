package ru.nsu.kozoliy.Order;

public class Order {
    private final int id;
    private final int count;

    private Pizza pizza;
    private Runnable consumer;

    /**
     * Default constructor of pizza order.
     *
     * @param id    of order
     * @param count of pizza that client required
     */
    public Order(int id, int count) {
        this.id = id;
        this.count = count;
    }

    public Runnable getConsumer() {
        return consumer;
    }

    public void setConsumer(Runnable consumer) {
        this.consumer = consumer;
    }

    public int getCount() {
        return count;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public int getId() {
        return id;
    }
}
