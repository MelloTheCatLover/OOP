package ru.nsu.kozoliy.PizzeriaLogic;

public interface IPizzeria extends Runnable, OrderGetter, OrderProvider {
    void stopWorking();

    void endWorking();
}
