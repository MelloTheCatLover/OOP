package ru.nsu.kozoliy.PizzeriaLogic;

public interface OrderProvider {
    void makeOrder(int count) throws InterruptedException;

}