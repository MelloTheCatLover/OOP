package ru.nsu.kozoliy.services;

import ru.nsu.kozoliy.modelInterfaces.IBacker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Сервис для управления работой пекарей.
 */
public class BackerService implements Runnable {
    private final List<IBacker> backers;
    private final ExecutorService executorService;

    /**
     * Создает новый экземпляр сервиса для управления пекарями.
     *
     * @param backers список пекарей
     */
    public BackerService(List<IBacker> backers) {
        this.backers = backers;
        executorService = Executors.newFixedThreadPool(backers.size());
    }

    /**
     * Останавливает сервис управления пекарями.
     */
    public void stopService() {
        executorService.shutdownNow();
    }

    @Override
    public void run() {
        backers.forEach(executorService::execute);
    }
}
