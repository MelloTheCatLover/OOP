package ru.nsu.kozoliy.Backer;

import ru.nsu.kozoliy.Order.Order;
import ru.nsu.kozoliy.Order.Pizza;

public interface IBackerRepository extends Runnable{
    Pizza makePizza(Order order) throws InterruptedException;
}
