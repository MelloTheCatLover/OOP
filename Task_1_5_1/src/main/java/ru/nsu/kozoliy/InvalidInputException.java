package ru.nsu.kozoliy;

public class InvalidInputException extends CalculatorException {
    public InvalidInputException(String input) {
        super("Invalid input: " + input);
    }
}

