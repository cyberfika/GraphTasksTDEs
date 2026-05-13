package br.edu.grafo.algorithm;

import java.util.*;

/**
 * Resultado imutavel do algoritmo de Kruskal.
 */
public class KruskalResult {
    private final List<MinimumEdge> edges;
    private final double totalWeight;
    private final boolean spanningTree;

    public KruskalResult(List<MinimumEdge> edges, double totalWeight, boolean spanningTree) {
        this.edges = new ArrayList<>(edges);
        this.totalWeight = totalWeight;
        this.spanningTree = spanningTree;
    }

    public List<MinimumEdge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public boolean isSpanningTree() {
        return spanningTree;
    }

    /**
     * Representa uma aresta nao direcionada usada na AGM.
     */
    public static class MinimumEdge {
        private final int vertexA;
        private final int vertexB;
        private final double weight;
        private final String label;

        public MinimumEdge(int vertexA, int vertexB, double weight, String label) {
            this.vertexA = vertexA;
            this.vertexB = vertexB;
            this.weight = weight;
            this.label = label;
        }

        public int getVertexA() {
            return vertexA;
        }

        public int getVertexB() {
            return vertexB;
        }

        public double getWeight() {
            return weight;
        }

        public String getLabel() {
            return label;
        }
    }
}
