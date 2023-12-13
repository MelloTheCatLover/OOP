package ru.nsu.kozoliy;

/**
 * Exception for invalid input. When it's not an operator or number.
 */
public class InvalidInputException extends CalculatorException {
    public InvalidInputException(String input) {
        super("Invalid input: " + input);
    }
}

