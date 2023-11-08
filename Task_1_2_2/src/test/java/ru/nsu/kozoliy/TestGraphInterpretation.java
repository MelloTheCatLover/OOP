package ru.nsu.kozoliy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;


/**
 * Тесты для 1.2.2.
 *
 */
public class TestGraphInterpretation {

    @Test
    public void  graphAdjacencyMatrixTest() {
        ArrayList<Vertex<String>> vertices = new ArrayList<>();
        ArrayList<Edge<String>> edges = new ArrayList<>();
        try {
            File file = new File("info.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            int vertexLen = Integer.parseInt(br.readLine());
            for (int i = 0; i < vertexLen; i++) {
                String cur = br.readLine();
                vertices.add(new Vertex<>(cur));
            }
            int edgeLen = Integer.parseInt(br.readLine());
            for (int i = 0; i < edgeLen; i++) {
                String[] curArr = br.readLine().split(" ");
                edges.add(new Edge<>(new Vertex<>(curArr[0]), new Vertex<>(curArr[1]),
                        Integer.parseInt(curArr[2])));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        AdjacencyMatrixInterpretation<String> graph
                = new AdjacencyMatrixInterpretation<>(vertices, edges);
        graph.addVertex(new Vertex<>("F"));
        graph.addEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("F"), 10));
        graph.removeEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 10));
        graph.removeVertex(new Vertex<>("D"));
        graph.changeEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("F"), 10),
                new Edge<>(new Vertex<>("A"), new Vertex<>("F"), 11));
        graph.changeVertex(new Vertex<>("C"), new Vertex<>("D"));

        ArrayList<Vertex<String>> expectedResult = new ArrayList<>();
        expectedResult.add(new Vertex<>("A"));
        expectedResult.add(new Vertex<>("F"));
        expectedResult.add(new Vertex<>("D"));
        expectedResult.add(new Vertex<>("B"));
        ArrayList<Vertex<String>> result = graph.shortestPath(new Vertex<>("A"));
        assertArrayEquals(result.toArray(), expectedResult.toArray());
    }


    @Test
    public void graphIncidenceMatrixTest() {
        ArrayList<Vertex<String>> vertices = new ArrayList<>();
        ArrayList<Edge<String>> edges = new ArrayList<>();
        try {
            File file = new File("info.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            int vertexLen = Integer.parseInt(br.readLine());
            for (int i = 0; i < vertexLen; i++) {
                String cur = br.readLine();
                vertices.add(new Vertex<>(cur));
            }
            int edgeLen = Integer.parseInt(br.readLine());
            for (int i = 0; i < edgeLen; i++) {
                String[] curArr = br.readLine().split(" ");
                edges.add(new Edge<>(new Vertex<>(curArr[0]), new Vertex<>(curArr[1]),
                        Integer.parseInt(curArr[2])));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        IncidenceMatrixInterpretation<String> graph
                = new IncidenceMatrixInterpretation<>(vertices, edges);
        graph.addVertex(new Vertex<>("F"));
        graph.addEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("F"), 10));
        graph.removeEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 10));
        graph.removeVertex(new Vertex<>("D"));
        graph.changeEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("F"), 10),
                new Edge<>(new Vertex<>("A"), new Vertex<>("F"), 12));
        graph.changeVertex(new Vertex<>("C"), new Vertex<>("D"));

        ArrayList<Vertex<String>> expectedResult = new ArrayList<>();
        expectedResult.add(new Vertex<>("A"));
        expectedResult.add(new Vertex<>("F"));
        expectedResult.add(new Vertex<>("D"));
        expectedResult.add(new Vertex<>("B"));
        ArrayList<Vertex<String>> result = graph.shortestPath(new Vertex<>("A"));
        assertArrayEquals(result.toArray(), expectedResult.toArray());
    }

    @Test
    public void graphAdjacencyListTest() {
        ArrayList<Vertex<String>> vertices = new ArrayList<>();
        ArrayList<Edge<String>> edges = new ArrayList<>();
        try {
            File file = new File("info.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            int vertexLen = Integer.parseInt(br.readLine());
            for (int i = 0; i < vertexLen; i++) {
                String cur = br.readLine();
                vertices.add(new Vertex<>(cur));
            }
            int edgeLen = Integer.parseInt(br.readLine());
            for (int i = 0; i < edgeLen; i++) {
                String[] curArr = br.readLine().split(" ");
                edges.add(new Edge<>(new Vertex<>(curArr[0]), new Vertex<>(curArr[1]),
                        Integer.parseInt(curArr[2])));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        AdjacencyListInterpretation<String> graph
                = new AdjacencyListInterpretation<>(vertices, edges);
        graph.addVertex(new Vertex<>("G"));
        graph.addEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("G"), 10));
        graph.removeEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 10));
        graph.removeVertex(new Vertex<>("D"));
        graph.changeEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("G"), 10),
                new Edge<>(new Vertex<>("A"), new Vertex<>("G"), 22));
        graph.changeVertex(new Vertex<>("C"), new Vertex<>("D"));

        ArrayList<Vertex<String>> expectedResult = new ArrayList<>();
        expectedResult.add(new Vertex<>("A"));
        expectedResult.add(new Vertex<>("G"));
        expectedResult.add(new Vertex<>("D"));
        expectedResult.add(new Vertex<>("B"));
        ArrayList<Vertex<String>> result = graph.shortestPath(new Vertex<>("A"));
        assertArrayEquals(result.toArray(), expectedResult.toArray());
    }
}
