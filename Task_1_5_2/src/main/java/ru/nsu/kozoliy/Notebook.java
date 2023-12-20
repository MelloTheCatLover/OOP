package ru.nsu.kozoliy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Notebook {
    private ArrayList<Note> entries;

    public Notebook() {
        entries = new ArrayList<>();
    }

    public void addEntry(Note entry) {
        entries.add(entry);
    }

    public void deleteEntry(String name) {
        entries.removeIf(entry -> entry.getName().equals(name));
    }


    public List<Note> getNotesSorted() {
        return entries.stream()
                .sorted(Comparator.comparing(Note::getTimeCreated))
                .collect(Collectors.toList());
    }

    public List<Note> getNotesInRangeWithKeywords(
            LocalDateTime from,
            LocalDateTime to,
            List<String> words
    ) {
        return entries.stream()
                .filter(note ->
                        note.getTimeCreated().isAfter(from) && note.getTimeCreated().isBefore(to))
                .filter(note ->
                        words.stream().anyMatch(keyword ->
                                note.getName().contains(keyword)
                        )
                )
                .sorted(Comparator.comparing(Note::getTimeCreated))
                .collect(Collectors.toList());
    }

    public String showByName(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        } else {
            StringBuilder result = new StringBuilder();

            for (Note entry : entries) {
                if (name.equals(entry.getName())) {
                    return entry.toString();
                }
            }

            return result.toString();
        }
    }

    @ExcludeFromJacocoGeneratedTestReport
    public static void main(String[] args) {
        Notebook notebook = new Notebook();

        notebook.addEntry(new Note("My first Note", "This is my very first note."));
        notebook.addEntry(new Note("My second Note", "This is my second note"));
        notebook.addEntry(new Note("I love Cats", "I love cat very much 4real"));

        System.out.println(notebook.showByName("My first Note"));

        //System.out.println(notebook.getNotesSorted());


        LocalDateTime start = LocalDateTime.now().minusDays(10);
        LocalDateTime end = LocalDateTime.now().plusDays(10);
        List<String> keywords = List.of("Note");
        System.out.println(notebook.getNotesInRangeWithKeywords(start, end, keywords).toString());
    }
}

