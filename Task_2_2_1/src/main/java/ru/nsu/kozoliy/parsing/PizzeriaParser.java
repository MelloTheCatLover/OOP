package ru.nsu.kozoliy.parsing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import ru.nsu.kozoliy.exceptions.ParsingException;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Парсер для чтения конфигурационного файла пиццерии в формате JSON.
 */
public class PizzeriaParser {

    private final Gson gson;

    /**
     * Создает новый экземпляр парсера.
     */
    public PizzeriaParser() {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    /**
     * Получает конфигурацию пиццерии из файла.
     *
     * @param filepath путь к файлу с конфигурацией
     * @return объект конфигурации пиццерии
     * @throws ParsingException если не удалось прочитать файл с конфигурацией
     */
    public Configuration getConfigurationFromFile(String filepath) {
        JsonReader reader;
        InputStream inputStream = this.getClass().getResourceAsStream(filepath);
        if (inputStream == null) {
            throw new ParsingException(filepath);
        }
        reader = new JsonReader(new InputStreamReader(inputStream));
        return gson.fromJson(reader, Configuration.class);
    }
}
