package ru.nsu.kozoliy.Model;

import ru.nsu.kozoliy.Courier.CourierRepository;
import ru.nsu.kozoliy.Courier.ICourierRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourierService implements Service {

    private final List<ICourierRepository> couriers;

    private final ExecutorService executorService;

    public CourierService(List<ICourierRepository> couriers) {
        this.couriers = couriers;
        executorService = Executors.newFixedThreadPool(couriers.size());
    }

    @Override
    public void stopService() {
        executorService.shutdownNow();
    }

    @Override
    public void run() {
        couriers.forEach(executorService::execute);
    }
}