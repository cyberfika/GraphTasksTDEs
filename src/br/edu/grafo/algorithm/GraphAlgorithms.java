package br.edu.grafo.algorithm;

import br.edu.grafo.model.*;
import java.util.*;

/**
 * Utilitário com algoritmos clássicos de grafos.
 *
 * <h2>Descrição</h2>
 * Fornece implementações estáticas de algoritmos fundamentais para análise
 * e manipulação de grafos, incluindo busca, alcançabilidade e visualização.
 *
 * <h2>Algoritmos Disponíveis</h2>
 * <ul>
 *   <li><b>Warshall</b>: Computa matriz de alcançabilidade (fechamento transitivo)</li>
 * </ul>
 *
 * <h2>Métodos de Visualização</h2>
 * <ul>
 *   <li><b>printBooleanMatrix</b>: Exibe matriz booleana formatada</li>
 *   <li><b>printReachabilityStatistics</b>: Mostra estatísticas de alcançabilidade</li>
 * </ul>
 *
 * <h2>Design Pattern</h2>
 * Utiliza padrão Utility Class com métodos estáticos, permitindo uso sem
 * instanciação e favorecendo composição/reuso.
 *
 * <h2>Exemplo de Uso</h2>
 * <pre>{@code
 * DirectedGraph grafo = new DirectedGraph(5);
 * // ... adicionar arestas ...
 *
 * // Executar Warshall
 * boolean[][] alcancabilidade = GraphAlgorithms.warshall(grafo);
 *
 * // Visualizar resultados
 * GraphAlgorithms.printBooleanMatrix(alcancabilidade, "Alcançabilidade");
 * GraphAlgorithms.printReachabilityStatistics(alcancabilidade, "Estatísticas");
 * }</pre>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 2.0
 * @see DirectedGraph
 */
public class GraphAlgorithms {

    /**
     * Algoritmo de Warshall - Matriz de Alcançabilidade
     * Computa o fechamento transitivo (matriz de alcançabilidade) do grafo
     *
     * @param graph DirectedGraph com V vértices
     * @return matriz booleana reach[V][V] onde reach[i][j] = true iff existe caminho de i para j
     *
     * Complexidade: O(V³)
     * Espaço: O(V²)
     */
    public static boolean[][] warshall(DirectedGraph graph) {
        int n = graph.getNumVertices();

        // Inicializa matriz de alcançabilidade com base nas arestas existentes
        boolean[][] reach = new boolean[n][n];

        // Inicialização: copia a adjacência (existe aresta)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                reach[i][j] = graph.hasEdge(i, j);
            }
            // Auto-alcançabilidade: todo vértice alcança a si mesmo
            reach[i][i] = true;
        }

        // Algoritmo de Warshall: para cada vértice intermediário k
        for (int k = 0; k < n; k++) {
            // Para cada par de vértices (i, j)
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Se i alcança k E k alcança j, então i alcança j
                    reach[i][j] = reach[i][j] || (reach[i][k] && reach[k][j]);
                }
            }
        }

        return reach;
    }

    /**
     * Imprime uma matriz booleana de forma legível
     * @param matrix matriz booleana N×N
     * @param title título da matriz
     */
    public static void printBooleanMatrix(boolean[][] matrix, String title) {
        int n = matrix.length;

        System.out.println("\n=== " + title + " ===\n");

        // Cabeçalho com números dos vértices
        System.out.print("V\\V ");
        for (int j = 0; j < n; j++) {
            System.out.print(String.format("%5d", j));
        }
        System.out.println();

        // Separador
        System.out.print("----");
        for (int j = 0; j < n; j++) {
            System.out.print("-----");
        }
        System.out.println();

        // Linhas da matriz
        for (int i = 0; i < n; i++) {
            System.out.print(String.format(" %d  ", i));
            for (int j = 0; j < n; j++) {
                // T para true, F para false
                char c = matrix[i][j] ? 'T' : 'F';
                System.out.print(String.format("%5c", c));
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Imprime estatísticas de alcançabilidade
     * @param reachability matriz de alcançabilidade
     * @param title título da análise
     */
    public static void printReachabilityStatistics(boolean[][] reachability, String title) {
        int n = reachability.length;

        System.out.println("\n=== " + title + " ===\n");

        // Para cada vértice, mostra quantos outros alcança
        for (int i = 0; i < n; i++) {
            int count = 0;
            StringBuilder reachable = new StringBuilder();

            for (int j = 0; j < n; j++) {
                if (reachability[i][j]) {
                    count++;
                    if (reachable.length() > 0) reachable.append(", ");
                    reachable.append("V").append(j);
                }
            }

            System.out.println("V" + i + " alcança " + count + " vértices: [" + reachable + "]");
        }
        System.out.println();
    }
}
