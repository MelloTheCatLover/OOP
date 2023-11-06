package ru.nsu.kozoliy;

import java.util.ArrayList;

/**
 * Класс для представления графа в виде матрицы инцидентности.
 *
 * @param <T> Тип вершин графа.
 */
public class IncidenceMatrixInterpretation<T> extends GraphLaws<T> {

    private final ArrayList<ArrayList<EdgeStatus<T>>> incidenceMatrix;

    /**
     * Конструктор для создания экземпляра IncidenceMatrixInterpretation.
     *
     * @param vertexes Список вершин графа.
     * @param edges    Список рёбер графа.
     */
    public IncidenceMatrixInterpretation(ArrayList<Vertex<T>> vertexes, ArrayList<Edge<T>> edges) {
        this.incidenceMatrix = new ArrayList<>();
        this.vertexes = vertexes;
        this.edges = edges;

        for (Vertex<T> vertex : vertexes) {
            ArrayList<EdgeStatus<T>> row = new ArrayList<>();
            for (Edge<T> edge : edges) {
                if (vertex.equals(edge.getSource()) && vertex.equals(edge.getDestination())) {
                    row.add(new EdgeStatus<>(edge, 2));
                } else if (vertex.equals(edge.getSource())) {
                    row.add(new EdgeStatus<>(edge, 1));
                } else if (vertex.equals(edge.getDestination())) {
                    row.add(new EdgeStatus<>(edge, -1));
                } else {
                    row.add(new EdgeStatus<>(null, 0));
                }
            }
            this.incidenceMatrix.add(row);
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
        if (!this.vertexes.contains(vertexToAdd)) {
            this.vertexes.add(vertexToAdd);
            ArrayList<EdgeStatus<T>> row = new ArrayList<>();
            for (int i = 0; i < this.edges.size(); i++) {
                row.add(new EdgeStatus<>(null, 0));
            }
            this.incidenceMatrix.add(row);
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
            Vertex<T> src = edgeToAdd.getSource();
            Vertex<T> dest = edgeToAdd.getDestination();
            for (int i = 0; i < this.vertexes.size(); i++) {
                if (this.vertexes.get(i).equals(src) && src.equals(dest)) {
                    this.incidenceMatrix.get(i).add(new EdgeStatus<>(edgeToAdd, 2));
                } else if (this.vertexes.get(i).equals(src)) {
                    this.incidenceMatrix.get(i).add(new EdgeStatus<>(edgeToAdd, 1));
                } else if (this.vertexes.get(i).equals(dest)) {
                    this.incidenceMatrix.get(i).add(new EdgeStatus<>(edgeToAdd, -1));
                } else {
                    this.incidenceMatrix.get(i).add(new EdgeStatus<>(null, 0));
                }
            }
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
            for (int i = 0; i < this.edges.size(); i++) {
                EdgeStatus<T> cur = new EdgeStatus<>(null, 0);
                this.incidenceMatrix.get(index).set(i, cur);
            }

            for (int i = 0; i < this.edges.size(); i++) {
                int count = 0;
                for (int j = 0; j < this.vertexes.size(); j++) {
                    if (this.incidenceMatrix.get(j).get(i).status == 1
                            || this.incidenceMatrix.get(j).get(i).status == -1) {
                        count = count + 1;
                    }
                    if (this.incidenceMatrix.get(j).get(i).status == 2) {
                        count = count + 2;
                    }
                }
                if (count < 2) {
                    removeEdge(this.edges.get(i));
                    i--;
                }
            }
            this.incidenceMatrix.remove(index);
            this.vertexes.remove(vertexToRemove);
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
            int index = this.edges.indexOf(edgeToRemove);
            for (int i = 0; i < this.vertexes.size(); i++) {
                this.incidenceMatrix.get(i).remove(index);
            }
            this.edges.remove(index);
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

            for (int i = 0; i < this.vertexes.size(); i++) {
                for (int j = 0; j < this.edges.size(); j++) {
                    if (this.incidenceMatrix.get(i).get(j).edge != null) {
                        if (this.incidenceMatrix.get(i).get(j).edge.getSource().equals(oldVertex)) {
                            this.incidenceMatrix.get(i).get(j).edge.setSource(newVertex);
                        }
                        if (this.incidenceMatrix.get(i).get(j)
                                .edge.getDestination().equals(oldVertex)) {
                            this.incidenceMatrix.get(i).get(j).edge.setDestination(newVertex);
                        }
                    }
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
                int index = this.edges.indexOf(oldEdge);
                this.edges.set(index, newEdge);
                int len = this.vertexes.size();
                for (int i = 0; i < len; i++) {
                    if (this.incidenceMatrix.get(i).get(index).status != 0) {
                        this.incidenceMatrix.get(i).get(index).edge = newEdge;
                    }
                }
            }
        }
    }

    /**
     * Класс для представления статуса рёбра в матрице инцидентности.
     *
     * @param <T> Тип вершины.
     */
    private static class EdgeStatus<T> {
        private Edge<T> edge;
        private final int status;

        /**
         * Конструктор класса EdgeStatus.
         *
         * @param edge   Ребро.
         * @param status Статус.
         */
        EdgeStatus(Edge<T> edge, int status) {
            this.edge = edge;
            this.status = status;
        }
    }
}
