# Memory

> Status: Active
> Authority: Tier 3 - Working Document
> Last Updated: 2026-05-13
> Owner: Jafte Carneiro Fagundes da Silva

## User Preferences

1. Respostas ao usuario podem ser em portugues.
2. Documentacao do repositorio pode ficar em portugues.
3. Comentarios e JavaDoc podem ficar em portugues.
4. Codigo, nomes de arquivos, classes, metodos, pacotes e identificadores tecnicos devem permanecer em ingles.
5. Evitar mudancas de comportamento sem pedido explicito.

## Approved Decisions

1. `AGENTS.md` deve existir na raiz como ponte para `agents/agents.md`.
2. `agents/agents.md` permanece como documento completo de regras.
3. A excecao de idioma deste projeto permite documentacao em portugues.
4. A normalizacao atual e documental; nao deve renomear classes nem alterar logica.
5. `output/` nao deve ser limpo sem confirmacao, pois pode conter artefatos gerados ou antigos.
6. AGM por Kruskal deve respeitar baixo acoplamento e responsabilidade unica.
7. A GUI do projeto deve preservar o Design System e o estilo visual do EnronAnalyzer.

## Rejected Decisions

1. Introduzir Maven, Gradle ou JUnit sem aprovacao explicita do usuario.
2. Apagar arquivos `.bin` ou limpar `output/` sem confirmacao.

## Repository Conventions

- Codigo-fonte fica em `src/br/edu/grafo`.
- Classes e arquivos Java usam nomes em ingles.
- Documentacao Markdown pode usar portugues.
- `GraphApplicationService` implementa `GraphService` e contem BFS, DFS, Dijkstra, Warshall, Kruskal.
- `GraphAlgorithms` contem apenas algoritmos puros (sem I/O).
- `GraphStorage` implementa `GraphRepository`; usa serializacao Java para `.bin`.
- `GraphConsoleUI` centraliza todo display de console, incluindo metodos estaticos de matriz booleana.
- Clientes de alto nivel (`Main`, `GraphGuiController`) dependem de interfaces, nao de implementacoes.

## Important Context

O diagnostico de 2026-05-07 encontrou documentacao stale com nomes antigos como `Aresta`, `GrafoDirecionado`, `AlgoritmosGrafo` e `ExemploGrafo`. O codigo atual usa `Edge`, `DirectedGraph`, `GraphAlgorithms` e `ExampleGraph`.

### 2026-05-13 - Quality Improvement Plan Complement Executado (Parcial)

Base: quality_improvement_plan_complement.md (itens P10b, P3b, P2b, P25).

**Arquivos criados:**
- `application/AlgorithmService.java` — interface ISP para algoritmos (BFS, DFS, Dijkstra, Warshall, Kruskal)
- `application/GraphPersistenceService.java` — interface ISP para persistencia (save, load, list)
- `application/VertexQueryService.java` — interface ISP para consulta de vertices por nome

**Modificacoes:**
- `GraphService`: estende as 3 novas interfaces (facade ISP); metodos de algoritmos, persistencia e vertices removidos do corpo (herdados); `listEdges()` adicionado (DRY compartilhado)
- `GraphApplicationService`: implementa `listEdges()` com logica de dedup bidirecional; helpers privados `isSymmetricEdge`, `buildEdgeKey`
- `GraphGuiController`: construtor `GraphGuiController(GraphService)` adicionado (P2b — testavel com mock); `listEdges()` simplificado para `return service.listEdges()`; imports nao usados removidos (`ArrayList`, `Objects`, `Optional`)
- `GraphConsoleUI`: `askNumVertices()`, `askBFSSourceVertex()`, `askDFSSourceVertex()`, `askDijkstraSourceVertex()`, `askAlgorithmSourceVertex()` retornam `OptionalInt` (P25 — elimina sentinela `-1`)
- `Main`: callers atualizados para `OptionalInt.ifPresent()`; `listEdgesForDisplay()`, `isSymmetricConnection()`, `buildBidirectionalKey()` removidos; imports nao usados removidos (`Edge`, `ArrayList`, `HashSet`, `Objects`, `Set`)

**Resultado:** compilacao verificada via PowerShell + javac — zero erros.

**Itens pendentes do complement (requerem aprovacao):**
- P26/P27: testes automatizados + Maven/Gradle
- P20b: mover `EdgeInput` para `application` (YAGNI — aguardar uso concreto na GUI)
- P2c: extrair `EdgeFormatter` de `GraphConsoleUI` (consequencia natural de futuras tarefas)
- P3c: mover listagem de arestas de `Main` para `GraphConsoleUI` (discordado como tarefa independente)

## Open Questions

1. O projeto deve receber testes automatizados com JUnit? (requer Maven/Gradle)
2. O diretorio `output/` deve ser limpo e regenerado?
3. `compile.sh` deve ser atualizado para remover `--release 8` obsoleto?

## Session Log

### 2026-05-07 - Auditoria De Alinhamento

- Confirmado que o codigo atual usa nomes em ingles.
- Identificada divergencia entre documentacao e codigo.
- Identificado que `AGENTS.md` esperado na raiz estava ausente.
- Identificado que a documentacao podia permanecer em portugues por decisao do usuario.

### 2026-05-07 - Normalizacao Documental

- Criado `AGENTS.md` na raiz como ponte para `agents/agents.md`.
- Registrada excecao de idioma nas regras de agente.
- Atualizados documentos principais para refletir nomes e responsabilidades reais.
- Sem alteracao de comportamento Java.

### 2026-05-13 - Kruskal

- Implementado `KruskalAlgorithm` separado de `GraphAlgorithms` para manter responsabilidade focada.
- Exposto `executeKruskal` em `GraphApplicationService`.
- Adicionada opcao de menu para AGM.
- A AGM usa a interpretacao nao direcionada do `DirectedGraph`.

### 2026-05-13 - GUI

- Plano de refatoracao salvo em `docs/gui_refactor_plan.md`.
- Branch de trabalho para GUI: `teste/GUI`.
- Criado shell Swing inicial em `src/br/edu/grafo/gui`.
- Mantido `Main` textual como fallback operacional.

### 2026-05-13 - Expansao De GUI E Grafos Tematicos

- Tema da GUI consolidado em modo unico escuro e monocromatico.
- Dropdowns ajustados para fundo branco, texto preto e selecao preto/branco.
- Sidebar passou a listar todos os vertices do grafo atual.
- Aba `Load/Save` passou a carregar Curitiba, Solar System e Solar Hyperspace.
- `Main` passou a permitir escolha inicial entre Console e GUI.
- `SolarSystemGraphFactory` foi separado em:
  - `createScientificGraph()`
  - `createHyperspaceGraph()`
- A explicacao do comportamento do Warshall no sistema solar foi documentada.
- A unidade de peso na GUI foi alinhada com o grafo carregado.
- O usuario fara uma revisao posterior de qualidade e depuracao do codigo.

### 2026-05-13 - Quality Improvement Plan Executado

Base teorica: SOLID (Martin), Effective Java (Bloch), UML Distilled (Fowler), agents.md.

**Arquivos criados:**
- `application/GraphService.java` — interface para servico de aplicacao (DIP, ISP)
- `util/GraphRepository.java` — interface para persistencia (DIP)
- `application/EdgeDisplayItem.java` — DTO imutavel compartilhado console+GUI (DRY, SRP)
- `application/GraphType.java` — enum com metadados por tipo de grafo (OCP)

**Modificacoes:**
- `Edge`: campos `final`, setters removidos — imutabilidade (LSP, P8, P14)
- `DirectedGraph`: removido `printAdjacencies()` e `System.out.println`; vertices invalidos lancam `IllegalArgumentException`; `getInformation()` retorna `Optional<String>`; `getAdjacentVertices(int)` retorna `List<Integer>`; usa `List<List<Edge>>`; metodos legados em portugues removidos (SRP, P1, P15, P16, P18, P23, P24)
- `GraphStorage`: implementa `GraphRepository`; `load()` retorna `Optional<DirectedGraph>`; sem `println`; metodos legados removidos (DIP, P11, P18)
- `GraphApplicationService`: implementa `GraphService`; injeta `GraphRepository`; campo `grafo` -> `graph`; `saveGraph`/`loadGraph` retornam `boolean` (DIP, P11, P17)
- `GraphAlgorithms`: removidos `printBooleanMatrix` e `printReachabilityStatistics` (SRP, P7)
- `GraphConsoleUI`: usa `EdgeDisplayItem` compartilhado; inner class duplicada removida; metodos estaticos de display de matriz adicionados (DRY, P19)
- `GraphGuiController`: depende de `GraphService`; usa `GraphType` enum; usa `EdgeDisplayItem` compartilhado (OCP, DIP, P5, P13, P19)
- `Main`: depende de `GraphService`; sem dependencia direta de `GraphStorage` (DIP)
- `ExampleGraph`, `CuritibaWalkGraphExample`, `SolarSystemGraphExample`: atualizados para nova API
- `GraphEditPanel`, `ShortestPathPanel`: atualizados para `EdgeDisplayItem` compartilhado e `Optional`
- `docs/design.md`, `docs/plan.md`, `docs/tasks.md`: atualizados com diagramas UML Mermaid e status

**Resultado:** compilacao verificada via PowerShell + javac — zero erros.
