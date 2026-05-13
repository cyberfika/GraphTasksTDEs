package br.edu.grafo.application;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShortestPathResultTest {

    @Test
    void constructor_setsFields() {
        List<Integer> path = Arrays.asList(0, 2, 4);
        ShortestPathResult r = new ShortestPathResult(0, 4, 8.5, path, true);
        assertEquals(0, r.getSource());
        assertEquals(4, r.getDestination());
        assertEquals(8.5, r.getTotalDistance(), 1e-15);
        assertEquals(path, r.getPath());
        assertTrue(r.isReachable());
    }

    @Test
    void getPath_returnsUnmodifiable() {
        List<Integer> path = Arrays.asList(1, 2);
        ShortestPathResult r = new ShortestPathResult(1, 2, 3.0, path, true);
        assertThrows(UnsupportedOperationException.class, () -> r.getPath().add(99));
    }

    @Test
    void result_notReachable() {
        ShortestPathResult r = new ShortestPathResult(0, 3, Double.POSITIVE_INFINITY, Collections.emptyList(), false);
        assertFalse(r.isReachable());
        assertTrue(r.getPath().isEmpty());
    }

    @Test
    void constructor_defensivelyCopiesPath() {
        List<Integer> original = new java.util.ArrayList<>(Arrays.asList(0, 1));
        ShortestPathResult r = new ShortestPathResult(0, 1, 1.0, original, true);
        original.add(99);
        assertEquals(2, r.getPath().size());
    }
}
