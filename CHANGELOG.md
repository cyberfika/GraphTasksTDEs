# Changelog

Todas as mudancas relevantes deste projeto sao documentadas aqui.
Formato baseado em [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

---

## [Unreleased] - branch `teste/GUI`

### Added
- `application/GraphService.java` — interface de contrato para `GraphApplicationService` (DIP, ISP)
- `util/GraphRepository.java` — interface de contrato para `GraphStorage` (DIP)
- `application/EdgeDisplayItem.java` — DTO imutavel compartilhado entre console e GUI (DRY)
- `application/GraphType.java` — enum com metadados por tipo de grafo: nome de exibicao, unidade de peso e flag de persistencia (OCP)
- `docs/quality_improvement_plan.md` — plano de melhoria de qualidade com analise SOLID completa
- `CHANGELOG.md` — este arquivo

### Changed

#### `model/Edge.java`
- Campos `destination`, `weight`, `label` tornados `final`
- Setters `setDestination`, `setWeight`, `setLabel` removidos
- Classe declarada `final`
- *Motivo: imutabilidade para value objects (Effective Java Item 17; corrige P8, P14)*

#### `model/DirectedGraph.java`
- Representacao interna migrada de `List<Edge>[]` para `List<List<Edge>>` (elimina array generico, P15)
- `printAdjacencies()` removido — responsabilidade de I/O nao pertence ao modelo (SRP, P1, P21)
- `System.out.println` em todos os metodos substituido por `IllegalArgumentException` para vertices invalidos (P23)
- `getInformation(int)` agora retorna `Optional<String>` em vez de `String` nullable (P24)
- `getAdjacentVertices(int)` agora retorna `List<Integer>` imutavel (substitui API C-style com array de saida, P16)
- `createEdge(...)` agora retorna `boolean` — `true` se criada, `false` se duplicada (sem excecao para duplicata)
- `removeEdge(...)` agora retorna `boolean` — `true` se removida, `false` se inexistente (sem excecao)
- Metodos legados em portugues removidos: `cria_adjacencia`, `remove_adjacencia`, `imprime_adjacencias`, `seta_informacao`, `adjacentes`, `existeAresta`, `getAresta` (P18)

#### `util/GraphStorage.java`
- Classe refatorada de utilitario estatico para instancia que implementa `GraphRepository`
- `loadGraph(String)` agora retorna `Optional<DirectedGraph>` em vez de `DirectedGraph` nullable
- `System.out.println` removido — feedback de I/O e responsabilidade do chamador
- Metodos legados em portugues removidos: `salvarGrafo`, `carregarGrafo`, `arquivoExiste`, `listarGrafos` (P18)

#### `application/GraphApplicationService.java`
- Implementa `GraphService`
- Campo `grafo` renomeado para `graph` (P17)
- Injeta `GraphRepository` via construtor — padrao DIP com default `GraphStorage` (P11)
- `saveGraph(String)` agora retorna `boolean`
- `loadGraph(String)` agora retorna `boolean` e define internamente o grafo ativo (Tell, Don't Ask)
- Adicionado `listSavedGraphs()` delegando ao repositorio
- Callers de `getInformation()` atualizados para `Optional`

#### `algorithm/GraphAlgorithms.java`
- `printBooleanMatrix(boolean[][], String)` removido — responsabilidade de display nao pertence a algoritmos (SRP, P7)
- `printReachabilityStatistics(boolean[][], String)` removido — idem
- Classe declarada `final` com construtor privado

#### `interfaces/GraphConsoleUI.java`
- Inner class `EdgeDisplayItem` removida — substituida por `application/EdgeDisplayItem` compartilhado (DRY, P19)
- `displayExistingEdges` atualizado para usar `application.EdgeDisplayItem`
- Metodos estaticos `printBooleanMatrix` e `printReachabilityStatistics` adicionados — centralizam display de matrizes booleanas para uso por qualquer entrypoint
- `displayGraphSaveMessage` substituido por `displayGraphSaveSuccess` e `displayGraphSaveError` (feedback diferenciado)

#### `gui/GraphGuiController.java`
- Dependencia de `GraphApplicationService` substituida por `GraphService` interface (DIP, P13)
- Campo `currentGraphName` (String) complementado por `currentGraphType` (GraphType enum)
- `weightUnit()` agora delega a `currentGraphType.getWeightUnit()` — elimina cadeia de if (OCP, P5)
- `hasPersistableGraphName()` simplificado com `currentGraphType.isUserSaved()` (P22)
- Inner class `EdgeDisplayItem` removida — substituida por `application/EdgeDisplayItem` (DRY, P19)
- `savedGraphNames()` agora delega a `service.listSavedGraphs()` — sem dependencia direta de `GraphStorage`

#### `app/Main.java`
- Dependencia de `GraphApplicationService` substituida por `GraphService` interface (DIP, P12)
- Dependencias diretas de `GraphStorage` removidas — tudo passa por `service` (DIP)
- `handleSaveGraph` usa retorno `boolean` de `saveGraph` para feedback diferenciado
- `handleLoadGraph` usa retorno `boolean` de `loadGraph` — remove verificacao `fileExists` redundante
- Inner class `EdgeReference` substituida por `application/EdgeDisplayItem` (DRY)

#### `app/ExampleGraph.java`
- `printAdjacencies()` substituido por metodo local `printAdjacencyList` usando `getAdjacencies()` e `getInformation()` (SRP)
- `getAdjacentVertices(int, int[])` substituido por `getAdjacentVertices(int)` retornando `List<Integer>`
- `GraphAlgorithms.printBooleanMatrix` substituido por `GraphConsoleUI.printBooleanMatrix` (estatico)
- Secoes de teste de erro atualizadas para capturar `IllegalArgumentException`
- Aresta duplicada: resultado verificado via retorno `boolean` de `createEdge`

#### `app/CuritibaWalkGraphExample.java`, `app/SolarSystemGraphExample.java`
- `graph.printAdjacencies()` substituido por `new GraphConsoleUI().displayAdjacencyList(graph)` (SRP)

#### `gui/panel/GraphEditPanel.java`
- `GraphGuiController.EdgeDisplayItem` substituido por `application.EdgeDisplayItem`

#### `gui/panel/ShortestPathPanel.java`
- `graph.getInformation(vertex)` (nullable) substituido por `graph.getInformation(vertex)` com `Optional` e stream

#### `docs/design.md`
- Diagrama de classes Mermaid atualizado com novas interfaces, enum e contratos
- Diagramas de sequencia adicionados: "Carregar Grafo (Console)" e "Executar Warshall (GUI)"
- Secao "Interfaces and Contracts" adicionada
- Secao "Design Decisions" adicionada com justificativas

#### `docs/plan.md`, `docs/tasks.md`, `docs/memory.md`
- Atualizados para refletir estado atual pos-refatoracao

---

## [2.0.0] - 2026-05-13 - GUI e Grafos Tematicos

### Added
- Interface grafica Swing (`GraphDesktopApp`, `GraphMainWindow`, paineis)
- `CuritibaWalkGraphFactory` — grafo de caminhada em Curitiba (km)
- `SolarSystemGraphFactory` — grafo do sistema solar (AU) e grafo hyperspace
- `KruskalAlgorithm` + `KruskalResult` — AGM na interpretacao nao direcionada
- Opcao de modo de execucao no `Main` (Console ou GUI)
- `ShortestPathResult` — resultado imutavel para menor caminho

### Changed
- `GraphApplicationService` expandido com `executeKruskal`, `executeShortestPath`, `findVertexByName`
- `GraphConsoleUI` expandido com opcoes de menu 3-17

---

## [1.0.0] - 2026-05-07 - TDE 2 Completo

### Added
- `GraphAlgorithms.warshall()` — fechamento transitivo O(V³)
- `GraphAlgorithms.printBooleanMatrix()`, `printReachabilityStatistics()`
- `GraphStorage` — persistencia em `.bin` via serializacao Java
- Menu interativo com save/load no `Main`
- Documentacao completa: `START_HERE.md`, `docs/plan.md`, `docs/design.md`, `docs/tasks.md`, `docs/memory.md`, `docs/knowledge/`

### Changed
- `DirectedGraph` ganhou `hasEdge(int, int)` e `getEdge(int, int)`

---

## [0.1.0] - 2026-05-07 - TDE 1 Completo

### Added
- `Edge` — aresta direcionada, ponderada, rotulada, serializavel
- `DirectedGraph` — grafo com lista de adjacencia, informacoes de vertices
- `GraphApplicationService` — BFS, DFS, Dijkstra
- `GraphConsoleUI` — interface de console
- `Main` — entrypoint com menu interativo
- `ExampleGraph` — demonstracao manual do grafo
