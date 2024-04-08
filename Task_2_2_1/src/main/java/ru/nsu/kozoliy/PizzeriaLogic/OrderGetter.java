package ru.nsu.kozoliy.PizzeriaLogic;

import ru.nsu.kozoliy.Order.Order;

public interface OrderGetter {
    Order getOrder() throws InterruptedException;

    boolean isNoOrders();
}
