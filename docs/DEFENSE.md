# Defense

> Status: Active
> Authority: Tier 3 - Presentation and Study Notes
> Last Updated: 2026-05-07
> Owner: Jafte Carneiro Fagundes da Silva

## Como Usar Este Documento

Este arquivo e um roteiro de estudo para defender o projeto. Ele explica o que o sistema faz, onde cada responsabilidade esta no codigo, como os fluxos acontecem e quais pontos tecnicos podem ser perguntados.

Use junto com estes arquivos:

- `src/br/edu/grafo/model/Edge.java`
- `src/br/edu/grafo/model/DirectedGraph.java`
- `src/br/edu/grafo/application/GraphApplicationService.java`
- `src/br/edu/grafo/algorithm/GraphAlgorithms.java`
- `src/br/edu/grafo/interfaces/GraphConsoleUI.java`
- `src/br/edu/grafo/util/GraphStorage.java`
- `src/br/edu/grafo/app/Main.java`
- `src/br/edu/grafo/app/ExampleGraph.java`

## Resumo Para Defesa

O GraphTasksTDEs e uma aplicacao Java de console para criar, manipular, analisar, salvar e carregar grafos direcionados, ponderados e rotulados.

Em termos simples:

- O grafo e guardado como lista de adjacencia.
- Cada aresta tem destino, peso e rotulo opcional.
- O menu permite criar arestas, remover arestas, visualizar o grafo e executar algoritmos.
- BFS, DFS e Dijkstra ficam no servico de aplicacao.
- Warshall fica em uma classe de algoritmos estaticos.
- A persistencia salva o objeto do grafo em arquivo `.bin`.

## Mapa Mental Do Sistema

```text
Usuario
  -> Main
    -> GraphConsoleUI              # pergunta e imprime
    -> GraphApplicationService     # coordena a regra de uso
      -> DirectedGraph             # estrutura de dados
      -> GraphAlgorithms           # Warshall
      -> GraphStorage              # save/load
```

## Estrutura De Pacotes

| Pacote | Arquivo | Papel |
| --- | --- | --- |
| `model` | `Edge.java` | Modelo de uma aresta. |
| `model` | `DirectedGraph.java` | Estrutura do grafo. |
| `algorithm` | `GraphAlgorithms.java` | Warshall e impressao de matriz booleana. |
| `application` | `GraphApplicationService.java` | Casos de uso e algoritmos BFS/DFS/Dijkstra. |
| `interfaces` | `GraphConsoleUI.java` | Entrada e saida do console. |
| `util` | `GraphStorage.java` | Persistencia em `.bin`. |
| `app` | `Main.java` | Programa interativo. |
| `app` | `ExampleGraph.java` | Demonstracao manual. |

## O Que Cada Classe Faz

## `Edge`

`Edge` representa uma aresta direcionada.

Campos:

- `destination`: vertice destino.
- `weight`: peso da aresta.
- `label`: rotulo opcional.

Construtores:

```java
new Edge(destination, weight)
new Edge(destination, weight, label)
```

Como a origem nao fica dentro de `Edge`, ela e inferida pela lista onde a aresta esta guardada. Se `adjacencyList[0]` contem uma `Edge` com `destination = 2`, isso significa que existe a aresta `0 -> 2`.

Ponto importante para defesa:

- `Edge` implementa `Serializable` para permitir salvar o grafo inteiro em arquivo.
- A classe possui setters, entao ela nao e imutavel.
- `toString()` formata a aresta para aparecer nas listas impressas.

## `DirectedGraph`

`DirectedGraph` e a estrutura central do projeto.

Campos principais:

```java
private List<Edge>[] adjacencyList;
private String[] vertexInformation;
private int numVertices;
```

### Como O Grafo E Criado

No construtor:

```java
public DirectedGraph(int numVertices) {
    this.numVertices = numVertices;
    this.adjacencyList = new ArrayList[numVertices];
    this.vertexInformation = new String[numVertices];

    for (int i = 0; i < numVertices; i++) {
        adjacencyList[i] = new ArrayList<>();
        vertexInformation[i] = "";
    }
}
```

O que acontece:

1. Guarda a quantidade de vertices.
2. Cria um array de listas.
3. Para cada vertice, cria uma lista vazia de arestas.
4. Cria tambem um vetor de informacoes textuais dos vertices.

### Como Uma Aresta E Adicionada

Metodo principal:

```java
createEdge(int origin, int destination, double weight, String label)
```

Fluxo:

1. Verifica se `origin` e `destination` sao vertices validos.
2. Percorre a lista `adjacencyList[origin]`.
3. Se ja existe uma aresta para o mesmo destino, imprime aviso e nao adiciona.
4. Caso contrario, cria `new Edge(destination, weight, label)`.
5. Adiciona essa aresta na lista de saida da origem.

Por isso o grafo nao aceita duas arestas iguais da mesma origem para o mesmo destino.

### Como Uma Aresta E Removida

Metodo:

```java
removeEdge(int origin, int destination)
```

Fluxo:

1. Valida os vertices.
2. Usa `removeIf` para remover arestas cujo destino seja igual a `destination`.
3. Se nada foi removido, imprime aviso.

### Como Consultar Se Existe Aresta

Metodo:

```java
hasEdge(int origin, int destination)
```

Ele percorre a lista de arestas que saem de `origin` e verifica se alguma tem `destination`.

### Como Obter As Adjacencias

Metodo:

```java
getAdjacencies(int vertex)
```

Ele retorna uma copia da lista:

```java
return new ArrayList<>(adjacencyList[vertex]);
```

Ponto de defesa:

- Isso evita que codigo externo altere diretamente a lista interna do grafo.

### Metodos Legados

No final de `DirectedGraph` existem metodos em portugues/deprecated, como:

- `cria_adjacencia`
- `remove_adjacencia`
- `imprime_adjacencias`
- `existeAresta`
- `getAresta`

Eles chamam os metodos novos em ingles. Existem para compatibilidade com codigo anterior, mas a API preferida e a em ingles.

## `GraphApplicationService`

Essa classe faz a ponte entre interface e dominio. Ela segura o grafo atual em:

```java
private DirectedGraph grafo;
```

Responsabilidade:

- Criar grafo.
- Adicionar/remover arestas.
- Executar BFS, DFS, Dijkstra e Warshall.
- Salvar/carregar usando `GraphStorage`.

Ponto de arquitetura:

- A UI nao precisa saber como BFS funciona.
- O `Main` nao precisa conhecer detalhes da estrutura de dados.
- O servico centraliza o caso de uso.

### Criacao Do Grafo

```java
public void createGraph(int numVertices) {
    this.grafo = new DirectedGraph(numVertices);

    for (int i = 0; i < numVertices; i++) {
        grafo.setInformation(i, "V" + i);
    }
}
```

Ele cria o grafo e atribui um nome padrao para cada vertice: `V0`, `V1`, `V2`, etc.

## Algoritmos

## BFS

Metodo:

```java
executeBFS(int sourceVertex)
```

Objetivo:

- Visitar vertices por camadas, primeiro os vizinhos mais proximos da origem.

Estruturas usadas:

- `boolean[] visited`: marca quem ja foi visitado.
- `Queue<Integer> queue`: controla a ordem de visita.
- `List<Integer> result`: guarda a ordem final.

Fluxo:

1. Valida o vertice de origem.
2. Marca a origem como visitada.
3. Coloca a origem na fila.
4. Enquanto a fila nao estiver vazia:
   - Remove o primeiro vertice da fila.
   - Adiciona no resultado.
   - Percorre suas arestas de saida.
   - Para cada vizinho ainda nao visitado, marca e coloca na fila.

Trecho essencial:

```java
queue.add(sourceVertex);
visited[sourceVertex] = true;

while (!queue.isEmpty()) {
    int v = queue.poll();
    result.add(v);

    for (Edge edge : grafo.getAdjacencies(v)) {
        int neighbor = edge.getDestination();
        if (!visited[neighbor]) {
            visited[neighbor] = true;
            queue.add(neighbor);
        }
    }
}
```

Complexidade:

- Tempo: O(V+E)
- Espaco: O(V)

## DFS

Metodo:

```java
executeDFS(int sourceVertex)
```

Objetivo:

- Visitar o grafo indo o mais fundo possivel antes de voltar.

Estruturas usadas:

- `boolean[] visited`
- `List<Integer> result`
- Recursao em `dfsRecursive`

Fluxo:

1. Valida a origem.
2. Chama `dfsRecursive`.
3. A recursao marca o vertice, adiciona no resultado e visita cada vizinho ainda nao visitado.

Trecho essencial:

```java
private void dfsRecursive(int v, boolean[] visited, List<Integer> result) {
    visited[v] = true;
    result.add(v);

    for (Edge edge : grafo.getAdjacencies(v)) {
        int neighbor = edge.getDestination();
        if (!visited[neighbor]) {
            dfsRecursive(neighbor, visited, result);
        }
    }
}
```

Complexidade:

- Tempo: O(V+E)
- Espaco: O(V), por causa do vetor e da pilha de recursao.

## Dijkstra

Metodo:

```java
executeDijkstra(int sourceVertex)
```

Objetivo:

- Calcular menor custo da origem ate todos os outros vertices.

Estruturas usadas:

- `double[] distances`: melhor distancia conhecida.
- `boolean[] visited`: vertices ja processados.
- `PriorityQueue<PriorityPair>`: sempre pega o vertice com menor distancia atual.

Fluxo:

1. Inicializa todas as distancias com infinito.
2. Define `distances[sourceVertex] = 0`.
3. Coloca a origem na fila de prioridade.
4. Enquanto a fila nao estiver vazia:
   - Remove o par com menor distancia.
   - Se ja foi visitado, ignora.
   - Marca como visitado.
   - Para cada aresta de saida, calcula uma distancia candidata.
   - Se a candidata for melhor, atualiza e adiciona na fila.

Trecho essencial:

```java
if (distances[u] + weight < distances[v]) {
    distances[v] = distances[u] + weight;
    pq.add(new PriorityPair(distances[v], v));
}
```

Complexidade:

- Tempo: O((V+E) log V)
- Espaco: O(V)

Ponto de defesa:

- Dijkstra funciona corretamente com pesos nao negativos. Se houver pesos negativos, o algoritmo pode produzir resultado incorreto.

## Warshall

Metodo:

```java
GraphAlgorithms.warshall(DirectedGraph graph)
```

Objetivo:

- Calcular alcancabilidade entre todos os pares de vertices.
- A resposta e uma matriz booleana.
- `reach[i][j] = true` significa que existe algum caminho de `i` ate `j`.

Fluxo:

1. Cria uma matriz `boolean[n][n]`.
2. Marca `true` onde existe aresta direta.
3. Marca a diagonal como `true`, pois cada vertice alcanca a si mesmo.
4. Usa tres loops `k`, `i`, `j`.
5. Se `i` alcanca `k` e `k` alcanca `j`, entao `i` alcanca `j`.

Trecho essencial:

```java
reach[i][j] = reach[i][j] || (reach[i][k] && reach[k][j]);
```

Complexidade:

- Tempo: O(V^3)
- Espaco: O(V^2)

Diferença entre Warshall e Dijkstra:

- Dijkstra calcula menor custo a partir de uma origem.
- Warshall calcula se existe caminho entre todos os pares.
- Dijkstra retorna `double[]`.
- Warshall retorna `boolean[][]`.

## `GraphConsoleUI`

Essa classe concentra as interacoes com o usuario.

Ela:

- Mostra o menu.
- Pergunta origem, destino, peso e rotulo.
- Mostra matriz de adjacencia.
- Mostra lista de adjacencia.
- Mostra resultados de BFS, DFS, Dijkstra e Warshall.
- Mostra informacoes do grafo.
- Pede nome de arquivo para salvar/carregar.

Exemplo de separacao:

- `GraphConsoleUI` pergunta o que o usuario quer.
- `GraphApplicationService` executa.
- `DirectedGraph` guarda os dados.

Ponto de defesa:

- Isso evita colocar toda a logica dentro do `Main`.

## `Main`

`Main` e o ponto de entrada do menu interativo.

Fluxo geral:

1. Cria `GraphConsoleUI`.
2. Cria `GraphApplicationService`.
3. Tenta inicializar o grafo.
4. Entra no loop de menu.
5. Cada opcao chama um handler especifico.
6. Fecha o scanner no final.

### Inicializacao

Metodo:

```java
initializeGraph(ui, service)
```

Ele verifica se existe `grafo.bin`. Se existir, pergunta se o usuario quer carregar. Se nao carregar, cria um novo grafo.

### Menu

Metodo:

```java
handleMenuOption(ui, service)
```

Mapeamento:

| Opcao | Acao |
| --- | --- |
| `1` | Adicionar aresta |
| `2` | Criar novo grafo |
| `3` | Carregar grafo Curitiba Walk |
| `4` | Carregar grafo Sistema Solar |
| `5` | Carregar grafo Solar Hyperspace |
| `6` | Carregar grafo de arquivo .bin |
| `7` | Remover aresta |
| `8` | Salvar grafo em .bin |
| `9` | Mostrar lista de adjacencia |
| `10` | Mostrar matriz de adjacencia |
| `11` | Informacoes do grafo |
| `12` | Mostrar nomes dos vertices |
| `13` | BFS - Busca em Largura |
| `14` | DFS - Busca em Profundidade |
| `15` | Dijkstra - Menor Caminho |
| `16` | Kruskal - Arvore Geradora Minima |
| `17` | Warshall - Matriz de Alcancabilidade |
| `0` | Sair |

Ponto de defesa:

- `Main` nao implementa os algoritmos; ele so direciona a execucao.

## `GraphStorage`

Responsavel pela persistencia.

Campo:

```java
private static final String DATA_DIR = "data";
```

O bloco `static` cria o diretorio `data` se ele nao existir.

### Salvar

Metodo:

```java
saveGraph(DirectedGraph graph, String filename)
```

Fluxo:

1. Monta o caminho `data/<filename>.bin`.
2. Abre `ObjectOutputStream`.
3. Escreve o objeto `DirectedGraph`.
4. Retorna `true` se funcionou.
5. Retorna `false` se houve erro de I/O.

### Carregar

Metodo:

```java
loadGraph(String filename)
```

Fluxo:

1. Monta o caminho `data/<filename>.bin`.
2. Abre `ObjectInputStream`.
3. Le o objeto.
4. Faz cast para `DirectedGraph`.
5. Retorna o grafo ou `null` se falhar.

Ponto de seguranca para defesa:

- Desserializacao Java deve ser usada apenas com arquivos locais/confiaveis.
- Nao e recomendavel carregar `.bin` desconhecido vindo da internet.

## `ExampleGraph`

`ExampleGraph` e um roteiro automatico de demonstracao.

Ele:

1. Cria um grafo com 5 vertices.
2. Adiciona 8 arestas.
3. Define informacoes nos vertices.
4. Imprime matriz e lista.
5. Lista adjacentes.
6. Remove uma aresta.
7. Testa remocao inexistente.
8. Testa validacoes.
9. Testa aresta duplicada.
10. Cria um grafo separado para Warshall.
11. Mostra matriz direta e matriz de alcancabilidade.

Como executar:

```bash
java -cp output br.edu.grafo.app.ExampleGraph
```

## Fluxos Para Explicar Na Defesa

## Fluxo 1: Criar Grafo

```text
Main -> GraphConsoleUI.askNumVertices()
Main -> GraphApplicationService.createGraph(n)
GraphApplicationService -> new DirectedGraph(n)
DirectedGraph -> cria listas vazias
```

Resposta curta:

> O usuario informa a quantidade de vertices; o servico cria um `DirectedGraph`; o construtor inicializa uma lista de adjacencia vazia para cada vertice.

## Fluxo 2: Adicionar Aresta

```text
Main -> GraphConsoleUI.askEdgeInput()
Main -> GraphApplicationService.addEdge()
GraphApplicationService -> DirectedGraph.createEdge()
DirectedGraph -> valida vertices, verifica duplicata, adiciona Edge
```

Resposta curta:

> A aresta e guardada na lista da origem. O objeto `Edge` guarda destino, peso e rotulo.

## Fluxo 3: Executar BFS

```text
Main -> handleBFS()
GraphConsoleUI -> origem
GraphApplicationService.executeBFS(origem)
GraphConsoleUI.displayBFSResult(resultado)
```

Resposta curta:

> BFS usa fila para visitar primeiro os vertices mais proximos da origem.

## Fluxo 4: Executar Warshall

```text
Main -> handleWarshall()
GraphApplicationService.executeWarshall()
GraphAlgorithms.warshall(grafo)
GraphConsoleUI.displayWarshallMatrix()
GraphConsoleUI.displayWarshallStatistics()
```

Resposta curta:

> Warshall transforma as arestas diretas em uma matriz de alcancabilidade, depois usa vertices intermediarios para descobrir caminhos indiretos.

## Fluxo 5: Salvar E Carregar

Salvar:

```text
Main -> GraphConsoleUI.askSaveFilename()
GraphApplicationService.saveGraph()
GraphStorage.saveGraph()
data/<nome>.bin
```

Carregar:

```text
Main -> GraphStorage.listGraphs()
GraphConsoleUI.displayLoadOptions()
GraphStorage.loadGraph()
GraphApplicationService.setGraph()
```

## Principios De Projeto Que Voce Pode Citar

## Separacao De Responsabilidades

- `DirectedGraph` cuida da estrutura do grafo.
- `GraphApplicationService` cuida dos casos de uso.
- `GraphConsoleUI` cuida da interacao com usuario.
- `GraphStorage` cuida de persistencia.
- `GraphAlgorithms` cuida de algoritmo reutilizavel.

## Encapsulamento

- Os campos de `DirectedGraph` sao privados.
- `getAdjacencies` retorna copia da lista.
- O usuario do grafo usa metodos publicos em vez de acessar a estrutura interna.

## Reuso

- `GraphAlgorithms.warshall` pode ser usado pelo menu e pelo exemplo.
- `GraphStorage` pode salvar qualquer `DirectedGraph`.

## Manutenibilidade

- Se mudar a forma de imprimir, mexe em `GraphConsoleUI`.
- Se mudar Warshall, mexe em `GraphAlgorithms`.
- Se mudar persistencia, mexe em `GraphStorage`.

## Perguntas Provaveis E Respostas

### Por que lista de adjacencia?

Porque e eficiente para grafos esparsos. Em vez de guardar uma matriz `V x V`, guardamos apenas as arestas existentes. Isso economiza memoria quando ha poucos relacionamentos.

### Qual a diferenca entre matriz de adjacencia e lista de adjacencia?

- Matriz: tabela `V x V`, rapida para verificar se existe aresta, mas ocupa O(V^2).
- Lista: cada vertice guarda seus vizinhos, ocupa O(V+E), melhor para grafos esparsos.

### O grafo e direcionado?

Sim. Se existe `0 -> 1`, isso nao significa que existe `1 -> 0`. Para ter os dois sentidos, duas arestas precisam ser criadas.

### O grafo e ponderado?

Sim. Cada `Edge` tem `weight`, usado por Dijkstra.

### O grafo e rotulado?

Sim. `Edge` tem `label`, e `DirectedGraph` tambem permite guardar informacao textual de vertices.

### Onde BFS, DFS e Dijkstra estao?

Em `GraphApplicationService`.

### Onde Warshall esta?

Em `GraphAlgorithms`.

### Por que Warshall retorna boolean?

Porque ele calcula alcancabilidade, nao distancia. A pergunta e: "existe caminho?", entao `true` ou `false` basta.

### Por que Dijkstra usa `PriorityQueue`?

Para sempre processar primeiro o vertice com menor distancia conhecida. Isso melhora a eficiencia em relacao a procurar manualmente o menor valor a cada passo.

### O que acontece com vertice invalido?

Depende da camada:

- Em BFS/DFS/Dijkstra, `GraphApplicationService` lanca `IllegalArgumentException`.
- Em varios metodos de `DirectedGraph`, o codigo imprime mensagem e retorna sem alterar o grafo.

Esse e um ponto que poderia ser padronizado em melhoria futura.

### Como o sistema salva o grafo?

Usando serializacao Java. `DirectedGraph` e `Edge` implementam `Serializable`, entao `GraphStorage` consegue gravar o objeto em arquivo `.bin`.

### Existe banco de dados?

Nao. A persistencia e local em arquivos `.bin` dentro de `data/`.

### Existe teste automatizado?

Nao. A validacao atual e manual via `ExampleGraph`. Isso pode ser citado como melhoria futura.

## Pontos Fortes Para Defender

- Separacao clara entre UI, servico, modelo, algoritmo e persistencia.
- Uso de lista de adjacencia, apropriada para grafos esparsos.
- Algoritmos classicos implementados de forma legivel.
- Menu interativo e exemplo automatico.
- Persistencia simples sem dependencias externas.
- Compatibilidade com Java 8+.

## Limitacoes Honestamente Assumidas

- Nao ha testes automatizados.
- A politica de erro nao e uniforme.
- `Edge` possui setters, entao nao e imutavel.
- `GraphStorage` usa desserializacao Java, adequada para arquivo local confiavel, mas nao para entrada desconhecida.
- `GraphConsoleUI.displayGraphInfo` calcula densidade como `E / (V * (V - 1))`; para grafo com 1 vertice, isso pode gerar divisao por zero conceitual.

## Roteiro De Apresentacao Em 2 Minutos

1. "Este projeto implementa grafos direcionados, ponderados e rotulados em Java."
2. "A estrutura principal e `DirectedGraph`, que usa lista de adjacencia."
3. "`Edge` representa destino, peso e rotulo."
4. "O menu esta em `Main` e a entrada/saida em `GraphConsoleUI`."
5. "A regra de uso fica em `GraphApplicationService`, onde estao BFS, DFS e Dijkstra."
6. "Warshall esta separado em `GraphAlgorithms`, porque e algoritmo reutilizavel."
7. "A persistencia esta em `GraphStorage`, usando arquivos `.bin`."
8. "A demonstracao pode ser executada com `ExampleGraph`."

## Roteiro De Apresentacao Em 5 Minutos

1. Mostrar estrutura de pacotes.
2. Explicar `DirectedGraph` e lista de adjacencia.
3. Explicar `Edge`.
4. Explicar fluxo do menu.
5. Explicar BFS/DFS/Dijkstra.
6. Explicar Warshall com matriz booleana.
7. Explicar save/load.
8. Mostrar limitações e melhorias futuras.

## Comandos Uteis

Compilar:

```bash
./compile.sh
```

Executar menu:

```bash
java -cp output br.edu.grafo.app.Main
```

Executar exemplo:

```bash
java -cp output br.edu.grafo.app.ExampleGraph
```
