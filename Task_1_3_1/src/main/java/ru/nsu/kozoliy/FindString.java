package ru.nsu.kozoliy;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Класс для поиска подстроки в текстовых данных.
 */
public class FindString {
    private final int bufferSize = 1000000;
    private final String target;
    private Reader fileInfo;
    private final Charset encoding;

    /**
     * Конструктор класса FindString.
     *
     * @param filename  Имя файла, в котором будет выполняться поиск.
     * @param target    Целевая подстрока, которую нужно найти в файле.
     * @param fileType  Тип файла: RESOURCE (ресурс) или FILE (обычный файл).
     * @throws IOException если произошла ошибка ввода-вывода при открытии файла.
     */
    public FindString(String filename, String target, fileType fileType) throws IOException {
        this.encoding = StandardCharsets.UTF_8;
        byte[] bytes = target.getBytes(StandardCharsets.UTF_8);
        this.target = new String(bytes, StandardCharsets.UTF_8);
        openFile(filename, fileType);
    }

    /**
     * Метод для поиска всех вхождений целевой подстроки в файле.
     *
     * @return Список индексов начала каждого вхождения целевой подстроки в файле.
     * @throws IOException если произошла ошибка ввода-вывода при чтении файла.
     */
    public ArrayList<Integer> find() throws IOException {
        ArrayList<Integer> occurrences = new ArrayList<>();
        int[] prefixArray = buildPrefixArray(target);

        int textIndex = 0;
        int targetIndex = 0;
        String text;

        while (true) {
            try {
                text = new String(readNextChunk());
            } catch (EOFException e) {
                return occurrences;
            }
            while (textIndex < text.length()) {
                if (text.charAt(textIndex) == target.charAt(targetIndex)) {
                    textIndex++;
                    targetIndex++;
                    if (targetIndex == target.length()) {
                        occurrences.add(textIndex - targetIndex + 1);
                        targetIndex = prefixArray[targetIndex - 1];
                    }
                } else {
                    if (targetIndex != 0) {
                        targetIndex = prefixArray[targetIndex - 1];
                    } else {
                        textIndex++;
                    }
                }
            }
        }
    }

    /**
     * Метод для построения префиксного массива для целевой подстроки.
     *
     * @param str Целевая подстрока.
     * @return Префиксный массив.
     */
    private static int[] buildPrefixArray(String str) {
        int[] prefixArray = new int[str.length()];
        int length = 0;
        int i = 1;

        while (i < str.length()) {
            if (str.charAt(i) == str.charAt(length)) {
                length++;
                prefixArray[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = prefixArray[length - 1];
                } else {
                    prefixArray[i] = 0;
                    i++;
                }
            }
        }

        return prefixArray;
    }

    /**
     * Метод для открытия файла в указанном режиме (ресурс или обычный файл).
     *
     * @param filename Имя файла.
     * @param fileType Тип файла: RESOURCE (ресурс) или FILE (обычный файл).
     * @throws IOException если произошла ошибка ввода-вывода при открытии файла.
     */
    private void openFile(String filename, fileType fileType) throws IOException {
        InputStream inputStream = (fileType == fileType.RESOURCE)
                ? getClass().getClassLoader().getResourceAsStream(filename)
                : new FileInputStream(filename);

        assert inputStream != null;
        this.fileInfo = new InputStreamReader(inputStream, this.encoding);
    }

    /**
     * Метод для чтения следующего фрагмента файла.
     *
     * @return Массив символов, содержащий следующий фрагмент файла.
     * @throws IOException если произошла ошибка ввода-вывода при чтении файла.
     */
    private char[] readNextChunk() throws IOException {
        char[] buffer = new char[bufferSize];
        int bytesRead = this.fileInfo.read(buffer);

        if (bytesRead == -1) {
            throw new EOFException();
        }

        char[] result = new char[bytesRead];
        System.arraycopy(buffer, 0, result, 0, bytesRead);
        return result;
    }

    /**
     * Перечисление для определения типа файла (ресурс или обычный файл).
     */
    public enum fileType {
        RESOURCE,
        FILE
    }

    /**
     * Точка входа в программу
     *
     */
    public static void main(String[] args) throws IOException {
        FindString finder = new FindString("Input.txt", "cat", fileType.RESOURCE);
        ArrayList<Integer> res = finder.find();
        for (Integer re : res) {
            System.out.println(re);
        }
    }
}