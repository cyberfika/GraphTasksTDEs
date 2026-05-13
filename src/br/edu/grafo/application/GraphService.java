package br.edu.grafo.application;

import br.edu.grafo.algorithm.KruskalResult;
import br.edu.grafo.model.DirectedGraph;
import java.util.List;

/**
 * Contrato para o servico de aplicacao de grafos.
 *
 * <p>Encapsula todos os casos de uso do sistema: criacao, edicao,
 * execucao de algoritmos, persistencia e consulta de nomes.
 * Corrige a violacao do Interface Segregation Principle (ISP) e do
 * Dependency Inversion Principle (DIP): clientes de alto nivel
 * dependem desta abstracao, nao de {@code GraphApplicationService} concreto.</p>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 1.0
 */
public interface GraphService {

    // --- Gestao do grafo ativo ---

    void createGraph(int numVertices);

    void setGraph(DirectedGraph graph);

    DirectedGraph getGraph();

    boolean hasGraph();

    // --- Edicao de arestas ---

    /**
     * Adiciona aresta.
     * @return {@code true} se criada, {@code false} se ja existia
     */
    boolean addEdge(int origin, int destination, double weight, String label);

    /**
     * Remove aresta.
     * @return {@code true} se removida, {@code false} se nao existia
     */
    boolean removeEdge(int origin, int destination);

    // --- Algoritmos ---

    List<Integer> executeBFS(int sourceVertex);

    List<Integer> executeDFS(int sourceVertex);

    double[] executeDijkstra(int sourceVertex);

    ShortestPathResult executeShortestPath(int source, int destination);

    boolean[][] executeWarshall();

    KruskalResult executeKruskal();

    // --- Persistencia ---

    /**
     * Salva o grafo ativo.
     * @return {@code true} se salvo com sucesso
     */
    boolean saveGraph(String name);

    /**
     * Carrega grafo e define como ativo.
     * @return {@code true} se carregado com sucesso
     */
    boolean loadGraph(String name);

    /**
     * Lista nomes de grafos salvos.
     */
    String[] listSavedGraphs();

    // --- Consulta de vertices ---

    int findVertexByName(String query);

    List<String> listVertexNames();

    List<String> findVertexNameSuggestions(String query);
}
