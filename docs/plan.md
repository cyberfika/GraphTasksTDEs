# Plan

> Status: Active
> Authority: Tier 3 - Working Document
> Last Updated: 2026-05-13
> Owner: Jafte Carneiro Fagundes da Silva

## Objective

Executar o plano de melhoria de qualidade (quality_improvement_plan.md) para corrigir
violacoes de OOP/SOLID identificadas na codebase, alinhando o codigo com os principios
do agents.md.

## Scope

Incluido:

- Tornar `Edge` imutavel (remover setters, finalizar campos).
- Refatorar `DirectedGraph`: remover I/O, lancamento de excecoes, `Optional`, nova API.
- Extrair interfaces `GraphService` e `GraphRepository` (DIP).
- Criar `EdgeDisplayItem` compartilhado entre console e GUI (DRY).
- Criar `GraphType` enum (OCP).
- Refatorar `GraphStorage` para implementar `GraphRepository`.
- Refatorar `GraphApplicationService` para implementar `GraphService`.
- Remover metodos de display de `GraphAlgorithms` (SRP).
- Remover metodos legados em portugues.
- Atualizar todos os callers para usar nova API.
- Atualizar documentacao (design.md, tasks.md, plan.md, memory.md).

Fora do escopo (requer aprovacao separada):

- Testes automatizados (JUnit 5, Mockito) — requer decisao sobre build tool.
- Maven/Gradle — mudanca estrutural significativa.
- Algoritmos adicionais (Floyd-Warshall para distancias, etc.).

## Assumptions

1. Java 8+ compatibilidade mantida (Optional, lambdas, default methods sao Java 8).
2. Serializacao `.bin` existente permanece compativel (serialVersionUID mantido).
3. Documentacao pode ser em portugues; identificadores de codigo permanecem em ingles.
4. Metodos legados em portugues sao removidos (nenhum chamador externo conhecido).

## Architecture Impact

- Nova interface `GraphService`: `Main` e `GraphGuiController` passam a depender dela.
- Nova interface `GraphRepository`: `GraphApplicationService` passa a depender dela.
- `Edge` imutavel: impacta todos os callers (nenhum usava setters — sem quebra).
- `getInformation()` retorna `Optional<String>`: todos os callers atualizados.
- `createEdge`/`removeEdge` retornam `boolean`: callers atualizados para usar retorno.
- `GraphStorage` virou classe instanciavel: apenas `GraphApplicationService` a instancia.

## Implementation Strategy

Implementado em 2026-05-13:

1. Criadas interfaces `GraphService` e `GraphRepository`.
2. Criados value objects `EdgeDisplayItem` e enum `GraphType`.
3. `Edge` tornada imutavel (final fields, no setters).
4. `DirectedGraph` refatorado: sem I/O, exceptions, Optional, List<List<Edge>>.
5. `GraphStorage` refatorado: implementa GraphRepository, retorna Optional.
6. `GraphApplicationService` refatorado: implementa GraphService, injeta GraphRepository.
7. `GraphAlgorithms` refatorado: apenas algoritmos puros (sem print).
8. `GraphConsoleUI` refatorado: usa EdgeDisplayItem compartilhado, metodos estaticos de display.
9. `GraphGuiController` refatorado: usa GraphService, GraphType, EdgeDisplayItem compartilhado.
10. `Main` refatorado: usa GraphService, EdgeDisplayItem, Optional.
11. `ExampleGraph`, `CuritibaWalkGraphExample`, `SolarSystemGraphExample` atualizados.
12. `GraphEditPanel` atualizado para usar EdgeDisplayItem compartilhado.
13. `ShortestPathPanel` atualizado para Optional.

## Testing Strategy

Validacao atual:

- Compilacao verificada: zero erros de compilacao.
- Verificacao manual de `ExampleGraph` recomendada.

Gap conhecido:

- Nenhum teste automatizado. Testes requerem decisao sobre JUnit 5 e build tool.

## Risks

- Arquivos `.bin` gerados com versoes anteriores podem ser incompativeis se `Edge` tiver
  mudado de forma incompativel. `serialVersionUID = 1L` mantido em ambos `Edge` e
  `DirectedGraph`, mas a remocao de campos (setters nao afetam serializacao) nao
  quebra leitura. Monitorar ao carregar arquivos `.bin` antigos.

## Acceptance Criteria

- [x] `Edge` imutavel: sem setters, campos finais.
- [x] `DirectedGraph`: sem `System.out.println`, sem metodos legados, `Optional` returns.
- [x] Interfaces `GraphService` e `GraphRepository` criadas e implementadas.
- [x] `EdgeDisplayItem` compartilhado (removida duplicacao).
- [x] `GraphType` enum (eliminada cadeia de if).
- [x] `GraphAlgorithms`: sem metodos de display.
- [x] Compilacao sem erros.
- [ ] Testes automatizados adicionados.
- [ ] Maven/Gradle configurado.
