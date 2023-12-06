package ru.nsu.kozoliy;

/**
 * Exception for division by zero.
 */
public class DivisionByZeroException extends CalculatorException {
    public DivisionByZeroException() {
        super("Cannot divide by zero");
    }
}
