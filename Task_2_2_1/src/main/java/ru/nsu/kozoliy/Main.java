package ru.nsu.kozoliy;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import ru.nsu.kozoliy.interfaces.Ipizzeria;
import ru.nsu.kozoliy.model.Pizzeria;
import ru.nsu.kozoliy.parsing.Configuration;
import ru.nsu.kozoliy.parsing.PizzeriaParser;

/**
 * Начальная точка программы.
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * Главный метод программы.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        // Установка FileHandler для записи логов в файл
        try {
            FileHandler fileHandler = new FileHandler("logfile.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.severe("Failed to create log file: " + e.getMessage());
        }

        PizzeriaParser parser = new PizzeriaParser();
        Configuration configuration = parser.getConfigurationFromFile("/config.json");
        Ipizzeria pizzeria = new Pizzeria(configuration);
        boolean isWorking = true;

        Scanner scanner = new Scanner(System.in);

        while (isWorking) {
            String command = scanner.nextLine();
            switch (command) {
                case "start":
                    new Thread(pizzeria).start();
                    break;
                case "stop":
                    logger.info("Stopping work");
                    pizzeria.stopWorking();
                    break;
                case "end":
                    pizzeria.endWorking();
                    logger.info("Ending work");
                    isWorking = false;
                    break;
                case "workingWeek":
                    logger.info("Starting work for the week");
                    for (int i = 1; i < 8; i++) {
                        new Thread(pizzeria).start();
                        logger.info("==================DAY" + i + "==================");
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            logger.severe("Thread interrupted while sleeping: " + e.getMessage());
                        }
                        logger.info("\n\n==================DAY" + i + " ENDED==================\n");
                        pizzeria.stopWorking();
                    }
                    pizzeria.endWorking();
                    logger.info("Ending work for the week");
                    isWorking = false;
                    break;
                default:
                    logger.warning("Unknown command: " + command);
                    System.out.println("Unknown command");
                    break;
            }
        }

        scanner.close();
    }
}