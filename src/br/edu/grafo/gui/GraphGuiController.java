package br.edu.grafo.gui;

import br.edu.grafo.algorithm.KruskalResult;
import br.edu.grafo.application.EdgeDisplayItem;
import br.edu.grafo.application.GraphApplicationService;
import br.edu.grafo.application.GraphService;
import br.edu.grafo.application.GraphType;
import br.edu.grafo.application.ShortestPathResult;
import br.edu.grafo.model.DirectedGraph;
import br.edu.grafo.model.Edge;
import br.edu.grafo.util.CuritibaWalkGraphFactory;
import br.edu.grafo.util.SolarSystemGraphFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Controlador da GUI de grafos.
 *
 * <p>Atua como intermediario entre os paineis Swing e a camada de aplicacao,
 * seguindo o padrao MVC. Depende de {@link GraphService} (DIP), nao
 * de {@code GraphApplicationService} diretamente.</p>
 *
 * <p>Usa {@link GraphType} para metadados de cada tipo de grafo, eliminando
 * a cadeia de {@code if} que violava o OCP.</p>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 3.0
 */
public class GraphGuiController {

    private final GraphService service;
    private GraphType currentGraphType = GraphType.NONE;
    private String currentGraphName = GraphType.NONE.getDefaultDisplayName();

    public GraphGuiController() {
        this.service = new GraphApplicationService();
    }

    // --- Acesso ao grafo ---

    public boolean hasGraph() {
        return service.hasGraph();
    }

    public DirectedGraph graph() {
        return service.getGraph();
    }

    public String currentGraphName() {
        return currentGraphName;
    }

    /**
     * Retorna a unidade de peso das arestas para o tipo de grafo atual.
     * Delega ao enum {@link GraphType}, eliminando cadeia de if (OCP).
     */
    public String weightUnit() {
        return currentGraphType.getWeightUnit();
    }

    // --- Criacao / carregamento ---

    public void createGraph(int numVertices) {
        service.createGraph(numVertices);
        currentGraphType = GraphType.MANUAL;
        currentGraphName = GraphType.MANUAL.getDefaultDisplayName();
    }

    public void loadCuritibaGraph() {
        service.setGraph(CuritibaWalkGraphFactory.createGraph());
        currentGraphType = GraphType.CURITIBA_WALK;
        currentGraphName = GraphType.CURITIBA_WALK.getDefaultDisplayName();
    }

    public void loadSolarSystemGraph() {
        service.setGraph(SolarSystemGraphFactory.createScientificGraph());
        currentGraphType = GraphType.SOLAR_SYSTEM;
        currentGraphName = GraphType.SOLAR_SYSTEM.getDefaultDisplayName();
    }

    public void loadSolarSystemHyperspaceGraph() {
        service.setGraph(SolarSystemGraphFactory.createHyperspaceGraph());
        currentGraphType = GraphType.SOLAR_SYSTEM_HYPERSPACE;
        currentGraphName = GraphType.SOLAR_SYSTEM_HYPERSPACE.getDefaultDisplayName();
    }

    public boolean loadGraph(String name) {
        boolean success = service.loadGraph(name);
        if (success) {
            currentGraphType = GraphType.USER_SAVED;
            currentGraphName = name;
        }
        return success;
    }

    public void saveGraph(String name) {
        service.saveGraph(name);
        currentGraphType = GraphType.USER_SAVED;
        currentGraphName = name;
    }

    /**
     * Indica se o grafo atual tem um nome persistivel (foi salvo/carregado pelo usuario).
     */
    public boolean hasPersistableGraphName() {
        return hasGraph() && currentGraphType.isUserSaved()
                && currentGraphName != null && !currentGraphName.isEmpty();
    }

    // --- Edicao de arestas ---

    public void addEdge(int origin, int destination, double weight, String label) {
        service.addEdge(origin, destination, weight, label);
    }

    public void removeEdge(int origin, int destination) {
        service.removeEdge(origin, destination);
    }

    public void removeDisplayedEdge(EdgeDisplayItem item) {
        service.removeEdge(item.origin, item.destination);
        if (item.bidirectional) {
            service.removeEdge(item.destination, item.origin);
        }
    }

    // --- Listagem de arestas ---

    public List<EdgeDisplayItem> listEdges() {
        List<EdgeDisplayItem> items = new ArrayList<>();
        Set<String> processedBidirectional = new HashSet<>();

        for (int origin = 0; origin < graph().getNumVertices(); origin++) {
            for (Edge edge : graph().getAdjacencies(origin)) {
                int destination = edge.getDestination();
                boolean bidirectional = isSymmetric(origin, edge);

                if (bidirectional) {
                    String key = buildBidirectionalKey(origin, destination, edge);
                    if (!processedBidirectional.add(key)) {
                        continue;
                    }
                }

                items.add(new EdgeDisplayItem(origin, destination, edge.getWeight(), edge.getLabel(), bidirectional));
            }
        }
        return items;
    }

    private boolean isSymmetric(int origin, Edge edge) {
        Optional<Edge> reverse = graph().getEdge(edge.getDestination(), origin);
        return reverse.isPresent()
                && Double.compare(reverse.get().getWeight(), edge.getWeight()) == 0
                && Objects.equals(reverse.get().getLabel(), edge.getLabel());
    }

    private String buildBidirectionalKey(int origin, int destination, Edge edge) {
        int a = Math.min(origin, destination);
        int b = Math.max(origin, destination);
        return a + ":" + b + ":" + edge.getWeight() + ":" + edge.getLabel();
    }

    // --- Consulta de vertices ---

    public List<String> listVertexNames() {
        return service.listVertexNames();
    }

    public List<String> findVertexNameSuggestions(String query) {
        return service.findVertexNameSuggestions(query);
    }

    public int findVertexByName(String name) {
        return service.findVertexByName(name);
    }

    // --- Algoritmos ---

    public List<Integer> bfs(int source) {
        return service.executeBFS(source);
    }

    public List<Integer> dfs(int source) {
        return service.executeDFS(source);
    }

    public double[] dijkstra(int source) {
        return service.executeDijkstra(source);
    }

    public ShortestPathResult shortestPath(int source, int destination) {
        return service.executeShortestPath(source, destination);
    }

    public boolean[][] warshall() {
        return service.executeWarshall();
    }

    public KruskalResult kruskal() {
        return service.executeKruskal();
    }

    // --- Persistencia ---

    public String[] savedGraphNames() {
        return service.listSavedGraphs();
    }

    // --- Estatisticas do grafo ---

    public int vertexCount() {
        return graph().getNumVertices();
    }

    public int edgeCount() {
        int count = 0;
        for (int i = 0; i < graph().getNumVertices(); i++) {
            count += graph().getAdjacencies(i).size();
        }
        return count;
    }

    public int uniqueConnectionCount() {
        Set<String> set = new HashSet<>();
        for (int origin = 0; origin < graph().getNumVertices(); origin++) {
            for (Edge edge : graph().getAdjacencies(origin)) {
                int a = Math.min(origin, edge.getDestination());
                int b = Math.max(origin, edge.getDestination());
                set.add(a + ":" + b);
            }
        }
        return set.size();
    }

    public double densityPercent() {
        int vertices = vertexCount();
        return vertices <= 1 ? 0.0 : (100.0 * edgeCount()) / (vertices * (vertices - 1));
    }

    public String graphInfoText() {
        return "Graph: " + currentGraphName() + '\n'
                + "Vertices: " + vertexCount() + '\n'
                + "Edges: " + edgeCount() + '\n'
                + "Unique connections: " + uniqueConnectionCount() + '\n'
                + String.format("Density: %.2f%%%n", densityPercent());
    }
}
