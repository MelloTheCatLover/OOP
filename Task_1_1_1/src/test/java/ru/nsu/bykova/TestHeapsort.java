package ru.nsu.bykova;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestHeapsort {

    @Test
    public void testHeapSort() {
        int[] arr = {12, 11, 13, 5, 6, 7};
        int[] expected = {5, 6, 7, 11, 12, 13};
        Main.heapSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testEmptyArray() {
        int[] arr = {};
        int[] expected = {};
        Main.heapSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testLargeArray() {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Main.heapSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testDuplicateValues() {
        int[] arr = {5, 5, 5, 5, 5};
        int[] expected = {5, 5, 5, 5, 5};
        Main.heapSort(arr);
        assertArrayEquals(expected, arr);
    }
}