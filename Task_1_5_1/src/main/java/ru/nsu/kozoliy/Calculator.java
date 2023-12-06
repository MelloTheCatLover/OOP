package ru.nsu.kozoliy;

import java.util.Stack;
import java.util.EmptyStackException;


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
     * Factory class responsible for creating different operators.
     */
    public static class Factory {
        /**
         * Creates an operator based on the provided type.
         *
         * @param type The type of the operator.
         * @return The created operator.
         * @throws InvalidInputException If the input type is not recognized.
         */
        public Operator createOperator(String type) {
            return switch (type) {
                case "+" -> new Add();
                case "-" -> new Subtract();
                case "/" -> new Divide();
                case "*" -> new Multiply();
                case "log" -> new Log();
                case "pow" -> new Power();
                case "sqrt" -> new Sqrt();
                case "sin" -> new Sin();
                case "cos" -> new Cos();
                case "tan" -> new Tan();
                case "cot" -> new Cot();
                default -> throw new InvalidInputException(type);
            };
        }
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


    public static class Add implements Operator {
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

    public static class Subtract implements Operator {
        @Override
        public void findAnswer(Stack<Double> stack) {
            try {
                double operand1 = stack.pop();
                double operand2 = stack.pop();
                stack.push(operand1 - operand2);
            } catch (EmptyStackException e) {
                throw new EmptyStackException();
            }
        }
    }

    public static class Multiply implements Operator {
        @Override
        public void findAnswer(Stack<Double> stack) {
            try {
                double operand1 = stack.pop();
                double operand2 = stack.pop();
                stack.push(operand1 * operand2);
            } catch (EmptyStackException e) {
                throw new EmptyStackException();
            }
        }
    }

    public static class Divide implements Operator {
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
            if (operand2 == 0) {
                throw new DivisionByZeroException();
            }
            stack.push(operand1 / operand2);
        }
    }

    public static class Power implements Operator {
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
            if (operand1 == 0 && operand2 <= 0) {
                throw new DivisionByZeroException();
            }
            stack.push(Math.pow(operand1, operand2));
        }
    }

    public static class Sqrt implements Operator {
        @Override
        public void findAnswer(Stack<Double> stack) {
            double operand1;
            try {
                operand1 = stack.pop();
            } catch (EmptyStackException e) {
                throw new EmptyStackException();
            }
            if (operand1 < 0) {
                throw new InvalidSqrtException(operand1);
            }
            stack.push(Math.sqrt(operand1));
        }
    }

    public static class Log implements Operator {
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

    public static class Cos implements Operator {
        @Override
        public void findAnswer(Stack<Double> stack) {
            try {
                double operand1 = stack.pop();
                stack.push(Math.cos(operand1));
            } catch (EmptyStackException e) {
                throw new EmptyStackException();
            }
        }
    }

    public static class Sin implements Operator {

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

    public static class Tan implements Operator {

        @Override
        public void findAnswer(Stack<Double> stack) {
            try {
                double operand1 = stack.pop();
                stack.push(Math.sin(operand1) / Math.cos(operand1));
            } catch (EmptyStackException e) {
                throw new EmptyStackException();
            }
        }
    }

    public static class Cot implements Operator {

        @Override
        public void findAnswer(Stack<Double> stack) {
            try {
                double operand1 = stack.pop();
                stack.push(Math.cos(operand1) / Math.sin(operand1));
            } catch (EmptyStackException e) {
                throw new EmptyStackException();
            }
        }
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
            Calculator calc = new Calculator(new Calculator.Factory());
            String input = "cos ";

            System.out.println(calc.calculate(input));
        }
    }
}
