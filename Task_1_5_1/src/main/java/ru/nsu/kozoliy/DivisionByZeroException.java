package ru.nsu.kozoliy;

public class DivisionByZeroException extends CalculatorException {
    public DivisionByZeroException() {
        super("Cannot divide by zero");
    }
}
