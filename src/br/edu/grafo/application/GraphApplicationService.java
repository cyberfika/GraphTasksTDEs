package br.edu.grafo.application;

import br.edu.grafo.model.*;
import br.edu.grafo.algorithm.*;
import br.edu.grafo.util.*;
import java.util.*;

/**
 * Application service for graph use cases.
 *
 * Orchestrates graph operations and algorithms.
 * Separates use-case logic from presentation and domain logic.
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 2.0
 */
public class GraphApplicationService {
    private DirectedGraph grafo;

    public GraphApplicationService() {
    }

    public void createGraph(int numVertices) {
        this.grafo = new DirectedGraph(numVertices);

        for (int i = 0; i < numVertices; i++) {
            grafo.setInformation(i, "V" + i);
        }
    }

    public void setGraph(DirectedGraph graph) {
        this.grafo = graph;
    }

    public DirectedGraph getGraph() {
        return grafo;
    }

    public boolean hasGraph() {
        return grafo != null;
    }

    public void addEdge(int origin, int destination, double weight, String label) {
        if (!label.isEmpty()) {
            grafo.createEdge(origin, destination, weight, label);
        } else {
            grafo.createEdge(origin, destination, weight);
        }
    }

    public void removeEdge(int origin, int destination) {
        grafo.removeEdge(origin, destination);
    }

    public List<Integer> executeBFS(int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= grafo.getNumVertices()) {
            throw new IllegalArgumentException("Invalid vertex: " + sourceVertex);
        }

        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[grafo.getNumVertices()];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(sourceVertex);
        visited[sourceVertex] = true;

        while (!queue.isEmpty()) {
            int v = queue.poll();
            result.add(v);

            for (Edge edge : grafo.getAdjacencies(v)) {
                int neighbor = edge.getDestination();
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }

        return result;
    }

    public List<Integer> executeDFS(int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= grafo.getNumVertices()) {
            throw new IllegalArgumentException("Invalid vertex: " + sourceVertex);
        }

        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[grafo.getNumVertices()];

        dfsRecursive(sourceVertex, visited, result);

        return result;
    }

    private void dfsRecursive(int v, boolean[] visited, List<Integer> result) {
        visited[v] = true;
        result.add(v);

        for (Edge edge : grafo.getAdjacencies(v)) {
            int neighbor = edge.getDestination();
            if (!visited[neighbor]) {
                dfsRecursive(neighbor, visited, result);
            }
        }
    }

    public double[] executeDijkstra(int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= grafo.getNumVertices()) {
            throw new IllegalArgumentException("Invalid vertex: " + sourceVertex);
        }

        int n = grafo.getNumVertices();
        double[] distances = new double[n];
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
        }
        distances[sourceVertex] = 0;

        PriorityQueue<PriorityPair> pq = new PriorityQueue<>();
        pq.add(new PriorityPair(0, sourceVertex));

        while (!pq.isEmpty()) {
            PriorityPair current = pq.poll();
            int u = current.vertex;

            if (visited[u]) continue;
            visited[u] = true;

            for (Edge edge : grafo.getAdjacencies(u)) {
                int v = edge.getDestination();
                double weight = edge.getWeight();

                if (distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    pq.add(new PriorityPair(distances[v], v));
                }
            }
        }

        return distances;
    }

    public boolean[][] executeWarshall() {
        return GraphAlgorithms.warshall(grafo);
    }

    public void saveGraph(String filename) {
        GraphStorage.saveGraph(grafo, filename);
    }

    public DirectedGraph loadGraph(String filename) {
        return GraphStorage.loadGraph(filename);
    }

    /**
     * Helper class for Dijkstra priority queue.
     */
    private static class PriorityPair implements Comparable<PriorityPair> {
        double distance;
        int vertex;

        PriorityPair(double distance, int vertex) {
            this.distance = distance;
            this.vertex = vertex;
        }

        @Override
        public int compareTo(PriorityPair other) {
            return Double.compare(this.distance, other.distance);
        }
    }
}
