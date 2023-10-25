package ru.nsu.kozoliy;

import java.util.ArrayList;

/**
 * Интерфейс для представления различных видов графов.
 *
 * @param <T> Тип вершин в графе.
 */
public interface GraphLaws<T> {
    /**
     * Добавляет вершину в граф.
     *
     * @param vertexToAdd Вершина для добавления.
     */
    void addVertex(T vertexToAdd);

    /**
     * Добавляет ребро в граф.
     *
     * @param edgeToAdd Ребро для добавления.
     */
    void addEdge(Edge<T> edgeToAdd);

    /**
     * Удаляет вершину из графа.
     *
     * @param vertexToRemove Вершина для удаления.
     */
    void removeVertex(T vertexToRemove);

    /**
     * Удаляет ребро из графа.
     *
     * @param edgeToRemove Ребро для удаления.
     */
    void removeEdge(Edge<T> edgeToRemove);

    /**
     * Заменяет одну вершину на другую.
     *
     * @param oldVertex Старая вершина.
     * @param newVertex Новая вершина.
     */
    void changeVertex(T oldVertex, T newVertex);

    /**
     * Заменяет одно ребро другим.
     *
     * @param oldEdge Старое ребро.
     * @param newEdge Новое ребро.
     */
    void changeEdge(Edge<T> oldEdge, Edge<T> newEdge);

    /**
     * Возвращает список вершин в графе.
     *
     * @return Список вершин.
     */
    ArrayList<T> getVertexes();

    /**
     * Возвращает список рёбер, инцидентных заданной вершине.
     *
     * @param vertex Вершина, для которой необходимо получить список инцидентных рёбер.
     * @return Список инцидентных рёбер.
     */
    ArrayList<Edge<T>> getNeighbors(T vertex);
}
