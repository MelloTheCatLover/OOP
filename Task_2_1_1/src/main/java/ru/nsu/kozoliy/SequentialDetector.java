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
    public static boolean sequentialNoPrimesDetector(long[] array) {
        for (long j : array) {
            if (!PrimeChecker.isPrime(j)) {
                return true;
            }
        }
        return false;
    }
}
