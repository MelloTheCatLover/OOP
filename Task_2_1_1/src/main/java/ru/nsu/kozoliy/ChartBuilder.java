package ru.nsu.kozoliy;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 * This class builds a chart comparing the execution times of different solutions.
 */
public class ChartBuilder {

    /**
     * Main method to run the program and display the chart.
     */
    public static void main(String[] args) {
        // Create arrays with sample data
        long[] smallArray = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053,
            20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053,
            20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053,
            20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053,
            20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        long prime = 1000000007;
        long[] largeArray = new long[1000];
        for (int i = 0; i < 1000; i++) {
            largeArray[i] = prime;
        }

        // Perform sequential detection for small array
        long startTimeSequential = System.nanoTime();
        boolean resultSequential = SequentialDetector.sequentialNoPrimesDetector(smallArray);
        long endTimeSequential = System.nanoTime();
        long timeElapsedSequentialSmallArray = endTimeSequential - startTimeSequential;

        System.out.println("Sequential Solution Result: " + resultSequential);
        System.out.println("Time Elapsed for Sequential Solution Small Array: "
                + timeElapsedSequentialSmallArray + " nanoseconds");

        // Perform sequential detection for large array
        startTimeSequential = System.nanoTime();
        resultSequential = SequentialDetector.sequentialNoPrimesDetector(largeArray);
        endTimeSequential = System.nanoTime();
        long timeElapsedSequentialLargeArray = endTimeSequential - startTimeSequential;

        System.out.println("Sequential Solution Result: " + resultSequential);
        System.out.println("Time Elapsed for Sequential Solution Large Array: "
                + timeElapsedSequentialLargeArray + " nanoseconds");

        // Perform parallel stream detection for small array
        long startTimeParallelStream = System.nanoTime();
        boolean resultParallelStream = ParallelStreamDetector
                .parallelStreamNoPrimesDetector(smallArray);
        long endTimeParallelStream = System.nanoTime();
        long timeElapsedParallelStreamSmallArray = endTimeParallelStream - startTimeParallelStream;

        System.out.println("Parallel Stream Solution Result: " + resultParallelStream);
        System.out.println("Time Elapsed for Parallel Stream Solution: "
                + timeElapsedParallelStreamSmallArray + " nanoseconds");

        // Perform parallel stream detection for large array
        startTimeParallelStream = System.nanoTime();
        ParallelStreamDetector.parallelStreamNoPrimesDetector(largeArray);
        endTimeParallelStream = System.nanoTime();
        long timeElapsedParallelStreamLargeArray = endTimeParallelStream - startTimeParallelStream;

        System.out.println("Parallel Stream Solution Result: " + resultParallelStream);
        System.out.println("Time Elapsed for Parallel Stream Solution: "
                + timeElapsedParallelStreamLargeArray + " nanoseconds");

        // Perform parallel detection with different number of threads for small array
        long startTimeParallel;
        long endTimeParallel;
        long timeElapsedParallel;
        long[] arrayOfResultSmallArray = new long[9];
        for (int numOfThreads = 2; numOfThreads <= 10; numOfThreads++) {
            startTimeParallel = System.nanoTime();
            ParallelDetector.parallelNoPrimesDetector(smallArray, numOfThreads);
            endTimeParallel = System.nanoTime();
            timeElapsedParallel = endTimeParallel - startTimeParallel;
            arrayOfResultSmallArray[numOfThreads - 2] = timeElapsedParallel;
        }

        // Perform parallel detection with different number of threads for large array
        long[] arrayOfResultLargeArray = new long[9];
        for (int numOfThreads = 2; numOfThreads <= 10; numOfThreads++) {
            startTimeParallel = System.nanoTime();
            ParallelDetector.parallelNoPrimesDetector(largeArray, numOfThreads);
            endTimeParallel = System.nanoTime();
            timeElapsedParallel = endTimeParallel - startTimeParallel;
            arrayOfResultLargeArray[numOfThreads - 2] = timeElapsedParallel;
        }

        /*
        // Create and display the chart
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Execution Time Comparison");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(timeElapsedSequentialSmallArray, "Sequential Detector",
                    "Sequential");
            dataset.addValue(timeElapsedSequentialLargeArray, "Sequential Detector Large",
                    "Sequential");
            dataset.addValue(arrayOfResultSmallArray[0], "Parallel Detector",
                    "Threads: 2");
            dataset.addValue(arrayOfResultLargeArray[0], "Parallel Detector Large",
                    "Threads: 2");
            dataset.addValue(arrayOfResultSmallArray[1], "Parallel Detector",
                    "Threads 3");
            dataset.addValue(arrayOfResultLargeArray[1], "Parallel Detector Large",
                    "Threads 3");
            dataset.addValue(arrayOfResultSmallArray[2], "Parallel Detector",
                    "Threads: 4");
            dataset.addValue(arrayOfResultLargeArray[2], "Parallel Detector Large",
                    "Threads: 4");
            dataset.addValue(arrayOfResultSmallArray[3], "Parallel Detector",
                    "Threads: 5");
            dataset.addValue(arrayOfResultLargeArray[3], "Parallel Detector Large",
                    "Threads: 5");
            dataset.addValue(arrayOfResultSmallArray[4], "Parallel Detector",
                    "Threads 6");
            dataset.addValue(arrayOfResultLargeArray[4], "Parallel Detector Large",
                    "Threads 6");
            dataset.addValue(arrayOfResultSmallArray[5], "Parallel Detector",
                    "Threads 7");
            dataset.addValue(arrayOfResultLargeArray[5], "Parallel Detector Large",
                    "Threads 7");
            dataset.addValue(arrayOfResultSmallArray[6], "Parallel Detector",
                    "Threads: 8");
            dataset.addValue(arrayOfResultLargeArray[6], "Parallel Detector Large",
                    "Threads: 8");
            dataset.addValue(arrayOfResultSmallArray[7], "Parallel Detector",
                    "Threads: 9");
            dataset.addValue(arrayOfResultLargeArray[7], "Parallel Detector Large",
                    "Threads: 9");
            dataset.addValue(arrayOfResultSmallArray[8], "Parallel Detector",
                    "Threads: 10");
            dataset.addValue(arrayOfResultLargeArray[8], "Parallel Detector Large",
                    "Threads: 10");
            dataset.addValue(timeElapsedParallelStreamSmallArray, "ParallelStream Detector",
                    "Type 10");
            dataset.addValue(timeElapsedParallelStreamLargeArray,
                    "ParallelStream Detector Large", "Type 10");

            JFreeChart chart = ChartFactory.createBarChart(
                    "Execution Time Comparison",
                    "Solution Type",
                    "Execution Time (nanoseconds)",
                    dataset
            );

            ChartPanel chartPanel = new ChartPanel(chart);
            frame.setLayout(new BorderLayout());
            frame.add(chartPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

         */

    }
}
