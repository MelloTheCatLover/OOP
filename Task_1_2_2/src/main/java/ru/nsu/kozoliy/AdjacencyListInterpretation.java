package ru.nsu.kozoliy;

import java.util.ArrayList;

public class AdjacencyListInterpretation<T> extends GraphLaws<T> {
    private ArrayList<ArrayList<Vertex<T>>> adjacencyList;

    public AdjacencyListInterpretation(ArrayList<Vertex<T>> vertexes, ArrayList<Edge<T>> edges) {
        this.adjacencyList = new ArrayList<>();
        this.vertexes = vertexes;
        this.edges = edges;

        for (int i = 0; i < vertexes.size(); i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (Edge<T> edge : edges) {
            int sourceIndex = vertexes.indexOf(edge.getSource());
            adjacencyList.get(sourceIndex).add(edge.getDestination());
        }
    }

    @Override
    public boolean addVertex(Vertex<T> vertexToAdd) {
        if (!vertexes.contains(vertexToAdd)) {
            vertexes.add(vertexToAdd);
            adjacencyList.add(new ArrayList<>());
            return true;
        }
        return false;
    }

    @Override
    public void removeVertex(Vertex<T> vertexToRemove) {
        if (vertexes.contains(vertexToRemove)) {
            int index = vertexes.indexOf(vertexToRemove);
            vertexes.remove(index);
            adjacencyList.remove(index);
            for (int i = 0; i < adjacencyList.size(); i++) {
                adjacencyList.get(i).remove(vertexToRemove);
            }
            edges.removeIf(edge -> edge.getSource().equals(vertexToRemove) || edge.getDestination().equals(vertexToRemove));
        }
    }

    @Override
    public void changeVertex(Vertex<T> oldVertex, Vertex<T> newVertex) {
        if (vertexes.contains(oldVertex) && !vertexes.contains(newVertex)) {
            int index = vertexes.indexOf(oldVertex);
            vertexes.set(index, newVertex);
            for (ArrayList<Vertex<T>> vertices : adjacencyList) {
                if (vertices.contains(oldVertex)) {
                    vertices.set(vertices.indexOf(oldVertex), newVertex);
                }
            }
            for (Edge<T> edge : edges) {
                if (edge.getSource().equals(oldVertex)) {
                    edge.setSource(newVertex);
                }
                if (edge.getDestination().equals(oldVertex)) {
                    edge.setDestination(newVertex);
                }
            }
        }
    }

    @Override
    public void addEdge(Edge<T> edgeToAdd) {
        if (vertexes.contains(edgeToAdd.getSource()) && vertexes.contains(edgeToAdd.getDestination())) {
            int sourceIndex = vertexes.indexOf(edgeToAdd.getSource());
            adjacencyList.get(sourceIndex).add(edgeToAdd.getDestination());
            edges.add(edgeToAdd);
        }
    }

    @Override
    public void removeEdge(Edge<T> edgeToRemove) {
        if (edges.contains(edgeToRemove)) {
            int sourceIndex = vertexes.indexOf(edgeToRemove.getSource());
            adjacencyList.get(sourceIndex).remove(edgeToRemove.getDestination());
            edges.remove(edgeToRemove);
        }
    }

    @Override
    public void changeEdge(Edge<T> oldEdge, Edge<T> newEdge) {
        if (edges.contains(oldEdge)) {
            int index = edges.indexOf(oldEdge);
            edges.set(index, newEdge);
        }
    }
}