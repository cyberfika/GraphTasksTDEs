package br.edu.grafo.model;

import java.io.Serializable;

/**
 * Representa uma aresta (edge) direcionada em um grafo.
 *
 * <h2>Descrição</h2>
 * Uma aresta é a conexão entre dois vértices em um grafo direcionado.
 * Cada aresta possui um vértice destino, um peso (custo) e um rótulo opcional.
 *
 * <h2>Características</h2>
 * <ul>
 *   <li>Direcionada: de um vértice origem para um vértice destino</li>
 *   <li>Ponderada: possui um peso (double) que representa custo, distância, etc.</li>
 *   <li>Rotulada: pode conter um rótulo opcional (String) para documentação</li>
 *   <li>Serializável: pode ser salva em arquivo binário</li>
 * </ul>
 *
 * <h2>Exemplo de Uso</h2>
 * <pre>{@code
 * // Criar aresta do vértice origem para destino 2 com peso 5.0
 * Edge edge1 = new Edge(2, 5.0);
 *
 * // Criar aresta com rótulo
 * Edge edge2 = new Edge(3, 7.5, "important_edge");
 * }</pre>
 *
 * <h2>Notas Implementação</h2>
 * Esta classe usa a igualdade padrão de identidade de objeto ({@code Object.equals}).
 * Duas instâncias de {@code Edge} com o mesmo destino são objetos distintos.
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 2.0
 * @see DirectedGraph
 */
public class Edge implements Serializable {
    private static final long serialVersionUID = 1L;

    private int destination;
    private double weight;
    private String label;

    /**
     * Constrói uma aresta com destino, peso e rótulo.
     *
     * @param destination índice do vértice destino
     * @param weight      peso da aresta (custo, distância, etc.)
     * @param label       rótulo descritivo (pode ser vazio)
     */
    public Edge(int destination, double weight, String label) {
        this.destination = destination;
        this.weight = weight;
        this.label = label;
    }

    /**
     * Constrói uma aresta com destino e peso (sem rótulo).
     *
     * @param destination índice do vértice destino
     * @param weight      peso da aresta
     */
    public Edge(int destination, double weight) {
        this(destination, weight, "");
    }

    /**
     * Retorna o vértice destino dessa aresta.
     *
     * @return índice do vértice destino
     */
    public int getDestination() {
        return destination;
    }

    /**
     * Define o vértice destino dessa aresta.
     *
     * @param destination novo índice do vértice destino
     */
    public void setDestination(int destination) {
        this.destination = destination;
    }

    /**
     * Retorna o peso dessa aresta.
     *
     * @return peso (custo, distância, etc.)
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Define o peso dessa aresta.
     *
     * @param weight novo peso da aresta
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Retorna o rótulo dessa aresta.
     *
     * @return rótulo descritivo ou string vazia se não houver rótulo
     */
    public String getLabel() {
        return label;
    }

    /**
     * Define o rótulo dessa aresta.
     *
     * @param label novo rótulo descritivo
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Retorna representação em string desta aresta.
     *
     * Formato: [destino, peso] ou [destino, peso, rótulo] se houver rótulo.
     *
     * @return string representando a aresta
     */
    @Override
    public String toString() {
        if (label.isEmpty()) {
            return String.format("[%d, %.2f]", destination, weight);
        }
        return String.format("[%d, %.2f, %s]", destination, weight, label);
    }
}
