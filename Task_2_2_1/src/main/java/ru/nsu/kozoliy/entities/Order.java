package ru.nsu.kozoliy.entities;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;


/**
 * Класс, представляющий заказ в пиццерии.
 * Заказ содержит список пицц и идентификатор.
 */
public class Order {

    private int id; // Идентификатор заказа
    private List<Pizza> pizzas; // Список пицц в заказе

    private Runnable user; // Пользователь, который делает заказ

    /**
     * Конструктор для создания пустого заказа.
     * Создает новый заказ с пустым списком пицц.
     */
    public Order() {
        this.pizzas = new ArrayList<>();
    }

    /**
     * Конструктор для создания заказа с указанным идентификатором и списком пицц.
     *
     * @param id     идентификатор заказа
     * @param pizzas список пицц в заказе
     */
    public Order(int id, List<Pizza> pizzas) {
        this.id = id;
        this.pizzas = pizzas;
    }

    /**
     * Метод для получения пользователя, сделавшего заказ.
     *
     * @return пользователь, сделавший заказ
     */
    public Runnable getUser() {
        return user;
    }

    /**
     * Метод для установки пользователя, сделавшего заказ.
     *
     * @param user пользователь, сделавший заказ
     */
    public void setUser(Runnable user) {
        this.user = user;
    }

    /**
     * Метод для получения идентификатора заказа.
     *
     * @return идентификатор заказа
     */
    public int getId() {
        return id;
    }

    /**
     * Метод для установки идентификатора заказа.
     *
     * @param id идентификатор заказа
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Метод для получения списка пицц в заказе.
     *
     * @return список пицц в заказе
     */
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Метод для установки списка пицц в заказе.
     *
     * @param pizzas список пицц в заказе
     */
    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    /**
     * Переопределенный метод toString для получения строкового представления заказа.
     *
     * @return строковое представление заказа
     */
    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", pizzas=" + pizzas +
                '}';
    }

}
