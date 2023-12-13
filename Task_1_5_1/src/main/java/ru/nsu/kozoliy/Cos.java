package ru.nsu.kozoliy;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Represents the cos operation.
 */
public class Cos implements Operator {
    @Override
    public void findAnswer(Stack<Double> stack) {
        try {
            double operand1 = stack.pop();
            stack.push(Math.cos(operand1));
        } catch (RuntimeException e) {
            throw new EmptyStackException();
        }
    }
}
