import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.ParallelStreamDetector;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsForParallelStreamDetector {
    
    @Test
    public void testParallelStream2() {
        long[] nums = {2, 123};
        assertTrue(ParallelStreamDetector.parallelStreamNoPrimesDetector(nums));
        long[] nums2 = {961, 1234};
        assertTrue(ParallelStreamDetector.parallelStreamNoPrimesDetector(nums2));
        long[] nums3 = {0, 1234};
        assertTrue(ParallelStreamDetector.parallelStreamNoPrimesDetector(nums3));
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
    public void testParallelStreamNonPrimeInMiddle() {
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

        assertTrue(ParallelStreamDetector.parallelStreamNoPrimesDetector(smallArray));
    }


    @Test
    public void testParallelStreamEmpty() {
        long[] nums = new long[1];
        assertTrue(ParallelStreamDetector.parallelStreamNoPrimesDetector(nums));
    }
}
