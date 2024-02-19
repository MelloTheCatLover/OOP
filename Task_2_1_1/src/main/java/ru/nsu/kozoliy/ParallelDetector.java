package ru.nsu.kozoliy;

import java.util.concurrent.atomic.AtomicBoolean;

public class ParallelDetector {

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
                        break; // Остановить поток, если найдено непростое число
                    }
                }
            });
            threads[i].start();
        }

        // Обработка исключения InterruptedException
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted while waiting for threads to finish: " + e.getMessage());
            Thread.currentThread().interrupt(); // Повторно прерывает поток
        }

        return hasNonPrime.get();
    }

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


    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        long[] nums = {6, 8, 7, 13, 5, 9, 4};
        System.out.println(ParallelNoPrimesDetector(nums, 2)); // true
    }
}
