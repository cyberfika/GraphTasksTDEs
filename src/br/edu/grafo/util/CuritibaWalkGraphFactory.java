package br.edu.grafo.util;

import br.edu.grafo.model.DirectedGraph;

/**
 * Fabrica um grafo de caminhada de Curitiba a partir do mapa em docs/walkCuritiba.jpg.
 *
 * O projeto usa {@link DirectedGraph}; para representar um grafo nao direcionado,
 * cada conexao e adicionada nos dois sentidos com o mesmo peso.
 */
public final class CuritibaWalkGraphFactory {

    private CuritibaWalkGraphFactory() {
    }

    public static DirectedGraph createGraph() {
        String[] vertexNames = new String[] {
                "Terminal Campina do Siqueira",
                "Gastao Camara",
                "Bigorrilho",
                "Bruno Filgueira",
                "Praca da Ucrania",
                "Pres. Taunay",
                "Brigadeiro Franco",
                "Visconde de Nacar",
                "Praca Osorio",
                "Praca Rui Barbosa",
                "Catedral",
                "Praca Carlos Gomes",
                "Passeio Publico",
                "Maria Clara",
                "Constantino Marochi",
                "Moyses Marcondes",
                "Bom Jesus",
                "Terminal Cabral",
                "Praca Oswaldo Cruz",
                "Cel. Dulcidio",
                "Bento Viana",
                "Silva Jardim",
                "Petit Carneiro",
                "Dom Pedro",
                "Sebastiao Parana",
                "Vital Brasil",
                "Morretes",
                "Carlos Dietzsch",
                "Terminal Portao",
                "UTFPR",
                "Getulio Vargas",
                "Almirante Goncalves",
                "Cons. Dantas",
                "Joao Viana Seiler",
                "T.R.E.",
                "Parolin",
                "Ferrovia",
                "Rocha Pombo",
                "Padua Fleury",
                "Terminal Hauer",
                "Estacao Central",
                "Eufrasio Correia",
                "Mariano Torres",
                "Rodoferroviaria",
                "V. Capanema",
                "Hospital Cajuru",
                "Jardim Botanico",
                "Urbano Lopes",
                "Amaro Prestes",
                "Profa. Maria Aguiar",
                "Terminal Capao da Imbuia"
        };

        DirectedGraph graph = new DirectedGraph(vertexNames.length);
        for (int i = 0; i < vertexNames.length; i++) {
            graph.setInformation(i, vertexNames[i]);
        }

        // Eixo oeste.
        connectUndirected(graph, 0, 1, distanceFromMinutes(4), "Campina do Siqueira - Gastao Camara");
        connectUndirected(graph, 1, 2, distanceFromMinutes(4), "Gastao Camara - Bigorrilho");
        connectUndirected(graph, 2, 3, distanceFromMinutes(5), "Bigorrilho - Bruno Filgueira");
        connectUndirected(graph, 3, 4, distanceFromMinutes(4), "Bruno Filgueira - Praca da Ucrania");
        connectUndirected(graph, 4, 5, distanceFromMinutes(6), "Praca da Ucrania - Pres. Taunay");
        connectUndirected(graph, 5, 6, distanceFromMinutes(3), "Pres. Taunay - Brigadeiro Franco");
        connectUndirected(graph, 6, 7, distanceFromMinutes(3), "Brigadeiro Franco - Visconde de Nacar");
        connectUndirected(graph, 7, 8, distanceFromMinutes(4), "Visconde de Nacar - Praca Osorio");
        connectUndirected(graph, 8, 9, distanceFromMinutes(9), "Praca Osorio - Praca Rui Barbosa");

        // Ligacoes centrais inferidas a partir do mapa.
        connectUndirected(graph, 9, 10, distanceFromMinutes(7), "Praca Rui Barbosa - Catedral");
        connectUndirected(graph, 10, 11, distanceFromMinutes(7), "Catedral - Praca Carlos Gomes");
        connectUndirected(graph, 11, 12, distanceFromMinutes(9), "Praca Carlos Gomes - Passeio Publico");
        connectUndirected(graph, 10, 18, distanceFromMinutes(7), "Catedral - Praca Oswaldo Cruz");
        connectUndirected(graph, 10, 29, distanceFromMinutes(9), "Catedral - UTFPR");
        connectUndirected(graph, 12, 40, distanceFromMinutes(6), "Passeio Publico - Estacao Central");

        // Eixo norte.
        connectUndirected(graph, 12, 13, distanceFromMinutes(8), "Passeio Publico - Maria Clara");
        connectUndirected(graph, 13, 14, distanceFromMinutes(5), "Maria Clara - Constantino Marochi");
        connectUndirected(graph, 14, 15, distanceFromMinutes(6), "Constantino Marochi - Moyses Marcondes");
        connectUndirected(graph, 15, 16, distanceFromMinutes(6), "Moyses Marcondes - Bom Jesus");
        connectUndirected(graph, 16, 17, distanceFromMinutes(8), "Bom Jesus - Terminal Cabral");

        // Eixo sul.
        connectUndirected(graph, 18, 19, distanceFromMinutes(7), "Praca Oswaldo Cruz - Cel. Dulcidio");
        connectUndirected(graph, 19, 20, distanceFromMinutes(6), "Cel. Dulcidio - Bento Viana");
        connectUndirected(graph, 20, 21, distanceFromMinutes(5), "Bento Viana - Silva Jardim");
        connectUndirected(graph, 21, 22, distanceFromMinutes(7), "Silva Jardim - Petit Carneiro");
        connectUndirected(graph, 22, 23, distanceFromMinutes(6), "Petit Carneiro - Dom Pedro");
        connectUndirected(graph, 23, 24, distanceFromMinutes(5), "Dom Pedro - Sebastiao Parana");
        connectUndirected(graph, 24, 25, distanceFromMinutes(5), "Sebastiao Parana - Vital Brasil");
        connectUndirected(graph, 25, 26, distanceFromMinutes(5), "Vital Brasil - Morretes");
        connectUndirected(graph, 26, 27, distanceFromMinutes(7), "Morretes - Carlos Dietzsch");
        connectUndirected(graph, 27, 28, distanceFromMinutes(7), "Carlos Dietzsch - Terminal Portao");

        // Eixo sudeste.
        connectUndirected(graph, 29, 30, distanceFromMinutes(9), "UTFPR - Getulio Vargas");
        connectUndirected(graph, 30, 31, distanceFromMinutes(6), "Getulio Vargas - Almirante Goncalves");
        connectUndirected(graph, 31, 32, distanceFromMinutes(5), "Almirante Goncalves - Cons. Dantas");
        connectUndirected(graph, 32, 33, distanceFromMinutes(4), "Cons. Dantas - Joao Viana Seiler");
        connectUndirected(graph, 33, 34, distanceFromMinutes(5), "Joao Viana Seiler - T.R.E.");
        connectUndirected(graph, 34, 35, distanceFromMinutes(5), "T.R.E. - Parolin");
        connectUndirected(graph, 35, 36, distanceFromMinutes(8), "Parolin - Ferrovia");
        connectUndirected(graph, 36, 37, distanceFromMinutes(6), "Ferrovia - Rocha Pombo");
        connectUndirected(graph, 37, 38, distanceFromMinutes(9), "Rocha Pombo - Padua Fleury");
        connectUndirected(graph, 38, 39, distanceFromMinutes(9), "Padua Fleury - Terminal Hauer");

        // Eixo leste.
        connectUndirected(graph, 40, 41, distanceFromMinutes(5), "Estacao Central - Eufrasio Correia");
        connectUndirected(graph, 41, 42, distanceFromMinutes(8), "Eufrasio Correia - Mariano Torres");
        connectUndirected(graph, 42, 43, distanceFromMinutes(6), "Mariano Torres - Rodoferroviaria");
        connectUndirected(graph, 43, 44, distanceFromMinutes(9), "Rodoferroviaria - V. Capanema");
        connectUndirected(graph, 44, 45, distanceFromMinutes(7), "V. Capanema - Hospital Cajuru");
        connectUndirected(graph, 45, 46, distanceFromMinutes(5), "Hospital Cajuru - Jardim Botanico");
        connectUndirected(graph, 46, 47, distanceFromMinutes(8), "Jardim Botanico - Urbano Lopes");
        connectUndirected(graph, 47, 48, distanceFromMinutes(6), "Urbano Lopes - Amaro Prestes");
        connectUndirected(graph, 48, 49, distanceFromMinutes(5), "Amaro Prestes - Profa. Maria Aguiar");
        connectUndirected(graph, 49, 50, distanceFromMinutes(5), "Profa. Maria Aguiar - Terminal Capao da Imbuia");

        return graph;
    }

    private static void connectUndirected(DirectedGraph graph, int vertexA, int vertexB, double distanceKm, String label) {
        graph.createEdge(vertexA, vertexB, distanceKm, label);
        graph.createEdge(vertexB, vertexA, distanceKm, label);
    }

    private static double distanceFromMinutes(int minutes) {
        return minutes / 15.0;
    }
}
