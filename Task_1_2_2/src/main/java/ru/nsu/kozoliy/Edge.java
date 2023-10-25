package ru.nsu.kozoliy;

/**
 * Класс, представляющий ребро в графе.
 *
 * @param <T> Тип вершин, соединенных ребром.
 */
public class Edge<T> {
    /**
     * Начальная вершина ребра.
     */
    public T source;

    /**
     * Конечная вершина ребра.
     */
    public T destination;

    /**
     * Вес ребра (длина, стоимость и т. д.).
     */
    public final int weight;

    /**
     * Конструктор класса Edge.
     *
     * @param source      Начальная вершина ребра.
     * @param destination Конечная вершина ребра.
     * @param weight      Вес ребра.
     */
    public Edge(T source, T destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}
