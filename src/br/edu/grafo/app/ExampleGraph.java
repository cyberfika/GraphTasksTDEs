package br.edu.grafo.app;

import br.edu.grafo.algorithm.GraphAlgorithms;
import br.edu.grafo.interfaces.GraphConsoleUI;
import br.edu.grafo.model.DirectedGraph;
import br.edu.grafo.model.Edge;

import java.util.List;

/**
 * Programa de exemplo demonstrando o funcionamento do sistema de grafos.
 *
 * <h2>Descricao</h2>
 * Executa operacoes em sequencia demonstrando:
 * <ul>
 *   <li>Criacao de grafo com vertices e arestas</li>
 *   <li>Configuracao de informacoes nos vertices</li>
 *   <li>Visualizacao em lista de adjacencia</li>
 *   <li>Obtencao de adjacentes (nova API: List<Integer>)</li>
 *   <li>Manipulacao de arestas (adicao e remocao)</li>
 *   <li>Tratamento de erros por excecao</li>
 *   <li>Execucao do algoritmo Warshall</li>
 *   <li>Analise de alcancabilidade entre vertices</li>
 * </ul>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 3.0
 * @see DirectedGraph
 * @see GraphAlgorithms
 */
public class ExampleGraph {

    public static void main(String[] args) {
        DirectedGraph graph = new DirectedGraph(5);

        System.out.println("=== EXEMPLO DE GRAFO DIRECIONADO, PONDERADO E ROTULADO ===\n");

        // 1. ADICIONANDO ADJACENCIAS
        System.out.println("1. CRIANDO ADJACENCIAS:");
        graph.createEdge(0, 1, 5.0, "aresta_A");
        graph.createEdge(0, 2, 3.0);
        graph.createEdge(1, 2, 2.0, "aresta_B");
        graph.createEdge(1, 3, 7.0);
        graph.createEdge(2, 3, 4.0, "aresta_C");
        graph.createEdge(2, 4, 6.0);
        graph.createEdge(3, 4, 1.0, "aresta_D");
        graph.createEdge(4, 0, 8.0);
        System.out.println("8 adjacencias criadas\n");

        // 2. SETANDO INFORMACOES NOS VERTICES
        System.out.println("2. SETANDO INFORMACOES NOS VERTICES:");
        graph.setInformation(0, "Origem");
        graph.setInformation(1, "Intermediario A");
        graph.setInformation(2, "Intermediario B");
        graph.setInformation(3, "Intermediario C");
        graph.setInformation(4, "Destino");
        System.out.println("Informacoes definidas\n");

        // 3. IMPRIMINDO LISTA DE ADJACENCIA (via GraphConsoleUI)
        System.out.println("3. LISTA DE ADJACENCIA:");
        printAdjacencyList(graph);

        // 4. OBTENDO ADJACENTES (nova API: List<Integer>)
        System.out.println("\n4. OBTENDO ADJACENTES:");
        for (int i = 0; i < graph.getNumVertices(); i++) {
            List<Integer> adjacents = graph.getAdjacentVertices(i);
            System.out.print("Adjacentes de V" + i + ": ");
            if (adjacents.isEmpty()) {
                System.out.println("(nenhum)");
            } else {
                StringBuilder sb = new StringBuilder();
                for (int v : adjacents) {
                    if (sb.length() > 0) sb.append(" ");
                    sb.append("V").append(v);
                }
                System.out.println(sb);
            }
        }

        // 5. REMOVENDO UMA ADJACENCIA
        System.out.println("\n5. REMOVENDO ADJACENCIA (0 -> 2):");
        boolean removed = graph.removeEdge(0, 2);
        System.out.println(removed ? "Adjacencia removida" : "Adjacencia nao encontrada");

        // 6. LISTA ATUALIZADA
        System.out.println("\n6. LISTA ATUALIZADA:");
        printAdjacencyList(graph);

        // 7. TENTANDO REMOVER ADJACENCIA QUE NAO EXISTE
        System.out.println("\n7. TESTANDO REMOCAO DE ARESTA INEXISTENTE:");
        boolean removedAgain = graph.removeEdge(0, 2);
        System.out.println(removedAgain ? "Aresta removida (inesperado)" : "Aresta nao existia (correto)");

        // 8. TESTANDO CASOS DE ERRO (excecoes esperadas)
        System.out.println("\n8. TESTANDO VALIDACOES (excecoes esperadas):");

        try {
            graph.createEdge(10, 5, 5.0);
        } catch (IllegalArgumentException e) {
            System.out.println("createEdge(10, 5): " + e.getMessage());
        }

        try {
            graph.setInformation(-1, "teste");
        } catch (IllegalArgumentException e) {
            System.out.println("setInformation(-1): " + e.getMessage());
        }

        try {
            graph.getAdjacentVertices(6);
        } catch (IllegalArgumentException e) {
            System.out.println("getAdjacentVertices(6): " + e.getMessage());
        }

        // 9. TESTANDO ARESTA DUPLICADA
        System.out.println("\n9. TESTANDO ARESTA DUPLICADA:");
        boolean created = graph.createEdge(1, 3, 5.0, "duplicada");
        System.out.println(created ? "Aresta criada (inesperado)" : "Aresta ja existia (correto)");

        // 10. ALGORITMO DE WARSHALL
        System.out.println("\n10. ALGORITMO DE WARSHALL - MATRIZ DE ALCANCABILIDADE:");
        System.out.println("Criando novo grafo para Warshall (com arestas restauradas):");

        DirectedGraph graphWarshall = new DirectedGraph(5);
        graphWarshall.createEdge(0, 1, 5.0);
        graphWarshall.createEdge(0, 2, 3.0);
        graphWarshall.createEdge(1, 2, 2.0);
        graphWarshall.createEdge(1, 3, 7.0);
        graphWarshall.createEdge(2, 3, 4.0);
        graphWarshall.createEdge(2, 4, 6.0);
        graphWarshall.createEdge(3, 4, 1.0);
        graphWarshall.createEdge(4, 0, 8.0);

        boolean[][] adjacencia = new boolean[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                adjacencia[i][j] = graphWarshall.hasEdge(i, j);
            }
        }

        System.out.println("\nMatriz de Adjacencia Inicial (arestas diretas):");
        GraphConsoleUI.printBooleanMatrix(adjacencia, "ADJACENCIA DIRETA");

        boolean[][] alcancabilidade = GraphAlgorithms.warshall(graphWarshall);

        System.out.println("\nMatriz de Alcancabilidade Final (fechamento transitivo):");
        GraphConsoleUI.printBooleanMatrix(alcancabilidade, "ALCANCABILIDADE (Warshall)");

        GraphConsoleUI.printReachabilityStatistics(alcancabilidade, "ESTATISTICAS DE ALCANCABILIDADE");

        System.out.println("VERIFICACOES ESPECIFICAS:");
        System.out.println("V0 -> V4: " + (alcancabilidade[0][4] ? "SIM (via V1->V2->V4)" : "NAO"));
        System.out.println("V2 -> V0: " + (alcancabilidade[2][0] ? "SIM" : "NAO (nao ha ciclo direto)"));
        System.out.println("V4 -> V0: " + (alcancabilidade[4][0] ? "SIM (aresta direta)" : "NAO"));
        System.out.println("V1 -> V4: " + (alcancabilidade[1][4] ? "SIM (via V2)" : "NAO"));
        System.out.println();
    }

    /**
     * Exibe a lista de adjacencia do grafo no console.
     * Responsabilidade de display separada do modelo (SRP).
     */
    private static void printAdjacencyList(DirectedGraph graph) {
        for (int i = 0; i < graph.getNumVertices(); i++) {
            System.out.print("V" + i);
            graph.getInformation(i).ifPresent(info -> System.out.print(" (" + info + ")"));
            System.out.print(" -> ");
            List<Edge> edges = graph.getAdjacencies(i);
            System.out.println(edges.isEmpty() ? "[]" : edges);
        }
        System.out.println();
    }
}
