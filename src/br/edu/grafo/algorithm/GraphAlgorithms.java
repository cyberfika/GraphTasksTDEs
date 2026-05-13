package br.edu.grafo.algorithm;

import br.edu.grafo.model.DirectedGraph;

/**
 * Algoritmos classicos de grafos.
 *
 * <h2>Responsabilidade</h2>
 * Esta classe e exclusivamente um repositorio de algoritmos puros (sem I/O).
 * A exibicao dos resultados e responsabilidade das camadas de apresentacao
 * ({@code GraphConsoleUI} e paineis GUI).
 *
 * <h2>Algoritmos Disponíveis</h2>
 * <ul>
 *   <li><b>Warshall</b>: fechamento transitivo (matriz de alcancabilidade)</li>
 * </ul>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 3.0
 * @see DirectedGraph
 */
public final class GraphAlgorithms {

    private GraphAlgorithms() {
    }

    /**
     * Algoritmo de Warshall: computa o fechamento transitivo do grafo.
     *
     * <p>A diagonal principal e marcada como {@code true} (auto-alcancabilidade).
     * {@code reach[i][j] = true} se e somente se existe caminho de {@code i} para {@code j}.</p>
     *
     * @param graph grafo de entrada com V vertices
     * @return matriz booleana V x V de alcancabilidade
     * @throws IllegalArgumentException se {@code graph} for null
     *
     * <p>Complexidade: O(V³) | Espaco: O(V²)</p>
     */
    public static boolean[][] warshall(DirectedGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph must not be null");
        }

        int n = graph.getNumVertices();
        boolean[][] reach = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                reach[i][j] = graph.hasEdge(i, j);
            }
            reach[i][i] = true;
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    reach[i][j] = reach[i][j] || (reach[i][k] && reach[k][j]);
                }
            }
        }

        return reach;
    }
}
