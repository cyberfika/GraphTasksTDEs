package br.edu.grafo.util;

import br.edu.grafo.model.DirectedGraph;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GraphStorageTest {

    private static final String TEST_DIR = "data";
    private static final String TEST_NAME = "_test_graph_storage";
    private static GraphStorage storage;

    @BeforeAll
    static void setUp() {
        storage = new GraphStorage();
    }

    @AfterAll
    static void cleanUp() {
        new File(TEST_DIR, TEST_NAME + ".bin").delete();
    }

    @Test
    @Order(1)
    void save_returnsTrue() {
        DirectedGraph g = new DirectedGraph(3);
        g.createEdge(0, 1, 5.0);
        g.createEdge(1, 2, 3.0);
        assertTrue(storage.save(g, TEST_NAME));
    }

    @Test
    @Order(2)
    void exists_returnsTrue_afterSave() {
        assertTrue(storage.exists(TEST_NAME));
    }

    @Test
    @Order(3)
    void load_returnsGraph() {
        Optional<DirectedGraph> loaded = storage.load(TEST_NAME);
        assertTrue(loaded.isPresent());
        assertEquals(3, loaded.get().getNumVertices());
        assertTrue(loaded.get().hasEdge(0, 1));
        assertTrue(loaded.get().hasEdge(1, 2));
    }

    @Test
    @Order(4)
    void load_preservesEdgeData() {
        Optional<DirectedGraph> loaded = storage.load(TEST_NAME);
        assertTrue(loaded.isPresent());
        DirectedGraph g = loaded.get();
        assertEquals(5.0, g.getEdge(0, 1).get().getWeight(), 1e-15);
        assertEquals(3.0, g.getEdge(1, 2).get().getWeight(), 1e-15);
    }

    @Test
    @Order(5)
    void listAll_includesSavedGraph() {
        String[] names = storage.listAll();
        boolean found = false;
        for (String name : names) {
            if (TEST_NAME.equals(name)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Saved graph should appear in listAll()");
    }

    @Test
    void load_nonExistent_returnsEmpty() {
        Optional<DirectedGraph> loaded = storage.load("__nonexistent_graph_name__");
        assertFalse(loaded.isPresent());
    }

    @Test
    void exists_nonExistent_returnsFalse() {
        assertFalse(storage.exists("__nonexistent_graph_name__"));
    }

    @Test
    void listAll_doesNotReturnNull() {
        assertNotNull(storage.listAll());
    }
}
