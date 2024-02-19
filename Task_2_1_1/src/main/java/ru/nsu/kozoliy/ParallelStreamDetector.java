package ru.nsu.kozoliy;

import java.util.Arrays;

/**
 * This class provides methods for detecting non-prime numbers in an array using parallel streams.
 */
public class ParallelStreamDetector {

    /**
     * Determines if there is at least one non-prime number in the given array using parallel streams.
     *
     * @param numbers the array of numbers to check
     * @return true if there is at least one non-prime number, otherwise false
     */
    public static boolean ParallelStreamNoPrimesDetector(long[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(num -> !isPrime(num));
    }

    /**
     * Checks if the given number is prime.
     *
     * @param number the number to check
     * @return true if the number is prime, otherwise false
     */
    static boolean isPrime(long number) {
        if(number < 2) return false;
        if(number == 2 || number == 3) return true;
        if(number%2 == 0 || number%3 == 0) return false;

        long sqrtN = (long)Math.sqrt(number);

        for(long i = 6L; i<= sqrtN; i+=6) {
            if(number%(i-1) == 0 || number%(i+1) == 0) return false;
        }
        return true;
    }

    /**
     * Main method for testing the ParallelStreamNoPrimesDetector method.
     *
     * @param args command-line arguments
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        long[] nums = {6, 8, 7, 13, 5, 9, 4};
        System.out.println(ParallelStreamNoPrimesDetector(nums)); // true
    }
}
