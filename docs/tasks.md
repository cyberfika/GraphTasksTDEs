# Tasks

> Status: Active
> Authority: Tier 3 - Working Document
> Last Updated: 2026-05-13
> Owner: Jafte Carneiro Fagundes da Silva

## Backlog

- [ ] Adicionar testes automatizados para `DirectedGraph`, `GraphAlgorithms`, `GraphApplicationService`.
- [ ] Adicionar testes automatizados para `KruskalAlgorithm`.
- [ ] Adicionar testes automatizados para a camada GUI.
- [ ] Configurar Maven ou Gradle (requer aprovacao do usuario para mudanca estrutural).
- [ ] Migrar `compile.sh` de `--release 8` para `--release 17` (ou remover --release).
- [ ] Substituir sentinel `-1` em metodos de UI por `Optional<Integer>` (Fase 5 do plano).
- [ ] Avaliar limpeza de artefatos antigos em `output/` com confirmacao do usuario.

## In Progress

Nada em progresso no momento.

## Blocked

- [ ] Testes automatizados: dependem de decisao sobre adicionar JUnit 5 e build tool.

## Completed

### TDE 1

- [x] Representacao de grafo com `DirectedGraph`.
- [x] Modelo de aresta com `Edge`.
- [x] BFS, DFS, Dijkstra em `GraphApplicationService`.
- [x] Programa de exemplo `ExampleGraph`.

### TDE 2

- [x] Warshall em `GraphAlgorithms`.
- [x] Matriz de alcancabilidade.
- [x] Persistencia em `.bin` com `GraphStorage`.
- [x] Menu interativo com save/load.
- [x] AGM por Kruskal.
- [x] Interface grafica Swing.

### Quality Improvement Plan (2026-05-13)

- [x] `Edge` imutavel: campos `final`, sem setters (P8, P14).
- [x] `DirectedGraph` sem I/O: removido `printAdjacencies()` e `System.out.println` (P1, P23).
- [x] `DirectedGraph` lanca `IllegalArgumentException` para vertices invalidos (P23).
- [x] `DirectedGraph.getInformation()` retorna `Optional<String>` (P24).
- [x] `DirectedGraph.getAdjacentVertices(int)` retorna `List<Integer>` (P16).
- [x] `DirectedGraph` usa `List<List<Edge>>` em vez de `List<Edge>[]` (P15).
- [x] Metodos legados em portugues removidos de `DirectedGraph` e `GraphStorage` (P18).
- [x] Interface `GraphRepository` criada e implementada por `GraphStorage` (P9, P11, P13).
- [x] Interface `GraphService` criada e implementada por `GraphApplicationService` (P9, P10, P12).
- [x] `GraphApplicationService`: campo renomeado de `grafo` para `graph` (P17).
- [x] `GraphApplicationService`: injeta `GraphRepository` (DIP, P11).
- [x] `GraphApplicationService.loadGraph()` retorna `boolean` (Tell, Don't Ask).
- [x] `GraphApplicationService.saveGraph()` retorna `boolean`.
- [x] `GraphAlgorithms`: removidos `printBooleanMatrix` e `printReachabilityStatistics` (P7, SRP).
- [x] `GraphConsoleUI`: usa `EdgeDisplayItem` compartilhado (P19, P20).
- [x] `GraphConsoleUI`: metodos estaticos `printBooleanMatrix`, `printReachabilityStatistics` (display centralizado).
- [x] `GraphGuiController`: usa `GraphType` enum em vez de cadeia de if (P5, OCP).
- [x] `GraphGuiController`: usa `GraphService` interface (DIP, P13).
- [x] `EdgeDisplayItem` compartilhado em `application` (DRY, P19, P20).
- [x] `GraphType` enum criado (OCP, P5, P22).
- [x] `Main`: usa `GraphService` interface (DIP).
- [x] `Main`: remove dependencia direta de `GraphStorage` (DIP).
- [x] `ExampleGraph`, `CuritibaWalkGraphExample`, `SolarSystemGraphExample` atualizados.
- [x] `GraphEditPanel` atualizado para `EdgeDisplayItem` compartilhado.
- [x] `ShortestPathPanel` atualizado para `Optional<String>`.
- [x] Compilacao verificada: zero erros.
- [x] Documentacao atualizada: `design.md`, `plan.md`, `tasks.md`, `memory.md`.

## Verification Checklist

- [x] Compilar com javac (zero erros).
- [ ] Executar `java -cp output br.edu.grafo.app.ExampleGraph` manualmente.
- [ ] Verificar menu interativo `br.edu.grafo.app.Main`.
- [ ] Verificar GUI `br.edu.grafo.app.GraphDesktopApp`.
- [ ] Adicionar testes automatizados.
