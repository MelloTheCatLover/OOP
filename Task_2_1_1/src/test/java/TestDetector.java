


import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.ChartBuilder;
import ru.nsu.kozoliy.ParallelDetector;
import ru.nsu.kozoliy.ParallelStreamDetector;
import ru.nsu.kozoliy.SequentialDetector;



import static org.junit.jupiter.api.Assertions.*;

public class TestDetector {

    long[] numbers;
    @Test
    public void testSequential() {
        long prime = 1000000007;
        long[] numbers = new long[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = prime;
        }
        assertFalse(SequentialDetector.SequentialNoPrimesDetector(numbers));
    }

    @Test
    public void testParallel() {
        long prime = 1000000007;
        long[] numbers = new long[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = prime;
        }
        assertFalse(ParallelDetector.parallelNoPrimesDetector(numbers,4));
    }

    @Test
    public void testParallel1Thread() {
        long prime = 1000000007;
        long[] numbers = new long[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = prime;
        }
        assertFalse(ParallelDetector.parallelNoPrimesDetector(numbers,1));
    }
    @Test
    public void testParallelNonPrime() {
        long prime = 1000000007;
        long[] numbers = new long[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = prime;
        }
        numbers[9999] = 10;
        assertTrue(ParallelDetector.parallelNoPrimesDetector(numbers,5));
    }

    @Test
    public void testSequentialNonPrime() {
        long prime = 1000000007;
        long[] numbers = new long[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = prime;
        }
        numbers[9999] = 10;
        assertTrue(SequentialDetector.SequentialNoPrimesDetector(numbers));
    }

    @Test
    public void testParallelStreamNonPrime() {
        long prime = 1000000007;
        long[] numbers = new long[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = prime;
        }
        numbers[9999] = 10;
        assertTrue(ParallelStreamDetector.parallelStreamNoPrimesDetector(numbers));
    }


    @Test
    public void testParallelStream() {
        long[] nums = new long[1];
        assertTrue(ParallelStreamDetector.parallelStreamNoPrimesDetector(nums));
    }


    @Test
    public void testGraphic() {
        ChartBuilder.main();
    }
}