package br.edu.grafo.application;

import br.edu.grafo.algorithm.GraphAlgorithms;
import br.edu.grafo.algorithm.KruskalAlgorithm;
import br.edu.grafo.algorithm.KruskalResult;
import br.edu.grafo.model.DirectedGraph;
import br.edu.grafo.model.Edge;
import br.edu.grafo.util.GraphRepository;
import br.edu.grafo.util.GraphStorage;

import java.util.*;

/**
 * Implementacao de {@link GraphService}.
 *
 * <p>Orquestra os casos de uso do sistema: criacao e edicao de grafos,
 * execucao de algoritmos (BFS, DFS, Dijkstra, Warshall, Kruskal),
 * persistencia via {@link GraphRepository} e consulta de vertices.</p>
 *
 * <p>Depende de abstracao {@link GraphRepository} (DIP), nao de
 * {@code GraphStorage} diretamente. A implementacao padrao e injetada
 * no construtor sem argumentos.</p>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 3.0
 */
public class GraphApplicationService implements GraphService {

    private DirectedGraph graph;
    private final GraphRepository graphRepository;

    public GraphApplicationService() {
        this.graphRepository = new GraphStorage();
    }

    public GraphApplicationService(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    // --- Gestao do grafo ativo ---

    @Override
    public void createGraph(int numVertices) {
        this.graph = new DirectedGraph(numVertices);
        for (int i = 0; i < numVertices; i++) {
            graph.setInformation(i, "V" + i);
        }
    }

    @Override
    public void setGraph(DirectedGraph graph) {
        this.graph = graph;
    }

    @Override
    public DirectedGraph getGraph() {
        return graph;
    }

    @Override
    public boolean hasGraph() {
        return graph != null;
    }

    // --- Edicao de arestas ---

    @Override
    public boolean addEdge(int origin, int destination, double weight, String label) {
        if (label != null && !label.isEmpty()) {
            return graph.createEdge(origin, destination, weight, label);
        }
        return graph.createEdge(origin, destination, weight);
    }

    @Override
    public boolean removeEdge(int origin, int destination) {
        return graph.removeEdge(origin, destination);
    }

    // --- Algoritmos ---

    @Override
    public List<Integer> executeBFS(int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= graph.getNumVertices()) {
            throw new IllegalArgumentException("Invalid vertex: " + sourceVertex);
        }

        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[graph.getNumVertices()];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(sourceVertex);
        visited[sourceVertex] = true;

        while (!queue.isEmpty()) {
            int v = queue.poll();
            result.add(v);

            for (Edge edge : graph.getAdjacencies(v)) {
                int neighbor = edge.getDestination();
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }

        return result;
    }

    @Override
    public List<Integer> executeDFS(int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= graph.getNumVertices()) {
            throw new IllegalArgumentException("Invalid vertex: " + sourceVertex);
        }

        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[graph.getNumVertices()];
        dfsRecursive(sourceVertex, visited, result);
        return result;
    }

    private void dfsRecursive(int v, boolean[] visited, List<Integer> result) {
        visited[v] = true;
        result.add(v);

        for (Edge edge : graph.getAdjacencies(v)) {
            int neighbor = edge.getDestination();
            if (!visited[neighbor]) {
                dfsRecursive(neighbor, visited, result);
            }
        }
    }

    @Override
    public double[] executeDijkstra(int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= graph.getNumVertices()) {
            throw new IllegalArgumentException("Invalid vertex: " + sourceVertex);
        }

        int n = graph.getNumVertices();
        double[] distances = new double[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[sourceVertex] = 0;

        PriorityQueue<VertexDistance> pq = new PriorityQueue<>();
        pq.add(new VertexDistance(sourceVertex, 0));

        while (!pq.isEmpty()) {
            VertexDistance current = pq.poll();
            int u = current.vertex;

            if (visited[u]) continue;
            visited[u] = true;

            for (Edge edge : graph.getAdjacencies(u)) {
                int v = edge.getDestination();
                double candidate = distances[u] + edge.getWeight();
                if (candidate < distances[v]) {
                    distances[v] = candidate;
                    pq.add(new VertexDistance(v, candidate));
                }
            }
        }

        return distances;
    }

    @Override
    public ShortestPathResult executeShortestPath(int source, int destination) {
        if (source < 0 || source >= graph.getNumVertices()) {
            throw new IllegalArgumentException("Invalid source vertex: " + source);
        }
        if (destination < 0 || destination >= graph.getNumVertices()) {
            throw new IllegalArgumentException("Invalid destination vertex: " + destination);
        }

        int n = graph.getNumVertices();
        double[] distances = new double[n];
        int[] previous = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        Arrays.fill(previous, -1);
        distances[source] = 0.0;

        PriorityQueue<VertexDistance> pq = new PriorityQueue<>();
        pq.add(new VertexDistance(source, 0));

        while (!pq.isEmpty()) {
            VertexDistance current = pq.poll();
            int u = current.vertex;

            if (visited[u]) continue;
            visited[u] = true;

            if (u == destination) break;

            for (Edge edge : graph.getAdjacencies(u)) {
                int v = edge.getDestination();
                double candidate = distances[u] + edge.getWeight();
                if (candidate < distances[v]) {
                    distances[v] = candidate;
                    previous[v] = u;
                    pq.add(new VertexDistance(v, candidate));
                }
            }
        }

        boolean reachable = distances[destination] != Double.POSITIVE_INFINITY;
        List<Integer> path = new ArrayList<>();
        if (reachable) {
            for (int v = destination; v != -1; v = previous[v]) {
                path.add(v);
            }
            Collections.reverse(path);
        }

        return new ShortestPathResult(source, destination, distances[destination], path, reachable);
    }

    @Override
    public boolean[][] executeWarshall() {
        return GraphAlgorithms.warshall(graph);
    }

    @Override
    public KruskalResult executeKruskal() {
        return KruskalAlgorithm.computeMinimumSpanningTree(graph);
    }

    // --- Persistencia ---

    @Override
    public boolean saveGraph(String name) {
        return graphRepository.save(graph, name);
    }

    @Override
    public boolean loadGraph(String name) {
        Optional<DirectedGraph> loaded = graphRepository.load(name);
        loaded.ifPresent(g -> this.graph = g);
        return loaded.isPresent();
    }

    @Override
    public String[] listSavedGraphs() {
        return graphRepository.listAll();
    }

    // --- Consulta de vertices ---

    @Override
    public int findVertexByName(String query) {
        String normalizedQuery = normalize(query);

        for (int i = 0; i < graph.getNumVertices(); i++) {
            String info = graph.getInformation(i).orElse("");
            if (normalize(info).equals(normalizedQuery)) {
                return i;
            }
        }

        for (int i = 0; i < graph.getNumVertices(); i++) {
            String info = graph.getInformation(i).orElse("");
            if (normalize(info).contains(normalizedQuery)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public List<String> listVertexNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < graph.getNumVertices(); i++) {
            String label = graph.getInformation(i).orElse("V" + i);
            names.add(label.isEmpty() ? "V" + i : label);
        }
        return names;
    }

    @Override
    public List<String> findVertexNameSuggestions(String query) {
        String normalizedQuery = normalize(query);
        List<String> suggestions = new ArrayList<>();

        for (String name : listVertexNames()) {
            String normalizedName = normalize(name);
            if (normalizedName.contains(normalizedQuery) || normalizedQuery.contains(normalizedName)) {
                suggestions.add(name);
            }
        }

        return suggestions;
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }

    // --- Classe auxiliar para Dijkstra ---

    private static final class VertexDistance implements Comparable<VertexDistance> {
        private final int vertex;
        private final double distance;

        VertexDistance(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(VertexDistance other) {
            return Double.compare(this.distance, other.distance);
        }
    }
}
