package ru.nsu.kozoliy.entities;

import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;
import ru.nsu.kozoliy.interfaces.Icourier;
import ru.nsu.kozoliy.storage.Storage;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Класс, представляющий курьера в системе доставки пиццы.
 * Курьер забирает заказы с пиццей со склада и доставляет их клиентам.
 */
public class Courier implements Icourier {
    private final String name; // Имя курьера
    private final String surname; // Фамилия курьера
    private final int id; // Уникальный идентификатор курьера
    private final Queue<Order> bag; // Очередь заказов с пиццей, которые курьер доставляет
    private final int baggageSize; // Размер багажника курьера (максимальное количество пицц, которые он может перевезти)
    private final int deliveryTime; // Время доставки одной пиццы

    /**
     * Конструктор для создания экземпляра курьера.
     *
     * @param name         имя курьера
     * @param surname      фамилия курьера
     * @param id           уникальный идентификатор курьера
     * @param baggageSize  размер багажника курьера (максимальное количество пицц, которые он может перевезти)
     * @param deliveryTime время доставки одной пиццы
     */
    public Courier(String name, String surname, int id, int baggageSize, int deliveryTime) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.bag = new ArrayDeque<>();
        this.baggageSize = baggageSize;
        this.deliveryTime = deliveryTime;
    }

    /**
     * Метод доставки пиццы клиентам.
     * Курьер берет заказы с пиццей со склада и доставляет их клиентам.
     * Если в багажнике курьера есть заказы, он доставляет их по одному, ожидая время deliveryTime между доставками.
     * После успешной доставки курьер удаляет заказ из своего багажника.
     *
     * @throws InterruptedException если поток курьера был прерван во время доставки
     */
    @Override
    public void deliverPizza() throws InterruptedException {
        Storage storage = Storage.getInstance();
        while (!isBagFull()) {
            Order pizzaOrder = storage.getOrder();
            bag.add(pizzaOrder);
            System.out.println("Курьер " + name + " " + surname + " получил пиццу " + pizzaOrder.toString());
        }
        for (Order pizzaOrder : bag) {
            Thread.sleep(deliveryTime);
            pizzaOrder.getUser().run();
            bag.remove(pizzaOrder);
        }
    }

    /**
     * Метод запуска потока доставки пиццы клиентам.
     * При вызове метода запускается бесконечный цикл, в котором курьер доставляет пиццу.
     * Если поток курьера был прерван во время доставки, метод завершается.
     */
    @Override
    public void run() {
        while (true) {
            try {
                deliverPizza();
            } catch (InterruptedException e) {
                System.out.println("Курьера прервали");
                return;
            }
        }
    }

    /**
     * Метод проверки, заполнен ли багажник курьера.
     *
     * @return true, если багажник курьера заполнен, false в противном случае
     */
    private boolean isBagFull() {
        return bag.size() == baggageSize;
    }

    /**
     * Переопределенный метод toString для получения строкового представления курьера.
     * @return строковое представление курьера
     */
    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Courier{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", id=" + id +
                ", baggageSize=" + baggageSize +
                ", deliveryTime=" + deliveryTime +
                '}';
    }
}
