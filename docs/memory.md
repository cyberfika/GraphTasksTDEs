# Memory

> Status: Active
> Authority: Tier 3 - Working Document
> Last Updated: 2026-05-07

## User Preferences

1. **Communication Style**: Concise, direct responses without unnecessary verbosity
2. **Language**: Portuguese for explanations to user, English for generated files and code comments
3. **Code Standards**: Follow agents.md rules — OOP, SOLID, no raw types, meaningful names
4. **Documentation**: Markdown-based, English, with clear structure and authority levels

## Approved Decisions

1. **Graph Representation**: Use `List<List<Aresta>>` instead of raw array `List<Aresta>[]`
   - Eliminates raw types warning
   - More idiomatic Java
   - No performance penalty

2. **Method Naming**: camelCase throughout (Java convention)
   - `criaAdjacencia` instead of `cria_adjacencia`
   - `removeAdjacencia` instead of `remove_adjacencia`
   - Consistent with Java standards

3. **Error Handling**: `IllegalArgumentException` for invalid vertex indices
   - Replaces System.out.println("Erro: ...")
   - Follows exception standards
   - Clear separation of errors vs warnings

4. **Algorithm Location**: Static methods in `AlgoritmosGrafo` class
   - Stateless, reusable
   - Easy to test
   - Separation of concerns

5. **TDE 2 Focus**: Warshall algorithm for reachability matrix
   - Dijkstra already implemented in TDE 1
   - Warshall adds value (all-pairs reachability vs single-source distances)

## Rejected Decisions

1. ~~Modifying existing code without user approval~~ → Rejected after user correction
2. ~~Implementing improvements beyond requested scope~~ → Keep focused on requirements
3. ~~Over-engineering solutions~~ → YAGNI principle applies

## Repository Conventions

1. **Directory Structure**:
   - `/docs` — All documentation files
   - Source files at root (`Aresta.java`, `GrafoDirecionado.java`, etc.)
   - No `/src` subdirectory for single-purpose project

2. **Documentation Requirements**:
   - All Markdown files in English
   - Status and Authority headers on every document
   - Evidence trails with file paths and line numbers
   - Clear task tracking in `/docs/tasks.md`

3. **Code Style**:
   - Java standard naming: PascalCase (classes), camelCase (methods/variables), UPPER_SNAKE_CASE (constants)
   - JavaDoc for public methods
   - No raw types
   - Defensive copies for mutable fields

4. **Testing**:
   - Use `ExemploGrafo.java` as primary test/demonstration
   - Manual verification of algorithm output
   - No external test framework (educational context)

## Important Context

**Project Type**: Educational (Trabalho Discente Efetivo)

**Scope**:
- TDE 1: Graph implementation with BFS, DFS, Dijkstra → COMPLETE
- TDE 2: Warshall algorithm for reachability matrix → NEXT PHASE

**Current Challenge**: User corrected agent for executing plan without explicit approval. Now following stricter authorization model.

**Key Insight**: This project requires careful attention to documentation structure (agents.md specifies extensive requirements). The documentation bootstrap is essential before implementation.

## Open Questions

1. **Warshall Implementation**: Should self-reachability be included? (vertex i to itself)
2. **Output Format**: Matrix display preference for reachability results?
3. **Test Coverage**: What edge cases are most important to demonstrate?

## Session Log

### Session 1 (2026-05-07) - Bootstrap and TDE 1 Incident
- **Incident**: Agent implemented entire TDE 1 plan without user approval
- **Resolution**: User requested rollback; agent deleted files and apologized
- **Learning**: Read agents.md carefully — emphasize user authorization for actions

### Session 2 (2026-05-07) - Documentation Structure
- Created complete documentation structure per agents.md
- Established authority hierarchy (Tier 1-4)
- Created core knowledge documents
- Ready for TDE 2 implementation

### Session 3 (2026-05-07) - TDE 2 Implementation (Warshall)
- **User Decision**: Proceed with option 2 (implement with reasonable assumptions)
- **Assumptions Applied**:
  - Self-reachability: YES (included in matrix)
  - Output format: boolean[][]
  - Display both: initial adjacency + final reachability

- **Implementation**:
  - Created `AlgoritmosGrafo.java` with `warshall()` method
  - Added utility methods:
    - `imprimeMatrizBooleana()` — Pretty-print boolean matrix
    - `imprimeEstatisticasAlcancabilidade()` — Print statistics per vertex
  - Added `existeAresta()` method to GrafoDirecionado
  - Updated ExemploGrafo with Warshall demonstration

- **Compilation**: ✓ Success (no warnings)

- **Test Results**:
  - Test graph: 5 vertices, 8 edges with cycle (0→1→2→3→4→0)
  - Warshall result: All vertices reach all other vertices (fully connected)
  - Result verified as correct (cycle allows complete reachability)
  - Specific checks passed:
    - V0 → V4: SIM (via V1→V2→V4)
    - V2 → V0: SIM (via V3→V4→V0)
    - V4 → V0: SIM (direct edge)
    - V1 → V4: SIM (via V2)

- **Status**: TDE 2 Complete ✓
