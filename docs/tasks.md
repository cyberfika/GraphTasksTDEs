# Tasks

> Status: Active
> Authority: Tier 3 - Working Document
> Last Updated: 2026-05-07

## Backlog

### TDE 1 - Graph Implementation (Completed)
- [x] Create `Aresta` class with properties and toString()
- [x] Create `GrafoDirecionado` with adjacency list
- [x] Implement BFS algorithm
- [x] Implement DFS algorithm
- [x] Implement Dijkstra's shortest path
- [x] Create `ExemploGrafo` test program

### TDE 2 - Warshall and Reachability Matrix

1. **Analyze Warshall Algorithm**
   - [x] Review algorithm description and pseudocode
   - [x] Understand time/space complexity O(n³)
   - [x] Compare with Floyd-Warshall for shortest paths

2. **Implement Warshall Algorithm**
   - [x] Add `warshall(GrafoDirecionado g): boolean[][]` to `AlgoritmosGrafo`
   - [x] Initialize reachability matrix from adjacency
   - [x] Implement three nested loops for transitive closure
   - [x] Return final reachability matrix

3. **Testing and Verification**
   - [x] Test with example graph from TDE 1
   - [x] Manually verify reachability for all vertex pairs
   - [x] Verify self-reachability behavior (included in algorithm)
   - [x] Run and validate output

4. **Documentation and Examples**
   - [x] Update `ExemploGrafo` to demonstrate Warshall
   - [x] Add explanatory output for reachability matrix
   - [x] Document algorithm in `/docs/design.md`
   - [x] Add helper methods for printing matrices

5. **Code Quality**
   - [x] Compiled without warnings
   - [x] Added JavaDoc comments
   - [x] Follow coding standards
   - [x] OOP principles applied

6. **Additional Methods**
   - [x] Add `existeAresta()` method to GrafoDirecionado
   - [x] Add `imprimeMatrizBooleana()` utility
   - [x] Add `imprimeEstatisticasAlcancabilidade()` utility

## In Progress

(None currently)

## Blocked

(None currently)

## Completed

- TDE 1: Graph representation with BFS, DFS, Dijkstra implemented and tested

## Verification Checklist

When TDE 2 is complete, verify:

- [ ] Code compiles without errors or warnings
- [ ] Example program runs successfully
- [ ] Reachability matrix is correct for test graph
- [ ] All vertex reachability is computed correctly
- [ ] Documentation is complete
- [ ] Design document reflects algorithm addition
- [ ] No regression in existing functionality
