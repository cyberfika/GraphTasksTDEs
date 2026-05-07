package br.edu.grafo.app;

import br.edu.grafo.application.*;
import br.edu.grafo.interfaces.*;
import br.edu.grafo.model.*;
import br.edu.grafo.util.*;
import java.util.*;

/**
 * Ponto de entrada para o sistema de grafos direcionados.
 *
 * <h2>Descrição</h2>
 * Inicializa a aplicação e delega para as camadas de UI e serviço.
 * Esta classe tem responsabilidade mínima: inicialização e loop principal.
 *
 * <h2>Como Executar</h2>
 * <pre>{@code
 * java -cp output br.edu.grafo.app.Main
 * }</pre>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 2.0
 * @see GraphApplicationService
 * @see GraphConsoleUI
 */
public class Main {
    public static void main(String[] args) {
        GraphConsoleUI ui = new GraphConsoleUI();
        GraphApplicationService service = new GraphApplicationService();

        try {
            ui.displayWelcome();

            // Inicializa o grafo
            try {
                initializeGraph(ui, service);
            } catch (NoSuchElementException e) {
                // Scanner vazio (modo não-interativo ou teste)
                System.out.println("\n[Modo não-interativo detectado. Criando grafo com 5 vértices...]");
                service.createGraph(5);
            }

            if (!service.hasGraph()) {
                ui.displayOperationCanceled();
                ui.close();
                return;
            }

            // Loop principal
            boolean continueRunning = true;
            while (continueRunning) {
                try {
                    continueRunning = handleMenuOption(ui, service);
                } catch (NoSuchElementException e) {
                    // Fim de entrada - sair graciosamente
                    continueRunning = false;
                }
            }

            ui.displayGoodbye();
        } catch (Exception e) {
            System.err.println("Erro fatal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ui.close();
        }
    }

    /**
     * Inicializa o grafo: carrega existente ou cria novo.
     */
    private static void initializeGraph(GraphConsoleUI ui, GraphApplicationService service) {
        if (GraphStorage.fileExists("grafo")) {
            if (ui.askLoadSavedGraph()) {
                DirectedGraph loadedGraph = GraphStorage.loadGraph("grafo");
                if (loadedGraph != null) {
                    service.setGraph(loadedGraph);
                } else {
                    ui.displayLoadingError();
                    createNewGraph(ui, service);
                }
            } else {
                createNewGraph(ui, service);
            }
        } else {
            createNewGraph(ui, service);
        }
    }

    /**
     * Cria novo grafo com número de vértices especificado pelo usuário.
     */
    private static void createNewGraph(GraphConsoleUI ui, GraphApplicationService service) {
        int numVertices = ui.askNumVertices();
        if (numVertices > 0) {
            service.createGraph(numVertices);
        }
    }

    /**
     * Processa seleção de menu e dispatch para handler apropriado.
     *
     * @return true se usuário quer continuar, false se sair foi selecionado
     */
    private static boolean handleMenuOption(GraphConsoleUI ui, GraphApplicationService service) {
        String choice = ui.displayMenuAndGetChoice();

        switch (choice) {
            case "1":
                handleAddEdge(ui, service);
                break;
            case "2":
                handleRemoveEdge(ui, service);
                break;
            case "3":
                ui.displayAdjacencyMatrix(service.getGraph());
                break;
            case "4":
                ui.displayAdjacencyList(service.getGraph());
                break;
            case "5":
                handleBFS(ui, service);
                break;
            case "6":
                handleDFS(ui, service);
                break;
            case "7":
                handleDijkstra(ui, service);
                break;
            case "8":
                handleWarshall(ui, service);
                break;
            case "9":
                ui.displayGraphInfo(service.getGraph());
                break;
            case "10":
                handleSaveGraph(ui, service);
                break;
            case "11":
                handleLoadGraph(ui, service);
                break;
            case "0":
                return false;
            default:
                ui.displayInvalidOption();
        }

        return true;
    }

    private static void handleAddEdge(GraphConsoleUI ui, GraphApplicationService service) {
        GraphConsoleUI.EdgeInput input = ui.askEdgeInput(service.getGraph().getNumVertices());
        if (input != null) {
            service.addEdge(input.origin, input.destination, input.weight, input.label);
            if (service.getGraph().hasEdge(input.origin, input.destination)) {
                ui.displayEdgeAdded();
            }
        }
    }

    private static void handleRemoveEdge(GraphConsoleUI ui, GraphApplicationService service) {
        int maxVertex = service.getGraph().getNumVertices();
        ui.displayRemoveEdgePrompt(maxVertex);
        int origin = ui.askVertexIndex();

        if (origin >= 0) {
            System.out.print("Vértice destino (0-" + (maxVertex - 1) + "): ");
            int destination = ui.askVertexIndex();

            if (destination >= 0) {
                service.removeEdge(origin, destination);
            }
        }
    }

    private static void handleBFS(GraphConsoleUI ui, GraphApplicationService service) {
        int source = ui.askBFSSourceVertex(service.getGraph().getNumVertices());
        if (source >= 0) {
            List<Integer> visited = service.executeBFS(source);
            ui.displayBFSResult(visited);
        }
    }

    private static void handleDFS(GraphConsoleUI ui, GraphApplicationService service) {
        int source = ui.askDFSSourceVertex(service.getGraph().getNumVertices());
        if (source >= 0) {
            List<Integer> visited = service.executeDFS(source);
            ui.displayDFSResult(visited);
        }
    }

    private static void handleDijkstra(GraphConsoleUI ui, GraphApplicationService service) {
        int source = ui.askDijkstraSourceVertex(service.getGraph().getNumVertices());
        if (source >= 0) {
            double[] distances = service.executeDijkstra(source);
            ui.displayDijkstraResult(distances, source);
        }
    }

    private static void handleWarshall(GraphConsoleUI ui, GraphApplicationService service) {
        boolean[][] reachability = service.executeWarshall();
        ui.displayWarshallMatrix(reachability);
        ui.displayWarshallStatistics(reachability);
    }

    private static void handleSaveGraph(GraphConsoleUI ui, GraphApplicationService service) {
        String filename = ui.askSaveFilename();
        service.saveGraph(filename);
        ui.displayGraphSaveMessage(filename);
    }

    private static void handleLoadGraph(GraphConsoleUI ui, GraphApplicationService service) {
        String[] savedGraphs = GraphStorage.listGraphs();
        ui.displayLoadOptions(savedGraphs);

        if (savedGraphs.length == 0) {
            return;
        }

        String input = ui.getLoadInput();
        String graphName = resolveGraphName(input, savedGraphs);

        if (graphName != null && !graphName.isEmpty()) {
            if (!GraphStorage.fileExists(graphName)) {
                ui.displayFileNotFound(graphName);
                return;
            }

            DirectedGraph loadedGraph = GraphStorage.loadGraph(graphName);
            if (loadedGraph != null) {
                service.setGraph(loadedGraph);
                ui.displayGraphLoadedSuccessfully();
            }
        } else {
            ui.displayInvalidInput();
        }
    }

    /**
     * Resolve nome do grafo a partir de entrada do usuário (pode ser índice ou nome).
     */
    private static String resolveGraphName(String input, String[] savedGraphs) {
        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < savedGraphs.length) {
                return savedGraphs[index];
            }
        } catch (NumberFormatException e) {
            return input;
        }
        return null;
    }
}
