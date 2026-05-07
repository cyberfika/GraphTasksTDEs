# START HERE

> Status: Active
> Authority: Tier 3 - Guia de entrada
> Last Updated: 2026-05-07
> Owner: Jafte Carneiro Fagundes da Silva

## Resumo Do Projeto

**GraphTasksTDEs** e um projeto educacional em Java 8+ para representar grafos direcionados, ponderados e rotulados. O projeto inclui interface de console, persistencia em `.bin` e algoritmos classicos de grafos.

## Estado Atual

- **TDE 1**: representacao de grafo, BFS, DFS e Dijkstra completos.
- **TDE 2**: Warshall, matriz de alcancabilidade, menu e save/load completos.
- **Validacao atual**: execucao manual de `ExampleGraph`.
- **Testes automatizados**: ainda nao existem.

## Estrutura Principal

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
├── data/               # Grafos salvos em .bin
├── output/             # Classes compiladas e artefatos gerados
└── docs/               # Documentacao do projeto
```

## Como Compilar

O projeto nao usa Maven ou Gradle. E necessario ter `javac` no PATH.

```bash
./compile.sh
```

Ou manualmente:

```bash
javac -d output -sourcepath src \
  src/br/edu/grafo/model/*.java \
  src/br/edu/grafo/algorithm/*.java \
  src/br/edu/grafo/util/*.java \
  src/br/edu/grafo/application/*.java \
  src/br/edu/grafo/interfaces/*.java \
  src/br/edu/grafo/app/*.java
```

## Como Executar

Menu interativo:

```bash
java -cp output br.edu.grafo.app.Main
```

Programa de exemplo:

```bash
java -cp output br.edu.grafo.app.ExampleGraph
```

## Como Validar

Execute:

```bash
java -cp output br.edu.grafo.app.ExampleGraph
```

O exemplo demonstra criacao de grafo, manipulacao de arestas e Warshall. BFS, DFS e Dijkstra sao executados pelo menu via `GraphApplicationService`, mas ainda nao ha testes automatizados com JUnit.

## Decisoes De Arquitetura

- `DirectedGraph` representa o grafo com lista de adjacencia baseada em `List<Edge>[]`.
- `Edge` representa uma aresta direcionada com destino, peso e rotulo opcional.
- `GraphAlgorithms` contem Warshall e helpers de impressao de matriz.
- `GraphApplicationService` orquestra casos de uso e implementa BFS, DFS e Dijkstra.
- `GraphConsoleUI` concentra entrada e saida do console.
- `GraphStorage` usa serializacao Java para salvar e carregar grafos `.bin`.

## Politica De Idioma

- Documentacao pode ficar em portugues.
- Comentarios e JavaDoc podem ficar em portugues.
- Codigo, nomes de arquivos, classes, metodos, pacotes e identificadores tecnicos devem permanecer em ingles.

## Primeiros Arquivos Para Ler

1. `AGENTS.md`
2. `agents/agents.md`
3. `docs/knowledge/KNOWLEDGE_BASE.md`
4. `docs/design.md`
5. `src/br/edu/grafo/model/DirectedGraph.java`
6. `src/br/edu/grafo/application/GraphApplicationService.java`
7. `src/br/edu/grafo/algorithm/GraphAlgorithms.java`
