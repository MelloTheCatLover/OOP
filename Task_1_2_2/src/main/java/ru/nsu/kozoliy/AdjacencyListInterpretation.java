package ru.nsu.kozoliy;

import java.util.*;

public class AdjacencyListInterpretation<T> implements GraphLaws<T> {
    private final Map<T, LinkedList<Edge<T>>> adjacencyList;

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

    public ArrayList<Edge<T>> getNeighbors(T vertex) {
        ArrayList<Edge<T>> neighbors = new ArrayList<>();
        LinkedList<Edge<T>> edges = adjacencyList.get(vertex);

        if (edges != null) {
            neighbors.addAll(edges);
        }

        return neighbors;
    }

    public void printAdjacencyList() {
        for (T vertex : adjacencyList.keySet()) {
            System.out.print(vertex + ": ");
            LinkedList<Edge<T>> edges = adjacencyList.get(vertex);
            for (Edge<T> edge : edges) {
                System.out.print("(" + edge.destination + ", " + edge.weight + ") ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        AdjacencyListInterpretation<String> testGraph = new AdjacencyListInterpretation<>();

        // Добавление вершин
        testGraph.addVertex("A");
        testGraph.addVertex("B");
        testGraph.addVertex("C");
        testGraph.addVertex("D");

        // Добавление рёбер
        testGraph.addEdge(new Edge<>("A", "B", 2));
        testGraph.addEdge(new Edge<>("A", "C", 3));
        testGraph.addEdge(new Edge<>("B", "C", 1));
        testGraph.addEdge(new Edge<>("C", "D", 4));

        // Вывод списка смежности
        testGraph.printAdjacencyList();

        System.out.println(testGraph.getVertexes());
    }

}
