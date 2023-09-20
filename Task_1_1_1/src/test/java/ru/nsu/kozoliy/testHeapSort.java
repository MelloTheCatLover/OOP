package ru.nsu.kozoliy;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import  org.junit.jupiter.api.Test;


class TestHeapsort {

    @Test
    public void  testMain() {
        Main.main(new String[0]);
    }


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
    public void testLargeNegativeNumbers() {
        int[] fedArray = {-10000, -100000, -1000000, -100, -1000, -10};
        int[] expectedArray = {-1000000, -100000, -10000, -1000, -100, -10};
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