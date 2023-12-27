package ru.nsu.kozoliy;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import picocli.CommandLine;



/**
 * Command-line interface for interacting with a Notebook.
 */
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

    /**
     * Creates a new instance of CommandLineParserNotebook.
     *
     * @param notebook   The notebook to interact with.
     * @param serializer The serializer for notebook data.
     */
    public CommandLineParserNotebook(Notebook notebook, Serializer serializer) {
        this.notebook = notebook;
        this.serializer = serializer;
    }

    /**
     * Executes the specified commands based on the provided options.
     */
    @ExcludeFromJacocoGeneratedTestReport
    public void run() {
        try {
            processCommands();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Processes the specified commands based on the provided options.
     *
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * Adds a new note to the notebook.
     *
     * @throws IOException if an I/O error occurs.
     */
    private void addNoteCommand() throws IOException {
        notebook.addEntry(new Note(add.get(0), add.get(1)));
        serializer.serialize(notebook);
        System.out.println("Note added successfully.");
    }

    /**
     * Removes a note from the notebook.
     *
     * @throws IOException if an I/O error occurs.
     */
    private void removeNoteCommand() throws IOException {
        notebook.deleteEntry(remove);
        serializer.serialize(notebook);
        System.out.println("Note removed successfully.");
    }

    /**
     * Shows notes based on the specified options.
     */
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

    /**
     * Parses a string to a LocalDateTime object using the predefined formatter.
     *
     * @param dateTimeStr The string to parse.
     * @return The LocalDateTime object.
     */
    private LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}
