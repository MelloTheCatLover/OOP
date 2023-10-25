package ru.nsu.kozoliy;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixInterpretation<T> implements GraphLaws<T> {

    private final ArrayList<ArrayList<Integer>> adjacencyMatrix;
    private final ArrayList<T> vertexes;
    private final int serviceVar = -100;

    public AdjacencyMatrixInterpretation(ArrayList<T> vertexes, ArrayList<Edge<T>> edges) {
        this.adjacencyMatrix = new ArrayList<>();
        this.vertexes = vertexes;

        for (int i = 0; i < vertexes.size(); i++) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for(int j = 0; j < vertexes.size(); j++){
                tmp.add(serviceVar);
            }
            this.adjacencyMatrix.add(tmp);
        }

        for (Edge<T> edge : edges) {
            int row = this.vertexes.indexOf(edge.source);
            int col = this.vertexes.indexOf(edge.destination);
            this.adjacencyMatrix.get(row).set(col, edge.weight);
        }


    }

    @Override
    public void addVertex(T vertexToAdd) {
        if (!this.vertexes.contains(vertexToAdd)) {
            this.vertexes.add(vertexToAdd);

            ArrayList<Integer> additionalRow = new ArrayList<>();
            for (int i = 0; i < vertexes.size() - 1; i++) {
                adjacencyMatrix.get(i).add(serviceVar);
                additionalRow.add(serviceVar);
            }
            additionalRow.add(serviceVar);
            this.adjacencyMatrix.add(additionalRow);
        }
    }

    @Override
    public void addEdge(Edge<T> edgeToAdd) {
        if (this.vertexes.contains(edgeToAdd.source) && this.vertexes.contains(edgeToAdd.destination)) {
            int row = this.vertexes.indexOf(edgeToAdd.source);
            int col = this.vertexes.indexOf(edgeToAdd.destination);
            this.adjacencyMatrix.get(row).set(col, edgeToAdd.weight);
        }
    }

    @Override
    public void removeVertex(T vertexToRemove) {
        if (this.vertexes.contains(vertexToRemove)) {
            int id = this.vertexes.indexOf(vertexToRemove);
            int len = this.vertexes.size();
            for (int i = 0; i < len; i++) {
                this.adjacencyMatrix.get(i).remove(id);
            }
            this.adjacencyMatrix.remove(id);
            this.vertexes.remove(id);
        }
    }

    @Override
    public void removeEdge(Edge<T> edgeToRemove) {
        if (this.vertexes.contains(edgeToRemove.source) && this.vertexes.contains(edgeToRemove.destination)) {
            int row = this.vertexes.indexOf(edgeToRemove.source);
            int col = this.vertexes.indexOf(edgeToRemove.destination);
            this.adjacencyMatrix.get(row).set(col, serviceVar);
        }
    }

    @Override
    public void changeVertex(T oldVertex, T newVertex) {
        if (this.vertexes.contains(oldVertex)) {
            int index = this.vertexes.indexOf(oldVertex);
            this.vertexes.set(index, newVertex);
        }
    }

    @Override
    public void changeEdge(Edge<T> oldEdge, Edge<T> newEdge) {
        if (this.vertexes.contains(oldEdge.source) && this.vertexes.contains(oldEdge.destination)) {
            int row = this.vertexes.indexOf(oldEdge.source);
            int col = this.vertexes.indexOf(oldEdge.destination);
            this.adjacencyMatrix.get(row).set(col, newEdge.weight);
        }
    }

    @Override
    public ArrayList<T> getVertexes() {
        return vertexes;
    }

    @Override
    public ArrayList<Edge<T>> getNeighbors(T vertex) {
        ArrayList<Edge<T>> incidentEdges = new ArrayList<>();
        int vertexIndex = vertexes.indexOf(vertex);

        if (vertexIndex != -1) {
            for (int i = 0; i < vertexes.size(); i++) {
                if (i != vertexIndex && adjacencyMatrix.get(vertexIndex).get(i) != serviceVar) {
                    int weight = adjacencyMatrix.get(vertexIndex).get(i);
                    incidentEdges.add(new Edge<>(vertex, vertexes.get(i), weight));
                }
            }
        }

        return incidentEdges;
    }

    public void printAdjacencyMatrix() {
        System.out.print("    "); // Пустая ячейка для вершин

        // Выводим названия вершин в первой строке
        for (T vertex : vertexes) {
            System.out.print(vertex + "  ");
        }
        System.out.println();

        for (int i = 0; i < vertexes.size(); i++) {
            System.out.print(vertexes.get(i) + "  "); // Выводим название вершины

            for (int j = 0; j < vertexes.size(); j++) {
                int weight = adjacencyMatrix.get(i).get(j);
                if (weight == serviceVar) {
                    System.out.print("X   "); // Отсутствие ребра
                } else {
                    System.out.printf("%d   ", weight); // Вес ребра
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Создаем список вершин
        ArrayList<String> vertexes = new ArrayList<>();
        vertexes.add("A");
        vertexes.add("B");
        vertexes.add("C");
        vertexes.add("D");
        vertexes.add("К");

        // Создаем список рёбер
        ArrayList<Edge<String>> edges = new ArrayList<>();
        edges.add(new Edge<>("A", "B", 2));
        edges.add(new Edge<>("A", "C", 1));
        edges.add(new Edge<>("B", "C", 3));
        edges.add(new Edge<>("C", "D", 4));

        // Создаем объект матрицы смежности
        AdjacencyMatrixInterpretation<String> adjacencyMatrix = new AdjacencyMatrixInterpretation<>(vertexes, edges);

        // Выводим матрицу смежности
        adjacencyMatrix.printAdjacencyMatrix();
    }

}
