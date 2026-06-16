import br.edu.grafo.model.DirectedGraph;
import java.util.Arrays;
import java.util.List;

public class TestAlgorithms {
    public static void main(String[] args) {
        // Create a simple test graph
        DirectedGraph g = new DirectedGraph(5);

        // Component 1: 0-1-2
        g.createEdge(0, 1, 1);
        g.createEdge(1, 0, 1);
        g.createEdge(1, 2, 1);
        g.createEdge(2, 1, 1);

        // Component 2: 3-4
        g.createEdge(3, 4, 1);
        g.createEdge(4, 3, 1);

        System.out.println("=== Testing findComponents() ===");
        int components = g.findComponents();
        System.out.println("Total components: " + components + "\n");

        System.out.println("=== Testing isClique() ===");
        // Test clique: 0-1-2 should be a clique
        List<Integer> clique1 = Arrays.asList(0, 1, 2);
        System.out.println("Is {0,1,2} a clique? " + g.isClique(clique1));

        // Add edge to complete the clique
        g.createEdge(0, 2, 1);
        g.createEdge(2, 0, 1);
        System.out.println("After adding edges 0->2 and 2->0:");
        System.out.println("Is {0,1,2} a clique? " + g.isClique(clique1));

        System.out.println("\n=== Testing isMaximal() ===");
        System.out.println("Is {0,1,2} a maximal clique? " + g.isMaximal(clique1));

        // Test non-clique
        List<Integer> nonClique = Arrays.asList(0, 1, 3);
        System.out.println("Is {0,1,3} a clique? " + g.isClique(nonClique));
    }
}
