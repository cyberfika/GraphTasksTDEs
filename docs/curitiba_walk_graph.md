# Curitiba Walk Graph

> Status: Draft
> Authority: Tier 3 - Working Document
> Last Updated: 2026-05-13
> Owner: Jafte Carneiro Fagundes da Silva

## Purpose

Registrar a extracao manual do mapa `docs/walkCuritiba.jpg` para um novo grafo nao direcionado da cidade de Curitiba.

## Source

- `docs/walkCuritiba.jpg`

## Interpretation Rules

- Cada estacao tubo (circulo cinza) vira um vertice.
- Cada terminal (circulo preto) vira um vertice.
- Cada ligacao consecutiva no mapa vira uma aresta.
- As linhas de onibus foram ignoradas.
- O peso da aresta e a distancia em quilometros.
- A distancia foi calculada por:

```text
distance_km = time_minutes / 15
```

Isso decorre da velocidade media de caminhada indicada no mapa: `4 km/h`.

## Representation In The Project

O projeto atual possui apenas `DirectedGraph`. Para representar um grafo nao direcionado sem alterar a estrutura base, cada aresta foi adicionada nos dois sentidos com o mesmo peso.

Implementacao:

- `src/br/edu/grafo/util/CuritibaWalkGraphFactory.java`
- `src/br/edu/grafo/app/CuritibaWalkGraphExample.java`
- `src/br/edu/grafo/app/Main.java` com opcao dedicada de carregamento pelo menu

## Extraction Note

A leitura foi feita manualmente a partir da imagem. Os nomes dos vertices e a maior parte dos tempos estao legiveis. Algumas ligacoes centrais foram inferidas para manter conectividade entre os eixos do mapa.

## Main Corridors

- Oeste: Campina do Siqueira -> Praca Osorio -> Praca Rui Barbosa
- Norte: Passeio Publico -> Terminal Cabral
- Sul: Praca Oswaldo Cruz -> Terminal Portao
- Sudeste: UTFPR -> Terminal Hauer
- Leste: Estacao Central -> Terminal Capao da Imbuia

## Usage

Compilar:

```bash
javac -d output -sourcepath src \
  src/br/edu/grafo/model/*.java \
  src/br/edu/grafo/algorithm/*.java \
  src/br/edu/grafo/util/*.java \
  src/br/edu/grafo/application/*.java \
  src/br/edu/grafo/interfaces/*.java \
  src/br/edu/grafo/app/*.java
```

Executar exemplo:

```bash
java -cp output br.edu.grafo.app.CuritibaWalkGraphExample
```

Ou carregar pelo menu principal:

```text
13. Load Curitiba walk graph
```
