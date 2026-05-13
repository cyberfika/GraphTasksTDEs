package br.edu.grafo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {

    private DirectedGraph graph;

    @BeforeEach
    void setUp() {
        graph = new DirectedGraph(5);
    }

    @Test
    void constructor_withZeroVertices_throws() {
        assertThrows(IllegalArgumentException.class, () -> new DirectedGraph(0));
    }

    @Test
    void constructor_withNegativeVertices_throws() {
        assertThrows(IllegalArgumentException.class, () -> new DirectedGraph(-1));
    }

    @Test
    void getNumVertices_returnsCorrectCount() {
        assertEquals(5, graph.getNumVertices());
    }

    @Test
    void createEdge_returnsTrue() {
        assertTrue(graph.createEdge(0, 1, 5.0, "A"));
    }

    @Test
    void createEdge_withDuplicate_returnsFalse() {
        graph.createEdge(0, 1, 5.0);
        assertFalse(graph.createEdge(0, 1, 3.0));
    }

    @Test
    void createEdge_withInvalidOrigin_throws() {
        assertThrows(IllegalArgumentException.class, () -> graph.createEdge(10, 1, 5.0));
    }

    @Test
    void createEdge_withInvalidDestination_throws() {
        assertThrows(IllegalArgumentException.class, () -> graph.createEdge(1, -1, 5.0));
    }

    @Test
    void createEdge_withoutLabel_works() {
        assertTrue(graph.createEdge(0, 2, 3.0));
        assertTrue(graph.hasEdge(0, 2));
    }

    @Test
    void hasEdge_returnsTrue_whenEdgeExists() {
        graph.createEdge(1, 3, 7.0, "B");
        assertTrue(graph.hasEdge(1, 3));
    }

    @Test
    void hasEdge_returnsFalse_whenNoEdge() {
        assertFalse(graph.hasEdge(0, 4));
    }

    @Test
    void hasEdge_withInvalidVertex_throws() {
        assertThrows(IllegalArgumentException.class, () -> graph.hasEdge(-1, 0));
    }

    @Test
    void removeEdge_returnsTrue() {
        graph.createEdge(0, 1, 5.0);
        assertTrue(graph.removeEdge(0, 1));
    }

    @Test
    void removeEdge_returnsFalse_whenNotExists() {
        assertFalse(graph.removeEdge(0, 4));
    }

    @Test
    void removeEdge_removesEdge() {
        graph.createEdge(0, 1, 5.0);
        graph.removeEdge(0, 1);
        assertFalse(graph.hasEdge(0, 1));
    }

    @Test
    void removeEdge_withInvalidVertex_throws() {
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge(5, 0));
    }

    @Test
    void getEdge_returnsOptional_withExistingEdge() {
        graph.createEdge(2, 4, 6.0, "C");
        Optional<Edge> edge = graph.getEdge(2, 4);
        assertTrue(edge.isPresent());
        assertEquals(4, edge.get().getDestination());
        assertEquals(6.0, edge.get().getWeight());
    }

    @Test
    void getEdge_returnsEmpty_whenNoEdge() {
        Optional<Edge> edge = graph.getEdge(0, 3);
        assertFalse(edge.isPresent());
    }

    @Test
    void getEdge_withInvalidVertex_throws() {
        assertThrows(IllegalArgumentException.class, () -> graph.getEdge(0, 10));
    }

    @Test
    void getAdjacencies_returnsDefensiveCopy() {
        graph.createEdge(0, 1, 5.0);
        List<Edge> adj = graph.getAdjacencies(0);
        adj.clear();
        assertTrue(graph.hasEdge(0, 1));
    }

    @Test
    void getAdjacencies_withInvalidVertex_throws() {
        assertThrows(IllegalArgumentException.class, () -> graph.getAdjacencies(-1));
    }

    @Test
    void getAdjacentVertices_returnsUnmodifiable() {
        graph.createEdge(0, 1, 5.0);
        List<Integer> adj = graph.getAdjacentVertices(0);
        assertThrows(UnsupportedOperationException.class, () -> adj.add(99));
    }

    @Test
    void getAdjacentVertices_returnsDestinations() {
        graph.createEdge(0, 1, 5.0);
        graph.createEdge(0, 2, 3.0);
        List<Integer> adj = graph.getAdjacentVertices(0);
        assertEquals(2, adj.size());
        assertTrue(adj.contains(1));
        assertTrue(adj.contains(2));
    }

    @Test
    void getAdjacentVertices_empty_whenNoEdges() {
        List<Integer> adj = graph.getAdjacentVertices(0);
        assertTrue(adj.isEmpty());
    }

    @Test
    void setInformation_setsLabel() {
        graph.setInformation(0, "Origin");
        assertEquals(Optional.of("Origin"), graph.getInformation(0));
    }

    @Test
    void setInformation_withNull_clearsToEmpty() {
        graph.setInformation(1, "test");
        graph.setInformation(1, null);
        assertEquals(Optional.empty(), graph.getInformation(1));
    }

    @Test
    void setInformation_withInvalidVertex_throws() {
        assertThrows(IllegalArgumentException.class, () -> graph.setInformation(-1, "x"));
    }

    @Test
    void getInformation_returnsEmpty_whenNotSet() {
        assertEquals(Optional.empty(), graph.getInformation(3));
    }

    @Test
    void getInformation_withInvalidVertex_throws() {
        assertThrows(IllegalArgumentException.class, () -> graph.getInformation(100));
    }

    @Test
    void graph_withMultipleEdges_tracksCorrectly() {
        graph.createEdge(0, 1, 1.0);
        graph.createEdge(0, 2, 2.0);
        graph.createEdge(1, 2, 3.0);
        graph.createEdge(2, 3, 4.0);
        graph.createEdge(3, 4, 5.0);
        graph.createEdge(4, 0, 6.0);

        assertEquals(6, graph.getAdjacencies(0).size()
                + graph.getAdjacencies(1).size()
                + graph.getAdjacencies(2).size()
                + graph.getAdjacencies(3).size()
                + graph.getAdjacencies(4).size());
        // Each of these 6 edges counted once in their respective origin
        assertEquals(2, graph.getAdjacencies(0).size()); // ->1, ->2
        assertEquals(1, graph.getAdjacencies(1).size()); // ->2
        assertEquals(1, graph.getAdjacencies(2).size()); // ->3
        assertEquals(1, graph.getAdjacencies(3).size()); // ->4
        assertEquals(1, graph.getAdjacencies(4).size()); // ->0
    }

    @Test
    void directedGraph_isSerializable() {
        assertTrue(java.io.Serializable.class.isAssignableFrom(DirectedGraph.class));
    }
}
