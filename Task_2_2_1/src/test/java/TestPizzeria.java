import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.dto.BackerDto;
import ru.nsu.kozoliy.dto.CourierDto;
import ru.nsu.kozoliy.entities.Backer;
import ru.nsu.kozoliy.entities.Courier;
import ru.nsu.kozoliy.entities.Order;
import ru.nsu.kozoliy.entities.Pizza;
import ru.nsu.kozoliy.entities.User;
import ru.nsu.kozoliy.exceptions.ParsingException;
import ru.nsu.kozoliy.interfaces.Ibacker;
import ru.nsu.kozoliy.interfaces.Icourier;
import ru.nsu.kozoliy.model.Pizzeria;
import ru.nsu.kozoliy.parsing.Configuration;
import ru.nsu.kozoliy.parsing.PizzeriaParser;
import ru.nsu.kozoliy.services.BackerService;
import ru.nsu.kozoliy.services.CourierService;
import ru.nsu.kozoliy.services.UserGeneratorService;
import ru.nsu.kozoliy.services.UserService;
import ru.nsu.kozoliy.storage.Storage;


/**
 * Тесты для пиццерии.
 *
 */
public class TestPizzeria {

    private Pizzeria pizzeria;
    private Storage storage;

    /**
     * Initialization for future tests.
     *
     */
    @BeforeEach
    public void init() {
        storage = mock(Storage.class);
        order = mock(Order.class);
        PizzeriaParser parser = new PizzeriaParser();
        Configuration configurationDto = parser.getConfigurationFromFile("/testconfig.json");
        pizzeria = new Pizzeria(configurationDto);
    }

    private Order order;

    @Test
    public void testAddOrder() throws InterruptedException {
        storage.addOrder(order);

        verify(storage, times(1)).addOrder(order);
    }

    @Test
    public void testGetOrder() throws InterruptedException {
        when(storage.getOrder()).thenReturn(order);

        Order retrievedOrder = storage.getOrder();

        assertEquals(order, retrievedOrder);
    }

    @Test
    public void testIsFull() throws InterruptedException {
        when(storage.canBeFull()).thenReturn(true);

        assertTrue(storage.canBeFull());
    }

    @Test
    public void testSingletonInstance() {
        Storage instance1 = Storage.getInstance();
        Storage instance2 = Storage.getInstance();
        assertSame(instance1, instance2);

        // Проверяем, что возвращенный экземпляр не является null
        assertNotNull(instance1);
    }




    @Test
    public void testParser() throws InterruptedException {
        PizzeriaParser parser = new PizzeriaParser();
        Configuration configuration = parser.getConfigurationFromFile("/testconfig.json");
        Assertions.assertThrows(ParsingException.class,
                () ->
                        parser.getConfigurationFromFile("/unexistedfile.json.txt.csv"));
        assertEquals(configuration.backers().size(), 2);
        int i = 10000;
        for (BackerDto backerDto : configuration.backers()) {
            assertEquals(backerDto.workingTimeMs(), i++);
        }
        assertEquals(configuration.couriers().size(), 2);
        i = 1;
        for (CourierDto courierDto : configuration.couriers()) {
            assertEquals(courierDto.baggageSize(), i++);
        }
        assertEquals(configuration.storage().capacity(), 1);



        Order order1 = new Order();
        ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
        pizzas.add(new Pizza(30, Pizza.PizzaType.Vegan));
        pizzas.add(new Pizza(25, Pizza.PizzaType.BBQ));
        order1.setPizzas(pizzas);
        order1.setId(1);
        pizzeria.makeOrder(pizzas);
        BackerDto firstBackerDto = configuration.backers().get(0);

        Ibacker backer = new Backer(firstBackerDto.name(),
                firstBackerDto.surname(), firstBackerDto.id(),
                pizzeria, firstBackerDto.workingTimeMs());
        Thread backerThread = new Thread(backer);
        backerThread.start();
        Assertions.assertFalse(storage.canBeFull());

        System.out.println(storage.getCapacity());
        Thread.sleep(firstBackerDto.workingTimeMs() + 1000);
        assertTrue(pizzeria.isNoOrders());

        storage.addOrder(order1);

        //Assertions.assertTrue(storage.isFull());

        CourierDto firstCourierDto = configuration.couriers().get(0);
        Icourier courier = new Courier(firstCourierDto.name(),
                firstCourierDto.surname(),
                firstCourierDto.id(),
                firstCourierDto.baggageSize(),
                firstCourierDto.deliveryTime());
        Thread courierThread = new Thread(courier);
        courierThread.start();
        Thread.sleep(firstCourierDto.deliveryTime());

        assertTrue(backerThread.isAlive());
        assertTrue(courierThread.isAlive());

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
        assertTrue(customers.size() >= 3 && customers.size() <= 10);
    }

    @Test
    public void testCustomerServices() throws InterruptedException {
        assertTrue(pizzeria.isNoOrders());
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
        assertTrue(pizzeria.isNoOrders());
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
        assertTrue(pizzeria.isNoOrders());

        backerService.stopService();
        Thread.sleep(1000);
        Assertions.assertFalse(backerServiceThread.isAlive());


        CourierService courierService = ((Pizzeria) pizzeria).getCourierService();
        Thread courierServiceThread = new Thread(courierService);
        courierServiceThread.start();
        Assertions.assertFalse(storage.isEmpty());
        Thread.sleep(5000);

        courierService.stopService();
        Thread.sleep(1000);
        Assertions.assertFalse(courierServiceThread.isAlive());

    }


}