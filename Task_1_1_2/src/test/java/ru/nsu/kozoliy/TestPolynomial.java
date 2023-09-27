package ru.nsu.kozoliy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Класс для различных тестов проверки работоспособности функционала класса Polynomial.
 */
public class TestPolynomial {
    private Polynomial polynomial1;
    private Polynomial polynomial2;

    /**
     * Стартовые коэффициенты полиномов, которые используются в последующих тестах.
     */
    @BeforeEach
    public void setUp() {
        int[] coefficients1 = {4, 3, 6, 7};
        int[] coefficients2 = {3, 2, 8};

        polynomial1 = new Polynomial(coefficients1);
        polynomial2 = new Polynomial(coefficients2);
    }

    /**
     * Тест для проверки вывода решения примера.
     */
    @Test
    public void  testMain() {
        Polynomial.main(new String[0]);
    }

    /**
     * Тест для проверки правильности метода по сложению двух полиномов.
     */
    @Test
    public void testAddition() {
        Polynomial result = polynomial1.plus(polynomial2);
        Polynomial expected = new Polynomial(new int[]{7, 5, 14, 7});

        assertTrue(result.equals(expected));
    }

    /**
     * Тест для проверки правильности метода по вычитанию двух полиномов.
     */
    @Test
    public void testSubtraction() {
        Polynomial result = polynomial1.minus(polynomial2);
        Polynomial expected = new Polynomial(new int[]{1, 1, -2, 7});

        assertTrue(result.equals(expected));
    }

    /**
     * Тест для проверки правильности метода по умножению двух полиномов.
     */
    @Test
    public void testMultiplication() {
        Polynomial result = polynomial1.times(polynomial2);
        Polynomial expected = new Polynomial(new int[]{12, 17, 56, 57, 62, 56});

        assertTrue(result.equals(expected));
    }

    /**
     * Тест для проверки выводимого значения многочлена в некоторой точке.
     */
    @Test
    public void testEvaluate() {
        int result = polynomial1.evaluate(2);
        int expected = 90;

        assertEquals(expected, result);
    }

    /**
     * Тест для проверки расчета производной заданной степени у многочлена.
     */
    @Test
    public void testDifferentiate() {
        Polynomial result = polynomial1.differentiate(1);
        Polynomial expected = new Polynomial(new int[]{3, 12, 21});

        assertTrue(result.equals(expected));
    }

    /**
     * Тест для проверки вывода многочлена.
     */
    @Test
    public void testToString() {
        String result = polynomial1.toString();
        String expected = "7x^3 + 6x^2 + 3x + 4";

        assertEquals(expected, result);
    }
}

