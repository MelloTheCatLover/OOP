package ru.nsu.kozoliy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс для тестирования функциональности класса Polynomial.
 */
public class TestPolynomial {
    private Polynomial polynomial1;
    private Polynomial polynomial2;

    /**
     * Перед каждым тестовым методом инициализирует объекты Polynomial с тестовыми коэффициентами.
     */
    @BeforeEach
    public void setUp() {
        int[] coefficients1 = {4, 3, 6, 7};
        int[] coefficients2 = {3, 2, 8};

        polynomial1 = new Polynomial(coefficients1);
        polynomial2 = new Polynomial(coefficients2);
    }

    /**
     * Тест для проверки операции сложения многочленов.
     */
    @Test
    public void testAddition() {
        Polynomial result = polynomial1.plus(polynomial2);
        Polynomial expected = new Polynomial(new int[]{7, 5, 14, 7});

        assertEquals(expected, result);
    }

    /**
     * Тест для проверки операции вычитания многочленов.
     */
    @Test
    public void testSubtraction() {
        Polynomial result = polynomial1.minus(polynomial2);
        Polynomial expected = new Polynomial(new int[]{1, 1, -2, 7});

        assertEquals(expected, result);
    }

    /**
     * Тест для проверки операции умножения многочленов.
     */
    @Test
    public void testMultiplication() {
        Polynomial result = polynomial1.times(polynomial2);
        Polynomial expected = new Polynomial(new int[]{12, 17, 56, 57, 62, 56});

        assertEquals(expected, result);
    }

    /**
     * Тест для проверки операции вычисления значения многочлена в точке.
     */
    @Test
    public void testEvaluate() {
        int result = polynomial1.evaluate(2);
        int expected = 90;

        assertEquals(expected, result);
    }

    /**
     * Тест для проверки операции взятия производной многочлена.
     */
    @Test
    public void testDifferentiate() {
        Polynomial result = polynomial1.differentiate(1);
        Polynomial expected = new Polynomial(new int[]{3, 12, 21});

        assertEquals(expected, result);
    }

    /**
     * Тест для проверки преобразования многочлена в строковое представление.
     */
    @Test
    public void testToString() {
        String result = polynomial1.toString();
        String expected = "7x^3 + 6x^2 + 3x + 4";

        assertEquals(expected, result);
    }
}

