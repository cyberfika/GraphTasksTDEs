package br.edu.grafo.app;

import br.edu.grafo.interfaces.GraphConsoleUI;
import br.edu.grafo.model.DirectedGraph;
import br.edu.grafo.util.SolarSystemGraphFactory;

/**
 * Exemplo de geracao do grafo do sistema solar.
 */
public class SolarSystemGraphExample {
    public static void main(String[] args) {
        DirectedGraph graph = SolarSystemGraphFactory.createScientificGraph();

        System.out.println("=== SOLAR SYSTEM GRAPH ===");
        System.out.println("Vertices: " + graph.getNumVertices());
        System.out.println("Representation: undirected graph stored as symmetric directed edges.");
        System.out.println("Weight unit: astronomical units (AU).");
        System.out.println();

        new GraphConsoleUI().displayAdjacencyList(graph);
    }
}
