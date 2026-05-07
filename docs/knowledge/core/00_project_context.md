# Project Context

> Status: Active
> Authority: Tier 2 - Core Knowledge
> Last Updated: 2026-05-07
> Owner: Jafte Carneiro Fagundes da Silva

## Resumo

**GraphTasksTDEs** e um projeto educacional em Java para implementar e demonstrar grafos direcionados, ponderados e rotulados.

## Objetivos

1. Representar grafos direcionados com lista de adjacencia.
2. Demonstrar manipulacao de arestas e vertices.
3. Executar BFS, DFS, Dijkstra e Warshall.
4. Salvar e carregar grafos locais em `.bin`.
5. Manter documentacao navegavel para humanos e agentes.

## Escopo Atual

Incluido:

- `Edge`
- `DirectedGraph`
- `GraphAlgorithms`
- `GraphApplicationService`
- `GraphConsoleUI`
- `GraphStorage`
- `Main`
- `ExampleGraph`

Fora do escopo atual:

- GUI.
- Banco de dados.
- API web.
- Testes automatizados.
- Build tool como Maven ou Gradle.

## Fase Atual

- **TDE 1**: completo.
- **TDE 2**: completo.
- **Normalizacao documental**: concluida em 2026-05-07 para alinhar docs aos nomes reais do codigo.

## Restricoes

- Java 8+.
- Sem dependencias externas.
- Documentacao pode ficar em portugues.
- Identificadores de codigo devem permanecer em ingles.
- Nao alterar comportamento sem pedido explicito.

## Validacao

Validacao manual atual:

```bash
java -cp output br.edu.grafo.app.ExampleGraph
```

Gap:

- Nao ha testes automatizados.
- A compilacao do zero depende de `javac` disponivel no PATH.

## Related Documents

- `START_HERE.md`
- `README.md`
- `docs/design.md`
- `docs/tasks.md`
- `docs/memory.md`
- `docs/knowledge/KNOWLEDGE_BASE.md`
