package ru.nsu.kozoliy;


import java.util.Scanner;
import ru.nsu.kozoliy.interfaces.Ipizzeria;
import ru.nsu.kozoliy.model.Pizzeria;
import ru.nsu.kozoliy.parsing.Configuration;
import ru.nsu.kozoliy.parsing.PizzeriaParser;

/**
 * Начальная точка программы.
 *
 */
public class Main {
    /**
     * Стартовая точка программы.
     *
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        PizzeriaParser parser = new PizzeriaParser();
        Configuration configuration = parser.getConfigurationFromFile("/config.json");
        Ipizzeria pizzeria = new Pizzeria(configuration);
        boolean isWorking = true;
        while (isWorking) {
            String command = new Scanner(System.in).nextLine();
            switch (command) {
                case "start" -> {
                    System.out.println("Starting work");
                    new Thread(pizzeria).start();
                }
                case "stop" -> {
                    System.out.println("Stopping work");
                    pizzeria.stopWorking();
                }
                case "end" -> {
                    pizzeria.endWorking();
                    System.out.println("Ending work");
                    isWorking = false;
                }
                case "workingWeek" -> {
                    System.out.println("Starting work");


                    for (int i = 1; i < 8; i++) {
                        new Thread(pizzeria).start();
                        System.out.println("==================DAY" + i + "==================");
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("\n\n==================DAY" + i
                                + " ENDED==================\n");
                        pizzeria.stopWorking();
                    }
                    pizzeria.endWorking();
                    System.out.println("Ending work");
                    isWorking = false;
                }
                default -> System.out.println("Unknown command");
            }
        }

    }
}