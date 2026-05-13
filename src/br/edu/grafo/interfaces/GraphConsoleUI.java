package br.edu.grafo.interfaces;

import br.edu.grafo.algorithm.KruskalResult;
import br.edu.grafo.application.EdgeDisplayItem;
import br.edu.grafo.application.ShortestPathResult;
import br.edu.grafo.model.DirectedGraph;
import br.edu.grafo.model.Edge;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Interface de console para o sistema de grafos.
 *
 * <p>Concentra toda entrada e saida de console, separando as preocupacoes
 * de apresentacao da logica de aplicacao (SRP).
 * Os metodos estaticos de display de matrizes podem ser usados por
 * qualquer entrypoint (demos, testes manuais) sem instanciacao.</p>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 3.0
 */
public class GraphConsoleUI {

    private final Scanner scanner;

    public GraphConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    // --- Boas-vindas e saida ---

    public void displayWelcome() {
        System.out.println("=== DIRECTED GRAPH SYSTEM ===\n");
    }

    public String askExecutionMode() {
        System.out.println("Choose execution mode:");
        System.out.println("1. Console");
        System.out.println("2. GUI");
        System.out.print("\nChoose an option: ");
        return scanner.nextLine().trim();
    }

    public void displayGoodbye() {
        System.out.println("\n=== Thank you for using the system! ===");
    }

    // --- Criacao de grafo ---

    public int askNumVertices() {
        System.out.print("How many vertices? (1-100): ");
        try {
            int num = Integer.parseInt(scanner.nextLine().trim());
            if (num < 1 || num > 100) {
                System.out.println("Error: vertices must be between 1 and 100!");
                return -1;
            }
            System.out.println("Graph created with " + num + " vertices\n");
            return num;
        } catch (NumberFormatException e) {
            System.out.println("Error: invalid input!");
            return -1;
        }
    }

    // --- Menu principal ---

    public String displayMenuAndGetChoice() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Add edge");
        System.out.println("2. Create new graph");
        System.out.println("3. Load Curitiba walk graph");
        System.out.println("4. Load Solar System graph");
        System.out.println("5. Load Solar Hyperspace graph");
        System.out.println("6. Load graph from .bin");
        System.out.println("7. Remove edge");
        System.out.println("8. Save graph to .bin");
        System.out.println("9. Show adjacency list");
        System.out.println("10. Show adjacency matrix");
        System.out.println("11. Show graph info");
        System.out.println("12. Show vertex names");
        System.out.println("13. Breadth-First Search (BFS)");
        System.out.println("14. Depth-First Search (DFS)");
        System.out.println("15. Dijkstra - Shortest Path");
        System.out.println("16. Kruskal - Minimum Spanning Tree");
        System.out.println("17. Warshall - Reachability Matrix");
        System.out.println("0. Exit");
        System.out.print("\nChoose an option: ");
        return scanner.nextLine().trim();
    }

    // --- Mensagens gerais ---

    public void displayInvalidOption() {
        System.out.println("Invalid option!");
    }

    public void displayOperationCanceled() {
        System.out.println("Operation canceled.");
    }

    public void displayInvalidInput() {
        System.out.println("Invalid input.");
    }

    public void displayGraphRequired() {
        System.out.println("No graph is currently loaded. Create or load a graph first.");
    }

    public void displayLoadingError() {
        System.out.println("Failed to load. Creating new graph.\n");
    }

    // --- Arestas ---

    public EdgeInput askEdgeInput(int maxVertex) {
        System.out.println("\n=== ADD EDGE ===");
        try {
            System.out.print("Source vertex (0-" + (maxVertex - 1) + "): ");
            int origin = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Destination vertex (0-" + (maxVertex - 1) + "): ");
            int dest = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Weight: ");
            double weight = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Label (optional, press Enter to skip): ");
            String label = scanner.nextLine().trim();

            return new EdgeInput(origin, dest, weight, label);
        } catch (NumberFormatException e) {
            System.out.println("Error: invalid input!");
            return null;
        }
    }

    public void displayEdgeAdded() {
        System.out.println("Edge added successfully!");
    }

    public void displayEdgeDuplicate() {
        System.out.println("Warning: edge already exists, skipped.");
    }

    public boolean askContinueAddingEdges() {
        System.out.print("Add another edge? (Y/N): ");
        String response = scanner.nextLine().trim().toUpperCase();
        return response.equals("Y") || response.equals("YES");
    }

    public void displayRemoveEdgePrompt() {
        System.out.println("\n=== REMOVE EDGE ===");
        System.out.print("Choose the edge number to remove: ");
    }

    public void displayNoEdgesToRemove() {
        System.out.println("There are no edges to remove.");
    }

    public void displayExistingEdges(DirectedGraph graph, List<EdgeDisplayItem> edges) {
        System.out.println("\n=== EXISTING EDGES ===");
        for (int i = 0; i < edges.size(); i++) {
            EdgeDisplayItem item = edges.get(i);
            String originName = formatVertexLabel(graph, item.origin);
            String destinationName = formatVertexLabel(graph, item.destination);
            String labelSuffix = item.label.isEmpty() ? "" : " [" + item.label + "]";
            System.out.printf("%d. %s%s%s | weight=%.2f%s%n",
                    i + 1, originName, item.connector(), destinationName, item.weight, labelSuffix);
        }
    }

    public int askVertexIndex() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: invalid input!");
            return -1;
        }
    }

    // --- Visualizacao do grafo ---

    public void displayAdjacencyMatrix(DirectedGraph graph) {
        System.out.println("\n=== ADJACENCY MATRIX ===\n");

        System.out.print("V\\V ");
        for (int j = 0; j < graph.getNumVertices(); j++) {
            System.out.printf("%8d ", j);
        }
        System.out.println();
        for (int k = 0; k < 12 + graph.getNumVertices() * 9; k++) {
            System.out.print("-");
        }
        System.out.println();

        for (int i = 0; i < graph.getNumVertices(); i++) {
            System.out.printf(" %d  ", i);
            for (int j = 0; j < graph.getNumVertices(); j++) {
                Optional<Edge> edge = graph.getEdge(i, j);
                if (edge.isPresent()) {
                    System.out.printf("%8.2f ", edge.get().getWeight());
                } else {
                    System.out.print("       ∞ ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayAdjacencyList(DirectedGraph graph) {
        System.out.println("\n=== ADJACENCY LIST ===\n");

        for (int i = 0; i < graph.getNumVertices(); i++) {
            final int vertex = i;
            System.out.print("V" + vertex);
            graph.getInformation(vertex).ifPresent(info -> {
                if (!info.equals("V" + vertex)) {
                    System.out.print(" (" + info + ")");
                }
            });
            System.out.print(" -> ");

            List<Edge> edges = graph.getAdjacencies(i);
            System.out.println(edges.isEmpty() ? "[]" : edges);
        }
        System.out.println();
    }

    public void displayGraphInfo(DirectedGraph graph) {
        System.out.println("\n=== GRAPH INFORMATION ===\n");

        int numVertices = graph.getNumVertices();
        int numEdges = 0;
        for (int i = 0; i < numVertices; i++) {
            numEdges += graph.getAdjacencies(i).size();
        }

        System.out.println("Vertices: " + numVertices);
        System.out.println("Edges: " + numEdges);
        System.out.println("Unique connections (ignoring direction): " + countUniqueConnections(graph));
        if (numVertices <= 1) {
            System.out.println("Density: N/A (requires at least 2 vertices)");
        } else {
            System.out.printf("Density: %.2f%%%n", (100.0 * numEdges) / (numVertices * (numVertices - 1)));
        }

        System.out.println("\nOut-degrees:");
        for (int i = 0; i < numVertices; i++) {
            System.out.println("  V" + i + ": " + graph.getAdjacencies(i).size());
        }

        System.out.println("\nIn-degrees:");
        for (int i = 0; i < numVertices; i++) {
            int inDegree = 0;
            for (int j = 0; j < numVertices; j++) {
                if (graph.hasEdge(j, i)) inDegree++;
            }
            System.out.println("  V" + i + ": " + inDegree);
        }
        System.out.println();
    }

    // --- Algoritmos de busca ---

    public int askBFSSourceVertex(int maxVertex) {
        return askAlgorithmSourceVertex("BREADTH-FIRST SEARCH (BFS)", maxVertex);
    }

    public void displayBFSResult(List<Integer> visited) {
        System.out.println("\nVisit order (BFS): " + visited);
        System.out.println("  Vertices visited: " + visited.size() + "\n");
    }

    public int askDFSSourceVertex(int maxVertex) {
        return askAlgorithmSourceVertex("DEPTH-FIRST SEARCH (DFS)", maxVertex);
    }

    public void displayDFSResult(List<Integer> visited) {
        System.out.println("\nVisit order (DFS): " + visited);
        System.out.println("  Vertices visited: " + visited.size() + "\n");
    }

    public int askDijkstraSourceVertex(int maxVertex) {
        return askAlgorithmSourceVertex("DIJKSTRA - SHORTEST PATH", maxVertex);
    }

    public void displayDijkstraResult(double[] distances, int source) {
        System.out.println("\nMinimum distances from V" + source + ":\n");
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] == Double.POSITIVE_INFINITY) {
                System.out.println("  V" + i + ": ∞ (unreachable)");
            } else {
                System.out.println("  V" + i + ": " + distances[i]);
            }
        }
        System.out.println();
    }

    public String askVertexName(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // --- Warshall ---

    public void displayWarshallMatrix(boolean[][] reachability) {
        printBooleanMatrix(reachability, "WARSHALL - REACHABILITY MATRIX");
    }

    public void displayWarshallStatistics(boolean[][] reachability) {
        printReachabilityStatistics(reachability, "REACHABILITY STATISTICS");
    }

    /**
     * Imprime uma matriz booleana formatada.
     * Metodo estatico para uso sem instancia (demos, exemplos).
     *
     * @param matrix matriz booleana N x N
     * @param title  titulo da secao
     */
    public static void printBooleanMatrix(boolean[][] matrix, String title) {
        int n = matrix.length;

        System.out.println("\n=== " + title + " ===\n");

        System.out.print("V\\V ");
        for (int j = 0; j < n; j++) {
            System.out.printf("%5d", j);
        }
        System.out.println();

        System.out.print("----");
        for (int j = 0; j < n; j++) {
            System.out.print("-----");
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.printf(" %d  ", i);
            for (int j = 0; j < n; j++) {
                System.out.printf("%5c", matrix[i][j] ? 'T' : 'F');
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Imprime estatísticas de alcancabilidade por vertice.
     * Metodo estatico para uso sem instancia (demos, exemplos).
     *
     * @param reachability matriz de alcancabilidade
     * @param title        titulo da secao
     */
    public static void printReachabilityStatistics(boolean[][] reachability, String title) {
        int n = reachability.length;

        System.out.println("\n=== " + title + " ===\n");

        for (int i = 0; i < n; i++) {
            int count = 0;
            StringBuilder reachable = new StringBuilder();

            for (int j = 0; j < n; j++) {
                if (reachability[i][j]) {
                    count++;
                    if (reachable.length() > 0) reachable.append(", ");
                    reachable.append("V").append(j);
                }
            }

            System.out.println("V" + i + " reaches " + count + " vertices: [" + reachable + "]");
        }
        System.out.println();
    }

    // --- Kruskal ---

    public void displayKruskalResult(KruskalResult result) {
        System.out.println("\n=== KRUSKAL - MINIMUM SPANNING TREE ===\n");
        System.out.println("Interpretation: directed graph treated as undirected.");

        if (result.getEdges().isEmpty()) {
            System.out.println("No edges were selected.");
        } else {
            System.out.println("Selected edges:");
            int index = 1;
            for (KruskalResult.MinimumEdge edge : result.getEdges()) {
                String labelSuffix = edge.getLabel().isEmpty() ? "" : " [" + edge.getLabel() + "]";
                System.out.printf("  %d. V%d -- V%d | weight=%.2f%s%n",
                        index++, edge.getVertexA(), edge.getVertexB(), edge.getWeight(), labelSuffix);
            }
        }

        System.out.printf("%nTotal weight: %.2f%n", result.getTotalWeight());
        System.out.println(result.isSpanningTree()
                ? "Status: minimum spanning tree found."
                : "Status: graph is disconnected; partial forest returned.");
        System.out.println();
    }

    // --- Shortest path ---

    public void displayShortestPathResult(DirectedGraph graph, ShortestPathResult result) {
        System.out.println("\n=== SHORTEST PATH ===\n");
        String sourceLabel = formatVertexLabel(graph, result.getSource());
        String destinationLabel = formatVertexLabel(graph, result.getDestination());

        if (!result.isReachable()) {
            System.out.println("No path found between " + sourceLabel + " and " + destinationLabel + ".\n");
            return;
        }

        System.out.println("Source: " + sourceLabel);
        System.out.println("Destination: " + destinationLabel);
        System.out.printf("Total distance: %.2f%n", result.getTotalDistance());

        StringBuilder pathBuilder = new StringBuilder();
        for (int vertex : result.getPath()) {
            if (pathBuilder.length() > 0) pathBuilder.append(" -> ");
            pathBuilder.append(formatVertexLabel(graph, vertex));
        }
        System.out.println("Path: " + pathBuilder);
        System.out.println();
    }

    // --- Persistencia ---

    public String askSaveFilename() {
        System.out.println("\n=== SAVE GRAPH ===");
        System.out.print("Filename (without extension, default: grafo): ");
        String filename = scanner.nextLine().trim();
        return filename.isEmpty() ? "grafo" : filename;
    }

    public void displayGraphSaveSuccess(String filename) {
        System.out.println("Graph saved as: " + filename + ".bin\n");
    }

    public void displayGraphSaveError(String filename) {
        System.out.println("Error saving graph to: " + filename + ".bin\n");
    }

    public void displayLoadOptions(String[] savedGraphs) {
        System.out.println("\n=== LOAD GRAPH ===");
        if (savedGraphs.length == 0) {
            System.out.println("No saved graphs found.");
            return;
        }
        System.out.println("Available graphs:");
        for (int i = 0; i < savedGraphs.length; i++) {
            System.out.println("  " + (i + 1) + ". " + savedGraphs[i]);
        }
        System.out.print("\nChoose number or name: ");
    }

    public String getLoadInput() {
        return scanner.nextLine().trim();
    }

    public void displayFileNotFound(String filename) {
        System.out.println("Error: File '" + filename + ".bin' not found!");
    }

    public void displayGraphLoadedSuccessfully() {
        System.out.println("Graph loaded successfully!");
    }

    public void displayCuritibaGraphLoadedSuccessfully() {
        System.out.println("Curitiba walk graph loaded successfully!");
    }

    public void displaySolarSystemGraphLoadedSuccessfully() {
        System.out.println("Solar system graph loaded successfully!");
    }

    public void displaySolarSystemHyperspaceGraphLoadedSuccessfully() {
        System.out.println("Solar system hyperspace graph loaded successfully!");
    }

    // --- Nomes de vertices ---

    public void displayVertexNames(List<String> names) {
        System.out.println("\n=== VERTEX NAMES ===\n");
        for (int i = 0; i < names.size(); i++) {
            System.out.println((i + 1) + ". " + names.get(i));
        }
        System.out.println();
    }

    public void displayVertexNameSuggestions(String query, List<String> suggestions) {
        System.out.println("No exact match found for: " + query);
        if (suggestions.isEmpty()) {
            System.out.println("No similar vertex names found.");
        } else {
            System.out.println("Similar names:");
            for (String suggestion : suggestions) {
                System.out.println("  - " + suggestion);
            }
        }
    }

    // --- Utilitarios ---

    public void close() {
        scanner.close();
    }

    private String formatVertexLabel(DirectedGraph graph, int vertex) {
        return graph.getInformation(vertex)
                .map(info -> "V" + vertex + " (" + info + ")")
                .orElse("V" + vertex);
    }

    private int countUniqueConnections(DirectedGraph graph) {
        java.util.Set<String> connections = new java.util.HashSet<>();
        for (int origin = 0; origin < graph.getNumVertices(); origin++) {
            for (Edge edge : graph.getAdjacencies(origin)) {
                int a = Math.min(origin, edge.getDestination());
                int b = Math.max(origin, edge.getDestination());
                connections.add(a + ":" + b);
            }
        }
        return connections.size();
    }

    private int askAlgorithmSourceVertex(String algorithm, int maxVertex) {
        System.out.println("\n=== " + algorithm + " ===");
        System.out.print("Source vertex (0-" + (maxVertex - 1) + "): ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: invalid input!");
            return -1;
        }
    }

    // --- Inner classes ---

    /**
     * Objeto de entrada para criacao de aresta via console.
     */
    public static final class EdgeInput {
        public final int origin;
        public final int destination;
        public final double weight;
        public final String label;

        public EdgeInput(int origin, int destination, double weight, String label) {
            this.origin = origin;
            this.destination = destination;
            this.weight = weight;
            this.label = (label != null) ? label : "";
        }
    }
}
