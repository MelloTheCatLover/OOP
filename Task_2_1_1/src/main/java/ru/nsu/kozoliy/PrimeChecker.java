package ru.nsu.kozoliy;

/**
 * This class check number prime it is or not.
 */
public class PrimeChecker {

    /**
     * Checks if the given number is prime.
     *
     * @param number the number to check
     * @return true if the number is prime, otherwise false
     */
    static boolean isPrime(long number) {
        if (number < 2) {
            return false;
        }
        if (number == 2 || number == 3) {
            return true;
        }
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }

        long sqrtN = (long) Math.sqrt(number);

        for (long i = 6L; i <= sqrtN; i += 6) {
            if (number % (i - 1) == 0 || number % (i + 1) == 0) {
                return false;
            }
        }
        return true;
    }
}
