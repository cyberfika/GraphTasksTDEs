package br.edu.grafo.app;

import br.edu.grafo.interfaces.GraphConsoleUI;
import br.edu.grafo.model.DirectedGraph;
import br.edu.grafo.util.CuritibaWalkGraphFactory;

/**
 * Exemplo de geracao do grafo nao direcionado de caminhada de Curitiba.
 */
public class CuritibaWalkGraphExample {
    public static void main(String[] args) {
        DirectedGraph graph = CuritibaWalkGraphFactory.createGraph();

        System.out.println("=== CURITIBA WALK GRAPH ===");
        System.out.println("Vertices: " + graph.getNumVertices());
        System.out.println("Representation: undirected graph stored as symmetric directed edges.");
        System.out.println("Weight unit: kilometers.");
        System.out.println();

        new GraphConsoleUI().displayAdjacencyList(graph);
    }
}
