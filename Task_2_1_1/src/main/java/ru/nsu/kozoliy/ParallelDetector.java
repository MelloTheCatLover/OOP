package ru.nsu.kozoliy;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class provides methods for detecting non-prime numbers in an array using parallel threads.
 */
public class ParallelDetector {

    /**
     * Determines if there is at least one non-prime number in the given array using parallel threads.
     *
     * @param numbers    the array of numbers to check
     * @param numThreads the number of threads to use for parallel processing
     * @return true if there is at least one non-prime number, otherwise false
     */
    public static boolean ParallelNoPrimesDetector(long[] numbers, int numThreads) {
        AtomicBoolean hasNonPrime = new AtomicBoolean(false);
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int start = i * (numbers.length / numThreads);
            final int end = (i == numThreads - 1) ? numbers.length : (i + 1) * (numbers.length / numThreads);

            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    if (!isPrime(numbers[j])) {
                        hasNonPrime.set(true);
                        break; // Stop the thread if a non-prime number is found
                    }
                }
            });
            threads[i].start();
        }

        // Handle InterruptedException
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted while waiting for threads to finish: " + e.getMessage());
            Thread.currentThread().interrupt(); // Re-interrupts the thread
        }

        return hasNonPrime.get();
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

    /**
     * Main method for testing the ParallelNoPrimesDetector method.
     *
     * @param args command-line arguments
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        long[] nums = {6, 8, 7, 13, 5, 9, 4};
        System.out.println(ParallelNoPrimesDetector(nums, 2)); // true
    }
}
