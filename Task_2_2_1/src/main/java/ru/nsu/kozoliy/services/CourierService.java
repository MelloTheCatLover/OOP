package ru.nsu.kozoliy.services;

import ru.nsu.kozoliy.interfaces.Icourier;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Сервис для управления работой курьеров.
 */
public class CourierService implements Runnable {
    private final List<Icourier> couriers;
    private final ExecutorService executorService;

    /**
     * Создает новый экземпляр сервиса для управления курьерами.
     *
     * @param couriers список курьеров
     */
    public CourierService(List<Icourier> couriers) {
        this.couriers = couriers;
        executorService = Executors.newFixedThreadPool(couriers.size());
    }

    /**
     * Останавливает сервис управления курьерами.
     */
    public void stopService() {
        executorService.shutdownNow();
    }

    @Override
    public void run() {
        couriers.forEach(executorService::execute);
    }
}
