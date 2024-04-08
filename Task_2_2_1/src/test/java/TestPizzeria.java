import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import ru.nsu.kozoliy.Backer.BackerDto;
import ru.nsu.kozoliy.Backer.BackerRepository;
import ru.nsu.kozoliy.Backer.IBackerRepository;
import ru.nsu.kozoliy.Courier.CourierDto;
import ru.nsu.kozoliy.Courier.CourierRepository;
import ru.nsu.kozoliy.Courier.ICourierRepository;
import ru.nsu.kozoliy.Exceptions.ParsingException;
import ru.nsu.kozoliy.Model.BackerService;
import ru.nsu.kozoliy.Model.CourierService;
import ru.nsu.kozoliy.Model.CustomerGenerator;
import ru.nsu.kozoliy.Model.CustomerService;
import ru.nsu.kozoliy.Order.Order;
import ru.nsu.kozoliy.Parsing.Configuration;
import ru.nsu.kozoliy.Parsing.PizzeriaParser;
import ru.nsu.kozoliy.PizzeriaLogic.Pizzeria;
import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.Storage.IStorageRepository;
import ru.nsu.kozoliy.Storage.StorageRepository;
import ru.nsu.kozoliy.User.User;

import java.util.List;

public class TestPizzeria {
    private Pizzeria pizzeria;

    @BeforeEach
    public void init() {
        PizzeriaParser parser = new PizzeriaParser();
        Configuration configurationDto = parser.getConfigurationFromFile("/testconfig.json");
        pizzeria = new Pizzeria(configurationDto);
    }

    @Test
    public void TestWarehouse() throws InterruptedException {
        IStorageRepository storage = new StorageRepository(2);
        Assertions.assertFalse(storage.isFull());
        Assertions.assertTrue(storage.isEmpty());
        storage.addPizza(new Order(1, 2));
        Assertions.assertFalse(storage.isEmpty());
        Assertions.assertFalse(storage.isFull());
        storage.addPizza(new Order(2, 4));
        Assertions.assertTrue(storage.isFull());
        Assertions.assertFalse(storage.isEmpty());
    }

    @Test
    public void TestParser() throws InterruptedException {
        PizzeriaParser parser = new PizzeriaParser();
        Configuration configuration = parser.getConfigurationFromFile("/testconfig.json");
        Assertions.assertThrows(ParsingException.class,
                () ->
                        parser.getConfigurationFromFile("/unexistedfile.json.txt.csv"));
        Assertions.assertEquals(configuration.backers().size(), 2);
        int i = 10000;
        for (BackerDto backerDto : configuration.backers()) {
            Assertions.assertEquals(backerDto.workingTimeMs(), i++);
        }
        Assertions.assertEquals(configuration.couriers().size(), 2);
        i = 1;
        for (CourierDto courierDto : configuration.couriers()) {
            Assertions.assertEquals(courierDto.baggageCount(), i++);
        }
        Assertions.assertEquals(configuration.storage().capacity(), 1);

        BackerDto firstBackerDto = configuration.backers().get(0);
        CourierDto firstCourierDto = configuration.couriers().get(0);
        IStorageRepository storage = ((Pizzeria) pizzeria).getWarehouse();

        pizzeria.makeOrder(4);
        IBackerRepository backer = new BackerRepository(storage, pizzeria, firstBackerDto.workingTimeMs());
        Thread backerThread = new Thread(backer);
        backerThread.start();
        Assertions.assertFalse(storage.isFull());

        Thread.sleep(firstBackerDto.workingTimeMs() + 1000);
        Assertions.assertTrue(pizzeria.isNoOrders());
        Assertions.assertTrue(storage.isFull());

        ICourierRepository courier = new CourierRepository(storage, firstCourierDto.baggageCount(), firstCourierDto.deliveryTimeMs());
        Thread courierThread = new Thread(courier);
        courierThread.start();
        Thread.sleep(firstCourierDto.deliveryTimeMs());

        Assertions.assertTrue(backerThread.isAlive());
        Assertions.assertTrue(courierThread.isAlive());

        backerThread.interrupt();
        courierThread.interrupt();
        Thread.sleep(500);
        Assertions.assertFalse(backerThread.isAlive());
        Assertions.assertFalse(courierThread.isAlive());

    }

    @Test
    public void testCustomerRepository() {
        CustomerGenerator generator = new CustomerService(pizzeria);
        List<Runnable> customers = generator.generate();
        Assertions.assertTrue(customers.size() >= 3 && customers.size() <= 6);
    }

    @Test
    public void testCustomerServices() throws InterruptedException {
        Assertions.assertTrue(pizzeria.isNoOrders());
        CustomerService service = new CustomerService(pizzeria);
        Thread customerServiceThread = new Thread(service);
        customerServiceThread.start();
        Thread.sleep(1000);
        Assertions.assertFalse(pizzeria.isNoOrders());
        service.stopService();
        while (!pizzeria.isNoOrders()) {
            pizzeria.getOrder();
        }
        Thread.sleep(3000);
        Assertions.assertTrue(pizzeria.isNoOrders());
        System.out.println(customerServiceThread.isAlive());
    }

    @Test
    public void testWorkerService() throws InterruptedException {
        Thread customerThread = new Thread(new User(pizzeria, 1));
        customerThread.start();
        Thread.sleep(1000);
        Assertions.assertFalse(pizzeria.isNoOrders());
        customerThread.interrupt();

        BackerService backerService = ((Pizzeria) pizzeria).getBackerService();
        Thread backerServiceThread = new Thread(backerService);
        backerServiceThread.start();
        Thread.sleep(11001);
        Assertions.assertTrue(pizzeria.isNoOrders());

        backerService.stopService();
        Thread.sleep(1000);
        Assertions.assertFalse(backerServiceThread.isAlive());


        CourierService courierService = ((Pizzeria) pizzeria).getCourierService();
        Thread courierServiceThread = new Thread(courierService);
        courierServiceThread.start();
        Assertions.assertFalse(((Pizzeria) pizzeria).getWarehouse().isEmpty());
        Thread.sleep(5000);
        Assertions.assertTrue(((Pizzeria) pizzeria).getWarehouse().isEmpty());

        courierService.stopService();
        Thread.sleep(1000);
        Assertions.assertFalse(courierServiceThread.isAlive());

    }


}