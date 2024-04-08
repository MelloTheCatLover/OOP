package ru.nsu.kozoliy.Exceptions;

public class ParsingException extends RuntimeException {
    public ParsingException(String filename) {
        super("Parser cannot find file with name " + filename + " for parsing your pizzeria.");
    }

}
