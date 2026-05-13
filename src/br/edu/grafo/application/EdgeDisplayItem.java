package br.edu.grafo.application;

/**
 * Objeto de transferencia de dados representando uma aresta para exibicao.
 *
 * <p>Classe imutavel (value object). Elimina a duplicacao de {@code EdgeDisplayItem}
 * que existia simultaneamente em {@code GraphConsoleUI} e {@code GraphGuiController},
 * corrigindo violacao do DRY e do SRP.</p>
 *
 * <p>Usada tanto pela camada de console quanto pela GUI para representar
 * arestas em listas e formularios de remocao.</p>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 1.0
 */
public final class EdgeDisplayItem {

    public final int origin;
    public final int destination;
    public final double weight;
    public final String label;
    public final boolean bidirectional;

    public EdgeDisplayItem(int origin, int destination, double weight, String label, boolean bidirectional) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.label = (label != null) ? label : "";
        this.bidirectional = bidirectional;
    }

    /**
     * Retorna o conector adequado conforme direcionalidade.
     * {@code "--"} para bidirecional, {@code "->"} para unidirecional.
     */
    public String connector() {
        return bidirectional ? " -- " : " -> ";
    }

    @Override
    public String toString() {
        return "V" + origin + connector() + "V" + destination
                + " | weight=" + String.format("%.2f", weight)
                + (label.isEmpty() ? "" : " [" + label + "]");
    }
}
