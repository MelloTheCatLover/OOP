package ru.nsu.kozoliy;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Represents the logarithm operation.
 */
public class Log implements Operator {
    @Override
    public void findAnswer(Stack<Double> stack) {
        double operand1;
        double operand2;
        try {
            operand1 = stack.pop();
            operand2 = stack.pop();
        } catch (EmptyStackException e) {
            throw new EmptyStackException();
        }
        if (operand1 < 0 || operand1 == 1) {
            throw new InvalidLogException(operand1);
        }
        if (operand2 < 0) {
            throw new InvalidLogException(operand2);
        }
        stack.push(Math.log(operand2) / Math.log(operand1));
    }
}
