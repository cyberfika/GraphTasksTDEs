# Knowledge Base

> Status: Active
> Authority: Tier 2 - Core Knowledge Map
> Last Updated: 2026-05-07
>
> **Aluno**: Jafte Carneiro Fagundes da Silva
> **Curso**: Ciência da Computação
> **Disciplina**: Resolução de Problemas com Grafos

## Purpose

Central navigation map for all project documentation. Defines authority hierarchy, document relationships, concept clusters, and maintenance rules.

## Authority Hierarchy

### Tier 1: Source of Truth

Location: `/docs/knowledge/source-of-truth/`

- **Status**: No Tier 1 documents yet
- **Rules**: Authoritative. Defines requirements, constraints, and approved decisions.
- **Examples**: Requirements specifications, approved algorithm designs, security policies

### Tier 2: Core Knowledge

Location: `/docs/knowledge/core/` and root-level governance files

| Document | Purpose | Authority |
|----------|---------|-----------|
| `/docs/design.md` | System architecture, domain model, component diagram, UML | Tier 2 |
| `AGENTS.md` | AI agent guidelines and operating rules | Tier 2 |
| `/docs/knowledge/core/00_project_context.md` | Project summary, scope, goals | Tier 2 |
| `/docs/knowledge/core/01_domain_model.md` | Graph concepts, algorithm descriptions | Tier 2 |
| `/docs/knowledge/core/02_architecture.md` | Module structure, responsibilities, boundaries | Tier 2 |

### Tier 3: Implementation and Working Documents

Location: `/docs/` and `/docs/knowledge/implementation/` and `/docs/knowledge/meetings/`

| Document | Purpose | Authority |
|----------|---------|-----------|
| `/docs/plan.md` | Current implementation plan and strategy | Tier 3 |
| `/docs/tasks.md` | Task breakdown, progress tracking | Tier 3 |
| `/docs/memory.md` | Session notes, decisions, preferences | Tier 3 |
| `/docs/knowledge/implementation/` | Working notes, algorithm pseudocode, design sketches | Tier 3 |
| `/docs/knowledge/meetings/` | Meeting notes with date prefix | Tier 3 |

### Tier 4: Archive

Location: `/docs/knowledge/archive/`

- **Status**: No archived documents yet
- **Rule**: Historical reference only. Do not cite as current truth.
- **Marked**: Begins with archive banner

## Tier 1: Source of Truth

**Status**: Empty (no source of truth documents created yet)

Recommended future documents:
- `/docs/knowledge/source-of-truth/requirements.md` — TDE requirements from instructor
- `/docs/knowledge/source-of-truth/algorithm_specs.md` — Approved algorithm specifications
- `/docs/knowledge/source-of-truth/coding_standards.md` — Project coding standards

## Tier 2: Core Knowledge

### AGENTS.md
- **Location**: `AGENTS.md` (root)
- **Purpose**: Operating rules for AI agents assisting with Java development
- **Authority**: Defines how assistance is provided
- **Key Sections**: 42 sections covering initialization, standards, principles, workflows
- **Status**: Stable reference document

### `/docs/design.md`
- **Location**: `/docs/design.md`
- **Purpose**: Complete system design including architecture, UML, algorithms
- **Key Content**:
  - System overview with C4-style component diagram
  - Domain model (Aresta, GrafoDirecionado, AlgoritmosGrafo)
  - Class diagrams and sequence diagrams
  - Algorithm descriptions (BFS, DFS, Dijkstra, Warshall)
  - Error handling and performance analysis
  - Design decisions with rationales
- **Status**: Active, updated for TDE 2 planning

### `/docs/knowledge/core/00_project_context.md`
- **Location**: `/docs/knowledge/core/00_project_context.md`
- **Purpose**: Project summary, goals, scope
- **Content**:
  - What is GraphTasksTDEs?
  - Current phase (TDE 1 complete, TDE 2 in planning)
  - Team and ownership
  - Key constraints
- **Status**: To be created

### `/docs/knowledge/core/01_domain_model.md`
- **Location**: `/docs/knowledge/core/01_domain_model.md`
- **Purpose**: Graph domain concepts and terminology
- **Content**:
  - Vertex, edge, weight, label, path, reachability
  - Directed graph semantics
  - Algorithm definitions
- **Status**: To be created

### `/docs/knowledge/core/02_architecture.md`
- **Location**: `/docs/knowledge/core/02_architecture.md`
- **Purpose**: System architecture and module boundaries
- **Content**:
  - Layer breakdown (domain, algorithms, application)
  - Class responsibilities
  - Module interactions
  - Design patterns used
- **Status**: To be created

## Tier 3: Implementation and Working Documents

### `/docs/plan.md`
- **Purpose**: Current TDE 2 implementation plan
- **Status**: Active
- **Key Sections**: Objective, scope, assumptions, strategy, acceptance criteria
- **Audience**: Developer, AI agent

### `/docs/tasks.md`
- **Purpose**: Actionable task breakdown and progress tracking
- **Status**: Active
- **Key Sections**: Backlog, In Progress, Blocked, Completed, Verification Checklist
- **Audience**: Developer, project manager

### `/docs/memory.md`
- **Purpose**: Session context, decisions, preferences, lessons learned
- **Status**: Active
- **Key Sections**: User preferences, approved decisions, rejected decisions, open questions, session log
- **Audience**: AI agent continuity across sessions

### `/docs/knowledge/implementation/`
- **Purpose**: Working notes during implementation
- **Status**: To be created
- **Content**: Algorithm pseudocode, debugging notes, experiment results
- **Convention**: Numbered files with clear status

### `/docs/knowledge/meetings/`
- **Purpose**: Capture meetings and decisions
- **Status**: Empty (no meetings yet)
- **Convention**: Date-prefixed files (YYYY-MM-DD_topic.md)
- **Post-Meeting Workflow**: Extract decisions, actions, risks; update plan.md, tasks.md, memory.md

## Tier 4: Archive

**Status**: Empty (no archived documents yet)

**Future Usage**: When documents become superseded or historical, move to `/docs/knowledge/archive/` with archive banner.

## Concept Clusters

### Graph Fundamentals

**Documents**:
- `/docs/design.md` — Domain Model section
- `/docs/knowledge/core/01_domain_model.md` (to create)

**Concepts**:
- Vertices (nodes)
- Directed edges
- Weighted edges
- Labels and annotations
- Adjacency representation

### Algorithms

**Documents**:
- `/docs/design.md` — Algorithm descriptions and complexity analysis
- `/docs/knowledge/core/01_domain_model.md` (to create) — Algorithm pseudocode

**Algorithms**:
- **Traversal**: BFS, DFS
- **Shortest Paths**: Dijkstra
- **Reachability**: Warshall (Floyd-Warshall variant)

**Status by TDE**:
- TDE 1: BFS, DFS, Dijkstra ✓
- TDE 2: Warshall (in planning)

### Software Engineering Standards

**Documents**:
- `AGENTS.md` — Sections 1-42
- `/docs/design.md` — Design Decisions

**Topics**:
- OOP principles (encapsulation, abstraction, inheritance, polymorphism)
- SOLID principles (SRP, OCP, LSP, ISP, DIP)
- Code standards (naming, comments, structure)
- Error handling (exceptions, validation)
- Testing standards
- Documentation requirements

## Evidence Trails

### Why Adjacency List Over Matrix?

**Evidence**:
- `/docs/design.md` line: "Representation: Adjacency list (space: O(V+E), time: good for sparse graphs)"
- Educational context with small graphs
- Standard choice for sparse graph representation

### Why Static Methods in AlgoritmosGrafo?

**Evidence**:
- `/docs/design.md` → "Design Pattern: Utility class with static methods"
- **Rationale**: Stateless, reusable, easy to test
- Consistent with educational style

### Why IllegalArgumentException for Invalid Vertices?

**Evidence**:
- `/docs/design.md` → Error Handling Strategy section
- `/docs/memory.md` → Approved Decisions
- Java standard for programming errors vs data issues

## Navigation Paths

### For Someone New to the Project

1. Start: `START_HERE.md`
2. Then: `/docs/knowledge/KNOWLEDGE_BASE.md` (this file)
3. Then: `/docs/design.md` (understand architecture)
4. Then: `/docs/knowledge/core/00_project_context.md` (understand goals)
5. Then: Source code (`Aresta.java`, `GrafoDirecionado.java`, `AlgoritmosGrafo.java`)

### For Someone Implementing TDE 2

1. Review: `/docs/plan.md` (objectives and scope)
2. Review: `/docs/tasks.md` (task breakdown)
3. Refer: `/docs/design.md` → Warshall Algorithm section
4. Refer: `/docs/knowledge/core/01_domain_model.md` (algorithm semantics)
5. Code: Implement in `AlgoritmosGrafo.java`
6. Test: Update `ExemploGrafo.java`
7. Update: `/docs/tasks.md` and `/docs/memory.md`

### For Someone Debugging an Algorithm

1. Check: `/docs/design.md` → Algorithm section (pseudocode, complexity)
2. Check: `/docs/knowledge/core/01_domain_model.md` (formal definition)
3. Check: `/docs/knowledge/implementation/` (working notes, debug logs)
4. Check: Source code comments and JavaDoc
5. Verify: Test cases in `ExemploGrafo.java`

## Relationship Map

```
START_HERE.md
    ↓
    ├→ Documentation Map section
    └→ First Files to Read
            ↓
            ├→ AGENTS.md (how to work with agent)
            ├→ /docs/knowledge/KNOWLEDGE_BASE.md (this file)
            │   ↓
            │   ├→ /docs/plan.md (what we're building)
            │   ├→ /docs/tasks.md (how to break it down)
            │   ├→ /docs/design.md (architecture and UML)
            │   ├→ /docs/memory.md (session context)
            │   └→ /docs/knowledge/core/ (deep knowledge)
            └→ Source code (implementation)
                ├→ Aresta.java (edge model)
                ├→ GrafoDirecionado.java (graph model)
                ├→ AlgoritmosGrafo.java (algorithms)
                └→ ExemploGrafo.java (usage example)
```

## Maintenance Rules

### When to Update Knowledge Base

**Trigger**: Update `/docs/knowledge/KNOWLEDGE_BASE.md` when:
- New documentation file is added
- A document is archived or removed
- A major design decision changes
- A new module or algorithm is introduced
- A Tier 1 document is created or modified
- The user corrects an assumption
- A meeting produces new decisions

### Adding New Documents

**Procedure**:
1. Create document in appropriate location (Tier 1, 2, 3, or 4)
2. Include status, authority, and last-updated headers
3. Add entry to appropriate section in this Knowledge Base
4. Update related document relationships
5. Update task list if implementation required

### Archiving Documents

**Procedure**:
1. Move document to `/docs/knowledge/archive/`
2. Add archive banner at top: `> Status: Archived. This document is historical and non-authoritative.`
3. Remove from active sections in this Knowledge Base
4. Add archive note linking to active replacement (if any)
5. Preserve for historical traceability

### Reviewing Staleness

**Frequency**: Review knowledge base for staleness every session

**Indicators of Staleness**:
- "Last Updated" date more than 2 weeks old
- References to non-existent documents
- Conflicting information between documents
- Tasks marked as "in progress" for long periods

**Resolution**:
- Update "Last Updated" header when reviewing
- Create implementation notes if understanding changed
- Archive superseded documents
- Update memory.md with corrections

---

**Document Generated**: 2026-05-07
**Next Review**: When TDE 2 implementation begins
