package br.edu.grafo.app;

import br.edu.grafo.application.*;
import br.edu.grafo.interfaces.*;
import br.edu.grafo.model.*;
import br.edu.grafo.algorithm.*;
import br.edu.grafo.util.*;
import java.util.*;

/**
 * Ponto de entrada para o sistema de grafos direcionados.
 *
 * <h2>Descricao</h2>
 * Inicializa a aplicacao e delega para as camadas de UI e servico.
 * Esta classe tem responsabilidade minima: inicializacao e loop principal.
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

            GraphApplicationService service = new GraphApplicationService();

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
            System.err.println("Erro fatal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ui.close();
        }
    }

    /**
     * Cria novo grafo com numero de vertices especificado pelo usuario.
     */
    private static void createNewGraph(GraphConsoleUI ui, GraphApplicationService service) {
        int numVertices = ui.askNumVertices();
        if (numVertices > 0) {
            service.createGraph(numVertices);
        }
    }

    /**
     * Processa selecao de menu e dispatch para handler apropriado.
     *
     * @return true se usuario quer continuar, false se sair foi selecionado
     */
    private static boolean handleMenuOption(GraphConsoleUI ui, GraphApplicationService service) {
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
                handleLoadCuritibaWalkGraph(ui, service);
                break;
            case "4":
                handleLoadSolarSystemGraph(ui, service);
                break;
            case "5":
                handleLoadSolarSystemHyperspaceGraph(ui, service);
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
                handleListVertexNames(ui, service);
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

    private static boolean ensureGraphLoaded(GraphConsoleUI ui, GraphApplicationService service) {
        if (!service.hasGraph()) {
            ui.displayGraphRequired();
            return false;
        }
        return true;
    }

    private static void handleAddEdge(GraphConsoleUI ui, GraphApplicationService service) {
        boolean continueAdding = true;
        while (continueAdding) {
            GraphConsoleUI.EdgeInput input = ui.askEdgeInput(service.getGraph().getNumVertices());
            if (input != null) {
                boolean edgeExistedBefore = service.getGraph().hasEdge(input.origin, input.destination);
                service.addEdge(input.origin, input.destination, input.weight, input.label);
                boolean edgeExistsAfter = service.getGraph().hasEdge(input.origin, input.destination);
                if (!edgeExistedBefore && edgeExistsAfter) {
                    ui.displayEdgeAdded();
                }
            }

            continueAdding = ui.askContinueAddingEdges();
        }
    }

    private static void handleRemoveEdge(GraphConsoleUI ui, GraphApplicationService service) {
        List<EdgeReference> edges = listExistingEdges(service.getGraph());
        if (edges.isEmpty()) {
            ui.displayNoEdgesToRemove();
            return;
        }

        ui.displayExistingEdges(service.getGraph(), toDisplayItems(edges));

        ui.displayRemoveEdgePrompt();
        int choice = ui.askVertexIndex();
        int edgeIndex = choice - 1;

        if (edgeIndex >= 0 && edgeIndex < edges.size()) {
            EdgeReference selected = edges.get(edgeIndex);
            service.removeEdge(selected.origin, selected.edge.getDestination());
            if (selected.bidirectional) {
                service.removeEdge(selected.edge.getDestination(), selected.origin);
            }
        } else {
            ui.displayInvalidInput();
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
        if (hasNamedVertices(service.getGraph())) {
            handleNamedShortestPath(ui, service);
            return;
        }

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

    private static void handleKruskal(GraphConsoleUI ui, GraphApplicationService service) {
        KruskalResult result = service.executeKruskal();
        ui.displayKruskalResult(result);
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

    private static void handleLoadCuritibaWalkGraph(GraphConsoleUI ui, GraphApplicationService service) {
        DirectedGraph curitibaGraph = CuritibaWalkGraphFactory.createGraph();
        service.setGraph(curitibaGraph);
        ui.displayCuritibaGraphLoadedSuccessfully();
    }

    private static void handleLoadSolarSystemGraph(GraphConsoleUI ui, GraphApplicationService service) {
        DirectedGraph solarSystemGraph = SolarSystemGraphFactory.createScientificGraph();
        service.setGraph(solarSystemGraph);
        ui.displaySolarSystemGraphLoadedSuccessfully();
    }

    private static void handleLoadSolarSystemHyperspaceGraph(GraphConsoleUI ui, GraphApplicationService service) {
        DirectedGraph solarSystemHyperspaceGraph = SolarSystemGraphFactory.createHyperspaceGraph();
        service.setGraph(solarSystemHyperspaceGraph);
        ui.displaySolarSystemHyperspaceGraphLoadedSuccessfully();
    }

    private static void handleNamedShortestPath(GraphConsoleUI ui, GraphApplicationService service) {
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

    private static void handleListVertexNames(GraphConsoleUI ui, GraphApplicationService service) {
        ui.displayVertexNames(service.listVertexNames());
    }

    /**
     * Resolve nome do grafo a partir de entrada do usuario (pode ser indice ou nome).
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

    private static List<EdgeReference> listExistingEdges(DirectedGraph graph) {
        List<EdgeReference> edges = new ArrayList<>();
        Set<String> processedBidirectional = new HashSet<>();

        for (int origin = 0; origin < graph.getNumVertices(); origin++) {
            for (Edge edge : graph.getAdjacencies(origin)) {
                int destination = edge.getDestination();
                boolean bidirectional = isSymmetricConnection(graph, origin, edge);

                if (bidirectional) {
                    String key = buildBidirectionalKey(origin, destination, edge);
                    if (processedBidirectional.contains(key)) {
                        continue;
                    }
                    processedBidirectional.add(key);
                }

                edges.add(new EdgeReference(origin, edge, bidirectional));
            }
        }
        return edges;
    }

    private static List<GraphConsoleUI.EdgeDisplayItem> toDisplayItems(List<EdgeReference> edges) {
        List<GraphConsoleUI.EdgeDisplayItem> displayItems = new ArrayList<>();
        for (EdgeReference edgeReference : edges) {
            displayItems.add(new GraphConsoleUI.EdgeDisplayItem(
                    edgeReference.origin,
                    edgeReference.edge.getDestination(),
                    edgeReference.edge.getWeight(),
                    edgeReference.edge.getLabel(),
                    edgeReference.bidirectional
            ));
        }
        return displayItems;
    }

    private static boolean isSymmetricConnection(DirectedGraph graph, int origin, Edge edge) {
        Optional<Edge> reverse = graph.getEdge(edge.getDestination(), origin);
        if (!reverse.isPresent()) {
            return false;
        }

        Edge reverseEdge = reverse.get();
        return Double.compare(edge.getWeight(), reverseEdge.getWeight()) == 0
                && Objects.equals(edge.getLabel(), reverseEdge.getLabel());
    }

    private static String buildBidirectionalKey(int origin, int destination, Edge edge) {
        int vertexA = Math.min(origin, destination);
        int vertexB = Math.max(origin, destination);
        return vertexA + ":" + vertexB + ":" + edge.getWeight() + ":" + edge.getLabel();
    }

    private static boolean hasNamedVertices(DirectedGraph graph) {
        for (int i = 0; i < graph.getNumVertices(); i++) {
            String information = graph.getInformation(i);
            if (information != null && !information.isEmpty() && !information.equals("V" + i)) {
                return true;
            }
        }
        return false;
    }

    private static class EdgeReference {
        private final int origin;
        private final Edge edge;
        private final boolean bidirectional;

        private EdgeReference(int origin, Edge edge, boolean bidirectional) {
            this.origin = origin;
            this.edge = edge;
            this.bidirectional = bidirectional;
        }
    }
}
