package ru.nsu.kozoliy;

/**
 * Factory class responsible for creating different operators.
 */
public class Factory {
    /**
     * Creates an operator based on the provided type.
     *
     * @param type The type of the operator.
     * @return The created operator.
     * @throws InvalidInputException If the input type is not recognized.
     */
    public Operator createOperator(String type) {
        if (type.equals("+")) {
            return new Add();
        } else if (type.equals("-")) {
            return new Subtract();
        } else if (type.equals("/")) {
            return new Divide();
        } else if (type.equals("*")) {
            return new Multiply();
        } else if (type.equals("log")) {
            return new Log();
        } else if (type.equals("pow")) {
            return new Power();
        } else if (type.equals("sqrt")) {
            return new Sqrt();
        } else if (type.equals("sin")) {
            return new Sin();
        } else if (type.equals("cos")) {
            return new Cos();
        } else if (type.equals("tan")) {
            return new Tan();
        } else if (type.equals("cot")) {
            return new Cot();
        } else {
            throw new InvalidInputException("Wrong operator");
        }
    }
}