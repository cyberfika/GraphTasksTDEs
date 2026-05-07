package br.edu.grafo.model;

import java.util.*;
import java.io.Serializable;

/**
 * Representa um grafo direcionado, ponderado e rotulado.
 *
 * <h2>Descrição</h2>
 * Um grafo é uma estrutura de dados composta por vértices (nós) e arestas (conexões).
 * Esta implementação mantém o grafo usando lista de adjacência e suporta:
 * <ul>
 *   <li>Vértices identificados por índice inteiro (0 a numVertices-1)</li>
 *   <li>Arestas direcionadas de um vértice para outro</li>
 *   <li>Pesos nas arestas (distância, custo, capacidade, etc.)</li>
 *   <li>Rótulos descritivos nas arestas</li>
 *   <li>Informações/labels nos vértices</li>
 *   <li>Persistência em arquivo binário (Serializable)</li>
 * </ul>
 *
 * <h2>Representação Interna</h2>
 * Usa lista de adjacência: {@code List<Edge>[] adjacencyList}
 * onde cada índice i contém a lista de arestas saindo do vértice i.
 *
 * <h2>Complexidade</h2>
 * <table border="1">
 *   <tr><th>Operação</th><th>Tempo</th></tr>
 *   <tr><td>Adicionar aresta</td><td>O(k)</td></tr>
 *   <tr><td>Remover aresta</td><td>O(k)</td></tr>
 *   <tr><td>Verificar aresta</td><td>O(k)</td></tr>
 *   <tr><td>Obter adjacentes</td><td>O(1)</td></tr>
 * </table>
 * Onde k é o grau de saída do vértice.
 *
 * <h2>Exemplo de Uso</h2>
 * <pre>{@code
 * // Criar grafo com 5 vértices
 * DirectedGraph graph = new DirectedGraph(5);
 *
 * // Adicionar arestas
 * graph.createEdge(0, 1, 5.0);
 * graph.createEdge(1, 2, 3.0, "labeled_edge");
 *
 * // Verificar e manipular
 * if (graph.hasEdge(0, 1)) {
 *     System.out.println("Aresta encontrada");
 * }
 * }</pre>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 2.0
 * @see Edge
 */
public class DirectedGraph implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Edge>[] adjacencyList;
    private String[] vertexInformation;
    private int numVertices;

    @SuppressWarnings("unchecked")
    public DirectedGraph(int numVertices) {
        this.numVertices = numVertices;
        this.adjacencyList = new ArrayList[numVertices];
        this.vertexInformation = new String[numVertices];

        // Inicializa listas de adjacência e informações
        for (int i = 0; i < numVertices; i++) {
            adjacencyList[i] = new ArrayList<>();
            vertexInformation[i] = "";
        }
    }

    /**
     * Cria uma aresta entre origin e destination com peso weight
     */
    public void createEdge(int origin, int destination, double weight) {
        createEdge(origin, destination, weight, "");
    }

    /**
     * Cria uma aresta com rótulo
     */
    public void createEdge(int origin, int destination, double weight, String label) {
        if (!areValidVertices(origin, destination)) {
            System.out.println("Erro: vértices inválidos!");
            return;
        }

        // Verifica se já existe aresta de origin para destination
        for (Edge edge : adjacencyList[origin]) {
            if (edge.getDestination() == destination) {
                System.out.println("Aviso: aresta de " + origin + " para " + destination + " já existe!");
                return;
            }
        }

        adjacencyList[origin].add(new Edge(destination, weight, label));
    }

    /**
     * Remove a aresta entre origin e destination
     */
    public void removeEdge(int origin, int destination) {
        if (!areValidVertices(origin, destination)) {
            System.out.println("Erro: vértices inválidos!");
            return;
        }

        boolean removed = adjacencyList[origin].removeIf(edge -> edge.getDestination() == destination);

        if (!removed) {
            System.out.println("Aviso: aresta de " + origin + " para " + destination + " não encontrada!");
        }
    }

    /**
     * Imprime a matriz de adjacências do grafo
     */
    public void printAdjacencies() {
        System.out.println("\n=== MATRIZ DE ADJACÊNCIAS ===\n");

        // Cabeçalho com os vértices
        System.out.print("Vértice | ");
        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%8d | ", i);
        }
        System.out.println();
        for (int k = 0; k < 12 + numVertices * 12; k++) {
            System.out.print("-");
        }
        System.out.println();

        // Linhas da matriz
        for (int i = 0; i < numVertices; i++) {
            System.out.printf("   %d    | ", i);

            // Para cada coluna, procura se existe aresta
            for (int j = 0; j < numVertices; j++) {
                if (hasEdge(i, j)) {
                    Optional<Edge> edge = getEdge(i, j);
                    if (edge.isPresent()) {
                        System.out.printf("%8.2f | ", edge.get().getWeight());
                    } else {
                        System.out.print("      ∞ | ");
                    }
                } else {
                    System.out.print("      ∞ | ");
                }
            }
            System.out.println();
        }

        // Informações adicionais
        System.out.println("\n=== LISTA DE ADJACÊNCIA ===\n");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("V" + i);
            if (!vertexInformation[i].isEmpty()) {
                System.out.print(" (" + vertexInformation[i] + ")");
            }
            System.out.print(" -> ");

            if (adjacencyList[i].isEmpty()) {
                System.out.println("[]");
            } else {
                System.out.println(adjacencyList[i]);
            }
        }
        System.out.println();
    }

    /**
     * Atualiza a informação do vértice i
     */
    public void setInformation(int vertex, String information) {
        if (!isValidVertex(vertex)) {
            System.out.println("Erro: vértice " + vertex + " inválido!");
            return;
        }
        vertexInformation[vertex] = information;
    }

    /**
     * Retorna o número de adjacentes ao vértice e os armazena no vetor adj
     */
    public int getAdjacentVertices(int vertex, int[] adjacentArray) {
        if (!isValidVertex(vertex)) {
            System.out.println("Erro: vértice " + vertex + " inválido!");
            return 0;
        }

        int count = adjacencyList[vertex].size();

        // Verifica se o array tem tamanho suficiente
        if (adjacentArray.length < count) {
            System.out.println("Aviso: array é pequeno demais! Esperado: " + count);
            count = adjacentArray.length;
        }

        // Preenche o array com os vértices adjacentes
        for (int k = 0; k < count; k++) {
            adjacentArray[k] = adjacencyList[vertex].get(k).getDestination();
        }

        return count;
    }

    /**
     * Verifica se existe uma aresta de origin para destination
     */
    public boolean hasEdge(int origin, int destination) {
        if (!areValidVertices(origin, destination)) {
            return false;
        }
        for (Edge edge : adjacencyList[origin]) {
            if (edge.getDestination() == destination) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna a aresta de origin para destination, ou Optional.empty() se não existir
     */
    public Optional<Edge> getEdge(int origin, int destination) {
        if (!areValidVertices(origin, destination)) {
            return Optional.empty();
        }
        for (Edge edge : adjacencyList[origin]) {
            if (edge.getDestination() == destination) {
                return Optional.of(edge);
            }
        }
        return Optional.empty();
    }

    /**
     * Valida se um vértice é válido
     */
    private boolean isValidVertex(int vertex) {
        return vertex >= 0 && vertex < numVertices;
    }

    /**
     * Valida se dois vértices são válidos
     */
    private boolean areValidVertices(int origin, int destination) {
        return isValidVertex(origin) && isValidVertex(destination);
    }

    /**
     * Retorna o número de vértices
     */
    public int getNumVertices() {
        return numVertices;
    }

    /**
     * Retorna as adjacências de um vértice
     */
    public List<Edge> getAdjacencies(int vertex) {
        if (!isValidVertex(vertex)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(adjacencyList[vertex]);
    }

    /**
     * Retorna a informação de um vértice
     */
    public String getInformation(int vertex) {
        if (!isValidVertex(vertex)) {
            return null;
        }
        return vertexInformation[vertex];
    }

    // ========== Métodos legados para compatibilidade com código existente ==========

    /**
     * Cria uma adjacência (aresta) entre i e j com custo P
     * @deprecated Use {@link #createEdge(int, int, double)} instead
     */
    public void cria_adjacencia(int i, int j, double peso) {
        createEdge(i, j, peso);
    }

    /**
     * Cria uma adjacência com rótulo
     * @deprecated Use {@link #createEdge(int, int, double, String)} instead
     */
    public void cria_adjacencia(int i, int j, double peso, String rotulo) {
        createEdge(i, j, peso, rotulo);
    }

    /**
     * Remove a adjacência entre i e j
     * @deprecated Use {@link #removeEdge(int, int)} instead
     */
    public void remove_adjacencia(int i, int j) {
        removeEdge(i, j);
    }

    /**
     * Imprime a matriz de adjacências do grafo
     * @deprecated Use {@link #printAdjacencies()} instead
     */
    public void imprime_adjacencias() {
        printAdjacencies();
    }

    /**
     * Atualiza a informação do nó i com o valor V
     * @deprecated Use {@link #setInformation(int, String)} instead
     */
    public void seta_informacao(int i, String valor) {
        setInformation(i, valor);
    }

    /**
     * Retorna o número de adjacentes ao vértice i e os armazena no vetor adj
     * @deprecated Use {@link #getAdjacentVertices(int, int[])} instead
     */
    public int adjacentes(int i, int[] adj) {
        return getAdjacentVertices(i, adj);
    }

    /**
     * Verifica se existe uma aresta de i para j
     * @deprecated Use {@link #hasEdge(int, int)} instead
     */
    public boolean existeAresta(int i, int j) {
        return hasEdge(i, j);
    }

    /**
     * Retorna a aresta de i para j, ou Optional.empty() se não existir
     * @deprecated Use {@link #getEdge(int, int)} instead
     */
    public Optional<Edge> getAresta(int i, int j) {
        return getEdge(i, j);
    }
}
