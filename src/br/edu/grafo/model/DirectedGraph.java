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
}
