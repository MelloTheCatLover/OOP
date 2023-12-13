package ru.nsu.kozoliy;

/**
 * Exception for invalid log input. When base is equal to 1 or
 * below zero or input number below zero.
 */
public class InvalidLogException extends InvalidInputException {
    public InvalidLogException(double input) {
        super("Invalid Log input: " + input);
    }
}
