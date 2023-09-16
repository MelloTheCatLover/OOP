package ru.nsu.bykova;


import static org.junit.jupiter.api.Assertions.*;

import  org.junit.jupiter.api.Test;


class TestHeapsort {

    @Test
    public void testHeapSort() {
        int[] fedArray = {13, 12, 2, 13, 3, 10, 7};
        int[] expectedArray = {2, 3, 7, 10, 12, 13, 13};
        Main.heapSort(fedArray);
        assertArrayEquals(expectedArray, fedArray);
    }


    @Test
    public void testDuplicateValues() {
        int[] fedArray = {5, 5, 5, 5, 5};
        int[] expectedArray = {5, 5, 5, 5, 5};
        Main.heapSort(fedArray);
        assertArrayEquals(expectedArray, fedArray);
    }
    @Test
    public void testEmptyArray() {
        int[] fedArray = {};
        int[] expectedArray = {};
        Main.heapSort(fedArray);
        assertArrayEquals(expectedArray, fedArray);
    }

    @Test
    public void testLargeArray() {
        int[] fedArray = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expectedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Main.heapSort(fedArray);
        assertArrayEquals(expectedArray, fedArray);
    }

    @Test
    public void testNegativeNumbers() {
        int[] fedArray = {-5, -2, -13, -10, -1};
        int[] expectedArray = {-13, -10, -5, -2, -1};
        Main.heapSort(fedArray);
        assertArrayEquals(expectedArray, fedArray);
    }

    @Test
    public void testLargeNumbers() {
        int[] fedArray = {10000, 100000, 1000000, 100, 1000, 10};
        int[] expectedArray = {10, 100, 1000, 10000, 100000, 1000000};
        Main.heapSort(fedArray);
        assertArrayEquals(expectedArray, fedArray);
    }

    @Test
    public void testVeryLargeArray() {
        int[] fedArray = {56, 72, 34, 91, 18, 6, 48, 77, 12, 3, 29, 65, 47, 23, 89, 5, 2, 45, 67, 10, 59, 82, 37, 14,
                70, 99, 21, 39, 78, 1, 53, 41, 96, 8, 61, 25, 69, 15, 84, 30, 76, 50, 7, 38, 88, 17, 94, 64, 31, 95, 46,
                9, 55, 40, 63, 20, 75, 28, 66, 35, 4, 74, 51, 11, 33, 87, 60, 22, 92, 36, 57, 13, 81, 58, 26, 68, 32,
                90, 16, 73, 44, 80, 27, 62, 49, 19, 86, 43, 54, 79, 52, 98, 71, 42, 85, 24, 97};
        int[] expectedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
                25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
                50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74,
                75, 76, 77, 78, 79, 80, 81, 82, 84, 85, 86, 87, 88, 89, 90, 91, 92, 94, 95, 96, 97, 98, 99};
        Main.heapSort(fedArray);
        assertArrayEquals(expectedArray, fedArray);
    }

    @Test
    public  void reversedSortedArray() {
        int[] fedArray = {5, 4, 3, 2, 1, 0};
        int[] expectedArray = {0, 1, 2, 3, 4, 5};
        Main.heapSort(fedArray);
        assertArrayEquals(expectedArray, fedArray);
    }


}