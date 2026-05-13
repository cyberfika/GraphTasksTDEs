package br.edu.grafo.application;

import java.util.*;

/**
 * Resultado imutavel de um menor caminho.
 */
public class ShortestPathResult {
    private final int source;
    private final int destination;
    private final double totalDistance;
    private final List<Integer> path;
    private final boolean reachable;

    public ShortestPathResult(int source, int destination, double totalDistance, List<Integer> path, boolean reachable) {
        this.source = source;
        this.destination = destination;
        this.totalDistance = totalDistance;
        this.path = new ArrayList<>(path);
        this.reachable = reachable;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public List<Integer> getPath() {
        return Collections.unmodifiableList(path);
    }

    public boolean isReachable() {
        return reachable;
    }
}
