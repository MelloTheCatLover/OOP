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
 *
 */
public class TestFindStrings {

    @Test
    void testCatRu() throws IOException {
        FindString finder = new FindString("Вход.txt", "кош", FindString.FileType.RESOURCE);
        Assertions.assertEquals(finder.find(), Arrays.asList(41, 242, 360, 469, 709,
                800, 870, 1096, 1204, 1776, 2257, 2645, 3054));
    }

    @Test
    void testCat() throws IOException {
        FindString finder = new FindString("Input.txt", "cat", FindString.FileType.RESOURCE);
        Assertions.assertEquals(finder.find(), Arrays.asList(95, 187, 291, 447,
                738, 948, 1064, 1096, 1306, 1546, 1686, 1789));
    }

    @Test
    void testCatEmpty() throws IOException {
        FindString finder = new FindString("Nothing.txt", "cat", FindString.FileType.RESOURCE);
        Assertions.assertEquals(finder.find(), List.of());
    }

    @Test
    void generatedFile() throws IOException {

        ArrayList<Integer> result = generateFile(1500000000, "hello", "file.txt");
        FindString finder = new FindString("file.txt", "hello", FindString.FileType.FILE);

        Assertions.assertEquals(finder.find(), result);
        new File("file.txt").delete();
    }

    public ArrayList<Integer>  generateFile(int fileSize,
                                            String target, String outputFileName) throws IOException {
        ArrayList<Integer> result = new ArrayList<>();
        FileOutputStream fos = new FileOutputStream(outputFileName);
        Random rnd = new Random();

        int current = 0;
        byte[] targetBytes = target.getBytes();
        int targetLength = targetBytes.length;
        byte[] buffer = new byte[4096];

        while (current < fileSize) {
            if (rnd.nextInt(100) > 90) {
                int remainingSize = fileSize - current;
                if (remainingSize >= targetLength) {
                    result.add(current + 1);
                    fos.write(targetBytes);
                    current += targetLength;
                } else {
                    fos.write(targetBytes, 0, remainingSize);
                    current += remainingSize;
                }
            } else {
                int randomSize = Math.min(buffer.length, fileSize - current);
                String add = new String(buffer, StandardCharsets.US_ASCII);
                rnd.nextBytes(buffer);
                fos.write(add.getBytes());
                current += randomSize;
            }
        }

        fos.flush();
        fos.close();

        return result;
    }


}