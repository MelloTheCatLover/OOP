package ru.nsu.kozoliy;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Represents the division operation.
 */
public class Divide implements Operator {
    @Override
    public void findAnswer(Stack<Double> stack) {
        double operand1;
        double operand2;
        try {
            operand1 = stack.pop();
            operand2 = stack.pop();
        } catch (RuntimeException e) {
            throw new EmptyStackException();
        }
        if (operand2 == 0) {
            throw new DivisionByZeroException();
        }
        stack.push(operand1 / operand2);
    }
}
