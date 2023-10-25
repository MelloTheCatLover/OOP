package ru.nsu.kozoliy;

import java.util.ArrayList;

/**
 * Класс для представления графа в виде матрицы инцидентности.
 *
 * @param <T> Тип вершин графа.
 */
public class IncidenceMatrixInterpretation<T> implements GraphLaws<T> {

    private ArrayList<ArrayList<EdgeStatus<T>>> incidenceMatrix;
    private ArrayList<T> vertexes;
    private ArrayList<Edge<T>> edges;
    private final int serviceVar = 8;

    /**
     * Конструктор класса IncidenceMatrixInterpretation.
     * Инициализирует матрицу инцидентности на основе списка вершин и рёбер графа.
     *
     * @param vertexes Список вершин графа.
     * @param edges    Список рёбер графа.
     */
    public IncidenceMatrixInterpretation(ArrayList<T> vertexes, ArrayList<Edge<T>> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
        this.incidenceMatrix = new ArrayList<>();

        for (T vertex : vertexes) {
            ArrayList<EdgeStatus<T>> cur = new ArrayList<>();
            for (Edge<T> edge : edges) {
                if (vertex == edge.source && vertex == edge.destination) {
                    cur.add(new EdgeStatus<>(2, edge));
                } else if (vertex == edge.source) {
                    cur.add(new EdgeStatus<>(1, edge));
                } else if (vertex == edge.destination) {
                    cur.add(new EdgeStatus<>(-1, edge));
                } else {
                    cur.add(new EdgeStatus<>(0, null));
                }
            }
            this.incidenceMatrix.add(cur);
        }
    }

    /**
     * Добавляет новую вершину в граф.
     *
     * @param vertexToAdd Вершина для добавления.
     */
    @Override
    public void addVertex(T vertexToAdd) {
        if (!this.vertexes.contains(vertexToAdd)) {
            this.vertexes.add(vertexToAdd);
            ArrayList<EdgeStatus<T>> additionalVertex = new ArrayList<>();
            for (int i = 0; i < edges.size(); i++) {
                additionalVertex.add(new EdgeStatus<>(serviceVar, null));
            }
            this.incidenceMatrix.add(additionalVertex);
        }
    }

    /**
     * Добавляет новое ребро в граф.
     *
     * @param edgeToAdd Ребро для добавления.
     */
    @Override
    public void addEdge(Edge<T> edgeToAdd) {
        if (this.vertexes.contains(edgeToAdd.source)
                && this.vertexes.contains(edgeToAdd.destination)
                && !this.edges.contains(edgeToAdd)) {
            int len = this.vertexes.size();
            for (int i = 0; i < len; i++) {
                T source = edgeToAdd.source;
                T destination = edgeToAdd.destination;
                if (this.vertexes.get(i) == source && source == destination) {
                    EdgeStatus<T> cur = new EdgeStatus<>(2, edgeToAdd);
                    this.incidenceMatrix.get(i).add(cur);
                } else if (this.vertexes.get(i) == source) {
                    EdgeStatus<T> cur = new EdgeStatus<>(-1, edgeToAdd);
                    this.incidenceMatrix.get(i).add(cur);
                } else if (this.vertexes.get(i) == destination) {
                    EdgeStatus<T> cur = new EdgeStatus<>(1, edgeToAdd);
                    this.incidenceMatrix.get(i).add(cur);
                } else {
                    EdgeStatus<T> cur = new EdgeStatus<>(0, null);
                    this.incidenceMatrix.get(i).add(cur);
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
    public void removeVertex(T vertexToRemove) {
        if (this.vertexes.contains(vertexToRemove)) {
            int index = this.vertexes.indexOf(vertexToRemove);
            for (int i = 0; i < this.edges.size(); i++) {
                EdgeStatus<T> cur = new EdgeStatus<>(serviceVar, null);
                this.incidenceMatrix.get(index).set(i, cur);
            }
            for (int i = 0; i < this.edges.size(); i++) {
                int count = 0;
                for (int j = 0; j < this.vertexes.size(); j++) {
                    if (this.incidenceMatrix.get(j).get(i).status == 1
                            || this.incidenceMatrix.get(j).get(i).status == -1) {
                        count++;
                    }
                    if (this.incidenceMatrix.get(j).get(i).status == 2) {
                        count += 2;
                    }
                }
                if (count < 2) {
                    removeEdge(this.edges.get(i));
                    i--;
                }
            }
            this.vertexes.remove(vertexToRemove);
            this.incidenceMatrix.remove(index);
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
            int len = this.vertexes.size();
            int index = this.edges.indexOf(edgeToRemove);
            for (int i = 0; i < len; i++) {
                this.incidenceMatrix.get(i).remove(index);
            }
            this.edges.remove(index);
        }
    }

    /**
     * Изменяет вершину в графе.
     *
     * @param oldVertex Старая вершина.
     * @param newVertex Новая вершина.
     */
    @Override
    public void changeVertex(T oldVertex, T newVertex) {
        if (this.vertexes.contains(oldVertex)) {
            for (Edge<T> edgeToAdd : this.edges) {
                if (edgeToAdd.source == oldVertex) {
                    edgeToAdd.source = newVertex;
                }
                if (edgeToAdd.destination == oldVertex) {
                    edgeToAdd.destination = newVertex;
                }
            }
            this.vertexes.set(this.vertexes.indexOf(oldVertex), newVertex);
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
        if (this.edges.contains(oldEdge)) {
            int index = this.edges.indexOf(oldEdge);
            for (int i = 0; i < this.vertexes.size(); i++) {
                if (this.incidenceMatrix.get(i).get(index).status == 2) {
                    this.incidenceMatrix.get(i).set(index, new EdgeStatus<>(2, newEdge));
                } else if (this.incidenceMatrix.get(i).get(index).status == -1) {
                    this.incidenceMatrix.get(i).set(index, new EdgeStatus<>(-1, newEdge));
                } else if (this.incidenceMatrix.get(i).get(index).status == 1) {
                    this.incidenceMatrix.get(i).set(index, new EdgeStatus<>(1, newEdge));
                }
            }
            this.edges.set(index, newEdge);
        }
    }

    /**
     * Возвращает список вершин графа.
     *
     * @return Список вершин.
     */
    @Override
    public ArrayList<T> getVertexes() {
        return vertexes;
    }

    /**
     * Возвращает список инцидентных рёбер для заданной вершины.
     *
     * @param vertex Вершина, для которой ищутся инцидентные рёбра.
     * @return Список инцидентных рёбер.
     */
    @Override
    public ArrayList<Edge<T>> getNeighbors(T vertex) {
        ArrayList<Edge<T>> outgoingEdges = new ArrayList<>();
        int vertexIndex = vertexes.indexOf(vertex);

        if (vertexIndex != -1) {
            for (int i = 0; i < edges.size(); i++) {
                int status = incidenceMatrix.get(vertexIndex).get(i).status;

                if (status == 1) { // Выход из вершины
                    Edge<T> edge = incidenceMatrix.get(vertexIndex).get(i).edge;
                    outgoingEdges.add(edge);
                }
            }
        }

        return outgoingEdges;
    }

    /**
     * Внутренний класс для представления статуса ребра в матрице инцидентности.
     *
     * @param <T> Тип вершин графа.
     */
    private static class EdgeStatus<T> {
        int status;
        Edge<T> edge;

        public EdgeStatus(int status, Edge<T> edge) {
            this.status = status;
            this.edge = edge;
        }
    }
}
