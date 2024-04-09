package ru.nsu.kozoliy.ModelInterfaces;

public interface ICourier extends Runnable{
    void deliverPizza() throws InterruptedException;
}
