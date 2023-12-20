package ru.nsu.kozoliy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;

public class Serializer {

    private static final String path = "notebook.json";
    private final ObjectMapper objectMapper;


    public Serializer() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void serialize(Notebook notebook) throws IOException {
        objectMapper.writeValue(new File(path), notebook);
    }

    public Notebook deserialize() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return new Notebook();
        }
        return objectMapper.readValue(file, Notebook.class);
    }


}
