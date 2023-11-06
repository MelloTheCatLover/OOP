package ru.nsu.kozoliy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Абстрактный класс, представляющий законы для работы с графом.
 *
 * @param <T> Тип вершин в графе.
 */
public abstract class GraphLaws<T> {
    protected ArrayList<Vertex<T>> vertexes;
    protected ArrayList<Edge<T>> edges;
    protected static final int serviceVar = Integer.MAX_VALUE;

    /**
     * Абстрактный метод для добавления вершины в граф.
     *
     * @param vertex Вершина для добавления.
     * @return true, если вершина успешно добавлена, в противном случае - false.
     */
    public abstract boolean addVertex(Vertex<T> vertex);

    /**
     * Абстрактный метод для удаления вершины из графа.
     *
     * @param vertex Вершина для удаления.
     */
    public abstract void removeVertex(Vertex<T> vertex);

    /**
     * Абстрактный метод для изменения вершины в графе.
     *
     * @param oldVertex Старая вершина.
     * @param newVertex Новая вершина.
     */
    public abstract void changeVertex(Vertex<T> oldVertex, Vertex<T> newVertex);

    /**
     * Абстрактный метод для добавления ребра в граф.
     *
     * @param edge Ребро для добавления.
     */
    public abstract void addEdge(Edge<T> edge);

    /**
     * Абстрактный метод для удаления ребра из графа.
     *
     * @param edge Ребро для удаления.
     */
    public abstract void removeEdge(Edge<T> edge);

    /**
     * Абстрактный метод для изменения ребра в графе.
     *
     * @param oldEdge Старое ребро.
     * @param newEdge Новое ребро.
     */
    public abstract void changeEdge(Edge<T> oldEdge, Edge<T> newEdge);

    /**
     * Возвращает список рёбер, связанных с заданной вершиной.
     *
     * @param vertex Вершина, для которой нужно получить список рёбер.
     * @return Список рёбер, связанных с вершиной.
     */
    public ArrayList<Edge<T>> getEdge(Vertex<T> vertex) {
        ArrayList<Edge<T>> result = new ArrayList<>();
        for (Edge<T> edge : this.edges) {
            if (edge.getSource().equals(vertex)) {
                result.add(edge);
            }
        }
        return result;
    }

    /**
     * Вычисляет кратчайший путь от заданной вершины до всех остальных вершин в графе.
     *
     * @param vertex Вершина, от которой начинается поиск кратчайшего пути.
     * @return Список вершин, представляющих кратчайший путь от заданной вершины.
     */
    public ArrayList<Vertex<T>> shortestPath(Vertex<T> vertex) {
        int vertexLen = this.vertexes.size();
        int[] distance = new int[vertexLen];
        Arrays.fill(distance, serviceVar);
        distance[this.vertexes.indexOf(vertex)] = 0;
        boolean[] mark = new boolean[vertexLen];
        Arrays.fill(mark, false);

        for (int i = 0; i < vertexLen; i++) {
            int shortest = -1;
            for (int j = 0; j < vertexLen; j++) {
                if (!mark[j] && (shortest == -1 || distance[shortest] > distance[j])) {
                    shortest = j;
                }
            }
            if (distance[shortest] == serviceVar) {
                break;
            }
            mark[shortest] = true;

            ArrayList<Edge<T>> adjacentEdge = getEdge(this.vertexes.get(shortest));
            for (Edge<T> cur : adjacentEdge) {
                if (distance[shortest] + cur.getWeight()
                        < distance[this.vertexes.indexOf(cur.getDestination())]) {
                    distance[this.vertexes.indexOf(cur.getDestination())]
                            = distance[shortest] + cur.getWeight();
                }
            }
        }

        ArrayList<VertexDistance<T>> sortArray = new ArrayList<>();
        for (int i = 0; i < vertexLen; i++) {
            sortArray.add(new VertexDistance<>(this.vertexes.get(i), distance[i]));
        }
        Collections.sort(sortArray);
        ArrayList<Vertex<T>> result = new ArrayList<>();
        int len = sortArray.size();
        for (VertexDistance<T> vertexDistance : sortArray) {
            result.add(vertexDistance.vertex);
        }
        return result;
    }

    /**
     * Класс вершины и расстояния
     *
     */
    protected static class VertexDistance<T> implements Comparable<VertexDistance> {
        protected Vertex<T> vertex;
        protected int distance;

        /**
         * Конструктор класса VertexDistance.
         *
         * @param vertex   Вершина.
         * @param distance Расстояние.
         */
        VertexDistance(Vertex<T> vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        /**
         * Переопределение метода compareTo.
         *
         * @param vertexDistance Объект VertexDistance.
         * @return Положительное, отрицательное число или ноль.
         */
        @Override
        public int compareTo(VertexDistance vertexDistance) {
            return this.distance - vertexDistance.distance;
        }
    }

    @ExcludeFromJacocoGeneratedTestReport
    public static void main(String[] args) {

    }
}
