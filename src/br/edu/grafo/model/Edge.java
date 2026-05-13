package br.edu.grafo.model;

import java.io.Serializable;

/**
 * Representa uma aresta direcionada, ponderada e rotulada.
 *
 * <h2>Descricao</h2>
 * Uma aresta e a conexao entre dois vertices em um grafo direcionado.
 * Cada aresta possui um vertice destino, um peso e um rotulo opcional.
 *
 * <h2>Imutabilidade</h2>
 * Esta classe e imutavel (Effective Java, Item 17): todos os campos sao
 * {@code final}, nao ha setters, e o estado nao pode ser alterado apos
 * construcao. Isso garante seguranca em colecoes e elimina efeitos colaterais.
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 3.0
 * @see DirectedGraph
 */
public final class Edge implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int destination;
    private final double weight;
    private final String label;

    /**
     * Constroi uma aresta com destino, peso e rotulo.
     *
     * @param destination indice do vertice destino
     * @param weight      peso da aresta (custo, distancia, etc.)
     * @param label       rotulo descritivo (nao pode ser null; use "" se sem rotulo)
     */
    public Edge(int destination, double weight, String label) {
        this.destination = destination;
        this.weight = weight;
        this.label = (label != null) ? label : "";
    }

    /**
     * Constroi uma aresta com destino e peso (sem rotulo).
     *
     * @param destination indice do vertice destino
     * @param weight      peso da aresta
     */
    public Edge(int destination, double weight) {
        this(destination, weight, "");
    }

    /**
     * Retorna o vertice destino dessa aresta.
     */
    public int getDestination() {
        return destination;
    }

    /**
     * Retorna o peso dessa aresta.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Retorna o rotulo dessa aresta.
     * Nunca retorna null; retorna string vazia se sem rotulo.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Retorna representacao em string desta aresta.
     * Formato: {@code [destino, peso]} ou {@code [destino, peso, rotulo]}.
     */
    @Override
    public String toString() {
        if (label.isEmpty()) {
            return String.format("[%d, %.2f]", destination, weight);
        }
        return String.format("[%d, %.2f, %s]", destination, weight, label);
    }
}
