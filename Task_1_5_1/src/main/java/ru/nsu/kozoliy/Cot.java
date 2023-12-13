package ru.nsu.kozoliy;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Represents the cot operation.
 */
public class Cot implements Operator {

    @Override
    public void findAnswer(Stack<Double> stack) {
        try {
            double operand1 = stack.pop();
            stack.push(Math.cos(operand1) / Math.sin(operand1));
        } catch (RuntimeException e) {
            throw new EmptyStackException();
        }
    }
}