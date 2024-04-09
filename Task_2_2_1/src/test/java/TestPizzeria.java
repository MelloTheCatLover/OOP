import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.Dto.BackerDto;
import ru.nsu.kozoliy.Dto.CourierDto;
import ru.nsu.kozoliy.Entities.Backer;
import ru.nsu.kozoliy.Entities.Courier;
import ru.nsu.kozoliy.Entities.Order;
import ru.nsu.kozoliy.Entities.Pizza;
import ru.nsu.kozoliy.Entities.User;
import ru.nsu.kozoliy.Exceptions.ParsingException;
import ru.nsu.kozoliy.Model.Pizzeria;
import ru.nsu.kozoliy.ModelInterfaces.IBacker;
import ru.nsu.kozoliy.ModelInterfaces.ICourier;
import ru.nsu.kozoliy.Parsing.Configuration;
import ru.nsu.kozoliy.Parsing.PizzeriaParser;
import ru.nsu.kozoliy.Services.BackerService;
import ru.nsu.kozoliy.Services.CourierService;
import ru.nsu.kozoliy.Services.UserGeneratorService;
import ru.nsu.kozoliy.Services.UserService;
import ru.nsu.kozoliy.Storage.Storage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestPizzeria {
    private Pizzeria pizzeria;
    private Storage storage;

    @BeforeEach
    public void init() {
        storage = Storage.getInstance(1);
        PizzeriaParser parser = new PizzeriaParser();
        Configuration configurationDto = parser.getConfigurationFromFile("/testconfig.json");
        pizzeria = new Pizzeria(configurationDto);
    }

    @Test
    public void testAddOrder() throws InterruptedException {
        Order order = new Order();
        storage.addOrder(order);

        assertFalse(storage.isEmpty());
    }

    @Test
    public void testGetOrder() throws InterruptedException {
        Order order = new Order();
        storage.addOrder(order);

        Order retrievedOrder = storage.getOrder();

        assertNotNull(retrievedOrder);
        assertEquals(order, retrievedOrder);
        storage.getOrder();
    }

    @Test
    public void testIsFull() throws InterruptedException {
        assertTrue(storage.isEmpty());

        for (int i = 0; i < storage.getCapacity(); i++) {
            storage.addOrder(new Order());
        }

        assertTrue(storage.isFull());
    }

    @Test
    public void testSingletonInstance() {
        Storage instance1 = Storage.getInstance();
        Storage instance2 = Storage.getInstance();
        assertSame(instance1, instance2);
        assertNotNull(Storage.getInstance());
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
            Assertions.assertEquals(courierDto.baggageSize(), i++);
        }
        Assertions.assertEquals(configuration.storage().capacity(), 1);

        BackerDto firstBackerDto = configuration.backers().get(0);
        CourierDto firstCourierDto = configuration.couriers().get(0);

        Order order1 = new Order();
        ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
        pizzas.add(new Pizza(30, Pizza.PizzaType.Vegan));
        pizzas.add(new Pizza(25, Pizza.PizzaType.BBQ));
        order1.setPizzas(pizzas);
        order1.setId(1);
        pizzeria.makeOrder(pizzas);
        IBacker backer = new Backer(firstBackerDto.name(), firstBackerDto.surname(), firstBackerDto.id(), pizzeria, firstBackerDto.workingTimeMs());
        Thread backerThread = new Thread(backer);
        backerThread.start();
        Assertions.assertFalse(storage.isFull());

        System.out.println(storage.getCapacity());
        Thread.sleep(firstBackerDto.workingTimeMs() + 1000);
        Assertions.assertTrue(pizzeria.isNoOrders());
        storage.addOrder(order1);
        Assertions.assertTrue(storage.isFull());

        ICourier courier = new Courier(firstCourierDto.name(), firstCourierDto.surname(), firstCourierDto.id(), firstCourierDto.baggageSize(), firstCourierDto.deliveryTime());
        Thread courierThread = new Thread(courier);
        courierThread.start();
        Thread.sleep(firstCourierDto.deliveryTime());

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
        UserGeneratorService generator = new UserService(pizzeria);
        List<Runnable> customers = generator.generate();
        Assertions.assertTrue(customers.size() >= 3 && customers.size() <= 6);
    }

    @Test
    public void testCustomerServices() throws InterruptedException {
        Assertions.assertTrue(pizzeria.isNoOrders());
        UserService service = new UserService(pizzeria);
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
        ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
        pizzas.add(new Pizza(30, Pizza.PizzaType.Vegan));
        pizzas.add(new Pizza(25, Pizza.PizzaType.BBQ));
        Thread customerThread = new Thread(new User(pizzeria, pizzas));
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

        courierService.stopService();
        Thread.sleep(1000);
        Assertions.assertFalse(courierServiceThread.isAlive());

    }

}