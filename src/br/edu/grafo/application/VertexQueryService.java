package br.edu.grafo.application;

import java.util.List;

/**
 * Contrato para consulta de vertices por nome.
 *
 * <p>Segregado de {@link GraphService} para respeitar o Interface Segregation Principle (ISP):
 * clientes de autocompletar ou busca por nome nao dependem de metodos de algoritmos
 * ou persistencia.</p>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 1.0
 */
public interface VertexQueryService {

    /**
     * Busca vertice por nome exato ou parcial.
     * @return indice do vertice, ou {@code -1} se nao encontrado
     */
    int findVertexByName(String query);

    List<String> listVertexNames();

    List<String> findVertexNameSuggestions(String query);
}
