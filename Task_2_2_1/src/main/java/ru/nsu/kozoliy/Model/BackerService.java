package ru.nsu.kozoliy.Model;

import ru.nsu.kozoliy.Backer.BackerRepository;
import ru.nsu.kozoliy.Backer.IBackerRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackerService implements Service {
    private final List<IBackerRepository> backers;
    private final ExecutorService executorService;


    public BackerService(List<IBackerRepository> backers) {
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