package ru.nsu.kozoliy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a notebook containing a collection of notes.
 */
public class Notebook {
    private final ArrayList<Note> entries;

    /**
     * Constructs an empty notebook.
     */
    public Notebook() {
        entries = new ArrayList<>();
    }

    /**
     * Adds a note to the notebook.
     *
     * @param entry The note to be added.
     */
    public void addEntry(Note entry) {
        entries.add(entry);
    }

    /**
     * Deletes a note from the notebook by name.
     *
     * @param name The name of the note to be deleted.
     */
    public void deleteEntry(String name) {
        entries.removeIf(entry -> entry.getName().equals(name));
    }

    /**
     * Gets all notes in the notebook sorted by the time they were created.
     *
     * @return A list of notes sorted by creation time.
     */
    public List<Note> getNotesSorted() {
        return entries.stream()
                .sorted(Comparator.comparing(Note::getTimeCreated))
                .collect(Collectors.toList());
    }

    /**
     * Gets notes from the notebook within a specified time range and containing specific keywords in the name.
     *
     * @param from   The start of the time range.
     * @param to     The end of the time range.
     * @param words  A list of keywords to filter notes by name.
     * @return A list of notes matching the criteria, sorted by creation time.
     */
    public List<Note> getNotesInRangeWithKeywords(LocalDateTime from, LocalDateTime to, List<String> words) {
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

    /**
     * Shows the content of a note by its name.
     *
     * @param name The name of the note to show.
     * @return The content of the note with the specified name.
     */
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

    /**
     * Main method for testing the functionality of the Notebook class.
     *
     * @param args Command-line arguments (not used in this context).
     */
    @ExcludeFromJacocoGeneratedTestReport
    public static void main(String[] args) {
        // Test the functionality of the Notebook class
        Notebook notebook = new Notebook();

        notebook.addEntry(new Note("My first Note", "This is my very first note."));
        notebook.addEntry(new Note("My second Note", "This is my second note"));
        notebook.addEntry(new Note("I love Cats", "I love cat very much 4real"));

        System.out.println(notebook.showByName("My first Note"));

        LocalDateTime start = LocalDateTime.now().minusDays(10);
        LocalDateTime end = LocalDateTime.now().plusDays(10);
        List<String> keywords = List.of("Note");
        System.out.println(notebook.getNotesInRangeWithKeywords(start, end, keywords).toString());
    }
}

