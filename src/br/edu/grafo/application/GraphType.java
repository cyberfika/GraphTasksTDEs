package br.edu.grafo.application;

/**
 * Enumeracao dos tipos de grafo suportados pela aplicacao.
 *
 * <p>Encapsula metadados de apresentacao (nome de exibicao e unidade de peso),
 * eliminando a cadeia de {@code if} em {@code weightUnit()} e corrigindo a
 * violacao do Open/Closed Principle (OCP).</p>
 *
 * <p>Para adicionar um novo tipo de grafo, basta incluir uma nova constante
 * sem modificar nenhuma classe existente.</p>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 1.0
 */
public enum GraphType {

    NONE("No graph", "units", false),
    MANUAL("Manual graph", "units", false),
    CURITIBA_WALK("Curitiba walk graph", "km", false),
    SOLAR_SYSTEM("Solar system graph", "AU", false),
    SOLAR_SYSTEM_HYPERSPACE("Solar system hyperspace graph", "route cost", false),
    USER_SAVED("", "units", true);

    private final String defaultDisplayName;
    private final String weightUnit;
    private final boolean userSaved;

    GraphType(String defaultDisplayName, String weightUnit, boolean userSaved) {
        this.defaultDisplayName = defaultDisplayName;
        this.weightUnit = weightUnit;
        this.userSaved = userSaved;
    }

    /**
     * Nome de exibicao padrao desse tipo de grafo.
     * Para {@code USER_SAVED}, o nome real vem do arquivo carregado.
     */
    public String getDefaultDisplayName() {
        return defaultDisplayName;
    }

    /**
     * Unidade de peso das arestas para esse tipo de grafo.
     */
    public String getWeightUnit() {
        return weightUnit;
    }

    /**
     * Indica se esse tipo foi salvo pelo usuario (carregado de arquivo).
     */
    public boolean isUserSaved() {
        return userSaved;
    }
}
