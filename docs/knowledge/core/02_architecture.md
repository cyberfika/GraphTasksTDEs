# Architecture

> Status: Active
> Authority: Tier 2 - Core Knowledge
> Last Updated: 2026-05-07

## System Architecture

The GraphTasksTDEs system is organized in three layers:

```
┌──────────────────────────────────────────┐
│     Application Layer                     │
│     ExemploGrafo (Usage & Testing)        │
├──────────────────────────────────────────┤
│     Algorithm Layer                       │
│     AlgoritmosGrafo (Stateless utilities) │
│     - BFS                                 │
│     - DFS                                 │
│     - Dijkstra                            │
│     - Warshall                            │
├──────────────────────────────────────────┤
│     Domain Layer                          │
│     Aresta (Edge)                         │
│     GrafoDirecionado (Graph)              │
├──────────────────────────────────────────┤
```

### Responsibilities by Layer

#### Domain Layer
- **Purpose**: Model core concepts (Edge, Graph)
- **Responsibility**: Represent graph structure and state
- **Dependencies**: None (isolated from algorithms and application)

#### Algorithm Layer
- **Purpose**: Implement graph algorithms
- **Responsibility**: Compute properties on graphs (traversals, shortest paths, reachability)
- **Dependencies**: Domain layer (read-only, no modification)
- **Pattern**: Static utility methods (stateless)

#### Application Layer
- **Purpose**: Demonstrate and test system
- **Responsibility**: Create graphs, invoke algorithms, display results
- **Dependencies**: Domain and algorithm layers
- **Pattern**: Procedural example program

## Module Structure

```
GraphTasksTDEs/
├── AGENTS.md                    # AI agent rules
├── START_HERE.md                # Project entry point
├── Aresta.java                  # Edge model (domain)
├── GrafoDirecionado.java        # Graph model (domain)
├── AlgoritmosGrafo.java         # Algorithms (algorithm layer)
├── ExemploGrafo.java            # Example/test (application layer)
├── docs/                        # Documentation
│   ├── plan.md
│   ├── tasks.md
│   ├── design.md
│   ├── memory.md
│   └── knowledge/
│       ├── KNOWLEDGE_BASE.md
│       ├── core/
│       │   ├── 00_project_context.md
│       │   ├── 01_domain_model.md
│       │   └── 02_architecture.md
│       ├── source-of-truth/
│       ├── implementation/
│       ├── meetings/
│       └── archive/
```

## Dependency Graph

```
ExemploGrafo
  ├→ GrafoDirecionado (create and manipulate)
  ├→ Aresta (inspect via algorithms)
  └→ AlgoritmosGrafo (invoke algorithms)
      └→ GrafoDirecionado (read-only)
          └→ Aresta (read-only)
```

**Dependency Rules**:
1. Algorithms depend on domain (one-way)
2. Application depends on algorithms and domain (one-way)
3. Domain has no dependencies (isolated)
4. No circular dependencies

## Class Responsibilities

### Aresta (Edge)

**Single Responsibility**: Represent a directed edge with weight and label

**Public Interface**:
- Getters: `getDestino()`, `getPeso()`, `getRotulo()`
- Value semantics: `equals()`, `hashCode()`, `toString()`

**Design Notes**:
- Immutable (private fields, no setters)
- Equality based on destination (for edge uniqueness checks)
- No knowledge of containing graph or algorithms

### GrafoDirecionado (Graph)

**Single Responsibility**: Represent graph structure and maintain invariants

**Public Interface**:
- Mutation: `criaAdjacencia()`, `removeAdjacencia()`
- Query: `adjacentes()`, `existeAresta()`, `getAresta()`, `grauSaida()`, `grauEntrada()`
- Display: `imprimeMatriz()`, `imprimeLista()`, `toString()`
- Vertex info: `setaInformacao()`, `getInformacao()`

**Design Notes**:
- Maintains adjacency list internal structure
- Validates vertex indices before operations
- Throws exceptions for programming errors
- Handles edge cases (duplicate edges, missing edges)
- Provides multiple access patterns (array-based legacy + list-based modern)

### AlgoritmosGrafo (Algorithms)

**Single Responsibility**: Provide reusable graph algorithm implementations

**Public Interface**:
- Static methods: `bfs()`, `dfs()`, `dijkstra()`, `warshall()`
- Pure functions (no state, deterministic)

**Design Notes**:
- Stateless (no instance fields)
- Read-only access to graph (no modifications)
- Validates input vertex indices
- Returns well-defined results (lists, arrays, matrices)
- Private helper methods for internal recursion

### ExemploGrafo (Example)

**Single Responsibility**: Demonstrate system functionality and test correctness

**Public Interface**:
- Static `main()` method

**Design Notes**:
- Procedural style (educational)
- Shows all major features
- Includes example output for manual verification
- Documents expected results

## Design Patterns

| Pattern | Where | Why |
|---------|-------|-----|
| **Strategy** | AlgoritmosGrafo methods | Interchangeable algorithms for same problem |
| **Adapter** | Optional<Aresta> return | Null-safe API |
| **Template Method** | None (stateless utility) | Not applicable |
| **Factory** | Graph construction in ExemploGrafo | Clean object creation |

## Data Flow Examples

### Creating and Traversing a Graph

```
User Code
  ↓
  Create: new GrafoDirecionado(5)
    ↓ (domain initialization)
    Create empty adjacency lists
  ↓
  Add Edges: criaAdjacencia(0, 1, 5.0)
    ↓ (validation)
    Check vertex validity
    Check for duplicates
    ↓ (create edge)
    Create Aresta(1, 5.0)
    ↓ (store)
    Add to listaAdjacencia[0]
  ↓
  Traverse: bfs(graph, 0)
    ↓ (algorithm)
    Initialize visited[], queue
    Dequeue vertices level-by-level
    Return result List<Integer>
  ↓
  Display: Print or process results
```

### Dijkstra Shortest Path Computation

```
User Code
  ↓
  dijkstra(graph, source)
    ↓ (algorithm setup)
    Initialize dist[], visited[], PriorityQueue
    ↓ (main loop)
    while PriorityQueue not empty:
      Dequeue (distance, vertex)
      Get adjacencies: graph.getAdjacencias(vertex)
      For each edge:
        Update distances if improved
        Enqueue improved neighbors
    ↓ (return results)
    Return dist[] array
  ↓
  User processes distance array
```

## Boundary Definitions

### Domain-Algorithm Boundary

- **Algorithms** receive graph as read-only parameter
- **Algorithms** query graph structure (vertices, edges, adjacencies)
- **Algorithms** do NOT modify graph
- **Algorithms** return new data structures (lists, arrays, matrices)

### Algorithm-Application Boundary

- **Application** creates and configures graph
- **Application** invokes algorithms with valid parameters
- **Application** receives and displays results
- **Application** handles output formatting

## Scalability Considerations

### Current Design Assumptions

- Small graphs (V < 1000)
- In-memory representation
- Single-threaded execution
- No persistence

### Future Evolution Points

If system needs to scale:

1. **Large Graphs**: Consider graph compression, sparse matrix formats
2. **Distributed Graphs**: Add network layer, implement distributed algorithms
3. **Persistence**: Add repository layer with database/file access
4. **Performance**: Profile algorithms, optimize critical paths
5. **Concurrency**: Add thread-safety mechanisms if needed

## Testing Boundaries

### Unit-Level (Implicit)

- Domain objects (Aresta, GrafoDirecionado)
- Algorithm correctness through ExemploGrafo

### Integration-Level

- Algorithm + Graph interaction through ExemploGrafo
- End-to-end flow from graph creation to algorithm results

### What's NOT Tested

- Performance benchmarks (educational context)
- GUI or visualization
- Distributed scenarios
- Concurrent access

## Related Documents

- `/docs/design.md` — Class design, UML, algorithm details
- `/docs/knowledge/core/01_domain_model.md` — Concept definitions
- `/docs/knowledge/core/00_project_context.md` — Project goals and constraints
