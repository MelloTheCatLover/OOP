package ru.nsu.kozoliy.entities;

import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;
import ru.nsu.kozoliy.interfaces.Ibacker;
import ru.nsu.kozoliy.model.OrderGetter;
import ru.nsu.kozoliy.storage.Storage;

/**
 * Класс, представляющий пекаря в пиццерии.
 * Пекарь выпекает пиццу согласно заказам и добавляет их на склад.
 */
public class Backer implements Ibacker {
    private String name; // Имя пекаря
    private final String surname; // Фамилия пекаря
    private final int id; // Уникальный идентификатор пекаря
    private final OrderGetter orderGetter; // Получатель заказов для пекаря
    private final int workingTimeMs; // Время приготовления одной пиццы в миллисекундах

    /**
     * Конструктор для создания экземпляра пекаря.
     *
     * @param name          имя пекаря
     * @param surname       фамилия пекаря
     * @param id            уникальный идентификатор пекаря
     * @param orderGetter   получатель заказов для пекаря
     * @param workingTimeMs время приготовления одной пиццы в миллисекундах
     */
    public Backer(String name, String surname, int id, OrderGetter orderGetter, int workingTimeMs) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.orderGetter = orderGetter;
        this.workingTimeMs = workingTimeMs;
    }

    /**
     * Метод вывода информации о пекаре.
     * Выводит в консоль информацию о имени, фамилии и скорости работы пекаря.
     */
    @ExcludeFromJacocoGeneratedReport
    public void displayInfo() {
        System.out.println("Пекарь:" + name + " " + surname + ". Работает со скоростью: " + workingTimeMs);
    }

    /**
     * Переопределенный метод toString для получения строкового представления пекаря.
     *
     * @return строковое представление пекаря
     */
    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Backer{"
                + "name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", id=" + id
                + ", workingTimeMs=" + workingTimeMs
                + '}';
    }

    /**
     * Метод приготовления пиццы.
     * Пекарь выпекает пиццу заданного размера и типа.
     *
     * @param pizza пицца, которую нужно приготовить
     * @throws InterruptedException если поток пекаря был прерван во время приготовления
     */
    @Override
    public void makePizza(Pizza pizza) throws InterruptedException {
        System.out.println(name + surname + " выпекает " + pizza.getSize() + " сантиметровую пиццу " + pizza.getType() + ".");
        Thread.sleep(workingTimeMs);
        pizza.setCooked(true);
        System.out.println(name + " завершил приготовление пиццы " + pizza.getType() + ".");
    }

    /**
     * Метод запуска потока приготовления пиццы.
     * Пекарь бесконечно выпекает пиццу согласно заказам и добавляет их на склад.
     * Если поток пекаря был прерван во время приготовления, метод завершается.
     */
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Пекарь: " + name + " " + surname + "Вышел на смену");
                Order order = orderGetter.getOrder();
                for (Pizza pizza : order.getPizzas()) {
                    makePizza(pizza);
                }
                Storage storage = Storage.getInstance();
                storage.addOrder(order);
            } catch (InterruptedException e) {
                System.out.println("Пекаря прервали");
                return;
            }
        }
    }
}
