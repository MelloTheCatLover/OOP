package ru.nsu.kozoliy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;


public class TestGraphInterpretation {

    @Test
    public void testShortestPathsAdjacencyMatrix() {


        // Добавляем вершины
        ArrayList<String> vertexes = new ArrayList<>();
        vertexes.add("A");
        vertexes.add("B");
        vertexes.add("C");
        vertexes.add("D");
        vertexes.add("K");

        // Создаем список рёбер
        ArrayList<Edge<String>> edges = new ArrayList<>();
        edges.add(new Edge<>("A", "B", 2));
        edges.add(new Edge<>("A", "C", 1));
        edges.add(new Edge<>("B", "C", 3));
        edges.add(new Edge<>("C", "D", 4));

        AdjacencyMatrixInterpretation<String> graphRepresentation = new AdjacencyMatrixInterpretation<>(vertexes, edges);
        DijkstraAlgorithm<String> graph = new DijkstraAlgorithm<>(graphRepresentation);

        graphRepresentation.addVertex("Test");
        graphRepresentation.addEdge(new Edge<>("B", "Test", 6));
        graphRepresentation.removeVertex("D");
        graphRepresentation.removeEdge(new Edge<>("B", "E", 3));
        graphRepresentation.changeVertex("B", "G");
        graphRepresentation.changeEdge(new Edge<>("D", "E", 4), new Edge<>("D", "E", 16));


        String startVertex = "A";
        String result = graph.findShortestPaths(startVertex);
        assertEquals("[G - 2, C - 1, K - inf, Test - 8]", result);
    }

    @Test
    public void testShortestPathsAdjacencyList() {
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

        graphRepresentation.addVertex("Test");
        graphRepresentation.addEdge(new Edge<>("B", "Test", 6));
        graphRepresentation.removeVertex("D");
        graphRepresentation.removeEdge(new Edge<>("B", "E", 3));
        graphRepresentation.changeVertex("B", "G");
        graphRepresentation.changeEdge(new Edge<>("D", "E", 4), new Edge<>("D", "E", 16));

        String startVertex = "A";
        String result = graph.findShortestPaths(startVertex);
        assertEquals("[C - 3, E - inf, Test - 8, G - 2]", result);
    }


    @Test
    public void testShortestPathsIncidenceMatrix() {


        // Добавляем вершины
        ArrayList<String> vertexes = new ArrayList<>();
        vertexes.add("A");
        vertexes.add("B");
        vertexes.add("C");
        vertexes.add("D");
        vertexes.add("K");

        // Создаем список рёбер
        ArrayList<Edge<String>> edges = new ArrayList<>();
        edges.add(new Edge<>("A", "B", 2));
        edges.add(new Edge<>("A", "C", 1));
        edges.add(new Edge<>("B", "C", 3));
        edges.add(new Edge<>("C", "D", 4));

        IncidenceMatrixInterpretation<String> graphRepresentation = new IncidenceMatrixInterpretation<>(vertexes, edges);
        DijkstraAlgorithm<String> graph = new DijkstraAlgorithm<>(graphRepresentation);


        graphRepresentation.addVertex("Test");
        graphRepresentation.removeVertex("D");
        graphRepresentation.removeEdge(new Edge<>("B", "E", 3));
        graphRepresentation.changeVertex("B", "G");
        graphRepresentation.changeEdge(new Edge<>("A", "G", 2), new Edge<>("A", "G", 4));


        String startVertex = "A";
        String result = graph.findShortestPaths(startVertex);
        assertEquals("[G - 2, C - 1, K - inf, Test - inf]", result);
    }

}
