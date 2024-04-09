package ru.nsu.kozoliy.Model;

import ru.nsu.kozoliy.Entities.Order;

public interface OrderGetter {
    Order getOrder() throws InterruptedException;

    boolean isNoOrders();
}
