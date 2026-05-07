# Domain Model

> Status: Active
> Authority: Tier 2 - Core Knowledge
> Last Updated: 2026-05-07
> Owner: Jafte Carneiro Fagundes da Silva

## Conceitos Principais

O dominio gira em torno de vertices, arestas direcionadas, pesos, rotulos e alcancabilidade.

## Vertex

Um vertice e identificado por indice inteiro.

Regras:

- Indices validos: `0 <= vertex < numVertices`.
- A informacao textual do vertice e opcional.
- O projeto nao possui uma classe `Vertex`; vertices sao representados por indices.

## Edge

`Edge` representa uma aresta direcionada.

Propriedades:

- `destination`
- `weight`
- `label`

Observacoes:

- A origem e implicita pela posicao na lista de adjacencia de `DirectedGraph`.
- A classe possui setters publicos, portanto nao e imutavel.
- O codigo atual nao sobrescreve `equals` e `hashCode`.

## DirectedGraph

`DirectedGraph` representa o grafo.

Estrutura:

```java
List<Edge>[] adjacencyList;
String[] vertexInformation;
int numVertices;
```

Regras atuais:

- Nao adiciona duas arestas com mesma origem e mesmo destino.
- Permite ciclos.
- Permite self-loops.
- Usa mensagens no console em algumas entradas invalidas.
- Retorna copia em `getAdjacencies`.

## Path

Um caminho e uma sequencia de arestas conectando vertices.

Warshall usa apenas existencia de caminho. Dijkstra usa soma de pesos.

## Algorithms

### BFS

Implementado em:

- `GraphApplicationService.executeBFS(int sourceVertex)`

Complexidade:

- Tempo: O(V+E)
- Espaco: O(V)

### DFS

Implementado em:

- `GraphApplicationService.executeDFS(int sourceVertex)`

Complexidade:

- Tempo: O(V+E)
- Espaco: O(V)

### Dijkstra

Implementado em:

- `GraphApplicationService.executeDijkstra(int sourceVertex)`

Complexidade:

- Tempo: O((V+E) log V)
- Espaco: O(V)

Observacao:

- Dijkstra pressupoe pesos nao negativos para resultado matematicamente correto.

### Warshall

Implementado em:

- `GraphAlgorithms.warshall(DirectedGraph graph)`

Saida:

- `boolean[][]`, onde `reachability[i][j]` indica se existe caminho de `i` para `j`.

Complexidade:

- Tempo: O(V^3)
- Espaco: O(V^2)

## Persistencia

`GraphStorage` salva e carrega `DirectedGraph` usando serializacao Java.

Arquivos `.bin` devem ser tratados como locais e confiaveis.

## Related Documents

- `docs/design.md`
- `docs/knowledge/core/02_architecture.md`
- `src/br/edu/grafo/model/Edge.java`
- `src/br/edu/grafo/model/DirectedGraph.java`
- `src/br/edu/grafo/application/GraphApplicationService.java`
- `src/br/edu/grafo/algorithm/GraphAlgorithms.java`
