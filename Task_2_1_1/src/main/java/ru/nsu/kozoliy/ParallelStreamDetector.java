package ru.nsu.kozoliy;

import java.util.Arrays;

/**
 * This class provides methods for detecting non-prime numbers in an array using parallel streams.
 */
public class ParallelStreamDetector {

    /**
     * Determines if there is at least one non-prime number
     * in the given array using parallel streams.
     *
     * @param numbers the array of numbers to check
     * @return true if there is at least one non-prime number, otherwise false
     */
    public static boolean parallelStreamNoPrimesDetector(long[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(num -> !PrimeChecker.isPrime(num));
    }


    /**
     * Main method for testing the ParallelStreamNoPrimesDetector method.
     *
     * @param args command-line arguments
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        long[] nums = {6, 8, 7, 13, 5, 9, 4};
        System.out.println(parallelStreamNoPrimesDetector(nums)); // true
    }
}
