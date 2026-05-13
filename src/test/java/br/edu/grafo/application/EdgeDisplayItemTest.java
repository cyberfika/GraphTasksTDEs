package br.edu.grafo.application;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EdgeDisplayItemTest {

    @Test
    void constructor_setsFields() {
        EdgeDisplayItem item = new EdgeDisplayItem(2, 5, 3.5, "foo", true);
        assertEquals(2, item.origin);
        assertEquals(5, item.destination);
        assertEquals(3.5, item.weight, 1e-15);
        assertEquals("foo", item.label);
        assertTrue(item.bidirectional);
    }

    @Test
    void constructor_withNullLabel_usesEmpty() {
        EdgeDisplayItem item = new EdgeDisplayItem(0, 1, 1.0, null, false);
        assertEquals("", item.label);
    }

    @Test
    void connector_returnsDoubleArrow_whenBidirectional() {
        EdgeDisplayItem item = new EdgeDisplayItem(0, 1, 1.0, "", true);
        assertEquals(" -- ", item.connector());
    }

    @Test
    void connector_returnsSingleArrow_whenDirected() {
        EdgeDisplayItem item = new EdgeDisplayItem(0, 1, 1.0, "", false);
        assertEquals(" -> ", item.connector());
    }

    @Test
    void toString_bidirectionalWithLabel() {
        EdgeDisplayItem item = new EdgeDisplayItem(0, 1, 2.5, "test", true);
        assertEquals("V0 -- V1 | weight=" + String.format("%.2f", 2.5) + " [test]", item.toString());
    }

    @Test
    void toString_directedWithoutLabel() {
        EdgeDisplayItem item = new EdgeDisplayItem(3, 7, 10.0, "", false);
        assertEquals("V3 -> V7 | weight=" + String.format("%.2f", 10.0), item.toString());
    }
}
