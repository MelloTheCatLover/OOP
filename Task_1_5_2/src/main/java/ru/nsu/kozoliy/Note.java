package ru.nsu.kozoliy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a note in a notebook with a name, content, and creation time.
 */
public class Note {
    private String name;
    private String content;
    private LocalDateTime timeCreated;

    /**
     * Gets the name of the note.
     *
     * @return The name of the note.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the content of the note.
     *
     * @return The content of the note.
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the time when the note was created.
     *
     * @return The creation time of the note.
     */
    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    /**
     * Constructs a new Note with the specified name and content, setting the creation timestamp to the current time.
     *
     * @param name    The name of the note.
     * @param content The content of the note.
     */
    public Note(String name, String content) {
        this.name = name;
        this.content = content;
        this.timeCreated = LocalDateTime.now();
    }

    /**
     * Returns a string representation of the note, including its name, content, and creation timestamp.
     *
     * @return A string representation of the note.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return "Name: " + name + "\nContent: " + content + "\nTime note have been created: "
                + timeCreated.format(formatter);
    }
}

