package ru.nsu.kozoliy;

import java.util.Objects;

/**
 * Класс, представляющий вершину в графе.
 *
 * @param <T> Тип объекта, хранящегося в вершине.
 */
public class Vertex<T> {
    private T object;

    /**
     * Конструктор для создания вершины с указанным объектом.
     *
     * @param object Объект, хранящийся в вершине.
     */
    Vertex(T object) {
        this.object = object;
    }

    /**
     * Получить объект, хранящийся в вершине.
     *
     * @return Объект вершины.
     */
    T getObject() {
        return this.object;
    }

    /**
     * Установить объект, хранящийся в вершине.
     *
     * @param object Новый объект вершины.
     */
    void setObject(T object) {
        this.object = object;
    }

    /**
     * Переопределение метода equals для сравнения вершин.
     *
     * @param obj Объект для сравнения.
     * @return true, если вершины равны, в противном случае - false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vertex<T> vertex = (Vertex<T>) obj;
        return this.object.equals(vertex.object);
    }

    /**
     * Переопределение метода hashCode для вычисления хеш-кода вершины.
     *
     * @return Значение хеш-кода вершины.
     */
    @Override
    public int hashCode() {
        return Objects.hash(object);
    }
}
