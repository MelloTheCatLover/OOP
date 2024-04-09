package ru.nsu.kozoliy.Parsing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import ru.nsu.kozoliy.Exceptions.ParsingException;

import java.io.InputStream;
import java.io.InputStreamReader;

public class PizzeriaParser {

    private final Gson gson;


    public PizzeriaParser() {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

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
