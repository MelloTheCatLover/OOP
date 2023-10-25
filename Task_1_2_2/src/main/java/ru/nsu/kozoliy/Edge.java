package ru.nsu.kozoliy;

public class Edge<T> {
    public T source;
    public T destination;

    public final int weight;

    public Edge(T source, T destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }


}
