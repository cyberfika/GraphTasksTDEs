package br.edu.grafo.util;

import br.edu.grafo.model.DirectedGraph;
import java.util.Optional;

/**
 * Contrato para persistencia de grafos.
 *
 * <p>Abstrai o mecanismo de armazenamento (arquivo, banco de dados, memoria),
 * permitindo substituir a implementacao sem alterar os clientes.
 * Corrige a violacao do Dependency Inversion Principle (DIP): clientes
 * dependem desta interface, nao de {@code GraphStorage} concreto.</p>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 1.0
 */
public interface GraphRepository {

    /**
     * Persiste o grafo com o nome dado.
     *
     * @param graph grafo a ser salvo
     * @param name  nome do grafo (sem extensao)
     * @return {@code true} se salvo com sucesso, {@code false} caso contrario
     */
    boolean save(DirectedGraph graph, String name);

    /**
     * Carrega o grafo com o nome dado.
     *
     * @param name nome do grafo (sem extensao)
     * @return {@code Optional} contendo o grafo, ou vazio se nao encontrado
     */
    Optional<DirectedGraph> load(String name);

    /**
     * Verifica se existe um grafo salvo com o nome dado.
     *
     * @param name nome do grafo (sem extensao)
     */
    boolean exists(String name);

    /**
     * Lista nomes de todos os grafos salvos.
     *
     * @return array de nomes (sem extensao); vazio se nenhum encontrado
     */
    String[] listAll();
}
