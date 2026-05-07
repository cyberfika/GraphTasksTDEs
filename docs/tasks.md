# Tasks

> Status: Active
> Authority: Tier 3 - Working Document
> Last Updated: 2026-05-07
> Owner: Jafte Carneiro Fagundes da Silva

## Backlog

- [ ] Adicionar testes automatizados para `DirectedGraph`, `GraphAlgorithms` e `GraphApplicationService`.
- [ ] Padronizar politica de erro entre modelo, servico e persistencia.
- [ ] Avaliar limpeza de artefatos antigos em `output/` com confirmacao do usuario.
- [ ] Considerar Maven ou Gradle somente se o usuario aprovar mudanca de estrutura.

## In Progress

- [x] Alinhar documentacao aos nomes reais em ingles.
- [x] Registrar excecao de idioma: documentacao e comentarios podem ficar em portugues.
- [x] Criar `AGENTS.md` na raiz como ponte para `agents/agents.md`.

## Blocked

- [ ] Testes automatizados: dependem de decisao sobre adicionar ou nao JUnit/build tool.

## Completed

### TDE 1

- [x] Representacao de grafo com `DirectedGraph`.
- [x] Modelo de aresta com `Edge`.
- [x] BFS em `GraphApplicationService`.
- [x] DFS em `GraphApplicationService`.
- [x] Dijkstra em `GraphApplicationService`.
- [x] Programa de exemplo `ExampleGraph`.

### TDE 2

- [x] Warshall em `GraphAlgorithms`.
- [x] Matriz de alcancabilidade.
- [x] Helpers `printBooleanMatrix` e `printReachabilityStatistics`.
- [x] Persistencia em `.bin` com `GraphStorage`.
- [x] Menu interativo com save/load.

## Verification Checklist

- [x] Compilar do zero com `C:\Program Files\JetBrains\CLion 2026.1\jbr\bin\javac.exe --release 8`.
- [x] Executar `java -cp output br.edu.grafo.app.ExampleGraph` com classes ja compiladas.
- [x] Conferir matriz de Warshall no exemplo manual.
- [x] Conferir documentacao principal contra nomes reais do codigo.
- [ ] Adicionar testes automatizados.

Observacao: a compilacao emitiu warnings de opcao Java 8 obsoleta no JDK moderno, mas gerou classes executaveis no runtime Java 8 disponivel.
