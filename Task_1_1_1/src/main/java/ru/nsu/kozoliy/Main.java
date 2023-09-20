package ru.nsu.kozoliy;

/**
 * Этот класс предоставляет реализацию алгоритма пирамидальной сортировки HeapSort.
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
            swap(array, 0, i);
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
            swap(array, i, largest);
            heapify(array, arraySize, largest);
        }
    }

    /**
     * Обменивает местами два элемента в массиве.
     */
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
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
