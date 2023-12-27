package ru.nsu.kozoliy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;


public class TestNotes {
    private Notebook notebook;
    private CommandLineParserNotebook parser;
    private ByteArrayOutputStream outputStream;


    @BeforeEach
    void setUp() {
        notebook = new Notebook();
        Serializer serializer = new Serializer();
        parser = new CommandLineParserNotebook(notebook, serializer);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void run_AddNotePrintSuccessMessage() throws IOException {
        parser.add = Arrays.asList("Title", "Content");
        parser.run();

        assertEquals("Note added successfully.\r\n", outputStream.toString());
    }

    @Test
    void run_RemoveNotePrintSuccessMessage() throws IOException {
        parser.add = Arrays.asList("Title", "Content");
        parser.remove = "Title";
        parser.run();

        assertEquals("Note added successfully.\r\n" +
                "Note removed successfully.\r\n", outputStream.toString());
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
    void testShowByName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        Note catNote = new Note("I love Cats", "I love cat very much 4real");
        notebook.addEntry(catNote);
        assertEquals("Name: I love Cats\n"
                        + "Content: I love cat very much 4real\n"
                        + "Time note have been created: "
                        + notebook.getNotesSorted().get(0).getTimeCreated().format(formatter)
                , notebook.showByName("I love Cats"));
    }


    @Test
    void testGetNotesInRangeWithKeywords() {
        Note firstNote = new Note("My first Note", "This is my very first note.");
        notebook.addEntry(firstNote);
        Note secondNote = new Note("My second Note", "This is my second note");
        notebook.addEntry(secondNote);

        LocalDateTime start = LocalDateTime.now().minusDays(10);
        LocalDateTime end = LocalDateTime.now().plusDays(10);
        List<String> keywords = List.of("first");
        List<Note> notes = notebook.getNotesInRangeWithKeywords(start, end, keywords);
        ArrayList<Note> expected = new ArrayList<>();

        expected.add(firstNote);

        assertEquals(expected, notes);
    }

    @Test
    void testGetNotesSorted() {
        Note firstNote = new Note("My first Note", "This is my very first note.");
        notebook.addEntry(firstNote);
        Note secondNote = new Note("My second Note", "This is my second note");
        notebook.addEntry(secondNote);
        Note thirdNote = new Note("I love Cats", "I love cat very much 4real");
        notebook.addEntry(thirdNote);

        ArrayList<Note> expected = new ArrayList<>();

        expected.add(firstNote);
        expected.add(secondNote);
        expected.add(thirdNote);

        assertEquals(expected, notebook.getNotesSorted());
    }


}
