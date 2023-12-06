package ru.nsu.kozoliy;

public class InvalidSqrtException extends InvalidInputException {
    public InvalidSqrtException(Double input) {
        super("Invalid sqrt input: " + input);
    }
}

