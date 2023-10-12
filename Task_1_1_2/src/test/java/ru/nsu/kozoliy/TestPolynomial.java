package ru.nsu.kozoliy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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

    @Test
    void voidSum() {
        Polynomial voidPolynomial = new Polynomial(new int[]{});

        assertEquals(voidPolynomial.plus(polynomial1), polynomial1);
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

    @Test
    public void testAdditionAndSubtraction() {
        Polynomial expected = polynomial1;
        Polynomial result = polynomial1.plus(polynomial2);
        result = result.minus(polynomial2);

        assertEquals(expected, result);
    }

    @Test
    public void testAdditionAndMultiplicationByTwo() {
        Polynomial twoAddition = polynomial1.plus(polynomial1);
        Polynomial multiByTwo  = polynomial1.times(new Polynomial(new int[]{2}));

        assertEquals(twoAddition, multiByTwo);
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

    @Test
    public void testMultiplicationLargeExponent() {
        Polynomial result = polynomial2.times(polynomial2.times(polynomial2.times(polynomial2)));
        Polynomial expected = new Polynomial(new int[]{81, 216, 1080, 1824, 4624,
            4864, 7680, 4096, 4096});

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

    @Test
    public void testAllInOne() {
        Polynomial polynomial3 = new Polynomial(new int[] {10, 2, 12, 4, 5});
        Polynomial polynomial4 = new Polynomial(new int[] {1, 2, 3});
        int result = (polynomial1.plus(polynomial2.times((polynomial3.differentiate(2))
            .minus(polynomial4)))).evaluate(10);
        int expected = 4898723;

        assertEquals(expected, result);
    }

    @Test
    public void testEvaluateZeroPolynomial() {
        Polynomial zeroPolynom = new Polynomial(new int[]{0});
        int result = zeroPolynom.evaluate(12);
        int expected = 0;

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

    @Test
    public void testSecondDifferentiate() {
        Polynomial result = new Polynomial(new int[]{3, 6, 45, 12, 13});
        result = result.differentiate(2);
        Polynomial expected = new Polynomial(new int[]{90, 72, 156});

        assertEquals(expected, result);
    }

    @Test
    void testNotEqual() {
        Polynomial polynomial3 = new Polynomial(new int[]{5, 2, 2, 1});
        Polynomial polynomial4 = new Polynomial(new int[]{4, 2, 2, 1});
        assertNotEquals(polynomial3, polynomial4);
    }

    @Test
    void testVoidEqual() {
        Polynomial polynomial3 = new Polynomial(new int[]{});
        Polynomial polynomial4 = new Polynomial(new int[]{});
        assertEquals(polynomial3, polynomial4);
    }

    @Test
    void testLowerZerosSub() {
        Polynomial polynomial3 = new Polynomial(new int[]{0, 0, 0, 2});
        Polynomial polynomial4 = new Polynomial(new int[]{5, 4, 2});
        Polynomial result = polynomial3.minus(polynomial4);
        Polynomial expected = new Polynomial(new int[]{-5, -4, -2, 2});
        assertEquals(result, expected);
    }

    @Test
    void testLowerZerosSum() {
        Polynomial polynomial3 = new Polynomial(new int[]{0, 0, 0, 2});
        Polynomial polynomial4 = new Polynomial(new int[]{5, -4, 2});
        Polynomial result = polynomial3.plus(polynomial4);
        Polynomial expected = new Polynomial(new int[]{5, -4, 2, 2});
        assertEquals(result, expected);
    }

    @Test
    void testSubToZero() {
        Polynomial polynomial3 = new Polynomial(new int[]{3, -2, 1});
        Polynomial expected = new Polynomial(new int[]{});
        Polynomial result = polynomial3.minus(polynomial3);
        assertEquals(result, expected);
    }

    @Test
    void testSumToZero() {
        Polynomial polynomial3 = new Polynomial(new int[]{});
        Polynomial polynomial4 = new Polynomial(new int[]{3, 2, 1});
        assertEquals(polynomial3.plus(polynomial4), polynomial4);
    }

    @Test
    void testMultiplicationVoidFirst() {
        Polynomial polynomial3 = new Polynomial(new int[]{});
        Polynomial polynomial4 = new Polynomial(new int[]{2, 5, 4});
        Polynomial expected = new Polynomial(new int[]{});
        assertEquals(polynomial3.times(polynomial4), expected);
    }

    @Test
    void testMultiplicationVoidSecond() {
        Polynomial polynomial3 = new Polynomial(new int[]{2, 5, 4});
        Polynomial polynomial4 = new Polynomial(new int[]{});
        Polynomial expected = new Polynomial(new int[]{});
        assertEquals(polynomial3.times(polynomial4), expected);
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

