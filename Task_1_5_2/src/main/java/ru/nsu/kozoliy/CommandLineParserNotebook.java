package ru.nsu.kozoliy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;



public class CommandLineParserNotebook {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "dd.MM.yyyy HH:mm");

    @ExcludeFromJacocoGeneratedTestReport
    public static void executeCommand(
            String[] args,
            Notebook notebook,
            Serializer serializer
    ) {
        Options options = new Options();

        Option add = new Option("add", true, "Add a note");
        add.setArgs(2);
        options.addOption(add);

        Option remove = new Option("rm", true, "Remove a note");
        options.addOption(remove);

        Option show = new Option("show", true, "Show notes");
        show.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(show);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("add")) {
                String[] values = cmd.getOptionValues("add");
                notebook.addEntry(new Note(values[0], values[1]));
                serializer.serialize(notebook);
                System.out.println("Note added successfully.");
            } else if (cmd.hasOption("rm")) {
                String title = cmd.getOptionValue("rm");
                notebook.deleteEntry(title);
                serializer.serialize(notebook);
                System.out.println("Note removed successfully.");
            } else if (cmd.hasOption("show")) {
                String[] values = cmd.getOptionValues("show");
                if (values == null || values.length == 0) {
                    notebook.getNotesSorted().forEach(System.out::println);
                } else {
                    // Parse date range and keywords from the command line arguments
                    LocalDateTime start = parseDateTime(values[0]);
                    LocalDateTime end = parseDateTime(values[1]);
                    List<String> keywords = new ArrayList<>(Arrays.asList(values).subList(2, values.length));

                    List<Note> filteredNotes = notebook.getNotesInRangeWithKeywords(
                            start,
                            end,
                            keywords
                    );
                    filteredNotes.forEach(System.out::println);
                }
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    @ExcludeFromJacocoGeneratedTestReport
    private static LocalDateTime parseDateTime(String dateTimeStr) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}
