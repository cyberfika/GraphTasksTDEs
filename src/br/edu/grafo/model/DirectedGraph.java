package br.edu.grafo.model;

import java.util.*;
import java.io.Serializable;

/**
 * Representa um grafo direcionado, ponderado e rotulado.
 *
 * <h2>Descricao</h2>
 * Estrutura de dados para grafos usando lista de adjacencia. Suporta:
 * <ul>
 *   <li>Vertices identificados por indice inteiro (0 a numVertices-1)</li>
 *   <li>Arestas direcionadas ponderadas e rotuladas</li>
 *   <li>Informacoes (labels) opcionais nos vertices</li>
 *   <li>Persistencia via serializacao Java</li>
 * </ul>
 *
 * <h2>Contrato de Erros</h2>
 * Indices de vertices invalidos lancam {@link IllegalArgumentException}.
 * Operacoes sobre arestas inexistentes ou duplicadas retornam {@code boolean}
 * sem lancar excecao.
 *
 * <h2>Separacao de Responsabilidades</h2>
 * Esta classe e exclusivamente um modelo de dominio. Nao possui metodos
 * de impressao ou I/O -- isso e responsabilidade das camadas de apresentacao.
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 3.0
 * @see Edge
 */
public class DirectedGraph implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<List<Edge>> adjacencyList;
    private final String[] vertexInformation;
    private final int numVertices;

    public DirectedGraph(int numVertices) {
        if (numVertices <= 0) {
            throw new IllegalArgumentException("Number of vertices must be positive, got: " + numVertices);
        }
        this.numVertices = numVertices;
        this.adjacencyList = new ArrayList<>(numVertices);
        this.vertexInformation = new String[numVertices];

        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new ArrayList<>());
            vertexInformation[i] = "";
        }
    }

    /**
     * Cria uma aresta entre origin e destination com peso e rotulo.
     *
     * @param origin      vertice origem
     * @param destination vertice destino
     * @param weight      peso da aresta
     * @param label       rotulo opcional
     * @return {@code true} se criada, {@code false} se ja existe
     * @throws IllegalArgumentException se qualquer vertice for invalido
     */
    public boolean createEdge(int origin, int destination, double weight, String label) {
        validateVertices(origin, destination);

        for (Edge edge : adjacencyList.get(origin)) {
            if (edge.getDestination() == destination) {
                return false;
            }
        }

        adjacencyList.get(origin).add(new Edge(destination, weight, label));
        return true;
    }

    /**
     * Cria uma aresta sem rotulo.
     *
     * @return {@code true} se criada, {@code false} se ja existe
     * @throws IllegalArgumentException se qualquer vertice for invalido
     */
    public boolean createEdge(int origin, int destination, double weight) {
        return createEdge(origin, destination, weight, "");
    }

    /**
     * Remove a aresta entre origin e destination.
     *
     * @return {@code true} se removida, {@code false} se nao existia
     * @throws IllegalArgumentException se qualquer vertice for invalido
     */
    public boolean removeEdge(int origin, int destination) {
        validateVertices(origin, destination);
        return adjacencyList.get(origin).removeIf(edge -> edge.getDestination() == destination);
    }

    /**
     * Verifica se existe uma aresta de origin para destination.
     *
     * @throws IllegalArgumentException se qualquer vertice for invalido
     */
    public boolean hasEdge(int origin, int destination) {
        validateVertices(origin, destination);
        for (Edge edge : adjacencyList.get(origin)) {
            if (edge.getDestination() == destination) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna a aresta de origin para destination.
     *
     * @return {@code Optional} com a aresta, ou vazio se nao existir
     * @throws IllegalArgumentException se qualquer vertice for invalido
     */
    public Optional<Edge> getEdge(int origin, int destination) {
        validateVertices(origin, destination);
        for (Edge edge : adjacencyList.get(origin)) {
            if (edge.getDestination() == destination) {
                return Optional.of(edge);
            }
        }
        return Optional.empty();
    }

    /**
     * Define a informacao (label) do vertice.
     *
     * @throws IllegalArgumentException se o vertice for invalido
     */
    public void setInformation(int vertex, String information) {
        validateVertex(vertex);
        vertexInformation[vertex] = (information != null) ? information : "";
    }

    /**
     * Retorna a informacao do vertice, se definida.
     *
     * @return {@code Optional} com a informacao, ou vazio se o vertice nao tiver label
     * @throws IllegalArgumentException se o vertice for invalido
     */
    public Optional<String> getInformation(int vertex) {
        validateVertex(vertex);
        String info = vertexInformation[vertex];
        return (info == null || info.isEmpty()) ? Optional.empty() : Optional.of(info);
    }

    /**
     * Retorna copia da lista de arestas saindo do vertice.
     *
     * @throws IllegalArgumentException se o vertice for invalido
     */
    public List<Edge> getAdjacencies(int vertex) {
        validateVertex(vertex);
        return new ArrayList<>(adjacencyList.get(vertex));
    }

    /**
     * Retorna lista dos indices de vertices adjacentes ao vertice dado.
     *
     * @throws IllegalArgumentException se o vertice for invalido
     */
    public List<Integer> getAdjacentVertices(int vertex) {
        validateVertex(vertex);
        List<Integer> result = new ArrayList<>();
        for (Edge edge : adjacencyList.get(vertex)) {
            result.add(edge.getDestination());
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Retorna o numero de vertices.
     */
    public int getNumVertices() {
        return numVertices;
    }

    // --- validacao interna ---

    private void validateVertex(int vertex) {
        if (vertex < 0 || vertex >= numVertices) {
            throw new IllegalArgumentException(
                    "Invalid vertex: " + vertex + " (valid range: 0 to " + (numVertices - 1) + ")");
        }
    }

    private void validateVertices(int origin, int destination) {
        validateVertex(origin);
        validateVertex(destination);
    }

    /**
     * Finds and prints all weakly connected components of the graph.
     *
     * <p>A weakly connected component is a maximal subset of vertices
     * such that there is a path between any pair of vertices if we consider
     * edges as undirected.</p>
     *
     * @return number of components found
     */
    public int findComponents() {
        boolean[] visited = new boolean[numVertices];
        int componentCount = 0;

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfsWeaklyConnected(i, visited, component);
                componentCount++;
                System.out.println("Component " + componentCount + ": " + component);
            }
        }

        return componentCount;
    }

    /**
     * DFS para encontrar componentes fracamente conectadas.
     * Trata as arestas como nao-direcionadas.
     */
    private void dfsWeaklyConnected(int vertex, boolean[] visited, List<Integer> component) {
        visited[vertex] = true;
        component.add(vertex);

        // Arestas saindo do vertice
        for (Edge edge : adjacencyList.get(vertex)) {
            if (!visited[edge.getDestination()]) {
                dfsWeaklyConnected(edge.getDestination(), visited, component);
            }
        }

        // Arestas chegando ao vertice (para considerar como nao-direcionado)
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                for (Edge edge : adjacencyList.get(i)) {
                    if (edge.getDestination() == vertex) {
                        dfsWeaklyConnected(i, visited, component);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Checks if a set of vertices forms a clique.
     *
     * <p>A clique is a subset of vertices where there is an edge
     * from each vertex to all other vertices in the set.</p>
     *
     * @param vertices list of vertex indices
     * @return {@code true} if they form a clique, {@code false} otherwise
     * @throws IllegalArgumentException if any vertex is invalid
     */
    public boolean isClique(List<Integer> vertices) {
        if (vertices == null || vertices.size() < 2) {
            return vertices != null && vertices.size() <= 1;
        }

        // Validates all vertices
        for (int vertex : vertices) {
            validateVertex(vertex);
        }

        // Checks if there is an edge between all pairs
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (i != j) {
                    int origin = vertices.get(i);
                    int destination = vertices.get(j);
                    if (!hasEdge(origin, destination)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Checks if a set of vertices forms a maximal clique.
     *
     * <p>A maximal clique is a clique that cannot be extended
     * by adding another vertex while remaining a clique.</p>
     *
     * @param vertices list of vertex indices
     * @return {@code true} if they form a maximal clique, {@code false} otherwise
     * @throws IllegalArgumentException if any vertex is invalid
     */
    public boolean isMaximal(List<Integer> vertices) {
        if (!isClique(vertices)) {
            return false;
        }

        // Checks if any other vertex can be added while maintaining clique property
        Set<Integer> vertexSet = new HashSet<>(vertices);
        for (int candidate = 0; candidate < numVertices; candidate++) {
            if (!vertexSet.contains(candidate)) {
                // Try adding candidate
                List<Integer> extended = new ArrayList<>(vertices);
                extended.add(candidate);

                // If candidate connects to all vertices in the clique, it's not maximal
                if (isClique(extended)) {
                    return false;
                }
            }
        }

        return true;
    }
}
