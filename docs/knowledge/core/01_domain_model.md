# Domain Model

> Status: Active
> Authority: Tier 2 - Core Knowledge
> Last Updated: 2026-05-07

## Overview

The domain model for GraphTasksTDEs centers on three core concepts:
1. **Aresta** (Edge) — A directed connection between vertices
2. **GrafoDirecionado** (Directed Graph) — A collection of vertices and edges
3. **Graph Algorithms** — Computations performed on graphs

## Core Concepts

### Vertex (Vértice)

A vertex is an atomic element of a graph, identified by an integer index (0 to V-1).

**Properties**:
- `index`: Unique integer from 0 to numVertices-1
- `informacao`: Optional string label for documentation
- `grauSaida`: Count of outgoing edges
- `grauEntrada`: Count of incoming edges

**Invariants**:
- Each vertex has a unique index
- Indices are contiguous from 0

### Edge (Aresta)

A directed edge connects a source vertex to a destination vertex with an associated weight and optional label.

**Properties**:
- `origem`: Source vertex (implicit, stored in adjacency list)
- `destino`: Destination vertex (int)
- `peso`: Edge weight (double) — can represent cost, distance, capacity, etc.
- `rotulo`: Optional label (String) for documentation

**Invariants**:
- Destination must be valid vertex (0 ≤ destino < V)
- Weight is a real number (can be 0, negative, or positive)
- Two edges are equal if they share the same destination (implicit origin)
- At most one edge from source i to destination j

**Semantics**:
- Represents a one-way connection from i to j
- Weight typically non-negative (varies by algorithm)
- Self-loops allowed (i can have edge to itself)

### Path (Caminho)

A sequence of edges connecting vertices v₀, v₁, ..., vₖ.

**Definition**:
- Path from v₀ to vₖ: sequence of edges (v₀→v₁), (v₁→v₂), ..., (vₖ₋₁→vₖ)
- Length: number of edges in path
- Cost: sum of weights of edges in path

**Variants**:
- **Simple Path**: No repeated vertices
- **Cycle**: Path where v₀ = vₖ (starts and ends at same vertex)
- **Reachable**: v₀ can reach vₖ if path exists

### Directed Graph (Grafo Direcionado)

A collection of vertices and directed edges.

**Properties**:
- `V`: Number of vertices
- `E`: Number of edges
- `arestas`: Set of directed edges

**Representation**: Adjacency list (list of lists)
```
listaAdjacencia[i] = List<Aresta> where each Aresta represents (i → j)
```

**Invariants**:
- Vertices indexed 0 to V-1
- At most one edge (i → j)
- Cycles and self-loops allowed
- Graph may be disconnected

### Graph Properties

| Property | Definition | Calculation |
|----------|-----------|-------------|
| **Order** | Number of vertices | V = numVertices |
| **Size** | Number of edges | E = sum of listaAdjacencia[i].size() |
| **Density** | Ratio of edges to possible edges | E / (V × (V-1)) |
| **Out-Degree(i)** | Number of outgoing edges | listaAdjacencia[i].size() |
| **In-Degree(i)** | Number of incoming edges | count of edges pointing to i |
| **Connected** | All vertices reachable from any vertex | N/A for directed graphs |
| **Strongly Connected** | Every vertex reachable from every other | Not required here |
| **Acyclic (DAG)** | No cycles | Not required here |

## Algorithms

### Breadth-First Search (BFS)

**Purpose**: Explore graph level-by-level from source vertex

**Input**: Graph G, start vertex s

**Output**: List of vertices in order visited

**Algorithm**:
```
visited[s] = true
queue = [s]
result = []

while queue not empty:
    v = queue.dequeue()
    result.append(v)
    for each neighbor u of v:
        if not visited[u]:
            visited[u] = true
            queue.enqueue(u)

return result
```

**Complexity**: Time O(V+E), Space O(V)

**Properties**:
- Finds shortest path in unweighted graphs
- Level-by-level exploration
- Uses queue (FIFO)

### Depth-First Search (DFS)

**Purpose**: Explore graph as far as possible along each branch

**Input**: Graph G, start vertex s

**Output**: List of vertices in order visited

**Algorithm** (recursive):
```
visited[s] = true
result.append(s)

for each neighbor u of s:
    if not visited[u]:
        dfs(u)

return result
```

**Complexity**: Time O(V+E), Space O(V) for recursion stack

**Properties**:
- Explores fully along each branch before backtracking
- Uses recursion (implicit stack)
- Good for finding connected components

### Dijkstra's Algorithm

**Purpose**: Find shortest paths from source to all vertices

**Input**: Graph G with non-negative weights, source vertex s

**Output**: Array dist[] where dist[v] = shortest path cost from s to v

**Algorithm**:
```
dist[s] = 0
dist[all others] = INFINITY
visited = {false}
pq = PriorityQueue with (dist[s], s)

while pq not empty:
    (d, u) = pq.dequeue()
    if visited[u]: continue
    visited[u] = true

    for each edge (u, v) with weight w:
        if dist[u] + w < dist[v]:
            dist[v] = dist[u] + w
            pq.enqueue((dist[v], v))

return dist[]
```

**Complexity**: Time O((V+E) log V), Space O(V)

**Properties**:
- Requires non-negative edge weights
- Single-source, all-destinations
- Optimal substructure (greedy algorithm)
- Returns infinity for unreachable vertices

### Warshall's Algorithm (Floyd-Warshall for Reachability)

**Purpose**: Compute transitive closure (reachability matrix)

**Input**: Graph G

**Output**: Boolean matrix reach[][] where reach[i][j] = true iff path exists from i to j

**Algorithm**:
```
reach[i][j] = true if edge (i→j) exists, else false

for k = 0 to V-1:
    for i = 0 to V-1:
        for j = 0 to V-1:
            reach[i][j] = reach[i][j] OR (reach[i][k] AND reach[k][j])

return reach[][]
```

**Complexity**: Time O(V³), Space O(V²)

**Properties**:
- All-pairs reachability (not shortest paths)
- Works with negative weights (as long as no negative cycles)
- Dynamic programming approach
- Variant of Floyd-Warshall algorithm

## Assumptions and Design Rules

### Semantic Assumptions

1. **Non-Negative Weights for Dijkstra**: Algorithm assumes weights ≥ 0
2. **At Most One Edge per Pair**: (i, j) can appear at most once
3. **Vertices are Integers**: 0-indexed, contiguous
4. **No Multiple Edges**: Multigraphs not supported
5. **No Undirected Edges**: All edges are directed

### Representation Assumptions

1. **Adjacency List**: More efficient for sparse graphs than matrix
2. **Edge Objects**: Separate Aresta class for encapsulation
3. **Integer Indexing**: Vertices identified by position, not name

### Algorithm Assumptions

1. **Stateless Algorithms**: No state stored in graph during computation
2. **All Vertices Valid**: Algorithms assume valid vertex indices
3. **Deterministic**: Same input produces same output
4. **No Cycles Affecting Correctness**: Algorithms handle cycles correctly

## Related Documents

- `/docs/design.md` — Class design and UML
- `/docs/knowledge/core/02_architecture.md` — System architecture
- Source code: `Aresta.java`, `GrafoDirecionado.java`, `AlgoritmosGrafo.java`
