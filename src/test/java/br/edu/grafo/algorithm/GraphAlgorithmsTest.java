package br.edu.grafo.algorithm;

import br.edu.grafo.model.DirectedGraph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GraphAlgorithmsTest {

    @Test
    void warshall_nullGraph_throws() {
        assertThrows(IllegalArgumentException.class, () -> GraphAlgorithms.warshall(null));
    }

    @Test
    void warshall_singleVertex_selfReachable() {
        DirectedGraph g = new DirectedGraph(1);
        boolean[][] reach = GraphAlgorithms.warshall(g);
        assertTrue(reach[0][0]);
        assertEquals(1, reach.length);
    }

    @Test
    void warshall_disconnectedGraph() {
        DirectedGraph g = new DirectedGraph(3);
        g.createEdge(0, 1, 1.0);
        boolean[][] reach = GraphAlgorithms.warshall(g);
        assertTrue(reach[0][0]);
        assertTrue(reach[0][1]);
        assertFalse(reach[0][2]);
        assertTrue(reach[1][1]);
        assertFalse(reach[1][0]);
        assertFalse(reach[1][2]);
        assertTrue(reach[2][2]);
        assertFalse(reach[2][0]);
        assertFalse(reach[2][1]);
    }

    @Test
    void warshall_fullyConnectedGraph() {
        DirectedGraph g = new DirectedGraph(5);
        g.createEdge(0, 1, 5.0);
        g.createEdge(0, 2, 3.0);
        g.createEdge(1, 2, 2.0);
        g.createEdge(1, 3, 7.0);
        g.createEdge(2, 3, 4.0);
        g.createEdge(2, 4, 6.0);
        g.createEdge(3, 4, 1.0);
        g.createEdge(4, 0, 8.0);

        boolean[][] reach = GraphAlgorithms.warshall(g);

        // With the cycle, all vertices reach all others
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertTrue(reach[i][j], "V" + i + " should reach V" + j);
            }
        }
    }

    @Test
    void warshall_diagonalAlwaysTrue() {
        DirectedGraph g = new DirectedGraph(4);
        boolean[][] reach = GraphAlgorithms.warshall(g);
        for (int i = 0; i < 4; i++) {
            assertTrue(reach[i][i], "Self-reachability at V" + i);
        }
    }

    @Test
    void warshall_transitiveClosure() {
        DirectedGraph g = new DirectedGraph(3);
        g.createEdge(0, 1, 1.0);
        g.createEdge(1, 2, 1.0);

        boolean[][] reach = GraphAlgorithms.warshall(g);
        assertTrue(reach[0][2], "0 should reach 2 via transitive closure");
        assertTrue(reach[0][1]);
        assertTrue(reach[1][2]);
        assertFalse(reach[2][0]);
        assertFalse(reach[2][1]);
    }

    @Test
    void warshall_returnedMatrixIsNew() {
        DirectedGraph g = new DirectedGraph(2);
        boolean[][] reach = GraphAlgorithms.warshall(g);
        reach[0][0] = false;
        boolean[][] reach2 = GraphAlgorithms.warshall(g);
        assertTrue(reach2[0][0], "Subsequent calls should return fresh matrix");
    }
}
