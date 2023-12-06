package ru.nsu.kozoliy;

public class InvalidLogException extends InvalidInputException {
    public InvalidLogException(double input) {
        super("Invalid Log input: " + input);
    }
}
