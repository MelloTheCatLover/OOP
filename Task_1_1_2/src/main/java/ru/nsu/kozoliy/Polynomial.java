package ru.nsu.kozoliy;

import java.util.Arrays;
import java.util.Objects;

/**
 * Класс являет представление многочлена некоторой степени n.
 */
public class Polynomial {
    private final int[] coefficients;

    /**
     * Конструктор для того, чтобы создавать копию массиваБ для того,
     * чтобы исключить возможность изменять его извне.
     * Коэффициенты являются массивом coefficients, с некоторой длинной.
     * В массиве они идут по возрастанию степеней.
     */
    public Polynomial(int[] coefficients) {
        if (coefficients.length == 0) {
            this.coefficients = new int[]{0};
        } else {
            this.coefficients = Arrays.copyOf(coefficients, coefficients.length);
        }
    }

    /**
     * Метод для сложения двух полиномов.
     *
     * @param other - Некоторый другой полином.
     * @return - Возвращаемый результат.
     */
    public Polynomial plus(Polynomial other) {
        int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
        int[] ans = new int[maxLength];

        for (int i = 0; i < this.coefficients.length; i++) {
            ans[i] += this.coefficients[i];
        }

        for (int i = 0; i < other.coefficients.length; i++) {
            ans[i] += other.coefficients[i];
        }

        return new Polynomial(ans);
    }

    /**
     * Метод для вычитания двух полиномов.
     *
     * @param other - Некоторый другой полином.
     * @return - Возвращаемый результат.
     */
    public Polynomial minus(Polynomial other) {
        int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
        int[] ans = new int[maxLength];

        for (int i = 0; i < this.coefficients.length; i++) {
            ans[i] += this.coefficients[i];
        }

        for (int i = 0; i < other.coefficients.length; i++) {
            ans[i] -= other.coefficients[i];
        }

        return new Polynomial(ans);
    }

    /**
     * Метод для перемножения двух полиномов.
     *
     * @param other - Некоторый другой полином.
     * @return - Возвращаемый результат.
     */
    public Polynomial times(Polynomial other) {
        int[] ans = new int[this.coefficients.length + other.coefficients.length - 1];

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                ans[i + j] += this.coefficients[i] * other.coefficients[j];
            }
        }

        return new Polynomial(ans);
    }

    /**
     * Метод для вычисления значения полинома в некоторой точке, релизуется подстановкой.
     *
     * @param x - Некоторый другой полином.
     * @return - Возвращаемый результат.
     */
    public int evaluate(int x) {
        int result = 0;
        int power = 1;

        for (int coefficient : coefficients) {
            result += coefficient * power;
            power *= x;
        }

        return result;
    }

    /**
     * Метод для вычисления производной. Степень производной задает пользователь.
     *
     * @param i Степень дифференцирования.
     * @return Результат дифференцирования многочлена.
     */
    public Polynomial differentiate(int i) {
        int n = coefficients.length;
        int[] ans = new int[n - i];

        for (int j = 0; j < n - i; j++) {
            int coeff = coefficients[j + i];
            for (int k = 1; k <= i; k++) {
                coeff *= (j + k);
            }
            ans[j] = coeff;
        }

        return new Polynomial(ans);
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Polynomial other = (Polynomial) obj;
        if (this.coefficients.length != other.coefficients.length) {
            return false;
        }
        return Arrays.equals(coefficients, other.coefficients);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        return prime * Objects.hash(Arrays.hashCode(coefficients));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int n = coefficients.length;

        for (int i = n - 1; i >= 0; i--) {
            int coeff = coefficients[i];

            if (coeff != 0) {
                if (i < n - 1) {
                    sb.append(" + ");
                }

                if (coeff != 1 || i == 0) {
                    sb.append(coeff);
                }

                if (i > 0) {
                    sb.append("x");
                    if (i > 1) {
                        sb.append("^").append(i);
                    }
                }
            }
        }

        return sb.toString();
    }
}
