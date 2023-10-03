package ru.nsu.kozoliy;

public class Main {

    /**
     * Точка входа в программу.
     */
    public static void main(String[] args) {
        int[] testPolynom1 = {4, 3, 6, 7};
        int[] testPolynom2 = {3, 2, 8};

        Polynomial p1 = new Polynomial(testPolynom1);
        Polynomial p2 = new Polynomial(testPolynom2);

        Polynomial result1 = p1.plus(p2.differentiate(2));
        Polynomial result2 = p1.times(p2);

        System.out.println(result1.toString());
        System.out.println(result2.evaluate(2));
    }
}
