package ru.nsu.kozoliy;

import java.util.EmptyStackException;
import java.util.Stack;



/**
 * A simple calculator that performs operations based on reverse Polish notation.
 */
public class Calculator {

    private final Factory factory;
    private final Stack<Double> stack;

    /**
     * Constructs a Calculator with the specified Factory.
     *
     * @param factory The factory used to create operators.
     */
    public Calculator(Factory factory) {
        this.factory = factory;
        this.stack = new Stack<>();
    }



    /**
     * Checks if a given string is a number.
     *
     * @param element The string to check.
     * @return True if the string is a number, false otherwise.
     */
    private static boolean isNumber(String element) {
        try {
            Double.parseDouble(element);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Calculates the result of the expression provided in reverse Polish notation.
     *
     * @param input The input expression in reverse Polish notation.
     * @return The result of the calculation.
     */
    public double calculate(String input) {
        String[] expression = input.split(" ");
        for (int i = expression.length - 1; i >= 0; i--) {
            if (isNumber(expression[i])) {
                this.stack.push(Double.parseDouble(expression[i]));
            } else {
                Operator func = this.factory.createOperator(expression[i]);
                func.findAnswer(this.stack);
            }
        }
        return this.stack.pop();
    }


    /**
     * Main class for testing the Calculator.
     */
    public static class Main {
        /**
         * The main method for testing the Calculator.
         *
         * @param args Command-line arguments (not used).
         */
        @ExcludeFromJacocoGeneratedTestReport
        public static void main(String[] args) {
            Calculator calc = new Calculator(new Factory());
            String input = "cos ";

            System.out.println(calc.calculate(input));
        }
    }
}
