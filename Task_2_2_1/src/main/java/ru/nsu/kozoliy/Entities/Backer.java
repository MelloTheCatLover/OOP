package ru.nsu.kozoliy.Entities;

import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;
import ru.nsu.kozoliy.ModelInterfaces.IBacker;
import ru.nsu.kozoliy.Storage.Storage;
import ru.nsu.kozoliy.Model.OrderGetter;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;

public class Backer implements IBacker {
    private  String name;
    private final String surname;
    private final int id;
    private final OrderGetter orderGetter;
    private final int workingTimeMs;

    public Backer(String name, String surname, int id, OrderGetter orderGetter, int workingTimeMs) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.orderGetter = orderGetter;
        this.workingTimeMs = workingTimeMs;
    }




    @ExcludeFromJacocoGeneratedReport
    public void displayInfo() {
        System.out.println("Пекарь:" + name + " " + surname + ". Работает со скоростью: " + workingTimeMs);
    }

    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Backer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", id=" + id +
                ", workingTimeMs=" + workingTimeMs +
                '}';
    }

    @Override
    public void makePizza(Pizza pizza) throws InterruptedException{

        System.out.println(name + surname + " выпекает " + pizza.getSize() + " сантиметровую пиццу " + pizza.getType() + ".");


        Thread.sleep(workingTimeMs);
        pizza.setCooked(true);
        System.out.println(name + " завершил приготовление пиццы " + pizza.getType() + ".");

    }


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
