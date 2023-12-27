package ru.nsu.kozoliy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;

/**
 * A class responsible for serializing and deserializing a Notebook to and from JSON format.
 */
public class Serializer {

    // The path to the JSON file for serialization and deserialization
    private static final String path = "notebook.json";

    // The ObjectMapper used for JSON serialization and deserialization
    private final ObjectMapper objectMapper;

    /**
     * Constructs a new Serializer with a default configuration.
     */
    public Serializer() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Serializes a Notebook object to a JSON file.
     *
     * @param notebook The Notebook object to be serialized.
     * @throws IOException If an I/O error occurs during serialization.
     */
    @ExcludeFromJacocoGeneratedTestReport
    public void serialize(Notebook notebook) throws IOException {
        objectMapper.writeValue(new File(path), notebook);
    }

    /**
     * Deserializes a Notebook object from a JSON file.
     *
     * @return The deserialized Notebook object.
     * @throws IOException If an I/O error occurs during deserialization.
     */
    @ExcludeFromJacocoGeneratedTestReport
    public Notebook deserialize() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return new Notebook();
        }
        return objectMapper.readValue(file, Notebook.class);
    }
}
