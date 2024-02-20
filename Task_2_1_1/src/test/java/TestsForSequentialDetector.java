import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.SequentialDetector;




/**
 * Test for Task_2_1_1.
 * Test for sequential detector.
 */
public class TestsForSequentialDetector {


    @Test
    public void testSequential() {
        long prime = 1000000007;
        long[] numbers = new long[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = prime;
        }
        assertFalse(SequentialDetector.sequentialNoPrimesDetector(numbers));
    }



    @Test
    public void testSequentialAllPrimeException() {
        long[] nums = {2, 123};
        assertTrue(SequentialDetector.sequentialNoPrimesDetector(nums));
        long[] nums2 = {961, 1234};
        assertTrue(SequentialDetector.sequentialNoPrimesDetector(nums2));
        long[] nums3 = {0, 1234};
        assertTrue(SequentialDetector.sequentialNoPrimesDetector(nums3));
    }

    @Test
    public void testSequentialEmpty() {
        long[] nums = {};
        assertFalse(SequentialDetector.sequentialNoPrimesDetector(nums));
    }

    @Test
    public void testSequentialNonPrimeInEnd() {
        long prime = 1000000007;
        long[] numbers = new long[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = prime;
        }
        numbers[9999] = 10;
        assertTrue(SequentialDetector.sequentialNoPrimesDetector(numbers));
    }


    @Test
    public void testSequentialNonPrimeInMiddle() {
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

        assertTrue(SequentialDetector.sequentialNoPrimesDetector(smallArray));
    }
}
