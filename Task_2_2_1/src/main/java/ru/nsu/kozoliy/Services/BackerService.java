package ru.nsu.kozoliy.Services;

import ru.nsu.kozoliy.ModelInterfaces.IBacker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackerService implements Runnable {
    private final List<IBacker> backers;
    private final ExecutorService executorService;


    public BackerService(List<IBacker> backers) {
        this.backers = backers;
        executorService = Executors.newFixedThreadPool(backers.size());
    }

    public void stopService() {
        executorService.shutdownNow();
    }

    @Override
    public void run() {
        backers.forEach(executorService::execute);
    }
}
