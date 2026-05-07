# START HERE

## Project Summary

**GraphTasksTDEs** is a Java educational project implementing directed, weighted, and labeled graph representations with classic graph algorithms (BFS, DFS, Dijkstra, Warshall).

Package structure:
- `br.edu.grafo.model` — Graph and Edge classes
- `br.edu.grafo.algorithm` — Algorithms (Warshall, etc.)
- `br.edu.grafo.util` — Storage/serialization utilities
- `br.edu.grafo.app` — Interactive menu and examples

**Current Focus**: Complete and stable - ready for use.

## Current Status

- **TDE 1**: Graph representation with basic algorithms — ✓ Complete
- **TDE 2**: Warshall algorithm and save/load functionality — ✓ Complete
- **Documentation**: Complete with package structure
- **Menu System**: Interactive CLI with save/load — ✓ Complete

## Team and Ownership

- Student: jafte
- Project Type: Educational (Trabalho Discente Efetivo)

## How to Run Locally

### Directory Structure
```
GraphTasksTDEs/
├── src/                    # Source code
│   └── br/edu/grafo/
│       ├── model/         # Aresta, GrafoDirecionado
│       ├── algorithm/     # AlgoritmosGrafo
│       ├── util/          # GrafoStorage
│       └── app/           # Main, ExemploGrafo
├── output/                # Compiled .class files
├── data/                  # Saved .bin files
└── docs/                  # Documentation
```

### Compile

**Using the compile script:**
```bash
./compile.sh
```

**Or manually:**
```bash
javac -d output -sourcepath src \
    src/br/edu/grafo/model/*.java \
    src/br/edu/grafo/algorithm/*.java \
    src/br/edu/grafo/util/*.java \
    src/br/edu/grafo/app/*.java
```

### Run Interactive Menu

**From project root (recommended):**
```bash
java -cp output br.edu.grafo.app.Main
```

This ensures that saved .bin files go to `/data` directory.

### Run Example Program

**From project root:**
```bash
java -cp output br.edu.grafo.app.ExemploGrafo
```

## How to Test

Run the example program which includes:
- Graph creation with weighted edges
- BFS and DFS traversals
- Dijkstra's shortest path algorithm
- Input validation

## Current Sprint or Current Focus

**TDE 2 - Reachability Matrix and Warshall Algorithm**
- Implement Warshall's algorithm for transitive closure
- Test reachability matrix computation
- Dijkstra algorithm (already implemented in TDE 1)

## Key Architecture Decisions

1. **Graph Representation**: Adjacency list using `List<List<Aresta>>`
2. **Edge Model**: Weighted, directed edges with optional labels
3. **Algorithms**: Static utility methods in `AlgoritmosGrafo` class
4. **Error Handling**: `IllegalArgumentException` for invalid vertices
5. **No Raw Types**: Generic `List<Aresta>` instead of raw arrays

See `/docs/knowledge/KNOWLEDGE_BASE.md` for detailed architecture.

## Credentials and Access Policy

No credentials required for local development.

## First Files to Read

1. `START_HERE.md` (this file)
2. `/docs/plan.md` — Current implementation plan
3. `/docs/design.md` — Architecture and UML diagrams
4. `/docs/knowledge/KNOWLEDGE_BASE.md` — Document map and authority hierarchy
5. Source code: `Aresta.java`, `GrafoDirecionado.java`, `AlgoritmosGrafo.java`

## Documentation Map

- `/docs/plan.md` — Implementation plan and acceptance criteria
- `/docs/tasks.md` — Task breakdown and progress
- `/docs/design.md` — Architecture, domain model, UML
- `/docs/memory.md` — Decisions, preferences, and session context
- `/docs/knowledge/` — Knowledge base with authority hierarchy
- `AGENTS.md` — Rules for AI agent assistance
