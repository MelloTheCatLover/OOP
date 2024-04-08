import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.ParallelDetector;



/**
 * Test for Task_2_1_1.
 * Test for parallel detector.
 */
public class TestsForParallelDetector {

    @Test
    public void testParallel2() {
        long[] nums = {2, 123};
        assertTrue(ParallelDetector.parallelNoPrimesDetector(nums, 2));
        long[] nums2 = {961, 1234};
        assertTrue(ParallelDetector.parallelNoPrimesDetector(nums2, 2));
        long[] nums3 = {0, 1234};
        assertTrue(ParallelDetector.parallelNoPrimesDetector(nums3, 2));
    }


    @Test
    public void testParallel() {
        long prime = 1000000007;
        long[] numbers = new long[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = prime;
        }
        assertFalse(ParallelDetector.parallelNoPrimesDetector(numbers, 4));
    }

    @Test
    public void testParallel1Thread() {
        long prime = 1000000007;
        long[] numbers = new long[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = prime;
        }
        assertFalse(ParallelDetector.parallelNoPrimesDetector(numbers, 1));
    }

    @Test
    public void testParallelNonPrime() {
        long prime = 1000000007;
        long[] numbers = new long[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = prime;
        }
        numbers[9999] = 10;
        assertTrue(ParallelDetector.parallelNoPrimesDetector(numbers, 5));
    }

    @Test
    public void testParallelNonPrimeInMiddle() {
        long[] smallArray = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053,
            20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053,
            20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 12, 20165149, 6998051, 6998053,
            20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053,
            20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        assertTrue(ParallelDetector.parallelNoPrimesDetector(smallArray, 2));
    }
}
