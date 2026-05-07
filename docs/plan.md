# Plan

> Status: Completed ✓
> Authority: Tier 3 - Working Document
> Last Updated: 2026-05-07
>
> **Aluno**: Jafte Carneiro Fagundes da Silva
> **Curso**: Ciência da Computação
> **Disciplina**: Resolução de Problemas com Grafos

## Objective

✓ **COMPLETE**: Implement Warshall's algorithm for computing the transitive closure (reachability matrix) of a directed graph.

## Scope

- **In Scope**:
  - Warshall algorithm implementation in `AlgoritmosGrafo`
  - Reachability matrix computation
  - Test cases demonstrating reachability between all vertex pairs
  - Documentation of algorithm and results

- **Out of Scope**:
  - Modifications to graph representation (use existing `GrafoDirecionado`)
  - GUI or visualization
  - Performance optimization beyond algorithm correctness

## Assumptions

1. Graph representation (`GrafoDirecionado`) is stable and complete
2. Vertices are indexed from 0 to numVertices-1
3. Reachability is determined by path existence (any cost)
4. Self-loops and cycles are allowed
5. Graph may be disconnected

## Questions for the User

1. ✓ **Self-reachability**: YES — Vertex can reach itself (included in matrix)
2. ✓ **Output format**: `boolean[][]` — True/false for reachability
3. ✓ **Display both matrices**: YES — Show initial adjacency and final reachability

## Constraints

- Java 8+ compatible
- Must compile without warnings
- No external dependencies beyond Java standard library
- Must follow OOP and SOLID principles

## Architecture Impact

- Minimal: Add static method `warshall()` to `AlgoritmosGrafo`
- No changes to core graph classes needed
- No changes to data structures

## Implementation Strategy

1. **Algorithm Implementation**
   - Create `warshall(GrafoDirecionado g): boolean[][]` method
   - Initialize reachability matrix from adjacency matrix
   - Apply Warshall's algorithm (Floyd-Warshall variant for reachability)
   - Return final reachability matrix

2. **Test Cases**
   - Test with simple connected graph
   - Test with disconnected graph
   - Test with cycles
   - Test with single vertex
   - Verify known reachability patterns

3. **Documentation**
   - Update `ExemploGrafo` to demonstrate Warshall
   - Document expected output for test graph
   - Add comments explaining algorithm

## Testing Strategy

- Unit-like tests via example program (`ExemploGrafo`)
- Manual verification of output against expected reachability
- Edge cases: single vertex, no edges, fully connected, disconnected components

## Risks

- Algorithm correctness validation (manual verification required)
- Large graphs may need memory optimization (not critical for educational use)
- Confusion between reachability (boolean) and shortest distance (Dijkstra)

## Acceptance Criteria

- [x] `warshall()` method implemented and compiles ✓
- [x] Test graph produces correct reachability matrix ✓
- [x] All vertices' reachability determined correctly ✓
- [x] Example program demonstrates Warshall output ✓
- [x] Code follows Java standards and OOP principles ✓
- [x] No compilation warnings ✓

## Implementation Summary

**Files Modified/Created**:
- Created `AlgoritmosGrafo.java` with:
  - `warshall(GrafoDirecionado): boolean[][]` — Main algorithm
  - `imprimeMatrizBooleana()` — Utility for pretty-printing
  - `imprimeEstatisticasAlcancabilidade()` — Utility for statistics

- Modified `GrafoDirecionado.java`:
  - Added `existeAresta(int, int): boolean` method

- Modified `ExemploGrafo.java`:
  - Added section 10 demonstrating Warshall
  - Shows both adjacency and reachability matrices
  - Includes verification checks

**Algorithm Details**:
- Time Complexity: O(V³)
- Space Complexity: O(V²)
- Handles cycles and self-reachability correctly
- Test result: All vertices reach all others (expected with cycle)
