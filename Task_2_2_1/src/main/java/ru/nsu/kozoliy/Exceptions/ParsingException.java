package ru.nsu.kozoliy.Exceptions;

/**
 * Исключение, которое возникает при ошибке парсинга файла конфигурации пиццерии.
 * Возникает, когда файл с указанным именем не найден для парсинга.
 */
public class ParsingException extends RuntimeException {

    /**
     * Создает новый экземпляр исключения с указанным именем файла.
     *
     * @param filename имя файла, который не удалось найти для парсинга
     */
    public ParsingException(String filename) {
        super("Cannot find file with such file name " + filename + " for parsing your pizzeria.");
    }
}
