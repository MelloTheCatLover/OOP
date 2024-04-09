package ru.nsu.kozoliy.services;

import java.util.List;

/**
 * Интерфейс для генерации пользовательских задач.
 */
public interface UserGeneratorService {
    /**
     * Генерирует список пользовательских задач.
     *
     * @return список пользовательских задач
     */
    List<Runnable> generate();
}
