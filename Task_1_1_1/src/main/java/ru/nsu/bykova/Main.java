package ru.nsu.bykova;

/**
 * Этот класс предоставляет реализацию алгоритма пирамидальной сортировки HeapSort.
 * HeapSort - это алгоритм сортировки, который использует структуру данных бинарной кучи
 * для построения упорядоченного массива.
 */
public class Main {

    /**
     * Сортирует целочисленный массив с использованием алгоритма HeapSort.
     *
     * @param array Массив, который нужно отсортировать.
     */
    public static void heapSort(int[] array) {
        int arraySize = array.length;
        for (int i = arraySize / 2 - 1; i >= 0; i--) {
            heapify(array, arraySize, i);
        }
        for (int i = arraySize - 1; i > 0; i--) {
            int temp = array[i];
            array[i] = array[0];
            array[0] = temp;
            heapify(array, i, 0);
        }
    }

    /**
     * Превращает поддерево с корнем в узле 'i' в кучу.
     *
     * @param array     Массив, который нужно отсортировать.
     * @param arraySize Размер массива.
     */
    private static void heapify(int[] array, int arraySize, int i) {
        int largest = i;
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;
        if (leftChild < arraySize && array[leftChild] > array[largest]) {
            largest = leftChild;
        }
        if (rightChild < arraySize && array[rightChild] > array[largest]) {
            largest = rightChild;
        }
        if (largest != i) {
            int temp = array[i];
            array[i] = array[largest];
            array[largest] = temp;
            heapify(array, arraySize, largest);
        }
    }

    /**
     * Точка входа в программу.
     */
    public static void main(String[] args) {
        int[] array = {4, 1, 7, 3, 9, 2, 5};

        // Вывод несортированного массива
        System.out.println("Unsorted Array:");
        for (int num : array) {
            System.out.print(num + " ");
        }

        // Сортировка массива
        heapSort(array);

        // Вывод отсортированного массива
        System.out.println("\nSorted Array:");
        for (int num : array) {
            System.out.print(num + " ");
        }
    }
}
