package ru.nsu.kozoliy;

import java.util.Objects;

/**
 * Класс, представляющий ребро в графе.
 *
 * @param <T> Тип вершин, соединенных ребром.
 */
public class Edge<T> {
    /**
     * Начальная вершина ребра.
     */
    private Vertex<T> source;

    /**
     * Конечная вершина ребра.
     */
    private Vertex<T> destination;

    /**
     * Вес ребра (длина, стоимость и т. д.).
     */
    private int weight;

    /**
     * Конструктор класса Edge.
     *
     * @param source      Начальная вершина ребра.
     * @param destination Конечная вершина ребра.
     * @param weight      Вес ребра.
     */
    public Edge(Vertex<T> source, Vertex<T> destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Получить начальную вершину ребра.
     *
     * @return Начальная вершина ребра.
     */
    Vertex<T> getSource() {
        return this.source;
    }

    /**
     * Установить начальную вершину ребра.
     *
     * @param source Новая начальная вершина ребра.
     */
    void setSource(Vertex<T> source) {
        this.source = source;
    }

    /**
     * Получить конечную вершину ребра.
     *
     * @return Конечная вершина ребра.
     */
    Vertex<T> getDestination() {
        return this.destination;
    }

    /**
     * Установить конечную вершину ребра.
     *
     * @param destination Новая конечная вершина ребра.
     */
    void setDestination(Vertex<T> destination) {
        this.destination = destination;
    }

    /**
     * Получить вес ребра.
     *
     * @return Вес ребра.
     */
    int getWeight() {
        return this.weight;
    }

    /**
     * Установить вес ребра.
     *
     * @param weight Новый вес ребра.
     */
    void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Переопределение метода equals для сравнения ребер.
     *
     * @param obj Объект для сравнения.
     * @return true, если ребра равны, в противном случае - false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Edge<Vertex<T>> edge = (Edge<Vertex<T>>) obj;
        return ((this.source.equals(edge.source)) && (this.destination.equals(edge.destination))
                && (this.weight == edge.weight));
    }

    /**
     * Переопределение метода hashCode для вычисления хеш-кода ребра.
     *
     * @return Значение хеш-кода ребра.
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, destination, weight);
    }
}
