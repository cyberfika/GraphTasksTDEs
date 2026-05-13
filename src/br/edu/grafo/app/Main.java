package br.edu.grafo.app;

import br.edu.grafo.application.EdgeDisplayItem;
import br.edu.grafo.application.GraphApplicationService;
import br.edu.grafo.application.GraphService;
import br.edu.grafo.application.ShortestPathResult;
import br.edu.grafo.interfaces.GraphConsoleUI;
import br.edu.grafo.model.DirectedGraph;
import br.edu.grafo.util.CuritibaWalkGraphFactory;
import br.edu.grafo.util.SolarSystemGraphFactory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Ponto de entrada para o sistema de grafos.
 *
 * <p>Inicializa a aplicacao, delega modo de execucao (console ou GUI) e
 * mantém o loop principal do menu. Responsabilidade minima: inicializacao
 * e dispatch de opcoes de menu.</p>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 3.0
 * @see GraphService
 * @see GraphConsoleUI
 */
public class Main {

    public static void main(String[] args) {
        GraphConsoleUI ui = new GraphConsoleUI();

        try {
            ui.displayWelcome();

            String executionMode = ui.askExecutionMode();
            if ("2".equals(executionMode)) {
                GraphDesktopApp.main(args);
                return;
            }
            if (!"1".equals(executionMode)) {
                ui.displayInvalidOption();
                return;
            }

            GraphService service = new GraphApplicationService();

            boolean continueRunning = true;
            while (continueRunning) {
                try {
                    continueRunning = handleMenuOption(ui, service);
                } catch (NoSuchElementException e) {
                    continueRunning = false;
                }
            }

            ui.displayGoodbye();
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ui.close();
        }
    }

    private static boolean handleMenuOption(GraphConsoleUI ui, GraphService service) {
        String choice = ui.displayMenuAndGetChoice();

        switch (choice) {
            case "1":
                if (!ensureGraphLoaded(ui, service)) break;
                handleAddEdge(ui, service);
                break;
            case "2":
                createNewGraph(ui, service);
                break;
            case "3":
                service.setGraph(CuritibaWalkGraphFactory.createGraph());
                ui.displayCuritibaGraphLoadedSuccessfully();
                break;
            case "4":
                service.setGraph(SolarSystemGraphFactory.createScientificGraph());
                ui.displaySolarSystemGraphLoadedSuccessfully();
                break;
            case "5":
                service.setGraph(SolarSystemGraphFactory.createHyperspaceGraph());
                ui.displaySolarSystemHyperspaceGraphLoadedSuccessfully();
                break;
            case "6":
                handleLoadGraph(ui, service);
                break;
            case "7":
                if (!ensureGraphLoaded(ui, service)) break;
                handleRemoveEdge(ui, service);
                break;
            case "8":
                if (!ensureGraphLoaded(ui, service)) break;
                handleSaveGraph(ui, service);
                break;
            case "9":
                if (!ensureGraphLoaded(ui, service)) break;
                ui.displayAdjacencyList(service.getGraph());
                break;
            case "10":
                if (!ensureGraphLoaded(ui, service)) break;
                ui.displayAdjacencyMatrix(service.getGraph());
                break;
            case "11":
                if (!ensureGraphLoaded(ui, service)) break;
                ui.displayGraphInfo(service.getGraph());
                break;
            case "12":
                if (!ensureGraphLoaded(ui, service)) break;
                ui.displayVertexNames(service.listVertexNames());
                break;
            case "13":
                if (!ensureGraphLoaded(ui, service)) break;
                handleBFS(ui, service);
                break;
            case "14":
                if (!ensureGraphLoaded(ui, service)) break;
                handleDFS(ui, service);
                break;
            case "15":
                if (!ensureGraphLoaded(ui, service)) break;
                handleDijkstra(ui, service);
                break;
            case "16":
                if (!ensureGraphLoaded(ui, service)) break;
                handleKruskal(ui, service);
                break;
            case "17":
                if (!ensureGraphLoaded(ui, service)) break;
                handleWarshall(ui, service);
                break;
            case "0":
                return false;
            default:
                ui.displayInvalidOption();
        }

        return true;
    }

    private static boolean ensureGraphLoaded(GraphConsoleUI ui, GraphService service) {
        if (!service.hasGraph()) {
            ui.displayGraphRequired();
            return false;
        }
        return true;
    }

    private static void createNewGraph(GraphConsoleUI ui, GraphService service) {
        ui.askNumVertices().ifPresent(service::createGraph);
    }

    private static void handleAddEdge(GraphConsoleUI ui, GraphService service) {
        boolean continueAdding = true;
        while (continueAdding) {
            GraphConsoleUI.EdgeInput input = ui.askEdgeInput(service.getGraph().getNumVertices());
            if (input != null) {
                boolean created = service.addEdge(input.origin, input.destination, input.weight, input.label);
                if (created) {
                    ui.displayEdgeAdded();
                } else {
                    ui.displayEdgeDuplicate();
                }
            }
            continueAdding = ui.askContinueAddingEdges();
        }
    }

    private static void handleRemoveEdge(GraphConsoleUI ui, GraphService service) {
        List<EdgeDisplayItem> edges = service.listEdges();
        if (edges.isEmpty()) {
            ui.displayNoEdgesToRemove();
            return;
        }

        ui.displayExistingEdges(service.getGraph(), edges);
        ui.displayRemoveEdgePrompt();
        int choice = ui.askVertexIndex();
        int edgeIndex = choice - 1;

        if (edgeIndex >= 0 && edgeIndex < edges.size()) {
            EdgeDisplayItem selected = edges.get(edgeIndex);
            service.removeEdge(selected.origin, selected.destination);
            if (selected.bidirectional) {
                service.removeEdge(selected.destination, selected.origin);
            }
        } else {
            ui.displayInvalidInput();
        }
    }

    private static void handleBFS(GraphConsoleUI ui, GraphService service) {
        ui.askBFSSourceVertex(service.getGraph().getNumVertices())
                .ifPresent(source -> ui.displayBFSResult(service.executeBFS(source)));
    }

    private static void handleDFS(GraphConsoleUI ui, GraphService service) {
        ui.askDFSSourceVertex(service.getGraph().getNumVertices())
                .ifPresent(source -> ui.displayDFSResult(service.executeDFS(source)));
    }

    private static void handleDijkstra(GraphConsoleUI ui, GraphService service) {
        if (hasNamedVertices(service.getGraph())) {
            handleNamedShortestPath(ui, service);
            return;
        }
        ui.askDijkstraSourceVertex(service.getGraph().getNumVertices())
                .ifPresent(source -> ui.displayDijkstraResult(service.executeDijkstra(source), source));
    }

    private static void handleWarshall(GraphConsoleUI ui, GraphService service) {
        boolean[][] reachability = service.executeWarshall();
        ui.displayWarshallMatrix(reachability);
        ui.displayWarshallStatistics(reachability);
    }

    private static void handleKruskal(GraphConsoleUI ui, GraphService service) {
        ui.displayKruskalResult(service.executeKruskal());
    }

    private static void handleSaveGraph(GraphConsoleUI ui, GraphService service) {
        String filename = ui.askSaveFilename();
        if (service.saveGraph(filename)) {
            ui.displayGraphSaveSuccess(filename);
        } else {
            ui.displayGraphSaveError(filename);
        }
    }

    private static void handleLoadGraph(GraphConsoleUI ui, GraphService service) {
        String[] savedGraphs = service.listSavedGraphs();
        ui.displayLoadOptions(savedGraphs);

        if (savedGraphs.length == 0) {
            return;
        }

        String input = ui.getLoadInput();
        String graphName = resolveGraphName(input, savedGraphs);

        if (graphName != null && !graphName.isEmpty()) {
            if (service.loadGraph(graphName)) {
                ui.displayGraphLoadedSuccessfully();
            } else {
                ui.displayFileNotFound(graphName);
            }
        } else {
            ui.displayInvalidInput();
        }
    }

    private static void handleNamedShortestPath(GraphConsoleUI ui, GraphService service) {
        String sourceName = ui.askVertexName("Source name: ");
        String destinationName = ui.askVertexName("Destination name: ");

        int source = service.findVertexByName(sourceName);
        int destination = service.findVertexByName(destinationName);

        if (source < 0) {
            ui.displayVertexNameSuggestions(sourceName, service.findVertexNameSuggestions(sourceName));
            return;
        }
        if (destination < 0) {
            ui.displayVertexNameSuggestions(destinationName, service.findVertexNameSuggestions(destinationName));
            return;
        }

        ShortestPathResult result = service.executeShortestPath(source, destination);
        ui.displayShortestPathResult(service.getGraph(), result);
    }

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

    private static boolean hasNamedVertices(DirectedGraph graph) {
        for (int i = 0; i < graph.getNumVertices(); i++) {
            Optional<String> info = graph.getInformation(i);
            if (info.isPresent() && !info.get().equals("V" + i)) {
                return true;
            }
        }
        return false;
    }
}
