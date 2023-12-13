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
        Operator operator = null;
        switch (type) {
            case "+":
                operator = new Add();
                break;
            case "-":
                operator = new Subtract();
                break;
            case "/":
                operator = new Divide();
                break;
            case "*":
                operator = new Multiply();
                break;
            case "log":
                operator = new Log();
                break;
            case "pow":
                operator = new Power();
                break;
            case "sqrt":
                operator = new Sqrt();
                break;
            case "sin":
                operator = new Sin();
                break;
            case "cos":
                operator = new Cos();
                break;
            case "tan":
                operator = new Tan();
                break;
            case "cot":
                operator = new Cot();
                break;
            default:
                throw new InvalidInputException("Wrong operator");
        }
        return operator;
    }
}