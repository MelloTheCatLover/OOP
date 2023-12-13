package ru.nsu.kozoliy;

/**
 * Exception representing all the exception that connect with calculator.
 */
public abstract class CalculatorException extends RuntimeException {
    public CalculatorException(String message) {
        super(message);
    }
}

