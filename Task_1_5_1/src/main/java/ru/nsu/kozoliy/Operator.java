package ru.nsu.kozoliy;

import java.util.Stack;


/**
 * Interface representing an operator.
 */
public interface Operator {
    /**
     * Performs the operation on the stack.
     *
     * @param stack The stack used in the calculation.
     */
    void findAnswer(Stack<Double> stack);
}