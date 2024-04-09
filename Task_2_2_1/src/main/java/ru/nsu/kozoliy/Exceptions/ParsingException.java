package ru.nsu.kozoliy.Exceptions;

public class ParsingException extends RuntimeException {
    public ParsingException(String filename) {
        super("Cannot find file with such file name " + filename + " for parsing your pizzeria.");
    }

}