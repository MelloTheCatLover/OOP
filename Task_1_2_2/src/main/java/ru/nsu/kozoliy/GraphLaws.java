package ru.nsu.kozoliy;

import java.util.ArrayList;

public interface GraphLaws<T> {
    void addVertex(T vertexToAdd);

    void addEdge(Edge<T> edgeToAdd);

    void removeVertex(T vertexToRemove);

    void removeEdge(Edge<T> edgeToRemove);

    void changeVertex(T oldVertex, T newVertex);

    void changeEdge(Edge<T> oldEdge, Edge<T> newEdge);

    ArrayList<T> getVertexes();

    ArrayList<Edge<T>> getNeighbors(T vertex);
}
