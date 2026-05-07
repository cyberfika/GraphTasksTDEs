# Defense

> Status: Active
> Authority: Tier 3 - Presentation Notes
> Last Updated: 2026-05-07
> Owner: Jafte Carneiro Fagundes da Silva

## Objetivo

Este documento resume pontos para defesa/apresentacao do projeto GraphTasksTDEs. Ele foi normalizado para refletir os nomes reais do codigo atual, que estao em ingles.

## Visao Geral

O projeto implementa um sistema educacional de grafos direcionados, ponderados e rotulados em Java.

Funcionalidades:

- Criar grafo com `DirectedGraph`.
- Representar arestas com `Edge`.
- Adicionar e remover arestas.
- Executar BFS, DFS e Dijkstra via `GraphApplicationService`.
- Executar Warshall via `GraphAlgorithms`.
- Salvar e carregar grafos `.bin` via `GraphStorage`.
- Usar menu interativo em `Main`.
- Demonstrar comportamento em `ExampleGraph`.

## Pontos De Arquitetura

| Classe | Papel |
| --- | --- |
| `Edge` | Aresta direcionada com destino, peso e rotulo. |
| `DirectedGraph` | Estrutura do grafo e lista de adjacencia. |
| `GraphAlgorithms` | Warshall e helpers de impressao. |
| `GraphApplicationService` | Casos de uso, BFS, DFS e Dijkstra. |
| `GraphConsoleUI` | Entrada e saida do console. |
| `GraphStorage` | Persistencia local por serializacao Java. |
| `Main` | Menu interativo. |
| `ExampleGraph` | Exemplo manual e demonstracao. |

## Algoritmos

### BFS

- Metodo: `GraphApplicationService.executeBFS`
- Complexidade: O(V+E)

### DFS

- Metodo: `GraphApplicationService.executeDFS`
- Complexidade: O(V+E)

### Dijkstra

- Metodo: `GraphApplicationService.executeDijkstra`
- Complexidade: O((V+E) log V)
- Observacao: pressupoe pesos nao negativos.

### Warshall

- Metodo: `GraphAlgorithms.warshall`
- Complexidade: O(V^3)
- Saida: `boolean[][]` de alcancabilidade.

## Exemplo De Codigo

```java
DirectedGraph graph = new DirectedGraph(5);
graph.createEdge(0, 1, 5.0, "edge_A");
graph.createEdge(1, 2, 2.0);

boolean[][] reachability = GraphAlgorithms.warshall(graph);
GraphAlgorithms.printBooleanMatrix(reachability, "Reachability");
```

## Persistencia

`GraphStorage` usa `ObjectOutputStream` e `ObjectInputStream` para salvar e carregar objetos `DirectedGraph` em arquivos `.bin`.

Observacao de seguranca:

- Arquivos `.bin` devem ser locais e confiaveis, pois desserializacao Java nao deve ser usada com entrada desconhecida.

## Como Demonstrar

Compilar:

```bash
./compile.sh
```

Executar exemplo:

```bash
java -cp output br.edu.grafo.app.ExampleGraph
```

Executar menu:

```bash
java -cp output br.edu.grafo.app.Main
```

## Gaps Que Podem Ser Mencionados

- Ainda nao ha testes automatizados.
- A politica de erro e mista: algumas classes imprimem mensagens e outras lancam excecoes.
- `output/` pode conter artefatos antigos e deve ser limpo somente com confirmacao.
