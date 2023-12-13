package ru.nsu.kozoliy;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Represents the power operation.
 */
public class Power implements Operator {
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
        if (operand1 == 0 && operand2 <= 0) {
            throw new DivisionByZeroException();
        }
        stack.push(Math.pow(operand1, operand2));
    }
}