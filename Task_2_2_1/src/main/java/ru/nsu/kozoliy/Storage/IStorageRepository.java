package ru.nsu.kozoliy.Storage;

import ru.nsu.kozoliy.Order.Order;

public interface IStorageRepository {

    void addPizza(Order pizza) throws InterruptedException;


    Order getPizza() throws InterruptedException;

    boolean isFull();

    boolean isEmpty();
}