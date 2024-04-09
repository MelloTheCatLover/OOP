package ru.nsu.kozoliy.ModelInterfaces;

import ru.nsu.kozoliy.Entities.Pizza;

public interface IBacker extends Runnable{
    void makePizza(Pizza pizza) throws InterruptedException;
}
