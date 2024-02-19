package ru.nsu.kozoliy;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;


public class ChartBuilder {
    public static void main() {
        long[] smallArray = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053,
                20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
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


        long startTimeSequential = System.nanoTime();
        boolean resultSequential = SequentialDetector.SequentialNoPrimesDetector(smallArray);
        long endTimeSequential = System.nanoTime();
        long timeElapsedSequentialSmallArray = endTimeSequential - startTimeSequential;

        System.out.println("Sequential Solution Result: " + resultSequential);
        System.out.println("Time Elapsed for Sequential Solution Small Array: " + timeElapsedSequentialSmallArray + " nanoseconds");

        startTimeSequential = System.nanoTime();
        resultSequential = SequentialDetector.SequentialNoPrimesDetector(largeArray);
        endTimeSequential = System.nanoTime();
        long timeElapsedSequentialLargeArray = endTimeSequential - startTimeSequential;


        System.out.println("Sequential Solution Result: " + resultSequential);
        System.out.println("Time Elapsed for Sequential Solution Large Array: " + timeElapsedSequentialLargeArray + " nanoseconds");

        long startTimeParallelStream = System.nanoTime();
        boolean resultParallelStream = ParallelStreamDetector.ParallelStreamNoPrimesDetector(smallArray);
        long endTimeParallelStream = System.nanoTime();
        long timeElapsedParallelStreamSmallArray = endTimeParallelStream - startTimeParallelStream;

        System.out.println("Parallel Stream Solution Result: " + resultParallelStream);
        System.out.println("Time Elapsed for Parallel Stream Solution: " + timeElapsedParallelStreamSmallArray + " nanoseconds");

        startTimeParallelStream = System.nanoTime();
        resultParallelStream = ParallelStreamDetector.ParallelStreamNoPrimesDetector(largeArray);
        endTimeParallelStream = System.nanoTime();
        long timeElapsedParallelStreamLargeArray = endTimeParallelStream - startTimeParallelStream;

        System.out.println("Parallel Stream Solution Result: " + resultParallelStream);
        System.out.println("Time Elapsed for Parallel Stream Solution: " + timeElapsedParallelStreamLargeArray + " nanoseconds");


        long startTimeParallel;
        boolean resultParallel;
        long endTimeParallel;
        long timeElapsedParallel;
        long[] arrayOfResultSmallArray = new long[9];
        for(int numOfThreads = 2; numOfThreads <= 10; numOfThreads++) {
            startTimeParallel = System.nanoTime();
            resultParallel = ParallelDetector.ParallelNoPrimesDetector(smallArray,numOfThreads);
            endTimeParallel = System.nanoTime();
            timeElapsedParallel = endTimeParallel - startTimeParallel;
            System.out.println("Parallel Solution Result: " + resultParallel);
            System.out.println("Num of threads: " + numOfThreads
                    + "Time Elapsed for Sequential Solution: " + timeElapsedParallel + " nanoseconds");
            arrayOfResultSmallArray[numOfThreads - 2] = timeElapsedParallel;
        }

        long[] arrayOfResultLargeArray = new long[9];
        for(int numOfThreads = 2; numOfThreads <= 10; numOfThreads++) {
            startTimeParallel = System.nanoTime();
            resultParallel = ParallelDetector.ParallelNoPrimesDetector(largeArray,numOfThreads);
            endTimeParallel = System.nanoTime();
            timeElapsedParallel = endTimeParallel - startTimeParallel;
            System.out.println("Parallel Solution Result: " + resultParallel);
            System.out.println("Num of threads: " + numOfThreads
                    + "Time Elapsed for Sequential Solution: " + timeElapsedParallel + " nanoseconds");
            arrayOfResultLargeArray[numOfThreads - 2] = timeElapsedParallel;
        }


        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Execution Time Comparison");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(timeElapsedSequentialSmallArray, "Sequential Detector", "Sequential");
            dataset.addValue(timeElapsedSequentialLargeArray, "Sequential Detector Large", "Sequential");
            dataset.addValue(arrayOfResultSmallArray[0], "Parallel Detector", "Threads: 2");
            dataset.addValue(arrayOfResultLargeArray[0], "Parallel Detector Large", "Threads: 2");
            dataset.addValue(arrayOfResultSmallArray[1], "Parallel Detector", "Threads 3");
            dataset.addValue(arrayOfResultLargeArray[1], "Parallel Detector Large", "Threads 3");
            dataset.addValue(arrayOfResultSmallArray[2], "Parallel Detector", "Threads: 4");
            dataset.addValue(arrayOfResultLargeArray[2], "Parallel Detector Large", "Threads: 4");
            dataset.addValue(arrayOfResultSmallArray[3], "Parallel Detector", "Threads: 5");
            dataset.addValue(arrayOfResultLargeArray[3], "Parallel Detector Large", "Threads: 5");
            dataset.addValue(arrayOfResultSmallArray[4], "Parallel Detector", "Threads 6");
            dataset.addValue(arrayOfResultLargeArray[4], "Parallel Detector Large", "Threads 6");
            dataset.addValue(arrayOfResultSmallArray[5], "Parallel Detector", "Threads 7");
            dataset.addValue(arrayOfResultLargeArray[5], "Parallel Detector Large", "Threads 7");
            dataset.addValue(arrayOfResultSmallArray[6], "Parallel Detector", "Threads: 8");
            dataset.addValue(arrayOfResultLargeArray[6], "Parallel Detector Large", "Threads: 8");
            dataset.addValue(arrayOfResultSmallArray[7], "Parallel Detector", "Threads: 9");
            dataset.addValue(arrayOfResultLargeArray[7], "Parallel Detector Large", "Threads: 9");
            dataset.addValue(arrayOfResultSmallArray[8], "Parallel Detector", "Threads: 10");
            dataset.addValue(arrayOfResultLargeArray[8], "Parallel Detector Large", "Threads: 10");
            dataset.addValue(timeElapsedParallelStreamSmallArray, "ParallelStream Detector", "Type 10");
            dataset.addValue(timeElapsedParallelStreamLargeArray, "ParallelStream Detector Large", "Type 10");

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

    }


}