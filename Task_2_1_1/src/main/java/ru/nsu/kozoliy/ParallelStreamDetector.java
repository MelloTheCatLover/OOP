package ru.nsu.kozoliy;

import java.util.Arrays;

public class ParallelStreamDetector {

    public static boolean ParallelStreamNoPrimesDetector(long[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(num -> !isPrime(num));
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
        System.out.println(ParallelStreamNoPrimesDetector(nums)); // true
    }
}