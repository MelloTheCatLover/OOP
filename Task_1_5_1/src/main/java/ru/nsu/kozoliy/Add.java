package ru.nsu.kozoliy;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Represents the addition operation.
 */
public class Add implements Operator {
    /**
     * Performs the addition operation on the stack.
     *
     * @param stack The stack used in the calculation.
     * @throws EmptyStackException If the stack is empty and an operand is required.
     */
    @Override
    public void findAnswer(Stack<Double> stack) {
        try {
            double operand1 = stack.pop();
            double operand2 = stack.pop();
            stack.push(operand1 + operand2);
        } catch (EmptyStackException e) {
            throw new EmptyStackException();
        }
    }
}
