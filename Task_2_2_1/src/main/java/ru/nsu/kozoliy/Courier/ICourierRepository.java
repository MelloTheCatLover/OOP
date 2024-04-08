package ru.nsu.kozoliy.Courier;

public interface ICourierRepository extends Runnable {
    void deliverPizza() throws InterruptedException;
}
