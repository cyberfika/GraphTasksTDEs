# Memory

> Status: Active
> Authority: Tier 3 - Working Document
> Last Updated: 2026-05-07
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

## Rejected Decisions

1. Renomear classes Java nesta tarefa.
2. Alterar APIs publicas nesta tarefa.
3. Introduzir Maven, Gradle ou JUnit nesta tarefa.
4. Apagar arquivos `.bin` ou limpar `output/` sem confirmacao.

## Repository Conventions

- Codigo-fonte fica em `src/br/edu/grafo`.
- Classes e arquivos Java usam nomes em ingles.
- Documentacao Markdown pode usar portugues.
- `GraphApplicationService` contem BFS, DFS e Dijkstra.
- `GraphAlgorithms` contem Warshall e helpers de impressao.
- `GraphStorage` usa serializacao Java para arquivos `.bin`.

## Important Context

O diagnostico de 2026-05-07 encontrou documentacao stale com nomes antigos como `Aresta`, `GrafoDirecionado`, `AlgoritmosGrafo` e `ExemploGrafo`. O codigo atual usa `Edge`, `DirectedGraph`, `GraphAlgorithms` e `ExampleGraph`.

## Open Questions

1. O projeto deve receber testes automatizados com JUnit?
2. A politica de erro deve ser padronizada para excecoes ou mensagens de console?
3. O diretorio `output/` deve ser limpo e regenerado?

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
