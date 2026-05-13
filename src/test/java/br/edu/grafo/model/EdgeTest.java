package br.edu.grafo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    @Test
    void constructor_setsAllFields() {
        Edge edge = new Edge(3, 5.5, "myLabel");
        assertEquals(3, edge.getDestination());
        assertEquals(5.5, edge.getWeight(), 1e-15);
        assertEquals("myLabel", edge.getLabel());
    }

    @Test
    void constructor_withNullLabel_defaultsToEmpty() {
        Edge edge = new Edge(1, 2.0, null);
        assertEquals("", edge.getLabel());
    }

    @Test
    void constructor_withoutLabel_usesEmptyString() {
        Edge edge = new Edge(0, 9.99);
        assertEquals(0, edge.getDestination());
        assertEquals(9.99, edge.getWeight(), 1e-15);
        assertEquals("", edge.getLabel());
    }

    @Test
    void toString_withLabel_returnsFormatted() {
        Edge edge = new Edge(2, 3.5, "test");
        assertEquals(String.format("[2, %.2f, test]", 3.5), edge.toString());
    }

    @Test
    void toString_withoutLabel_returnsCompact() {
        Edge edge = new Edge(1, 7.0);
        assertEquals(String.format("[1, %.2f]", 7.0), edge.toString());
    }

    @Test
    void toString_roundsWeightToTwoDecimals() {
        Edge edge = new Edge(0, 1.23456);
        assertEquals(String.format("[0, %.2f]", 1.23456), edge.toString());
    }

    @Test
    void instance_isImmutable() {
        Edge edge = new Edge(5, 10.0, "fixed");
        assertAll(
                () -> assertEquals(5, edge.getDestination()),
                () -> assertEquals(10.0, edge.getWeight()),
                () -> assertEquals("fixed", edge.getLabel())
        );
    }
}
