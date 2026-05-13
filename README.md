# GraphTasksTDEs - Sistema De Grafos Direcionados

> Status: Active
> Authority: Tier 3 - Guia do projeto
> Last Updated: 2026-05-13
> Owner: Jafte Carneiro Fagundes da Silva

## Informacoes Do Aluno

- **Nome**: Jafte Carneiro Fagundes da Silva
- **Curso**: Ciencia da Computacao
- **Disciplina**: Resolucao de Problemas com Grafos
- **Tipo**: Trabalho Discente Efetivo (TDE)

## Visao Geral

O **GraphTasksTDEs** e um projeto educacional em Java 8+ para representar, manipular e analisar grafos direcionados, ponderados e rotulados.

O projeto contem:

- interface de console;
- interface grafica Swing inicial;
- persistencia local em arquivos `.bin`;
- algoritmos classicos de grafos;
- grafos tematicos prontos para demonstracao e validacao manual.

## Funcionalidades Implementadas

### Estrutura De Grafo

- Criacao de grafos com vertices indexados.
- Armazenamento por lista de adjacencia com `List<Edge>[]`.
- Arestas direcionadas com peso e rotulo opcional.
- Informacao textual associada aos vertices.

### Algoritmos

- BFS
- DFS
- Dijkstra
- Warshall
- AGM por Kruskal

## Observacoes Importantes De Modelagem

### Grafo Direcionado

O modelo central do projeto e `DirectedGraph`, portanto o sistema trabalha nativamente com arestas direcionadas.

### Kruskal Em Interpretacao Nao Direcionada

O algoritmo de Kruskal foi implementado sobre uma interpretacao nao direcionada de `DirectedGraph`.

Isso significa que:

- a AGM e calculada a partir da versao nao direcionada implicita do grafo;
- se existirem `u -> v` e `v -> u`, o algoritmo considera a melhor representacao para a aresta nao direcionada correspondente;
- se o grafo estiver desconectado nessa interpretacao, o resultado pode ser uma floresta geradora minima parcial.

### Warshall E Alcancabilidade

O algoritmo de Warshall calcula o fechamento transitivo do grafo, identificando alcancabilidade entre vertices independentemente do peso das arestas.

## Grafos Tematicos Disponiveis

### Curitiba Walk Graph

Grafo tematico de caminhada em Curitiba, com vertices nomeados e pesos derivados de tempo estimado de deslocamento.

### Solar System

Modelo cientifico do sistema solar com:

- Sol;
- planetas;
- luas;
- pesos em unidades astronomicas ou valores convertidos a partir de quilometragem orbital.

### Solar Hyperspace

Variante do grafo do sistema solar com:

- a mesma base estrutural de planetas e luas;
- rotas extras entre planetas;
- pesos abstratos para simular custo de viagem em hiperespaco.

Essa separacao foi feita para distinguir o modelo cientifico da variacao tematica de navegacao.

## Interface Do Sistema

### Console

O entrypoint principal e:

```bash
java -cp output br.edu.grafo.app.Main
```

Ao iniciar, o usuario pode escolher entre a execucao em console ou a abertura da GUI.

### GUI Swing

O entrypoint grafico dedicado e:

```bash
java -cp output br.edu.grafo.app.GraphDesktopApp
```

A GUI atual inclui:

- janela principal Swing;
- controller separado da regra de negocio;
- topbar, sidebar e status bar;
- paineis de overview, edicao, carga/salvamento, menor caminho e algoritmos;
- reutilizacao do Design System adotado como referencia do projeto EnronAnalyzer.

O `Main` textual foi mantido como fallback operacional.

## Estrutura Do Projeto

```text
GraphTasksTDEs/
|-- AGENTS.md
|-- agents/agents.md
|-- compile.sh
|-- data/
|-- docs/
|-- output/
`-- src/
    `-- br/edu/grafo/
        |-- algorithm/
        |   |-- GraphAlgorithms.java
        |   |-- KruskalAlgorithm.java
        |   `-- KruskalResult.java
        |-- application/
        |   |-- GraphApplicationService.java
        |   `-- ShortestPathResult.java
        |-- app/
        |   |-- CuritibaWalkGraphExample.java
        |   |-- ExampleGraph.java
        |   |-- GraphDesktopApp.java
        |   |-- Main.java
        |   `-- SolarSystemGraphExample.java
        |-- gui/
        |   |-- GraphGuiController.java
        |   |-- GraphMainWindow.java
        |   |-- component/
        |   |-- design/
        |   |-- panel/
        |   `-- util/
        |-- interfaces/
        |   `-- GraphConsoleUI.java
        |-- model/
        |   |-- DirectedGraph.java
        |   `-- Edge.java
        `-- util/
            |-- CuritibaWalkGraphFactory.java
            |-- GraphStorage.java
            `-- SolarSystemGraphFactory.java
```

## Principais Classes

| Classe | Responsabilidade |
| --- | --- |
| `Edge` | Representa uma aresta com destino, peso e rotulo. |
| `DirectedGraph` | Mantem vertices, informacoes e lista de adjacencia. |
| `GraphAlgorithms` | Implementa Warshall e helpers de impressao. |
| `KruskalAlgorithm` | Calcula AGM sobre a interpretacao nao direcionada do grafo. |
| `KruskalResult` | Encapsula o resultado do Kruskal. |
| `GraphApplicationService` | Orquestra os casos de uso e executa BFS, DFS, Dijkstra, Warshall e Kruskal. |
| `GraphConsoleUI` | Centraliza a interacao textual com o usuario. |
| `GraphStorage` | Salva, carrega e lista grafos em arquivos `.bin`. |
| `GraphGuiController` | Faz a ponte entre GUI e servico de aplicacao. |
| `GraphMainWindow` | Organiza a janela principal da interface Swing. |
| `Main` | Ponto de entrada principal com escolha entre console e GUI. |
| `GraphDesktopApp` | Entry point dedicado da GUI. |

## Requisitos

- Java 8 ou superior
- `javac` disponivel no ambiente
- terminal compativel com shell script para usar `compile.sh`

## Como Compilar

Script do projeto:

```bash
./compile.sh
```

O script:

- localiza todos os arquivos `.java` em `src/`;
- compila com `--release 8`;
- gera as classes em `output/`;
- exibe os comandos de execucao ao final.

Compilacao manual:

```bash
javac --release 8 -d output -sourcepath src $(find src -name "*.java")
```

## Como Executar

### Menu Principal

```bash
java -cp output br.edu.grafo.app.Main
```

### GUI

```bash
java -cp output br.edu.grafo.app.GraphDesktopApp
```

### Exemplo Geral

```bash
java -cp output br.edu.grafo.app.ExampleGraph
```

### Exemplos Tematicos

```bash
java -cp output br.edu.grafo.app.CuritibaWalkGraphExample
java -cp output br.edu.grafo.app.SolarSystemGraphExample
```

## Persistencia

O projeto usa serializacao Java para salvar e carregar grafos em arquivos `.bin`.

Responsabilidades de `GraphStorage`:

- salvar grafos;
- carregar grafos;
- listar grafos existentes;
- verificar existencia de arquivo salvo.

Arquivos binarios devem ser tratados como locais e confiaveis.

## Validacao Atual

A validacao registrada no projeto e manual.

Fluxos ja usados para verificacao:

- execucao de `ExampleGraph`;
- compilacao com `javac --release 8`;
- uso do menu textual;
- verificacao funcional da GUI inicial;
- conferencias de Warshall, save/load e grafos tematicos.

## Limitacoes E Gaps Conhecidos

- Ainda nao ha testes automatizados com JUnit.
- O projeto nao usa Maven nem Gradle.
- A politica de erro ainda e mista entre modelo, servico e persistencia.
- `GraphStorage` usa `ObjectInputStream`, portanto nao e apropriado carregar `.bin` desconhecidos sem confianca.
- `output/` pode conter artefatos antigos e nao deve ser limpo sem confirmacao.

## Politica De Idioma

Por decisao registrada no repositorio:

- a documentacao pode ficar em portugues;
- comentarios e JavaDoc podem ficar em portugues;
- codigo, nomes de arquivos, classes, metodos, pacotes e identificadores tecnicos devem permanecer em ingles.

## Documentacao Relacionada

- `START_HERE.md`
- `AGENTS.md`
- `agents/agents.md`
- `docs/design.md`
- `docs/plan.md`
- `docs/tasks.md`
- `docs/memory.md`
- `docs/knowledge/KNOWLEDGE_BASE.md`
