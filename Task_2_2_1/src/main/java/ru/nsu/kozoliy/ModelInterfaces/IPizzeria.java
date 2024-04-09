package ru.nsu.kozoliy.ModelInterfaces;

import ru.nsu.kozoliy.Model.OrderGetter;
import ru.nsu.kozoliy.Model.OrderProvider;

public interface IPizzeria extends OrderGetter, OrderProvider, Runnable {

    void stopWorking();

    void endWorking();
}
