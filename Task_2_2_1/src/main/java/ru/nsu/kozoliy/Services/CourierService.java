package ru.nsu.kozoliy.Services;

import ru.nsu.kozoliy.ModelInterfaces.ICourier;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourierService implements Runnable {
    private final List<ICourier> couriers;

    private final ExecutorService executorService;

    public CourierService(List<ICourier> couriers) {
        this.couriers = couriers;
        executorService = Executors.newFixedThreadPool(couriers.size());
    }


    public void stopService() {
        executorService.shutdownNow();
    }

    @Override
    public void run() {
        couriers.forEach(executorService::execute);
    }
}
