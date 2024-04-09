package ru.nsu.kozoliy.Entities;


import java.util.ArrayList;
import java.util.List;

public class Order {

    private int id;
    private List<Pizza> pizzas;

    private Runnable user;

    public Order() {
        this.pizzas = new ArrayList<>();
    }

    public Order(int id, List<Pizza> pizzas) {
        this.id = id;
        this.pizzas = pizzas;
    }

    public Runnable getUser() {
        return user;
    }

    public void setUser(Runnable user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", pizzas=" + pizzas +
                '}';
    }

}
