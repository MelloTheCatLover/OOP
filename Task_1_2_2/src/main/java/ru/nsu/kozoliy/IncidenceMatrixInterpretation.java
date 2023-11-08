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
                    row.add(new EdgeStatus<>(edge, Status.LOOP));
                } else if (vertex.equals(edge.getSource())) {
                    row.add(new EdgeStatus<>(edge, Status.SOURCE));
                } else if (vertex.equals(edge.getDestination())) {
                    row.add(new EdgeStatus<>(edge, Status.DESTINATION));
                } else {
                    row.add(new EdgeStatus<>(null, Status.NOTHING));
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
                row.add(new EdgeStatus<>(null, Status.NOTHING));
            }
            this.incidenceMatrix.add(row);
            return true;
        }
        return false;
    }

    /**
     * Добавляет новое ребро в граф.
     * Если ребро уже в графе, ничего не происходит.
     *
     * @param edgeToAdd Ребро для добавления.
     */
    @Override
    public boolean addEdge(Edge<T> edgeToAdd) {
        if (this.vertexes.contains(edgeToAdd.getSource())
                && this.vertexes.contains(edgeToAdd.getDestination())) {
            System.out.println("Edge added: "
                    + edgeToAdd.getSource() + " -> " + edgeToAdd.getDestination());
            Vertex<T> src = edgeToAdd.getSource();
            Vertex<T> dest = edgeToAdd.getDestination();
            for (int i = 0; i < this.vertexes.size(); i++) {
                if (this.vertexes.get(i).equals(src) && src.equals(dest)) {
                    this.incidenceMatrix.get(i).add(new EdgeStatus<>(edgeToAdd, Status.LOOP));
                } else if (this.vertexes.get(i).equals(src)) {
                    this.incidenceMatrix.get(i).add(new EdgeStatus<>(edgeToAdd, Status.SOURCE));
                } else if (this.vertexes.get(i).equals(dest)) {
                    this.incidenceMatrix.get(i).add(new EdgeStatus<>(edgeToAdd, Status.DESTINATION));
                } else {
                    this.incidenceMatrix.get(i).add(new EdgeStatus<>(null, Status.NOTHING));
                }
            }
            this.edges.add(edgeToAdd);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Удаляет вершину из графа.
     *
     * @param vertexToRemove Вершина для удаления.
     */
    @Override
    public boolean removeVertex(Vertex<T> vertexToRemove) {
        if (this.vertexes.contains(vertexToRemove)) {
            int index = this.vertexes.indexOf(vertexToRemove);
            if (index < 0) {
                throw new IllegalArgumentException("Vertex not found in the graph.");
            }
            for (int i = 0; i < this.edges.size(); i++) {
                EdgeStatus<T> cur = new EdgeStatus<>(null, Status.NOTHING);
                this.incidenceMatrix.get(index).set(i, cur);
            }

            for (int i = 0; i < this.edges.size(); i++) {
                int count = 0;
                for (int j = 0; j < this.vertexes.size(); j++) {
                    if (this.incidenceMatrix.get(j).get(i).status == Status.SOURCE
                            || this.incidenceMatrix.get(j).get(i).status == Status.DESTINATION) {
                        count = count + 1;
                    }
                    if (this.incidenceMatrix.get(j).get(i).status == Status.LOOP) {
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
            return true;
        } else {
            throw new IllegalArgumentException("Vertex not found in the graph.");
        }
    }

    /**
     * Удаляет ребро из графа.
     *
     * @param edgeToRemove Ребро для удаления.
     */
    @Override
    public boolean removeEdge(Edge<T> edgeToRemove) {
        if (this.edges.contains(edgeToRemove)) {
            int index = this.edges.indexOf(edgeToRemove);
            if (index < 0) {
                throw new IllegalArgumentException("Edge not found in the graph.");
            }
            for (int i = 0; i < this.vertexes.size(); i++) {
                this.incidenceMatrix.get(i).remove(index);
            }
            this.edges.remove(index);
            return true;
        } else {
            throw new IllegalArgumentException("Edge not found in the graph.");
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
        if (this.vertexes.contains(oldVertex) && !this.vertexes.contains(newVertex)) {
            int index = this.vertexes.indexOf(oldVertex);
            this.vertexes.set(index, newVertex);

            System.out.println("Vertex replaced: " + oldVertex + " with " + newVertex);

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
            return true;
        } else {
            return false;
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
        if (oldEdge.getSource().equals(newEdge.getSource())
                && oldEdge.getDestination().equals(newEdge.getDestination())) {
            if (this.edges.contains(oldEdge)) {
                int index = this.edges.indexOf(oldEdge);
                this.edges.set(index, newEdge);
                int len = this.vertexes.size();
                for (int i = 0; i < len; i++) {
                    if (this.incidenceMatrix.get(i).get(index).status != Status.NOTHING) {
                        this.incidenceMatrix.get(i).get(index).edge = newEdge;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public enum Status {
        DESTINATION,
        SOURCE,
        LOOP,
        NOTHING
    }

    /**
     * Класс для представления статуса рёбра в матрице инцидентности.
     *
     * @param <T> Тип вершины.
     */
    private static class EdgeStatus<T> {
        private Edge<T> edge;
        private final Status status;

        /**
         * Конструктор класса EdgeStatus.
         *
         * @param edge   Ребро.
         * @param status Статус.
         */
        EdgeStatus(Edge<T> edge, Status status) {
            this.edge = edge;
            this.status = status;
        }
    }
}
