package br.edu.grafo.application;

import br.edu.grafo.model.DirectedGraph;
import java.util.List;

/**
 * Contrato facade para o servico de aplicacao de grafos.
 *
 * <p>Estende {@link AlgorithmService}, {@link GraphPersistenceService} e
 * {@link VertexQueryService} aplicando o Interface Segregation Principle (ISP):
 * cada sub-interface pode ser usada de forma independente por clientes que
 * nao precisam do contrato completo. Clientes de alto nivel que precisam de
 * tudo dependem desta abstracao (DIP), nao de {@code GraphApplicationService}.</p>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 2.0
 */
public interface GraphService extends AlgorithmService, GraphPersistenceService, VertexQueryService {

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

    // --- Display de arestas ---

    /**
     * Lista arestas do grafo ativo para exibicao, deduplicando conexoes bidirecionais.
     * Logica compartilhada entre console ({@code Main}) e GUI ({@code GraphGuiController}).
     */
    List<EdgeDisplayItem> listEdges();
}
