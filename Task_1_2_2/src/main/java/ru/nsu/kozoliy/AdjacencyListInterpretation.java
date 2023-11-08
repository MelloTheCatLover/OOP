package ru.nsu.kozoliy;

import java.util.ArrayList;

/**
 * Реализация графа с использованием списка смежности.
 *
 * @param <T> Тип вершин в графе.
 */
public class AdjacencyListInterpretation<T> extends GraphLaws<T> {
    private ArrayList<ArrayList<Vertex<T>>> adjacencyList;

    /**
     * Конструктор для создания экземпляра AdjacencyListInterpretation.
     *
     * @param vertexes Список вершин графа.
     * @param edges    Список рёбер графа.
     */
    public AdjacencyListInterpretation(ArrayList<Vertex<T>> vertexes, ArrayList<Edge<T>> edges) {
        this.adjacencyList = new ArrayList<>();
        this.vertexes = vertexes;
        this.edges = edges;

        for (int i = 0; i < vertexes.size(); i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (Edge<T> edge : edges) {
            int sourceIndex = vertexes.indexOf(edge.getSource());
            adjacencyList.get(sourceIndex).add(edge.getDestination());
        }
    }

    /**
     * Добавляет новую вершину в граф.
     *
     * @param vertexToAdd Вершина для добавления.
     * @return true, если вершина успешно добавлена, в противном случае - false.
     */
    @Override
    public boolean addVertex(Vertex<T> vertexToAdd) {
        if (!vertexes.contains(vertexToAdd)) {
            vertexes.add(vertexToAdd);
            adjacencyList.add(new ArrayList<>());
            return true;
        }
        return false;
    }

    /**
     * Удаляет вершину из графа.
     *
     * @param vertexToRemove Вершина для удаления.
     */
    @Override
    public boolean removeVertex(Vertex<T> vertexToRemove) {

        if (vertexes.contains(vertexToRemove)) {
            int index = vertexes.indexOf(vertexToRemove);
            if (index < 0) {
                throw new IllegalArgumentException("Vertex not found in the graph.");
            }
            vertexes.remove(index);
            adjacencyList.remove(index);
            for (ArrayList<Vertex<T>> vertices : adjacencyList) {
                vertices.remove(vertexToRemove);
            }
            edges.removeIf(edge -> edge.getSource().equals(vertexToRemove)
                    || edge.getDestination().equals(vertexToRemove));
            return true;
        } else {
            throw new IllegalArgumentException("Vertex not found in the graph.");
        }
    }

    /**
     * Заменяет вершину в графе.
     * Замена происходит только если граф содержит старую вершину и не содержит новой.
     *
     * @param oldVertex Старая вершина.
     * @param newVertex Новая вершина.
     */
    @Override
    public boolean changeVertex(Vertex<T> oldVertex, Vertex<T> newVertex) {
        if (vertexes.contains(oldVertex) && !vertexes.contains(newVertex)) {
            int index = vertexes.indexOf(oldVertex);
            vertexes.set(index, newVertex);

            System.out.println("Vertex replaced: " + oldVertex + " with " + newVertex);

            for (ArrayList<Vertex<T>> vertices : adjacencyList) {
                if (vertices.contains(oldVertex)) {
                    vertices.set(vertices.indexOf(oldVertex), newVertex);
                }
            }
            for (Edge<T> edge : edges) {
                if (edge.getSource().equals(oldVertex)) {
                    edge.setSource(newVertex);
                }
                if (edge.getDestination().equals(oldVertex)) {
                    edge.setDestination(newVertex);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Добавляет новое ребро в граф.
     * Если ребро уже в графе, ничего не происходит.
     *
     * @param edgeToAdd Ребро для добавления.
     */
    @Override
    public boolean addEdge(Edge<T> edgeToAdd) {
        if (vertexes.contains(edgeToAdd.getSource())
                && vertexes.contains(edgeToAdd.getDestination())) {
            System.out.println("Edge added: " +
                    edgeToAdd.getSource() + " -> " + edgeToAdd.getDestination());
            int sourceIndex = vertexes.indexOf(edgeToAdd.getSource());
            adjacencyList.get(sourceIndex).add(edgeToAdd.getDestination());
            edges.add(edgeToAdd);
            return true;
        }
        return false;
    }

    /**
     * Удаляет ребро из графа.
     *
     * @param edgeToRemove Ребро для удаления.
     */
    @Override
    public boolean removeEdge(Edge<T> edgeToRemove) {
        if (edges.contains(edgeToRemove)) {
            int sourceIndex = vertexes.indexOf(edgeToRemove.getSource());
            if (sourceIndex < 0) {
                throw new IllegalArgumentException("Edge not found in the graph.");
            }
            adjacencyList.get(sourceIndex).remove(edgeToRemove.getDestination());
            edges.remove(edgeToRemove);
            return true;
        } else {
            throw new IllegalArgumentException("Edge not found in the graph.");
        }
    }

    /**
     * Изменяет ребро в графе.
     *
     * @param oldEdge Старое ребро.
     * @param newEdge Новое ребро.
     */
    @Override
    public boolean changeEdge(Edge<T> oldEdge, Edge<T> newEdge) {
        if (edges.contains(oldEdge)) {
            int index = edges.indexOf(oldEdge);
            edges.set(index, newEdge);
            return true;
        } else {
            return false;
        }
    }
}
