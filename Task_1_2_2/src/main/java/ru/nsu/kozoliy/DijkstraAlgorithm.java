package ru.nsu.kozoliy;

import java.util.*;

/**
 * Реализация алгоритма Дейкстры для поиска кратчайших путей в графе.
 *
 * @param <T> Тип вершин в графе.
 */
public class DijkstraAlgorithm<T> {
    private final GraphLaws<T> representation;

    /**
     * Конструктор класса DijkstraAlgorithm.
     *
     * @param representation Представление графа, для которого будет выполняться поиск кратчайших путей.
     */
    public DijkstraAlgorithm(GraphLaws<T> representation) {
        this.representation = representation;
    }

    /**
     * Находит кратчайшие пути от указанной стартовой вершины до остальных вершин в графе.
     *
     * @param startVertex Начальная вершина, от которой начинается поиск кратчайших путей.
     * @return Строка, представляющая кратчайшие пути от начальной вершины до остальных вершин.
     */
    public String findShortestPaths(T startVertex) {
        List<T> vertices = representation.getVertexes();
        int vertexCount = vertices.size();

        List<Integer> distances = new ArrayList<>(Collections.nCopies(vertexCount, Integer.MAX_VALUE));
        boolean[] visited = new boolean[vertexCount];

        int startIndex = vertices.indexOf(startVertex);
        if (startIndex == -1) {
            return "Start vertex not found in the graph.";
        }

        distances.set(startIndex, 0);

        for (int i = 0; i < vertexCount - 1; i++) {
            int minDistance = Integer.MAX_VALUE;
            int minIndex = -1;

            for (int j = 0; j < vertexCount; j++) {
                if (!visited[j] && distances.get(j) < minDistance) {
                    minDistance = distances.get(j);
                    minIndex = j;
                }
            }

            if (minIndex == -1) {
                break;
            }

            visited[minIndex] = true;

            for (Edge<T> edge : representation.getNeighbors(vertices.get(minIndex))) {
                int neighborIndex = vertices.indexOf(edge.destination);
                if (!visited[neighborIndex] && distances.get(minIndex) != Integer.MAX_VALUE &&
                        distances.get(minIndex) + edge.weight < distances.get(neighborIndex)) {
                    distances.set(neighborIndex, distances.get(minIndex) + edge.weight);
                }
            }
        }

        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < vertexCount; i++) {
            if (i != startIndex) {
                result.append(vertices.get(i)).append(" - ");
                if (distances.get(i) == Integer.MAX_VALUE) {
                    result.append("inf");
                } else {
                    result.append(distances.get(i));
                }
                if (i < vertexCount - 1) {
                    result.append(", ");
                }
            }
        }
        result.append("]");

        return result.toString();
    }
}
