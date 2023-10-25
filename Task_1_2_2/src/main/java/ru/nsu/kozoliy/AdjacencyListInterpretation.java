package ru.nsu.kozoliy;

import java.util.*;

/**
 * Класс для представления графа в виде списка смежности.
 *
 * @param <T> Тип вершин графа.
 */
public class AdjacencyListInterpretation<T> implements GraphLaws<T> {
    private final Map<T, LinkedList<Edge<T>>> adjacencyList;

    /**
     * Конструктор класса AdjacencyListInterpretation.
     * Инициализирует пустой список смежности.
     */
    public AdjacencyListInterpretation() {
        adjacencyList = new HashMap<>();
    }


    @Override
    public void addVertex(T vertexToAdd) {
        if (!adjacencyList.containsKey(vertexToAdd)) {
            adjacencyList.put(vertexToAdd, new LinkedList<>());
        }
    }


    @Override
    public void removeVertex(T vertexToAdd) {
        adjacencyList.remove(vertexToAdd);
        for (T v : adjacencyList.keySet()) {
            adjacencyList.get(v).removeIf(edge -> edge.destination.equals(vertexToAdd));
        }
    }


    @Override
    public void addEdge(Edge<T> edgeToAdd) {
        adjacencyList.computeIfAbsent(edgeToAdd.source, k -> new LinkedList<>())
                .add(new Edge<>(edgeToAdd.source, edgeToAdd.destination, edgeToAdd.weight));
    }


    @Override
    public void removeEdge(Edge<T> edgeToRemove) {
        adjacencyList.getOrDefault(edgeToRemove.source, new LinkedList<>())
                .removeIf(edge -> edge.destination.equals(edgeToRemove.destination));
    }


    @Override
    public void changeVertex(T oldVertex, T newVertex) {
        if (adjacencyList.containsKey(oldVertex)) {
            adjacencyList.put(newVertex, adjacencyList.remove(oldVertex));
            for (T v : adjacencyList.keySet()) {
                adjacencyList.get(v).forEach(edge -> {
                    if (edge.destination.equals(oldVertex)) {
                        edge.destination = newVertex;
                    }
                });
            }
        }
    }


    @Override
    public void changeEdge(Edge<T> oldEdge, Edge<T> newEdge) {
        LinkedList<Edge<T>> edges = adjacencyList.get(oldEdge.source);
        if (edges != null) {
            edges.removeIf(edge -> edge.equals(oldEdge));
            if (newEdge != null) {
                edges.add(newEdge);
            }
        }
    }


    @Override
    public ArrayList<T> getVertexes() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    @Override
    public ArrayList<Edge<T>> getNeighbors(T vertex) {
        ArrayList<Edge<T>> neighbors = new ArrayList<>();
        LinkedList<Edge<T>> edges = adjacencyList.get(vertex);

        if (edges != null) {
            neighbors.addAll(edges);
        }

        return neighbors;
    }
}
