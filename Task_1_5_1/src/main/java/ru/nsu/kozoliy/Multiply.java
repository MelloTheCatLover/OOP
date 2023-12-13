package ru.nsu.kozoliy;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Represents the multiplication operation.
 */
public class Multiply implements Operator {
    @Override
    public void findAnswer(Stack<Double> stack) {
        try {
            double operand1 = stack.pop();
            double operand2 = stack.pop();
            stack.push(operand1 * operand2);
        } catch (RuntimeException e) {
            throw new EmptyStackException();
        }
    }
}

