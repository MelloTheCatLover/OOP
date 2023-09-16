package ru.nsu.bykova;

public class Main{
    public static void heapSort(int[] array){
        int arraySize = array.length;
        for(int i = arraySize / 2 - 1; i >= 0; i--){
            heapify(array, arraySize, i);
        }
        for(int i = arraySize - 1; i > 0; i--){
            swap(array, 0, i);
            heapify(array, i, 0);
        }
    }

    private static void heapify(int[] array, int arraySize, int i){
        int largest = i;
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;
        if (leftChild < arraySize && array[leftChild] > array[largest]){
            largest = leftChild;
        }
        if (rightChild < arraySize && array[rightChild] > array[largest]){
            largest = rightChild;
        }
        if (largest != i) {
            swap(array, i, largest);
            heapify(array, arraySize, largest);
        }
    }

    private static void swap(int[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}