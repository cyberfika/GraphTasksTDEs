package br.edu.grafo.algorithm;

import br.edu.grafo.model.*;
import java.util.*;

/**
 * Implementa Kruskal sobre a visao nao direcionada do grafo atual.
 */
public final class KruskalAlgorithm {

    private KruskalAlgorithm() {
    }

    public static KruskalResult computeMinimumSpanningTree(DirectedGraph graph) {
        int numVertices = graph.getNumVertices();
        List<KruskalResult.MinimumEdge> candidates = buildUndirectedEdges(graph);

        candidates.sort(Comparator.comparingDouble(KruskalResult.MinimumEdge::getWeight)
                .thenComparingInt(KruskalResult.MinimumEdge::getVertexA)
                .thenComparingInt(KruskalResult.MinimumEdge::getVertexB));

        DisjointSet disjointSet = new DisjointSet(numVertices);
        List<KruskalResult.MinimumEdge> selected = new ArrayList<>();
        double totalWeight = 0.0;

        for (KruskalResult.MinimumEdge edge : candidates) {
            if (disjointSet.union(edge.getVertexA(), edge.getVertexB())) {
                selected.add(edge);
                totalWeight += edge.getWeight();
                if (selected.size() == numVertices - 1) {
                    break;
                }
            }
        }

        boolean spanningTree = numVertices == 0 || selected.size() == numVertices - 1;
        return new KruskalResult(selected, totalWeight, spanningTree);
    }

    private static List<KruskalResult.MinimumEdge> buildUndirectedEdges(DirectedGraph graph) {
        Map<EdgeKey, KruskalResult.MinimumEdge> bestEdges = new HashMap<>();

        for (int origin = 0; origin < graph.getNumVertices(); origin++) {
            for (Edge edge : graph.getAdjacencies(origin)) {
                int destination = edge.getDestination();
                if (origin == destination) {
                    continue;
                }

                int vertexA = Math.min(origin, destination);
                int vertexB = Math.max(origin, destination);
                EdgeKey key = new EdgeKey(vertexA, vertexB);
                KruskalResult.MinimumEdge candidate =
                        new KruskalResult.MinimumEdge(vertexA, vertexB, edge.getWeight(), edge.getLabel());

                KruskalResult.MinimumEdge current = bestEdges.get(key);
                if (current == null || candidate.getWeight() < current.getWeight()) {
                    bestEdges.put(key, candidate);
                }
            }
        }

        return new ArrayList<>(bestEdges.values());
    }

    private static class EdgeKey {
        private final int vertexA;
        private final int vertexB;

        private EdgeKey(int vertexA, int vertexB) {
            this.vertexA = vertexA;
            this.vertexB = vertexB;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EdgeKey)) {
                return false;
            }
            EdgeKey other = (EdgeKey) obj;
            return vertexA == other.vertexA && vertexB == other.vertexB;
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertexA, vertexB);
        }
    }

    private static class DisjointSet {
        private final int[] parent;
        private final int[] rank;

        private DisjointSet(int size) {
            this.parent = new int[size];
            this.rank = new int[size];

            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        private int find(int vertex) {
            if (parent[vertex] != vertex) {
                parent[vertex] = find(parent[vertex]);
            }
            return parent[vertex];
        }

        private boolean union(int vertexA, int vertexB) {
            int rootA = find(vertexA);
            int rootB = find(vertexB);

            if (rootA == rootB) {
                return false;
            }

            if (rank[rootA] < rank[rootB]) {
                parent[rootA] = rootB;
            } else if (rank[rootA] > rank[rootB]) {
                parent[rootB] = rootA;
            } else {
                parent[rootB] = rootA;
                rank[rootA]++;
            }

            return true;
        }
    }
}
