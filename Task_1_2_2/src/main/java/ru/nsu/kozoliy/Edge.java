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



    Vertex<T> getSource() {
        return this.source;
    }


    void setSource(Vertex<T> source) {
        this.source = source;
    }


    Vertex<T> getDestination() {
        return this.destination;
    }

    void setDestination(Vertex<T> destination) {
        this.destination = destination;
    }


    int getWeight() {
        return this.weight;
    }


    void setWeight(int weight) {
        this.weight = weight;
    }

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
     * Override hashCode method.
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.source.hashCode();
        result = 17 * result + this.destination.hashCode();
        result = result + this.weight;
        return result;
    }
}
