package ru.nsu.kozoliy;

import java.util.*;

public class DijkstraAlgorithm<T> {
    private final GraphLaws<T> representation;

    public DijkstraAlgorithm(GraphLaws<T> representation) {
        this.representation = representation;
    }

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



    public static void main(String[] args) {
        AdjacencyListInterpretation<String> graphRepresentation = new AdjacencyListInterpretation<>();
        DijkstraAlgorithm<String> graph = new DijkstraAlgorithm<>(graphRepresentation);

        // Добавляем вершины
        graphRepresentation.addVertex("A");
        graphRepresentation.addVertex("B");
        graphRepresentation.addVertex("C");
        graphRepresentation.addVertex("D");
        graphRepresentation.addVertex("E");
        graphRepresentation.addVertex("G");

        // Добавляем рёбра
        graphRepresentation.addEdge(new Edge<>("A", "B", 2));
        graphRepresentation.addEdge(new Edge<>("A", "D", 5));
        graphRepresentation.addEdge(new Edge<>("B", "C", 1));
        graphRepresentation.addEdge(new Edge<>("B", "E", 3));
        graphRepresentation.addEdge(new Edge<>("D", "E", 4));

        String startVertex = "A";
        String shortestPaths = graph.findShortestPaths(startVertex);

        System.out.println(startVertex + ": " + shortestPaths);
    }
}

