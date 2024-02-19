package ru.nsu.kozoliy;

/**
 * This class provides methods for detecting non-prime numbers in an array sequentially.
 */
public class SequentialDetector {

    /**
     * Determines if there is at least one non-prime number in the given array sequentially.
     *
     * @param array the array of numbers to check
     * @return true if there is at least one non-prime number, otherwise false
     */
    public static boolean SequentialNoPrimesDetector(long[] array) {
        for (long j : array) {
            if (!isPrime(j)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given number is prime.
     *
     * @param number the number to check
     * @return true if the number is prime, otherwise false
     */
    static boolean isPrime(long number) {
        if (number < 2) return false;
        if (number == 2 || number == 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;

        long sqrtN = (long) Math.sqrt(number);

        for (long i = 6L; i <= sqrtN; i += 6) {
            if (number % (i - 1) == 0 || number % (i + 1) == 0) return false;
        }
        return true;
    }

}
