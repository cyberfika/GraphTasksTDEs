# GraphTasksTDEs - Sistema de Grafos Direcionados

![Java](https://img.shields.io/badge/Java-8%2B-orange?logo=java)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen)
![License](https://img.shields.io/badge/License-MIT-blue)
![Version](https://img.shields.io/badge/Version-2.0-blue)

## 👨‍🎓 Informações do Aluno

- **Nome**: Jafte Carneiro Fagundes da Silva
- **Curso**: Ciência da Computação
- **Disciplina**: Resolução de Problemas com Grafos
- **Tipo**: Trabalho Discente Efetivo (TDE)

---

## 📋 Descrição do Projeto

**GraphTasksTDEs** é um sistema educacional implementado em Java para representar e manipular grafos direcionados, ponderados e rotulados, com suporte a algoritmos clássicos de grafos.

### Objetivos
- ✅ Implementar representação eficiente de grafos (lista de adjacência)
- ✅ Desenvolver algoritmos de traversal (BFS, DFS)
- ✅ Implementar algoritmo de caminho mínimo (Dijkstra)
- ✅ Implementar algoritmo de alcançabilidade (Warshall)
- ✅ Criar interface interativa para manipulação
- ✅ Adicionar persistência (serialização em .bin)

---

## 🏗️ Estrutura do Projeto

```
GraphTasksTDEs/
├── src/br/edu/grafo/
│   ├── model/           # Modelos de dados (Aresta, GrafoDirecionado)
│   ├── algorithm/       # Algoritmos de grafos
│   ├── util/            # Utilitários (persistência)
│   └── app/             # Aplicação (Menu interativo, Exemplos)
├── output/              # Arquivos compilados (.class)
├── data/                # Grafos salvos (.bin)
├── docs/                # Documentação
│   ├── design.md        # Arquitetura e design
│   ├── plan.md          # Plano de implementação
│   ├── tasks.md         # Tarefas completadas
│   ├── memory.md        # Contexto de sessão
│   ├── uml/             # Diagramas PlantUML
│   └── knowledge/       # Base de conhecimento
├── compile.sh           # Script de compilação
├── START_HERE.md        # Guia rápido
└── README.md            # Este arquivo
```

---

## 🚀 Como Usar

### Pré-requisitos
- Java 8 ou superior instalado
- Bash ou terminal compatível (para compile.sh)

### Compilação

**Opção 1 - Script automático:**
```bash
./compile.sh
```

**Opção 2 - Comando manual:**
```bash
javac -d output -sourcepath src \
    src/br/edu/grafo/model/*.java \
    src/br/edu/grafo/algorithm/*.java \
    src/br/edu/grafo/util/*.java \
    src/br/edu/grafo/app/*.java
```

### Execução

**Menu Interativo:**
```bash
java -cp output br.edu.grafo.app.Main
```

**Programa de Exemplo:**
```bash
java -cp output br.edu.grafo.app.ExemploGrafo
```

---

## 📊 Funcionalidades

### 1. Gerenciamento de Grafos
- ➕ Criar grafo com número customizável de vértices
- ➕ Adicionar arestas direcionadas com peso e rótulo
- ❌ Remover arestas
- 👁️ Visualizar em formato de matriz ou lista
- ℹ️ Obter informações do grafo (vértices, arestas, densidade, graus)

### 2. Algoritmos de Traversal
- **BFS** (Busca em Largura) - O(V+E)
- **DFS** (Busca em Profundidade) - O(V+E)

### 3. Algoritmos de Caminho
- **Dijkstra** - Caminho mínimo desde uma origem - O((V+E) log V)
- **Warshall** - Matriz de alcançabilidade (fechamento transitivo) - O(V³)

### 4. Persistência
- 💾 Salvar grafos em arquivos binários (.bin)
- 📂 Carregar grafos salvos
- 📋 Listar grafos disponíveis

---

## 🏛️ Arquitetura

### Camadas

```
┌─────────────────────────────────────────┐
│     Application Layer (Menu, Example)    │
├─────────────────────────────────────────┤
│     Algorithm Layer (Warshall, etc.)    │
├─────────────────────────────────────────┤
│     Domain Layer (Graph, Edge)          │
├─────────────────────────────────────────┤
│     Persistence Layer (Storage)         │
└─────────────────────────────────────────┘
```

### Pacotes

| Pacote | Responsabilidade |
|--------|------------------|
| `br.edu.grafo.model` | Modelos de domínio (Aresta, GrafoDirecionado) |
| `br.edu.grafo.algorithm` | Algoritmos de grafos (Warshall, etc.) |
| `br.edu.grafo.util` | Utilitários (GrafoStorage para .bin) |
| `br.edu.grafo.app` | Interface (Main com menu, ExemploGrafo) |

---

## 📈 Algoritmos Implementados

### Warshall (Fechamento Transitivo)
```
Entrada: Grafo direcionado G = (V, E)
Saída: Matriz booleana reach[V][V] onde reach[i][j] = true ⟺ ∃ caminho de i para j

Algoritmo:
1. Inicializar reach[i][j] = true se aresta (i,j) existe
2. reach[i][i] = true (self-reachability)
3. Para cada k, i, j:
     reach[i][j] = reach[i][j] OR (reach[i][k] AND reach[k][j])
```

**Complexidade**:
- Tempo: O(V³)
- Espaço: O(V²)

### Dijkstra (Caminho Mínimo)
```
Entrada: Grafo G, vértice origem s
Saída: Array dist[] com distâncias mínimas de s

Algoritmo: Greedy com PriorityQueue
- Inicializar dist[s] = 0, dist[outros] = ∞
- Enquanto houver vértices não visitados:
  - Dequeue vértice u com menor dist[u]
  - Atualizar dist dos vizinhos de u
```

**Complexidade**:
- Tempo: O((V+E) log V)
- Espaço: O(V)

---

## 📝 Diagramas UML

Diagrama de Classes (veja em `/docs/uml/class_diagram.puml`):
- Classes de modelo (Aresta, GrafoDirecionado)
- Classes de algoritmo (AlgoritmosGrafo)
- Classes de aplicação (Main, ExemploGrafo)
- Classes de persistência (GrafoStorage)

Outros diagramas disponíveis:
- **Sequência**: Execução do algoritmo Warshall
- **Componentes**: Estrutura de módulos
- **Casos de Uso**: Funcionalidades do sistema
- **Implantação**: Ambiente de execução

---

## 🧪 Exemplo de Uso

### Criar e testar um grafo

```java
// Criar grafo com 5 vértices
GrafoDirecionado grafo = new GrafoDirecionado(5);

// Adicionar arestas
grafo.cria_adjacencia(0, 1, 5.0, "aresta_A");
grafo.cria_adjacencia(1, 2, 2.0);
grafo.cria_adjacencia(2, 3, 4.0);

// Executar Warshall
boolean[][] alcancabilidade = AlgoritmosGrafo.warshall(grafo);

// Salvar em arquivo
GrafoStorage.salvarGrafo(grafo, "meu_grafo");

// Carregar do arquivo
GrafoDirecionado carregado = GrafoStorage.carregarGrafo("meu_grafo");
```

---

## 📚 Documentação Adicional

- **START_HERE.md** - Guia rápido de início
- **docs/design.md** - Detalhes de arquitetura e design
- **docs/plan.md** - Plano de implementação
- **docs/knowledge/** - Base de conhecimento estruturada
- **docs/uml/** - Diagramas em PlantUML

---

## ✅ Checklist de Completude

### TDE 1 - Graph Implementation
- [x] Classe Aresta com equals() e hashCode()
- [x] Classe GrafoDirecionado com adjacência
- [x] Algoritmo BFS
- [x] Algoritmo DFS
- [x] Algoritmo Dijkstra
- [x] Exemplo de uso (ExemploGrafo)

### TDE 2 - Warshall & Persistência
- [x] Algoritmo Warshall
- [x] Matriz de alcançabilidade
- [x] Save/Load em .bin
- [x] Menu interativo
- [x] Documentação completa

### Qualidade de Código
- [x] Sem raw types
- [x] Nomes em camelCase
- [x] Javadoc para métodos públicos
- [x] Tratamento de exceções
- [x] Seguir SOLID e OOP

### Documentação
- [x] README.md com badges
- [x] Diagramas UML (PlantUML)
- [x] Especificações de algoritmos
- [x] Guia de uso
- [x] Estrutura de pacotes

---

## 🔧 Tecnologias

| Tecnologia | Versão | Uso |
|-----------|--------|-----|
| Java | 8+ | Linguagem principal |
| PlantUML | - | Diagramas UML |
| Markdown | - | Documentação |
| Git | - | Controle de versão |

---

## 📄 Licença

MIT License - Veja arquivo LICENSE para detalhes

---

## 🤝 Autor

**Jafte Carneiro Fagundes da Silva**
- Discente: Ciência da Computação
- Disciplina: Resolução de Problemas com Grafos
- Data: Maio 2026

---

## 📞 Suporte

Para dúvidas sobre implementação:
1. Consulte START_HERE.md
2. Veja exemplos em ExemploGrafo.java
3. Explore diagramas em /docs/uml
4. Revise documentação em /docs/design.md

---

**Status**: ✅ Projeto completo e pronto para entrega

