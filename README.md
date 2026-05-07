# GraphTasksTDEs - Sistema De Grafos Direcionados

> Status: Active
> Authority: Tier 3 - Guia do projeto
> Last Updated: 2026-05-07
> Owner: Jafte Carneiro Fagundes da Silva

## Informacoes Do Aluno

- **Nome**: Jafte Carneiro Fagundes da Silva
- **Curso**: Ciencia da Computacao
- **Disciplina**: Resolucao de Problemas com Grafos
- **Tipo**: Trabalho Discente Efetivo (TDE)

## Descricao

**GraphTasksTDEs** e um sistema educacional em Java para representar e manipular grafos direcionados, ponderados e rotulados.

Funcionalidades principais:

- Criacao de grafos com vertices indexados.
- Adicao e remocao de arestas direcionadas com peso e rotulo.
- Visualizacao por matriz e lista de adjacencia.
- BFS, DFS e Dijkstra no servico de aplicacao.
- Warshall em `GraphAlgorithms`.
- Persistencia de grafos em arquivos `.bin` via `GraphStorage`.

## Estrutura

```text
GraphTasksTDEs/
├── AGENTS.md
├── agents/agents.md
├── src/br/edu/grafo/
│   ├── model/          # Edge, DirectedGraph
│   ├── algorithm/      # GraphAlgorithms
│   ├── util/           # GraphStorage
│   ├── application/    # GraphApplicationService
│   ├── interfaces/     # GraphConsoleUI
│   └── app/            # Main, ExampleGraph
├── data/               # Grafos salvos
├── output/             # Classes compiladas
└── docs/               # Documentacao
```

## Requisitos

- Java 8 ou superior.
- `javac` no PATH para compilar do zero.
- Bash ou terminal compativel para usar `compile.sh`.

## Compilacao

```bash
./compile.sh
```

Ou:

```bash
javac -d output -sourcepath src \
  src/br/edu/grafo/model/*.java \
  src/br/edu/grafo/algorithm/*.java \
  src/br/edu/grafo/util/*.java \
  src/br/edu/grafo/application/*.java \
  src/br/edu/grafo/interfaces/*.java \
  src/br/edu/grafo/app/*.java
```

## Execucao

Menu interativo:

```bash
java -cp output br.edu.grafo.app.Main
```

Programa de exemplo:

```bash
java -cp output br.edu.grafo.app.ExampleGraph
```

## Classes Principais

| Classe | Responsabilidade |
| --- | --- |
| `Edge` | Representa uma aresta com destino, peso e rotulo. |
| `DirectedGraph` | Mantem vertices, informacoes e lista de adjacencia. |
| `GraphAlgorithms` | Implementa Warshall e helpers de impressao. |
| `GraphApplicationService` | Orquestra casos de uso; contem BFS, DFS e Dijkstra. |
| `GraphConsoleUI` | Centraliza entrada e saida do console. |
| `GraphStorage` | Salva, carrega e lista grafos `.bin`. |
| `Main` | Ponto de entrada do menu interativo. |
| `ExampleGraph` | Exemplo manual de uso e validacao. |

## Exemplo De Uso

```java
DirectedGraph graph = new DirectedGraph(5);

graph.createEdge(0, 1, 5.0, "edge_A");
graph.createEdge(1, 2, 2.0);
graph.createEdge(2, 3, 4.0);

boolean[][] reachability = GraphAlgorithms.warshall(graph);

GraphStorage.saveGraph(graph, "my_graph");
DirectedGraph loaded = GraphStorage.loadGraph("my_graph");
```

## Observacoes De Qualidade

- A documentacao pode ficar em portugues por decisao do usuario.
- Codigo, classes, metodos, pacotes e nomes de arquivos devem permanecer em ingles.
- A validacao atual e manual via `ExampleGraph`.
- Ainda nao ha testes automatizados com JUnit.
- `output/` pode conter artefatos compilados antigos; nao limpe sem confirmacao.

