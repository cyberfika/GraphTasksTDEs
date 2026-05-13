package br.edu.grafo.application;

import br.edu.grafo.algorithm.KruskalResult;
import java.util.List;

/**
 * Contrato para operacoes de algoritmos de grafos.
 *
 * <p>Segregado de {@link GraphService} para respeitar o Interface Segregation Principle (ISP):
 * clientes que apenas executam algoritmos nao dependem de metodos de persistencia ou consulta
 * de vertices.</p>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 1.0
 */
public interface AlgorithmService {

    List<Integer> executeBFS(int sourceVertex);

    List<Integer> executeDFS(int sourceVertex);

    double[] executeDijkstra(int sourceVertex);

    ShortestPathResult executeShortestPath(int source, int destination);

    boolean[][] executeWarshall();

    KruskalResult executeKruskal();
}
