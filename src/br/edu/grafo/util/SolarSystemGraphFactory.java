package br.edu.grafo.util;

import br.edu.grafo.model.DirectedGraph;

/**
 * Fabrica variantes do grafo do sistema solar.
 *
 * Scientific graph:
 * - pesos em unidades astronomicas (AU)
 * - Sol conectado aos planetas
 * - cada planeta conectado as suas luas
 *
 * Hyperspace graph:
 * - mesma base estrutural de planetas e luas
 * - arestas extras entre planetas representando rotas de viagem
 * - pesos representam custo simplificado de rota, nao distancia fisica
 */
public final class SolarSystemGraphFactory {
    private static final double AU_IN_KM = 149_597_870.7;

    private static final String[] VERTEX_NAMES = new String[] {
            "Sun",
            "Mercury",
            "Venus",
            "Earth",
            "Moon",
            "Mars",
            "Phobos",
            "Deimos",
            "Jupiter",
            "Io",
            "Europa",
            "Ganymede",
            "Callisto",
            "Saturn",
            "Mimas",
            "Enceladus",
            "Tethys",
            "Dione",
            "Rhea",
            "Titan",
            "Iapetus",
            "Uranus",
            "Miranda",
            "Ariel",
            "Umbriel",
            "Titania",
            "Oberon",
            "Neptune",
            "Triton",
            "Proteus",
            "Nereid"
    };

    private SolarSystemGraphFactory() {
    }

    /**
     * Mantido por compatibilidade com chamadas existentes.
     */
    public static DirectedGraph createGraph() {
        return createScientificGraph();
    }

    public static DirectedGraph createScientificGraph() {
        DirectedGraph graph = createBaseGraph();
        connectSunToPlanetsWithAstronomicalUnits(graph);
        connectPlanetsToMoonsWithAstronomicalUnits(graph);
        return graph;
    }

    public static DirectedGraph createHyperspaceGraph() {
        DirectedGraph graph = createBaseGraph();
        connectSunToPlanetsWithAstronomicalUnits(graph);
        connectPlanetsToMoonsWithAstronomicalUnits(graph);
        connectPlanetHyperspaceRoutes(graph);
        return graph;
    }

    private static DirectedGraph createBaseGraph() {
        DirectedGraph graph = new DirectedGraph(VERTEX_NAMES.length);
        for (int i = 0; i < VERTEX_NAMES.length; i++) {
            graph.setInformation(i, VERTEX_NAMES[i]);
        }
        return graph;
    }

    private static void connectSunToPlanetsWithAstronomicalUnits(DirectedGraph graph) {
        connectUndirected(graph, 0, 1, 0.3871, "Sun - Mercury");
        connectUndirected(graph, 0, 2, 0.7233, "Sun - Venus");
        connectUndirected(graph, 0, 3, 1.0000, "Sun - Earth");
        connectUndirected(graph, 0, 5, 1.5237, "Sun - Mars");
        connectUndirected(graph, 0, 8, 5.2038, "Sun - Jupiter");
        connectUndirected(graph, 0, 13, 9.5826, "Sun - Saturn");
        connectUndirected(graph, 0, 21, 19.1913, "Sun - Uranus");
        connectUndirected(graph, 0, 27, 30.0700, "Sun - Neptune");
    }

    private static void connectPlanetsToMoonsWithAstronomicalUnits(DirectedGraph graph) {
        connectUndirected(graph, 3, 4, kmToAu(384_400.0), "Earth - Moon");

        connectUndirected(graph, 5, 6, kmToAu(9_376.0), "Mars - Phobos");
        connectUndirected(graph, 5, 7, kmToAu(23_463.2), "Mars - Deimos");

        connectUndirected(graph, 8, 9, kmToAu(421_700.0), "Jupiter - Io");
        connectUndirected(graph, 8, 10, kmToAu(671_034.0), "Jupiter - Europa");
        connectUndirected(graph, 8, 11, kmToAu(1_070_412.0), "Jupiter - Ganymede");
        connectUndirected(graph, 8, 12, kmToAu(1_882_709.0), "Jupiter - Callisto");

        connectUndirected(graph, 13, 14, kmToAu(185_539.0), "Saturn - Mimas");
        connectUndirected(graph, 13, 15, kmToAu(238_037.0), "Saturn - Enceladus");
        connectUndirected(graph, 13, 16, kmToAu(294_672.0), "Saturn - Tethys");
        connectUndirected(graph, 13, 17, kmToAu(377_415.0), "Saturn - Dione");
        connectUndirected(graph, 13, 18, kmToAu(527_068.0), "Saturn - Rhea");
        connectUndirected(graph, 13, 19, kmToAu(1_221_870.0), "Saturn - Titan");
        connectUndirected(graph, 13, 20, kmToAu(3_560_820.0), "Saturn - Iapetus");

        connectUndirected(graph, 21, 22, kmToAu(129_390.0), "Uranus - Miranda");
        connectUndirected(graph, 21, 23, kmToAu(191_020.0), "Uranus - Ariel");
        connectUndirected(graph, 21, 24, kmToAu(266_300.0), "Uranus - Umbriel");
        connectUndirected(graph, 21, 25, kmToAu(435_910.0), "Uranus - Titania");
        connectUndirected(graph, 21, 26, kmToAu(583_520.0), "Uranus - Oberon");

        connectUndirected(graph, 27, 28, kmToAu(354_759.0), "Neptune - Triton");
        connectUndirected(graph, 27, 29, kmToAu(117_647.0), "Neptune - Proteus");
        connectUndirected(graph, 27, 30, kmToAu(5_513_818.0), "Neptune - Nereid");
    }

    /**
     * Rotas simplificadas inspiradas em hiperespaco:
     * ligacoes entre planetas com custo de viagem abstrato.
     */
    private static void connectPlanetHyperspaceRoutes(DirectedGraph graph) {
        connectUndirected(graph, 1, 2, 1.0, "Hyperspace: Mercury - Venus");
        connectUndirected(graph, 2, 3, 1.0, "Hyperspace: Venus - Earth");
        connectUndirected(graph, 3, 5, 1.0, "Hyperspace: Earth - Mars");
        connectUndirected(graph, 5, 8, 2.0, "Hyperspace: Mars - Jupiter");
        connectUndirected(graph, 8, 13, 2.0, "Hyperspace: Jupiter - Saturn");
        connectUndirected(graph, 13, 21, 3.0, "Hyperspace: Saturn - Uranus");
        connectUndirected(graph, 21, 27, 3.0, "Hyperspace: Uranus - Neptune");

        connectUndirected(graph, 3, 8, 2.0, "Hyperspace: Earth - Jupiter");
        connectUndirected(graph, 5, 13, 3.0, "Hyperspace: Mars - Saturn");
        connectUndirected(graph, 8, 21, 3.0, "Hyperspace: Jupiter - Uranus");
        connectUndirected(graph, 13, 27, 4.0, "Hyperspace: Saturn - Neptune");
        connectUndirected(graph, 2, 5, 2.0, "Hyperspace: Venus - Mars");
    }

    private static void connectUndirected(DirectedGraph graph, int vertexA, int vertexB, double weight, String label) {
        graph.createEdge(vertexA, vertexB, weight, label);
        graph.createEdge(vertexB, vertexA, weight, label);
    }

    private static double kmToAu(double distanceKm) {
        return distanceKm / AU_IN_KM;
    }
}
