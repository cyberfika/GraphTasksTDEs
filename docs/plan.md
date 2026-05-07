# Plan

> Status: Completed
> Authority: Tier 3 - Working Document
> Last Updated: 2026-05-07
> Owner: Jafte Carneiro Fagundes da Silva

## Objetivo

Registrar o plano concluido do TDE 2: Warshall, matriz de alcancabilidade e persistencia de grafos em `.bin`.

## Escopo

Incluido:

- Implementacao de Warshall em `GraphAlgorithms`.
- Exibicao de matriz booleana e estatisticas de alcancabilidade.
- Integracao com menu via `GraphApplicationService`.
- Exemplo manual em `ExampleGraph`.
- Persistencia local via `GraphStorage`.

Fora do escopo:

- Testes automatizados.
- Maven, Gradle ou JUnit.
- Refatoracao de comportamento de validacao.
- Limpeza de `output/`.

## Assumptions

1. O projeto deve permanecer compativel com Java 8+.
2. Vertices sao indexados de `0` a `numVertices - 1`.
3. Alcancabilidade considera existencia de caminho, independentemente de peso.
4. Autoalcancabilidade e considerada verdadeira em Warshall.
5. Documentacao pode ficar em portugues; identificadores de codigo permanecem em ingles.

## Arquitetura Impactada

- `GraphAlgorithms`: Warshall e helpers de impressao.
- `GraphApplicationService`: orquestracao dos algoritmos e operacoes do grafo.
- `GraphStorage`: persistencia por serializacao Java.
- `ExampleGraph`: exemplo manual de validacao.

## Estrategia De Implementacao

Implementacao atual:

1. `GraphAlgorithms.warshall(DirectedGraph graph)` cria uma matriz `boolean[][]`.
2. A matriz inicial recebe as arestas diretas existentes.
3. A diagonal e marcada como `true`.
4. Os tres loops de Warshall calculam o fechamento transitivo.
5. `ExampleGraph` exibe matriz inicial, matriz final e verificacoes especificas.

## Estrategia De Teste

Validacao atual:

- Execucao manual de `java -cp output br.edu.grafo.app.ExampleGraph`.
- Compilacao direta com o `javac.exe` configurado no projeto usando `--release 8`.

Gap conhecido:

- Nao ha testes automatizados.
- Nao foi introduzido JUnit nesta tarefa para evitar mudanca de estrutura e dependencia.
- A compilacao com JDK moderno em modo `--release 8` emite warnings de opcao obsoleta, mas gera bytecode executavel no Java 8.

## Riscos

- `GraphStorage` usa `ObjectInputStream`; arquivos `.bin` devem ser locais e confiaveis.
- `output/` pode conter classes antigas; limpar esse diretorio exige confirmacao separada.
- A politica de erro e mista entre `DirectedGraph`, `GraphApplicationService` e `GraphStorage`.

## Acceptance Criteria

- [x] Warshall implementado em `GraphAlgorithms`.
- [x] Menu integrado via `GraphApplicationService`.
- [x] Exemplo manual em `ExampleGraph`.
- [x] Save/load local via `GraphStorage`.
- [x] Documentacao alinhada aos nomes reais do codigo.
- [ ] Testes automatizados adicionados.
- [x] Compilacao verificada no ambiente atual com `javac.exe --release 8`.
- [x] Execucao manual verificada com Java 8.
