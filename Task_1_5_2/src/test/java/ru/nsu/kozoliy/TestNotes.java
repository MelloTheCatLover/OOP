package ru.nsu.kozoliy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

public class TestNotes {
    private Notebook notebook;

    @BeforeEach
    void setUp() {
        notebook = new Notebook();
    }

    @Test
    void testAddNote() {
        notebook.addEntry(new Note("My first Note", "This is my very first note."));
        List<Note> notes = notebook.getNotesSorted();
        assertFalse(notes.isEmpty());
        assertEquals(1, notes.size());
        Note note = notes.get(0);
        assertEquals("My first Note", note.getName());
        assertEquals("This is my very first note.", note.getContent());
    }

    @Test
    void testRemoveNote() {
        notebook.addEntry(new Note("My first Note", "This is my very first note."));
        notebook.deleteEntry("My first Note");
        List<Note> notes = notebook.getNotesSorted();
        assertTrue(notes.isEmpty());
    }


    @Test
    void testGetNotesInRangeWithKeywords() {
        notebook.addEntry(new Note("My first Note", "This is my very first note."));
        notebook.addEntry(new Note("My second Note", "This is my second note"));
        LocalDateTime start = LocalDateTime.now().minusDays(10);
        LocalDateTime end = LocalDateTime.now().plusDays(10);
        List<String> keywords = List.of("Note");
        List<Note> notes = notebook.getNotesInRangeWithKeywords(start, end, keywords);
        assertEquals("[Name: My first Note\n" +
                "Content: This is my very first note.\n" +
                "Time note have been created: 20.12.2023 08:29, Name: My second Note\n" +
                "Content: This is my second note\n" +
                "Time note have been created: 20.12.2023 08:29]", notes.toString() );
    }

    @Test
    void testGetNotesSorted() {
        notebook.addEntry(new Note("My first Note", "This is my very first note."));
        notebook.addEntry(new Note("My second Note", "This is my second note"));
        notebook.addEntry(new Note("I love Cats", "I love cat very much 4real"));

        assertEquals("[Name: My first Note\n" +
                "Content: This is my very first note.\n" +
                "Time note have been created: 20.12.2023 08:16, Name: My second Note\n" +
                "Content: This is my second note\n" +
                "Time note have been created: 20.12.2023 08:16, Name: I love Cats\n" +
                "Content: I love cat very much 4real\n" +
                "Time note have been created: 20.12.2023 08:16]", notebook.getNotesSorted().toString());
    }

}
