package ru.nsu.kozoliy;

import java.util.ArrayList;

/**
 * Реализация графа с использованием матрицы смежности.
 *
 * @param <T> Тип вершин в графе.
 */
public class AdjacencyMatrixInterpretation<T> extends GraphLaws<T> {
    private final ArrayList<ArrayList<Integer>> adjacencyMatrix;

    /**
     * Конструктор для создания экземпляра AdjacencyMatrixInterpretation.
     *
     * @param vertexes Список вершин графа.
     * @param edges    Список рёбер графа.
     */
    public AdjacencyMatrixInterpretation(ArrayList<Vertex<T>> vertexes, ArrayList<Edge<T>> edges) {
        this.adjacencyMatrix = new ArrayList<>();
        this.vertexes = vertexes;
        this.edges = edges;

        for (int i = 0; i < vertexes.size(); i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < vertexes.size(); j++) {
                row.add(0);
            }
            adjacencyMatrix.add(row);
        }

        for (Edge<T> edge : edges) {
            int row = vertexes.indexOf(edge.getSource());
            int col = vertexes.indexOf(edge.getDestination());
            this.adjacencyMatrix.get(row).set(col, edge.getWeight());
        }
    }

    /**
     * Добавляет новую вершину в граф.
     *
     * @param vertex Вершина для добавления.
     * @return true, если вершина успешно добавлена, в противном случае - false.
     */
    @Override
    public boolean addVertex(Vertex<T> vertex) {
        if (!this.vertexes.contains(vertex)) {
            ArrayList<Integer> newRow = new ArrayList<>();
            for (int i = 0; i < this.vertexes.size(); i++) {
                this.adjacencyMatrix.get(i).add(0);
                newRow.add(0);
            }
            newRow.add(0);
            this.adjacencyMatrix.add(newRow);
            this.vertexes.add(vertex);
            return true;
        }
        return false;
    }

    /**
     * Добавляет новое ребро в граф.
     *
     * @param edgeToAdd Ребро для добавления.
     */
    @Override
    public void addEdge(Edge<T> edgeToAdd) {
        if (this.vertexes.contains(edgeToAdd.getSource())
                && this.vertexes.contains(edgeToAdd.getDestination())) {
            int row = this.vertexes.indexOf(edgeToAdd.getSource());
            int col = this.vertexes.indexOf(edgeToAdd.getDestination());
            this.adjacencyMatrix.get(row).set(col, edgeToAdd.getWeight());
            this.edges.add(edgeToAdd);
        }
    }

    /**
     * Удаляет вершину из графа.
     *
     * @param vertexToRemove Вершина для удаления.
     */
    @Override
    public void removeVertex(Vertex<T> vertexToRemove) {
        if (this.vertexes.contains(vertexToRemove)) {
            int index = this.vertexes.indexOf(vertexToRemove);
            int len = this.vertexes.size();
            for (int i = 0; i < len; i++) {
                this.adjacencyMatrix.get(i).remove(index);
            }
            this.adjacencyMatrix.remove(index);
            this.vertexes.remove(index);
            for (int i = 0; i < this.edges.size(); i++) {
                Edge<T> cur = this.edges.get(i);
                if (cur.getSource().equals(vertexToRemove)
                        || cur.getDestination().equals(vertexToRemove)) {
                    this.edges.remove(i);
                    i = i - 1;
                }
            }
        }
    }

    /**
     * Удаляет ребро из графа.
     *
     * @param edgeToRemove Ребро для удаления.
     */
    @Override
    public void removeEdge(Edge<T> edgeToRemove) {
        if (this.edges.contains(edgeToRemove)) {
            int row = this.vertexes.indexOf(edgeToRemove.getSource());
            int col = this.vertexes.indexOf(edgeToRemove.getDestination());
            this.adjacencyMatrix.get(row).set(col, 0);
            this.edges.remove(edgeToRemove);
        }
    }

    /**
     * Заменяет вершину в графе.
     *
     * @param oldVertex Старая вершина.
     * @param newVertex Новая вершина.
     */
    @Override
    public void changeVertex(Vertex<T> oldVertex, Vertex<T> newVertex) {
        if (this.vertexes.contains(oldVertex) && !this.vertexes.contains(newVertex)) {
            int index = this.vertexes.indexOf(oldVertex);
            this.vertexes.set(index, newVertex);
            for (Edge<T> edge : this.edges) {
                if (edge.getSource().equals(oldVertex)) {
                    edge.setSource(newVertex);
                }
                if (edge.getDestination().equals(oldVertex)) {
                    edge.setDestination(newVertex);
                }
            }
        }
    }

    /**
     * Изменяет ребро в графе.
     *
     * @param oldEdge Старое ребро.
     * @param newEdge Новое ребро.
     */
    @Override
    public void changeEdge(Edge<T> oldEdge, Edge<T> newEdge) {
        if (oldEdge.getSource().equals(newEdge.getSource())
                && oldEdge.getDestination().equals(newEdge.getDestination())) {
            if (this.edges.contains(oldEdge)) {
                int row = this.vertexes.indexOf(oldEdge.getSource());
                int col = this.vertexes.indexOf(oldEdge.getDestination());
                this.adjacencyMatrix.get(row).set(col, newEdge.getWeight());
                int index = this.edges.indexOf(oldEdge);
                this.edges.set(index, newEdge);
            }
        }
    }
}
