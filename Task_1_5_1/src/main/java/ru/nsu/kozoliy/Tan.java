package ru.nsu.kozoliy;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Represents the tan operation.
 */
public class Tan implements Operator {

    @Override
    public void findAnswer(Stack<Double> stack) {
        try {
            double operand1 = stack.pop();
            stack.push(Math.sin(operand1) / Math.cos(operand1));
        } catch (RuntimeException e) {
            throw new EmptyStackException();
        }
    }
}