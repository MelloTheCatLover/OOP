package ru.nsu.kozoliy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note implements Comparable<Note> {
    private String name;
    private String content;
    private LocalDateTime timeCreated;

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public Note(String namw, String content) {
        this.name = namw;
        this.content = content;
        this.timeCreated = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return "Name: " + name + "\nContent: " + content + "\nTime note have been created: "
                + timeCreated.format(formatter);
    }


}

