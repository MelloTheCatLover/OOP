package ru.nsu.kozoliy;

import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {

    @Test
    public  void startingTest() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "sin + - 1 2 1";
        double res = calculator.calculate(input);
        assertEquals(res, 0);
    }

    @Test
    public  void AddTest() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "+ 10000000 12";
        double res = calculator.calculate(input);
        assertEquals(res, 10000012);
    }

    @Test
    public  void SubTest() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "- 100000 12";
        double res = calculator.calculate(input);
        assertEquals(res, 99988.0);
        input = "- 10 1000";
        res = calculator.calculate(input);
        assertEquals(res, -990.0);
        input = "- 5.542 0.540";
        res = calculator.calculate(input);
        assertEquals(res, 5.002, 0.001);
    }

    @Test
    public  void MultiplyTest() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "* 2 2";
        double res = calculator.calculate(input);
        assertEquals(res, 4);
        input = "* - 1 2 1000";
        res = calculator.calculate(input);
        assertEquals(res, -1000.0);
        input = "* 100 0";
        res = calculator.calculate(input);
        assertEquals(res, 0, 0.001);
        input = "* - 1 2.5 - 2.5 6";
        res = calculator.calculate(input);
        assertEquals(res, 5.25, 0.001);
    }

    @Test
    public  void PowerTest() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "pow 2 2";
        double res = calculator.calculate(input);
        assertEquals(res, 4);
        input = "pow 2 - 1 2";
        res = calculator.calculate(input);
        assertEquals(res, 0.5);
        input = "pow 2 0";
        res = calculator.calculate(input);
        assertEquals(res, 1, 0.001);
        input = "pow 3 - 1 10";
        res = calculator.calculate(input);
        assertEquals(res, 0.0000508, 0.00000001);
    }

    @Test
    public  void SqrtTest() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "sqrt 4";
        double res = calculator.calculate(input);
        assertEquals(res, 2);
        input = "sqrt 10";
        res = calculator.calculate(input);
        assertEquals(res, 3.16, 0.01);

    }

    @Test
    public  void TrigonometryTest() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "cos 2";
        double res = calculator.calculate(input);
        assertEquals(res, -0.416, 0.001);
        input = "sin / 3.14159265359 2";
        res = calculator.calculate(input);
        assertEquals(res, 1, 0.0001);
        input = "tan 0";
        res = calculator.calculate(input);
        assertEquals(res, 0, 0.001);
        input = "cot 8";
        res = calculator.calculate(input);
        assertEquals(res, -0.147, 0.001);
    }

    @Test
    public void LogTest() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "log 2 10";
        double res = calculator.calculate(input);
        assertEquals(res, 3.321, 0.001);
        input = "log 10 2";
        res = calculator.calculate(input);
        assertEquals(res, 0.3, 0.1);
    }


    @Test
    public void divisionByZeroTest() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "/ 2 0";
        assertThrows(DivisionByZeroException.class, () -> {
            calculator.calculate(input);
        });
    }

    @Test
    public void emptyStackTest() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "cos";
        assertThrows(EmptyStackException.class, () -> {
            calculator.calculate(input);
        });
        Calculator calculator2 = new Calculator(new Calculator.Factory());
        String input2 = "+ 1";
        assertThrows(EmptyStackException.class, () -> {
            calculator2.calculate(input2);
        });
        Calculator calculator3 = new Calculator(new Calculator.Factory());
        String input3 = "- 1";
        assertThrows(EmptyStackException.class, () -> {
            calculator3.calculate(input3);
        });
        Calculator calculator4 = new Calculator(new Calculator.Factory());
        String input4 = "* 1";
        assertThrows(EmptyStackException.class, () -> {
            calculator4.calculate(input4);
        });
        Calculator calculator5 = new Calculator(new Calculator.Factory());
        String input5 = "sqrt ";
        assertThrows(EmptyStackException.class, () -> {
            calculator5.calculate(input5);
        });
        Calculator calculator6 = new Calculator(new Calculator.Factory());
        String input6 = "pow 1";
        assertThrows(EmptyStackException.class, () -> {
            calculator6.calculate(input6);
        });

    }

    @Test
    public void WrongOperatorException() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "llog 8";
        assertThrows(InvalidInputException.class, () -> {
            calculator.calculate(input);
        });

        Calculator calculator2 = new Calculator(new Calculator.Factory());
        String input2 = "log a";
        assertThrows(InvalidInputException.class, () -> {
            calculator2.calculate(input2);
        });
    }

    @Test
    public void SqrtInputException() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "sqrt - 1 8";
        assertThrows(InvalidSqrtException.class, () -> {
            calculator.calculate(input);
        });
    }

    @Test
    public void LogInputException() {
        Calculator calculator = new Calculator(new Calculator.Factory());
        String input = "log - 1 2 10";
        assertThrows(InvalidLogException.class, () -> {
            calculator.calculate(input);
        });
        Calculator calculator2 = new Calculator(new Calculator.Factory());
        String input2 = "log 10 - 1 2";
        assertThrows(InvalidLogException.class, () -> {
            calculator2.calculate(input2);
        });
        Calculator calculator3 = new Calculator(new Calculator.Factory());
        String input3 = "log 1 2";
        assertThrows(InvalidLogException.class, () -> {
            calculator3.calculate(input3);
        });
    }
}
