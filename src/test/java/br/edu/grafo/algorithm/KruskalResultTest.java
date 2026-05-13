package br.edu.grafo.algorithm;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KruskalResultTest {

    @Test
    void constructor_setsFields() {
        KruskalResult.MinimumEdge e1 = new KruskalResult.MinimumEdge(0, 1, 2.0, "a");
        KruskalResult.MinimumEdge e2 = new KruskalResult.MinimumEdge(1, 2, 3.0, "b");
        List<KruskalResult.MinimumEdge> edges = Arrays.asList(e1, e2);
        KruskalResult r = new KruskalResult(edges, 5.0, true);
        assertEquals(2, r.getEdges().size());
        assertEquals(5.0, r.getTotalWeight(), 1e-15);
        assertTrue(r.isSpanningTree());
    }

    @Test
    void getEdges_returnsUnmodifiable() {
        KruskalResult r = new KruskalResult(new ArrayList<KruskalResult.MinimumEdge>(), 0.0, true);
        assertThrows(UnsupportedOperationException.class, () -> r.getEdges().add(null));
    }

    @Test
    void minimumEdge_constructor() {
        KruskalResult.MinimumEdge e = new KruskalResult.MinimumEdge(2, 5, 1.5, "label");
        assertEquals(2, e.getVertexA());
        assertEquals(5, e.getVertexB());
        assertEquals(1.5, e.getWeight(), 1e-15);
        assertEquals("label", e.getLabel());
    }

    @Test
    void result_notSpanningTree() {
        KruskalResult r = new KruskalResult(new ArrayList<KruskalResult.MinimumEdge>(), 0.0, false);
        assertFalse(r.isSpanningTree());
    }

    @Test
    void constructor_defensivelyCopiesEdges() {
        List<KruskalResult.MinimumEdge> original = new ArrayList<>();
        original.add(new KruskalResult.MinimumEdge(0, 1, 1.0, ""));
        KruskalResult r = new KruskalResult(original, 1.0, true);
        original.clear();
        assertEquals(1, r.getEdges().size());
    }
}
