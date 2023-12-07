package ru.nsu.kozoliy;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Represents the sin operation.
 */
public class Sin implements Operator {

    @Override
    public void findAnswer(Stack<Double> stack) {
        try {
            double operand1 = stack.pop();
            stack.push(Math.sin(operand1));
        } catch (EmptyStackException e) {
            throw new EmptyStackException();
        }
    }
}