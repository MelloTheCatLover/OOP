package ru.nsu.kozoliy;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Represents the square root operation.
 */
public class Sqrt implements Operator {
    @Override
    public void findAnswer(Stack<Double> stack) {
        double operand1;
        try {
            operand1 = stack.pop();
        } catch (RuntimeException e) {
            throw new EmptyStackException();
        }
        if (operand1 < 0) {
            throw new InvalidSqrtException(operand1);
        }
        stack.push(Math.sqrt(operand1));
    }
}
