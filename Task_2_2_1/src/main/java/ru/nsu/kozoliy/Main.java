package ru.nsu.kozoliy;


import java.util.Scanner;
import ru.nsu.kozoliy.interfaces.Ipizzeria;
import ru.nsu.kozoliy.model.Pizzeria;
import ru.nsu.kozoliy.parsing.Configuration;
import ru.nsu.kozoliy.parsing.PizzeriaParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Начальная точка программы.
 *
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        PizzeriaParser parser = new PizzeriaParser();
        Configuration configuration = parser.getConfigurationFromFile("/config.json");
        Ipizzeria pizzeria = new Pizzeria(configuration);
        boolean isWorking = true;

        Scanner scanner = new Scanner(System.in);

        while (isWorking) {
            String command = scanner.nextLine();
            switch (command) {
                case "start":
                    logger.info("Starting work");
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
                            logger.error("Thread interrupted while sleeping", e);
                        }
                        logger.info("\n\n==================DAY" + i + " ENDED==================\n");
                        pizzeria.stopWorking();
                    }
                    pizzeria.endWorking();
                    logger.info("Ending work for the week");
                    isWorking = false;
                    break;
                default:
                    logger.warn("Unknown command: {}", command);
                    System.out.println("Unknown command");
                    break;
            }
        }

        scanner.close();
    }
}