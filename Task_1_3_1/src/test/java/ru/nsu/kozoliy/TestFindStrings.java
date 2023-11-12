package ru.nsu.kozoliy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Тесты для 1.3.1.
 */
public class TestFindStrings {

    @Test
    void testCatRu() throws IOException {
        FindString finder = new FindString("Вход.txt", "кош", FindString.Filetype.RESOURCE);

        ArrayList<Integer> expected = new ArrayList<Integer>(Arrays.asList(41, 242, 360, 469, 709,
                800, 870, 1096, 1204, 1776, 2257, 2645, 3054));

        Assertions.assertEquals(finder.find().toString(), expected.toString());


    }

    @Test
    void testCat() throws IOException {
        FindString finder = new FindString("Input.txt", "cat", FindString.Filetype.RESOURCE);
        Assertions.assertEquals(finder.find().toString(), Arrays.asList(95, 187, 291, 447,
                738, 948, 1064, 1096, 1306, 1546, 1686, 1789).toString());
    }

    @Test
    void testCatEmpty() throws IOException {
        FindString finder = new FindString("Nothing.txt", "cat", FindString.Filetype.RESOURCE);
        Assertions.assertEquals(finder.find(), List.of());
    }

    @Test
    void generatedFile() throws IOException {

        ArrayList<Long> result = generateFile(1_610_612_736, "hello", "file.txt");
        FindString finder = new FindString("file.txt", "hello", FindString.Filetype.FILE);

        Assertions.assertEquals(finder.find(), result);
        new File("file.txt").delete();
    }


    /**
     *  Метод для генерации теста размером 15 гигабайт
     *  с помощью цикла и случайного включения ответа.
     *
     */
    public ArrayList<Long> generateFile(long fileSize, String target,
                                        String outputFileName) throws IOException {


        ArrayList<Long> result = new ArrayList<>();
        FileOutputStream fos = new FileOutputStream(outputFileName);
        Random rnd = new Random();

        long current = 0;
        byte[] targetBytes = target.getBytes();
        int targetLength = targetBytes.length;
        byte[] buffer = new byte[1048576];
        int counter = 0;
        while (counter < fileSize) {
            if (rnd.nextInt(100) > 90) {
                result.add((current + 1));
                current += target.length();
                fos.write(target.getBytes());
            } else {
                String add = new String(buffer, StandardCharsets.US_ASCII);
                rnd.nextBytes(buffer);
                fos.write(add.getBytes());
                current += add.length();
                counter += add.length() / 10;
            }
            counter++;
        }

        fos.flush();
        fos.close();

        return result;
    }


}