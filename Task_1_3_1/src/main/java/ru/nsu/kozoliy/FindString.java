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
    private InputStream inputStream;
    private final int bufferSize = 100000000;
    private final String target;
    private Reader fileInfo;
    private final Charset encoding;

    /**
     * Конструктор класса FindString.
     *
     * @param filename  Имя файла, в котором будет выполняться поиск.
     * @param target    Целевая подстрока, которую нужно найти в файле.
     * @param filetype  Тип файла: RESOURCE (ресурс) или FILE (обычный файл).
     * @throws IOException если произошла ошибка ввода-вывода при открытии файла.
     */
    public FindString(String filename, String target, Filetype filetype) throws IOException {
        this.encoding = StandardCharsets.UTF_8;
        byte[] bytes = target.getBytes(StandardCharsets.UTF_8);
        this.target = new String(bytes, StandardCharsets.UTF_8);
        openFile(filename, filetype);
        //inputStream.close();
    }




    /**
     * Метод для поиска всех вхождений целевой подстроки в файле.
     *
     * @return Список индексов начала каждого вхождения целевой подстроки в файле.
     * @throws IOException если произошла ошибка ввода-вывода при чтении файла.
     */
    public ArrayList<Long> find() throws IOException {
        ArrayList<Long> occurrences = new ArrayList<>();
        long[] prefixArray = buildPrefixArray(target);

        long numOfBuffer = 1;
        long textIndex = 0;
        long targetIndex = 0;
        String text;

        while (true) {
            try {
                text = new String(readNextChunk());
                //System.out.println("Text: " + text);
            } catch (EOFException e) {
                closeFile();
                return occurrences;
            }
            while (textIndex < text.length() * numOfBuffer) {
                if (text.charAt((int) (textIndex - bufferSize * (numOfBuffer - 1)))
                        == target.charAt((int) targetIndex)) {
                    textIndex++;
                    targetIndex++;
                    if (targetIndex == target.length()) {
                        occurrences.add(textIndex - targetIndex + 1);
                        targetIndex = prefixArray[(int) (targetIndex - 1)];

                    }
                } else {
                    if (targetIndex != 0) {
                        targetIndex = prefixArray[(int) (targetIndex - 1)];

                    } else {
                        textIndex++;
                    }
                }
            }
            numOfBuffer++;
        }

    }

    /**
     * File reader closer.
     */
    void closeFile() {
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Метод для построения префиксного массива для целевой подстроки.
     *
     * @param str Целевая подстрока.
     * @return Префиксный массив.
     */
    private static long[] buildPrefixArray(String str) {
        long[] prefixArray = new long[str.length()];
        long length = 0;
        long i = 1;

        while (i < str.length()) {
            if (str.charAt((int) i) == str.charAt((int) length)) {
                length++;
                prefixArray[(int) i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = prefixArray[(int) (length - 1)];
                } else {
                    prefixArray[(int) i] = 0;
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
     * @param filetype Тип файла: RESOURCE (ресурс) или FILE (обычный файл).
     * @throws IOException если произошла ошибка ввода-вывода при открытии файла.
     */
    private void openFile(String filename, Filetype filetype) throws IOException {
        inputStream = (filetype == Filetype.RESOURCE)
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
    public enum Filetype {
        RESOURCE,
        FILE
    }

    /**
     * Точка входа в программу.
     *
     */
    public static void main(String[] args) throws IOException {
        FindString finder = new FindString("Input.txt", "cat", Filetype.RESOURCE);
        ArrayList<Long> res = finder.find();
        finder.closeFile();
        for (Long re : res) {
            System.out.println(re);
        }
    }
}
