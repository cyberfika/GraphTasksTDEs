package br.edu.grafo.interfaces;

import br.edu.grafo.model.*;
import br.edu.grafo.util.*;
import java.util.*;

/**
 * Console user interface for graph manipulation.
 *
 * Handles all user input/output.
 * Separates presentation concerns from application logic.
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 2.0
 */
public class GraphConsoleUI {
    private final Scanner scanner;

    public GraphConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcome() {
        System.out.println("=== DIRECTED GRAPH SYSTEM ===\n");
    }

    public void displayGoodbye() {
        System.out.println("\n=== Thank you for using the system! ===");
    }

    public boolean askLoadSavedGraph() {
        System.out.println("File 'grafo.bin' found!");
        System.out.print("Load previous graph? (Y/N): ");
        String response = scanner.nextLine().trim().toUpperCase();
        return response.equals("Y") || response.equals("YES");
    }

    public int askNumVertices() {
        System.out.print("How many vertices? (1-100): ");
        try {
            int num = Integer.parseInt(scanner.nextLine().trim());
            if (num < 1 || num > 100) {
                System.out.println("Error: vertices must be between 1 and 100!");
                return -1;
            }
            System.out.println("✓ Graph created with " + num + " vertices\n");
            return num;
        } catch (NumberFormatException e) {
            System.out.println("Error: invalid input!");
            return -1;
        }
    }

    public String displayMenuAndGetChoice() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Add edge");
        System.out.println("2. Remove edge");
        System.out.println("3. Show adjacency matrix");
        System.out.println("4. Show adjacency list");
        System.out.println("5. Breadth-First Search (BFS)");
        System.out.println("6. Depth-First Search (DFS)");
        System.out.println("7. Dijkstra - Shortest Path");
        System.out.println("8. Warshall - Reachability Matrix");
        System.out.println("9. Show graph info");
        System.out.println("10. Save graph to .bin");
        System.out.println("11. Load graph from .bin");
        System.out.println("0. Exit");
        System.out.print("\nChoose an option: ");

        return scanner.nextLine().trim();
    }

    public void displayLoadingError() {
        System.out.println("Failed to load. Creating new graph.\n");
    }

    public void displayOperationCanceled() {
        System.out.println("Operation canceled.");
    }

    public void displayInvalidOption() {
        System.out.println("Invalid option!");
    }

    // Edge operations
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
        System.out.println("✓ Edge added successfully!");
    }

    public void displayRemoveEdgePrompt(int maxVertex) {
        System.out.println("\n=== REMOVE EDGE ===");
        System.out.print("Source vertex (0-" + (maxVertex - 1) + "): ");
    }

    public int askVertexIndex() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: invalid input!");
            return -1;
        }
    }

    public void displayAdjacencyMatrix(DirectedGraph grafo) {
        System.out.println("\n=== ADJACENCY MATRIX ===\n");

        System.out.print("V\\V ");
        for (int j = 0; j < grafo.getNumVertices(); j++) {
            System.out.printf("%8d ", j);
        }
        System.out.println();
        for (int k = 0; k < 12 + grafo.getNumVertices() * 9; k++) {
            System.out.print("-");
        }
        System.out.println();

        for (int i = 0; i < grafo.getNumVertices(); i++) {
            System.out.printf(" %d  ", i);
            for (int j = 0; j < grafo.getNumVertices(); j++) {
                if (grafo.hasEdge(i, j)) {
                    Optional<Edge> edge = grafo.getEdge(i, j);
                    if (edge.isPresent()) {
                        System.out.printf("%8.2f ", edge.get().getWeight());
                    } else {
                        System.out.print("       ∞ ");
                    }
                } else {
                    System.out.print("       ∞ ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayAdjacencyList(DirectedGraph grafo) {
        System.out.println("\n=== ADJACENCY LIST ===\n");

        for (int i = 0; i < grafo.getNumVertices(); i++) {
            System.out.print("V" + i);
            String info = grafo.getInformation(i);
            if (info != null && !info.isEmpty() && !info.equals("V" + i)) {
                System.out.print(" (" + info + ")");
            }
            System.out.print(" -> ");

            List<Edge> edges = grafo.getAdjacencies(i);
            if (edges.isEmpty()) {
                System.out.println("[]");
            } else {
                System.out.println(edges);
            }
        }
        System.out.println();
    }

    public int askBFSSourceVertex(int maxVertex) {
        return askAlgorithmSourceVertex("BREADTH-FIRST SEARCH (BFS)", maxVertex);
    }

    public void displayBFSResult(List<Integer> visited) {
        System.out.println("\n✓ Visit order (BFS): " + visited);
        System.out.println("  Vertices visited: " + visited.size() + "\n");
    }

    public int askDFSSourceVertex(int maxVertex) {
        return askAlgorithmSourceVertex("DEPTH-FIRST SEARCH (DFS)", maxVertex);
    }

    public void displayDFSResult(List<Integer> visited) {
        System.out.println("\n✓ Visit order (DFS): " + visited);
        System.out.println("  Vertices visited: " + visited.size() + "\n");
    }

    public int askDijkstraSourceVertex(int maxVertex) {
        return askAlgorithmSourceVertex("DIJKSTRA - SHORTEST PATH", maxVertex);
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

    public void displayDijkstraResult(double[] distances, int source) {
        System.out.println("\n✓ Minimum distances from V" + source + ":\n");
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] == Double.POSITIVE_INFINITY) {
                System.out.println("  V" + i + ": ∞ (unreachable)");
            } else {
                System.out.println("  V" + i + ": " + distances[i]);
            }
        }
        System.out.println();
    }

    public void displayWarshallMatrix(boolean[][] reachability) {
        System.out.println("\n=== WARSHALL - REACHABILITY MATRIX ===\n");

        int n = reachability.length;
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
                char c = reachability[i][j] ? 'T' : 'F';
                System.out.printf("%5c", c);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayWarshallStatistics(boolean[][] reachability) {
        System.out.println("=== REACHABILITY STATISTICS ===\n");

        int n = reachability.length;
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

    public void displayGraphInfo(DirectedGraph grafo) {
        System.out.println("\n=== GRAPH INFORMATION ===\n");

        int numVertices = grafo.getNumVertices();
        int numEdges = 0;

        for (int i = 0; i < numVertices; i++) {
            numEdges += grafo.getAdjacencies(i).size();
        }

        System.out.println("Vertices: " + numVertices);
        System.out.println("Edges: " + numEdges);
        System.out.printf("Density: %.2f%%%n", (100.0 * numEdges) / (numVertices * (numVertices - 1)));

        System.out.println("\nOut-degrees:");
        for (int i = 0; i < numVertices; i++) {
            System.out.println("  V" + i + ": " + grafo.getAdjacencies(i).size());
        }

        System.out.println("\nIn-degrees:");
        for (int i = 0; i < numVertices; i++) {
            int inDegree = 0;
            for (int j = 0; j < numVertices; j++) {
                if (grafo.hasEdge(j, i)) {
                    inDegree++;
                }
            }
            System.out.println("  V" + i + ": " + inDegree);
        }
        System.out.println();
    }

    public String askSaveFilename() {
        System.out.println("\n=== SAVE GRAPH ===");
        System.out.print("Filename (without extension, default: grafo): ");
        String filename = scanner.nextLine().trim();
        return filename.isEmpty() ? "grafo" : filename;
    }

    public void displayGraphSaveMessage(String filename) {
        System.out.println("✓ Graph saved as: " + filename + ".bin\n");
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

    public void displayInvalidInput() {
        System.out.println("Invalid input.");
    }

    public void close() {
        scanner.close();
    }

    /**
     * Value object for edge input.
     */
    public static class EdgeInput {
        public final int origin;
        public final int destination;
        public final double weight;
        public final String label;

        public EdgeInput(int origin, int destination, double weight, String label) {
            this.origin = origin;
            this.destination = destination;
            this.weight = weight;
            this.label = label;
        }
    }
}
