package ru.nsu.kozoliy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public abstract class GraphLaws<T> {
    protected ArrayList<Vertex<T>> vertexes;
    protected ArrayList<Edge<T>> edges;
    protected static final int serviceVar = Integer.MAX_VALUE;


    public abstract boolean addVertex(Vertex<T> vertex);

    public abstract void removeVertex(Vertex<T> vertex);

    public abstract void changeVertex(Vertex<T> oldVertex, Vertex<T> newVertex);

    public abstract void addEdge(Edge<T> edge);

    public abstract void removeEdge(Edge<T> edge);

    public abstract void changeEdge(Edge<T> oldEdge, Edge<T> newEdge);


    public ArrayList<Edge<T>> getEdge(Vertex<T> vertex) {
        ArrayList<Edge<T>> result = new ArrayList<>();
        for (Edge<T> tEdge : this.edges) {
            if (tEdge.getSource().equals(vertex)) {
                result.add(tEdge);
            }
        }
        return result;
    }


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
        for (VertexDistance<T> tVertexDistance : sortArray) {
            result.add(tVertexDistance.vertex);
        }
        return result;
    }

    protected class VertexDistance<T> implements Comparable<VertexDistance> {
        protected Vertex<T> vertex;
        protected int distance;

        /**
         * Class constructor.
         *
         * @param vertex   - vertex
         * @param distance - distance
         */
        VertexDistance(Vertex<T> vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        /**
         * Override compareTo method.
         *
         * @param vertexDistance - vertexDistance object
         * @return positive, negative number or zero
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