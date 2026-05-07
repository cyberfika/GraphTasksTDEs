package br.edu.grafo.app;

import br.edu.grafo.model.*;
import br.edu.grafo.algorithm.*;

/**
 * Programa de exemplo demonstrando o funcionamento completo do sistema de grafos.
 *
 * <h2>Descrição</h2>
 * Este programa executa uma sequência de operações que demonstram:
 * <ul>
 *   <li>Criação de grafo com vértices e arestas</li>
 *   <li>Configuração de informações nos vértices</li>
 *   <li>Visualização em diferentes formatos (matriz e lista)</li>
 *   <li>Manipulação de arestas (adição e remoção)</li>
 *   <li>Execução de algoritmo Warshall</li>
 *   <li>Análise de alcançabilidade entre vértices</li>
 * </ul>
 *
 * <h2>Saída Esperada</h2>
 * O programa exibe 10 seções demonstrando cada funcionalidade:
 * <ol>
 *   <li>Criação de 8 arestas</li>
 *   <li>Configuração de informações nos vértices</li>
 *   <li>Matriz e lista de adjacência inicial</li>
 *   <li>Listagem de vértices adjacentes</li>
 *   <li>Remoção de aresta</li>
 *   <li>Matriz atualizada</li>
 *   <li>Teste de remoção de aresta inexistente</li>
 *   <li>Testes de validação com casos de erro</li>
 *   <li>Teste de aresta duplicada</li>
 *   <li>Execução completa do algoritmo Warshall</li>
 * </ol>
 *
 * <h2>Grafo de Teste</h2>
 * Um grafo com 5 vértices (V0-V4) e 8 arestas ponderadas, formando um ciclo:
 * <pre>
 *   V0 --(5)--> V1 --(2)--> V2 --(4)--> V3 --(1)--> V4
 *   ^___________________________________(8)___________________________|
 * </pre>
 *
 * <h2>Como Executar</h2>
 * <pre>{@code
 * java -cp output br.edu.grafo.app.ExemploGrafo
 * }</pre>
 *
 * <h2>Resultado do Warshall</h2>
 * Devido ao ciclo na estrutura, todos os vértices alcançam todos os outros vértices.
 * A matriz de alcançabilidade será totalmente preenchida com valores T (true).
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 2.0
 * @see DirectedGraph
 * @see GraphAlgorithms
 */
public class ExampleGraph {
    public static void main(String[] args) {
        // Cria um grafo com 5 vértices
        DirectedGraph grafo = new DirectedGraph(5);

        System.out.println("=== EXEMPLO DE GRAFO DIRECIONADO, PONDERADO E ROTULADO ===\n");

        // 1. ADICIONANDO ADJACÊNCIAS
        System.out.println("1. CRIANDO ADJACÊNCIAS:");
        grafo.createEdge(0, 1, 5.0, "aresta_A");
        grafo.createEdge(0, 2, 3.0);
        grafo.createEdge(1, 2, 2.0, "aresta_B");
        grafo.createEdge(1, 3, 7.0);
        grafo.createEdge(2, 3, 4.0, "aresta_C");
        grafo.createEdge(2, 4, 6.0);
        grafo.createEdge(3, 4, 1.0, "aresta_D");
        grafo.createEdge(4, 0, 8.0);
        System.out.println("✓ 8 adjacências criadas\n");

        // 2. SETANDO INFORMAÇÕES NOS VÉRTICES
        System.out.println("2. SETANDO INFORMAÇÕES NOS VÉRTICES:");
        grafo.setInformation(0, "Origem");
        grafo.setInformation(1, "Intermediário A");
        grafo.setInformation(2, "Intermediário B");
        grafo.setInformation(3, "Intermediário C");
        grafo.setInformation(4, "Destino");
        System.out.println("✓ Informações definidas\n");

        // 3. IMPRIMINDO ADJACÊNCIAS
        System.out.println("3. IMPRIMINDO MATRIZ E LISTAS DE ADJACÊNCIA:");
        grafo.printAdjacencies();

        // 4. OBTENDO ADJACENTES
        System.out.println("\n4. OBTENDO ADJACENTES:");
        for (int i = 0; i < grafo.getNumVertices(); i++) {
            int[] adjacentes = new int[grafo.getNumVertices()];
            int qtd = grafo.getAdjacentVertices(i, adjacentes);
            System.out.print("Adjacentes de V" + i + ": ");
            if (qtd > 0) {
                for (int j = 0; j < qtd; j++) {
                    System.out.print("V" + adjacentes[j] + " ");
                }
                System.out.println();
            } else {
                System.out.println("(nenhum)");
            }
        }

        // 5. REMOVENDO UMA ADJACÊNCIA
        System.out.println("\n5. REMOVENDO ADJACÊNCIA (0 -> 2):");
        grafo.removeEdge(0, 2);
        System.out.println("✓ Adjacência removida");

        // 6. IMPRIMINDO NOVAMENTE
        System.out.println("\n6. MATRIZ ATUALIZADA:");
        grafo.printAdjacencies();

        // 7. TENTANDO REMOVER ADJACÊNCIA QUE NÃO EXISTE
        System.out.println("\n7. TESTANDO REMOÇÃO DE ARESTA INEXISTENTE:");
        grafo.removeEdge(0, 2);

        // 8. TESTANDO CASOS DE ERRO
        System.out.println("\n8. TESTANDO VALIDAÇÕES:");
        grafo.createEdge(10, 5, 5.0); // vértices inválidos
        grafo.setInformation(-1, "teste");  // vértice inválido
        int[] adj = new int[2];
        grafo.getAdjacentVertices(6, adj); // vértice inválido

        // 9. TESTANDO ARESTA DUPLICADA
        System.out.println("\n9. TESTANDO ARESTA DUPLICADA:");
        grafo.createEdge(1, 3, 5.0, "duplicada");

        // 10. ALGORITMO DE WARSHALL - MATRIZ DE ALCANÇABILIDADE
        System.out.println("\n10. ALGORITMO DE WARSHALL - MATRIZ DE ALCANÇABILIDADE:");
        System.out.println("Criando novo grafo para Warshall (com arestas restauradas):");

        // Recria o grafo com todas as arestas para demonstração clara
        DirectedGraph grafoWarshall = new DirectedGraph(5);
        grafoWarshall.createEdge(0, 1, 5.0);
        grafoWarshall.createEdge(0, 2, 3.0);
        grafoWarshall.createEdge(1, 2, 2.0);
        grafoWarshall.createEdge(1, 3, 7.0);
        grafoWarshall.createEdge(2, 3, 4.0);
        grafoWarshall.createEdge(2, 4, 6.0);
        grafoWarshall.createEdge(3, 4, 1.0);
        grafoWarshall.createEdge(4, 0, 8.0);

        // Monta matriz de adjacência (apenas arestas diretas)
        boolean[][] adjacencia = new boolean[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                adjacencia[i][j] = grafoWarshall.hasEdge(i, j);
            }
        }

        System.out.println("\nMatriz de Adjacência Inicial (arestas diretas):");
        GraphAlgorithms.printBooleanMatrix(adjacencia, "ADJACÊNCIA DIRETA");

        // Executa Warshall
        boolean[][] alcancabilidade = GraphAlgorithms.warshall(grafoWarshall);

        System.out.println("\nMatriz de Alcançabilidade Final (fechamento transitivo):");
        GraphAlgorithms.printBooleanMatrix(alcancabilidade, "ALCANÇABILIDADE (Warshall)");

        // Imprime estatísticas de alcançabilidade
        GraphAlgorithms.printReachabilityStatistics(alcancabilidade, "ESTATÍSTICAS DE ALCANÇABILIDADE");

        // Verifica alcançabilidades específicas
        System.out.println("VERIFICAÇÕES ESPECÍFICAS:");
        System.out.println("V0 -> V4: " + (alcancabilidade[0][4] ? "SIM (via V1->V2->V4)" : "NÃO"));
        System.out.println("V2 -> V0: " + (alcancabilidade[2][0] ? "SIM" : "NÃO (não há ciclo direto)"));
        System.out.println("V4 -> V0: " + (alcancabilidade[4][0] ? "SIM (aresta direta)" : "NÃO"));
        System.out.println("V1 -> V4: " + (alcancabilidade[1][4] ? "SIM (via V2)" : "NÃO"));
        System.out.println();
    }
}
