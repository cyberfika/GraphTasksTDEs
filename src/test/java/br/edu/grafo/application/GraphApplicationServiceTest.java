package br.edu.grafo.application;

import br.edu.grafo.model.DirectedGraph;
import br.edu.grafo.util.GraphRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GraphApplicationServiceTest {

    private GraphApplicationService service;
    private GraphRepository stubRepo;

    @BeforeEach
    void setUp() {
        stubRepo = new GraphRepository() {
            private DirectedGraph stored;
            private String storedName;

            @Override
            public boolean save(DirectedGraph graph, String name) {
                stored = graph;
                storedName = name;
                return true;
            }

            @Override
            public Optional<DirectedGraph> load(String name) {
                return storedName != null && storedName.equals(name)
                        ? Optional.of(stored)
                        : Optional.empty();
            }

            @Override
            public boolean exists(String name) {
                return storedName != null && storedName.equals(name);
            }

            @Override
            public String[] listAll() {
                return storedName != null ? new String[]{storedName} : new String[0];
            }
        };
        service = new GraphApplicationService(stubRepo);
    }

    @Test
    void hasGraph_returnsFalse_initially() {
        assertFalse(service.hasGraph());
    }

    @Test
    void createGraph_createsGraph() {
        service.createGraph(3);
        assertTrue(service.hasGraph());
        assertEquals(3, service.getGraph().getNumVertices());
    }

    @Test
    void createGraph_setsDefaultVertexNames() {
        service.createGraph(3);
        assertEquals(Optional.of("V0"), service.getGraph().getInformation(0));
        assertEquals(Optional.of("V1"), service.getGraph().getInformation(1));
        assertEquals(Optional.of("V2"), service.getGraph().getInformation(2));
    }

    @Test
    void setGraph_replacesCurrentGraph() {
        DirectedGraph g = new DirectedGraph(2);
        g.createEdge(0, 1, 5.0);
        service.setGraph(g);
        assertTrue(service.hasGraph());
        assertTrue(service.getGraph().hasEdge(0, 1));
    }

    @Test
    void getGraph_returnsNull_whenNoGraph() {
        assertNull(service.getGraph());
    }

    @Test
    void addEdge_delegatesToGraph() {
        service.createGraph(3);
        assertTrue(service.addEdge(0, 1, 5.0, "test"));
        assertTrue(service.getGraph().hasEdge(0, 1));
    }

    @Test
    void addEdge_withoutLabel_delegatesCorrectly() {
        service.createGraph(3);
        assertTrue(service.addEdge(0, 1, 3.0, null));
        assertTrue(service.getGraph().hasEdge(0, 1));
    }

    @Test
    void removeEdge_delegatesToGraph() {
        service.createGraph(3);
        service.addEdge(0, 1, 5.0, "");
        assertTrue(service.removeEdge(0, 1));
        assertFalse(service.getGraph().hasEdge(0, 1));
    }

    @Test
    void executeBFS_returnsCorrectOrder() {
        service.createGraph(4);
        service.addEdge(0, 1, 1.0, "");
        service.addEdge(0, 2, 1.0, "");
        service.addEdge(1, 3, 1.0, "");
        assertEquals(4, service.executeBFS(0).size());
        assertEquals(0, service.executeBFS(0).get(0).intValue());
    }

    @Test
    void executeDFS_returnsCorrectOrder() {
        service.createGraph(4);
        service.addEdge(0, 1, 1.0, "");
        service.addEdge(0, 2, 1.0, "");
        service.addEdge(1, 3, 1.0, "");
        assertEquals(4, service.executeDFS(0).size());
    }

    @Test
    void executeDijkstra_returnsDistances() {
        service.createGraph(3);
        service.addEdge(0, 1, 5.0, "");
        service.addEdge(1, 2, 3.0, "");
        double[] dist = service.executeDijkstra(0);
        assertEquals(0.0, dist[0], 1e-15);
        assertEquals(5.0, dist[1], 1e-15);
        assertEquals(8.0, dist[2], 1e-15);
    }

    @Test
    void executeDijkstra_unreachableReturnsInfinity() {
        service.createGraph(3);
        service.addEdge(0, 1, 1.0, "");
        double[] dist = service.executeDijkstra(0);
        assertEquals(Double.POSITIVE_INFINITY, dist[2], 0.0);
    }

    @Test
    void executeShortestPath_findsPath() {
        service.createGraph(3);
        service.addEdge(0, 1, 5.0, "");
        service.addEdge(1, 2, 3.0, "");
        ShortestPathResult r = service.executeShortestPath(0, 2);
        assertTrue(r.isReachable());
        assertEquals(8.0, r.getTotalDistance(), 1e-15);
    }

    @Test
    void executeShortestPath_noPath() {
        service.createGraph(3);
        service.addEdge(0, 1, 1.0, "");
        ShortestPathResult r = service.executeShortestPath(0, 2);
        assertFalse(r.isReachable());
    }

    @Test
    void saveGraph_delegatesToRepository() {
        service.createGraph(2);
        assertTrue(service.saveGraph("test"));
    }

    @Test
    void loadGraph_loadsFromRepository() {
        service.createGraph(2);
        service.addEdge(0, 1, 5.0, "");
        service.saveGraph("mygraph");
        service.createGraph(5);
        assertTrue(service.loadGraph("mygraph"));
        assertEquals(2, service.getGraph().getNumVertices());
    }

    @Test
    void loadGraph_nonExistent_returnsFalse() {
        service.createGraph(2);
        assertFalse(service.loadGraph("does_not_exist"));
    }

    @Test
    void listSavedGraphs_delegatesToRepository() {
        service.createGraph(2);
        service.saveGraph("g1");
        String[] list = service.listSavedGraphs();
        assertEquals(1, list.length);
        assertEquals("g1", list[0]);
    }

    @Test
    void findVertexByName_exactMatch() {
        service.createGraph(3);
        service.getGraph().setInformation(1, "Alpha");
        assertEquals(1, service.findVertexByName("Alpha"));
    }

    @Test
    void findVertexByName_caseInsensitive() {
        service.createGraph(2);
        service.getGraph().setInformation(0, "TestVertex");
        assertEquals(0, service.findVertexByName("testvertex"));
    }

    @Test
    void findVertexByName_notFound_returnsMinusOne() {
        service.createGraph(2);
        assertEquals(-1, service.findVertexByName("nonexistent"));
    }

    @Test
    void listVertexNames_returnsDefaultNames() {
        service.createGraph(3);
        assertEquals(3, service.listVertexNames().size());
    }

    @Test
    void listEdges_returnsEmpty_whenNoEdges() {
        service.createGraph(3);
        assertTrue(service.listEdges().isEmpty());
    }

    @Test
    void listEdges_returnsSingleDirectedEdge() {
        service.createGraph(3);
        service.addEdge(0, 1, 5.0, "e1");
        assertEquals(1, service.listEdges().size());
        assertEquals(0, service.listEdges().get(0).origin);
    }

    @Test
    void executeWarshall_delegates() {
        service.createGraph(3);
        service.addEdge(0, 1, 1.0, "");
        service.addEdge(1, 2, 1.0, "");
        boolean[][] reach = service.executeWarshall();
        assertTrue(reach[0][2]);
    }
}
