package ru.nsu.kozoliy.Model;

import ru.nsu.kozoliy.Entities.Pizza;

import java.util.List;

public interface OrderProvider {
    void makeOrder(List<Pizza> pizzas) throws InterruptedException;
}
