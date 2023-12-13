package ru.nsu.kozoliy;


/**
 * Exception for invalid sqrt input, when input is below 0.
 */
public class InvalidSqrtException extends InvalidInputException {
    public InvalidSqrtException(Double input) {
        super("Invalid sqrt input: " + input);
    }
}

