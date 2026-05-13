package br.edu.grafo.gui;

import br.edu.grafo.application.*;
import br.edu.grafo.algorithm.KruskalResult;
import br.edu.grafo.model.*;
import br.edu.grafo.util.*;

import java.util.*;

public class GraphGuiController {
    private final GraphApplicationService service = new GraphApplicationService();
    private String currentGraphName = "No graph";

    public boolean hasGraph() {
        return service.hasGraph();
    }

    public DirectedGraph graph() {
        return service.getGraph();
    }

    public String currentGraphName() {
        return currentGraphName;
    }

    public String weightUnit() {
        if ("Curitiba walk graph".equals(currentGraphName)) {
            return "km";
        }
        if ("Solar system graph".equals(currentGraphName)) {
            return "AU";
        }
        if ("Solar system hyperspace graph".equals(currentGraphName)) {
            return "route cost";
        }
        return "units";
    }

    public void createGraph(int numVertices) {
        service.createGraph(numVertices);
        currentGraphName = "Manual graph";
    }

    public void loadCuritibaGraph() {
        service.setGraph(CuritibaWalkGraphFactory.createGraph());
        currentGraphName = "Curitiba walk graph";
    }

    public void loadSolarSystemGraph() {
        service.setGraph(SolarSystemGraphFactory.createScientificGraph());
        currentGraphName = "Solar system graph";
    }

    public void loadSolarSystemHyperspaceGraph() {
        service.setGraph(SolarSystemGraphFactory.createHyperspaceGraph());
        currentGraphName = "Solar system hyperspace graph";
    }

    public boolean loadGraph(String name) {
        DirectedGraph loaded = service.loadGraph(name);
        if (loaded == null) {
            return false;
        }
        service.setGraph(loaded);
        currentGraphName = name;
        return true;
    }

    public void saveGraph(String name) {
        service.saveGraph(name);
        currentGraphName = name;
    }

    public boolean hasPersistableGraphName() {
        return hasGraph()
                && currentGraphName != null
                && !currentGraphName.isEmpty()
                && !"No graph".equals(currentGraphName)
                && !"Manual graph".equals(currentGraphName)
                && !"Curitiba walk graph".equals(currentGraphName)
                && !"Solar system graph".equals(currentGraphName)
                && !"Solar system hyperspace graph".equals(currentGraphName);
    }

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

    public List<EdgeDisplayItem> listEdges() {
        List<EdgeDisplayItem> items = new ArrayList<>();
        Set<String> processedBidirectional = new HashSet<>();

        for (int origin = 0; origin < graph().getNumVertices(); origin++) {
            for (Edge edge : graph().getAdjacencies(origin)) {
                int destination = edge.getDestination();
                boolean bidirectional = isSymmetric(origin, edge);
                if (bidirectional) {
                    String key = Math.min(origin, destination) + ":" + Math.max(origin, destination)
                            + ":" + edge.getWeight() + ":" + edge.getLabel();
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

    public List<String> listVertexNames() {
        return service.listVertexNames();
    }

    public List<String> findVertexNameSuggestions(String query) {
        return service.findVertexNameSuggestions(query);
    }

    public int findVertexByName(String name) {
        return service.findVertexByName(name);
    }

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

    public String[] savedGraphNames() {
        return GraphStorage.listGraphs();
    }

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
        StringBuilder builder = new StringBuilder();
        builder.append("Graph: ").append(currentGraphName()).append('\n');
        builder.append("Vertices: ").append(vertexCount()).append('\n');
        builder.append("Edges: ").append(edgeCount()).append('\n');
        builder.append("Unique connections: ").append(uniqueConnectionCount()).append('\n');
        builder.append(String.format("Density: %.2f%%%n", densityPercent()));
        return builder.toString();
    }

    public static class EdgeDisplayItem {
        public final int origin;
        public final int destination;
        public final double weight;
        public final String label;
        public final boolean bidirectional;

        public EdgeDisplayItem(int origin, int destination, double weight, String label, boolean bidirectional) {
            this.origin = origin;
            this.destination = destination;
            this.weight = weight;
            this.label = label;
            this.bidirectional = bidirectional;
        }

        @Override
        public String toString() {
            String connector = bidirectional ? " -- " : " -> ";
            return "V" + origin + connector + "V" + destination + " | weight=" + String.format("%.2f", weight)
                    + (label == null || label.isEmpty() ? "" : " [" + label + "]");
        }
    }
}
