package br.edu.grafo.application;

/**
 * Contrato para persistencia de grafos.
 *
 * <p>Segregado de {@link GraphService} para respeitar o Interface Segregation Principle (ISP):
 * clientes que apenas salvam ou carregam grafos nao dependem de metodos de algoritmos
 * ou consulta de vertices.</p>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 1.0
 */
public interface GraphPersistenceService {

    /**
     * Salva o grafo ativo com o nome fornecido.
     * @return {@code true} se salvo com sucesso
     */
    boolean saveGraph(String name);

    /**
     * Carrega o grafo com o nome fornecido e define como ativo.
     * @return {@code true} se carregado com sucesso
     */
    boolean loadGraph(String name);

    /**
     * Lista nomes de todos os grafos persistidos.
     */
    String[] listSavedGraphs();
}
