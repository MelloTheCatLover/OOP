package ru.nsu.kozoliy;

import picocli.CommandLine;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



@CommandLine.Command(
        name = "notebook",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Command-line interface for interacting with a Notebook."
)
public class CommandLineParserNotebook implements Runnable {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @CommandLine.Option(names = {"-a", "--add"}, arity = "2", description = "Add a note")
    List<String> add;

    @CommandLine.Option(names = {"-r", "--rm"}, description = "Remove a note")
    String remove;

    @CommandLine.Option(names = {"-s", "--show"}, arity = "0..*", description = "Show notes")
    List<String> show;

    private final Notebook notebook;
    private final Serializer serializer;

    public CommandLineParserNotebook(Notebook notebook, Serializer serializer) {
        this.notebook = notebook;
        this.serializer = serializer;
    }

    @ExcludeFromJacocoGeneratedTestReport
    public void run() {
        try {
            processCommands();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void processCommands() throws IOException {
        if (add != null) {
            addNoteCommand();
        }
        if (remove != null) {
            removeNoteCommand();
        }
        if (show != null) {
            showNotesCommand();
        }
    }

    private void addNoteCommand() throws IOException {
        notebook.addEntry(new Note(add.get(0), add.get(1)));
        serializer.serialize(notebook);
        System.out.println("Note added successfully.");
    }

    private void removeNoteCommand() throws IOException {
        notebook.deleteEntry(remove);
        serializer.serialize(notebook);
        System.out.println("Note removed successfully.");
    }

    private void showNotesCommand() {
        if (show.isEmpty()) {
            notebook.getNotesSorted().forEach(System.out::println);
        } else {
            LocalDateTime start = parseDateTime(show.get(0));
            LocalDateTime end = parseDateTime(show.get(1));
            List<String> keywords = new ArrayList<>(show.subList(2, show.size()));
            List<Note> filteredNotes = notebook.getNotesInRangeWithKeywords(start, end, keywords);
            filteredNotes.forEach(System.out::println);
        }
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

}
