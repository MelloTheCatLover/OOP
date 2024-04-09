package ru.nsu.kozoliy.Services;

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
