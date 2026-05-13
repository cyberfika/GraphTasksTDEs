package br.edu.grafo.gui.panel;

import br.edu.grafo.algorithm.KruskalResult;
import br.edu.grafo.gui.GraphGuiController;
import br.edu.grafo.gui.component.SwingHelper;
import br.edu.grafo.gui.design.DesignSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class AlgorithmsPanel extends JPanel {
    private final GraphGuiController controller;
    private final JTextArea outputArea;

    public AlgorithmsPanel(GraphGuiController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 12));
        setBackground(DesignSystem.bg());
        setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        top.setOpaque(false);

        JTextField sourceField = SwingHelper.textField(4);
        JButton bfsButton = SwingHelper.actionButton("BFS");
        JButton dfsButton = SwingHelper.actionButton("DFS");
        JButton dijkstraButton = SwingHelper.actionButton("Dijkstra");
        JButton warshallButton = SwingHelper.actionButton("Warshall");
        JButton kruskalButton = SwingHelper.actionButton("Kruskal");

        top.add(SwingHelper.formLabel("Source"));
        top.add(sourceField);
        top.add(bfsButton);
        top.add(dfsButton);
        top.add(dijkstraButton);
        top.add(warshallButton);
        top.add(kruskalButton);

        outputArea = SwingHelper.resultArea();
        add(top, BorderLayout.NORTH);
        add(SwingHelper.resultScroll(outputArea), BorderLayout.CENTER);

        bfsButton.addActionListener(e -> runTraversal(sourceField.getText().trim(), true));
        dfsButton.addActionListener(e -> runTraversal(sourceField.getText().trim(), false));
        dijkstraButton.addActionListener(e -> runDijkstra(sourceField.getText().trim()));
        warshallButton.addActionListener(e -> runWarshall());
        kruskalButton.addActionListener(e -> runKruskal());
    }

    private int parseSource(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void runTraversal(String sourceText, boolean bfs) {
        if (!controller.hasGraph()) {
            outputArea.setText("No graph loaded.");
            return;
        }
        int source = parseSource(sourceText);
        if (source < 0) {
            outputArea.setText("Invalid source vertex.");
            return;
        }
        List<Integer> result = bfs ? controller.bfs(source) : controller.dfs(source);
        outputArea.setText((bfs ? "BFS" : "DFS") + " visit order: " + result);
    }

    private void runDijkstra(String sourceText) {
        if (!controller.hasGraph()) {
            outputArea.setText("No graph loaded.");
            return;
        }
        int source = parseSource(sourceText);
        if (source < 0) {
            outputArea.setText("Invalid source vertex.");
            return;
        }
        double[] distances = controller.dijkstra(source);
        StringBuilder builder = new StringBuilder("Dijkstra distances from V" + source + " (" + controller.weightUnit() + "):\n");
        for (int i = 0; i < distances.length; i++) {
            builder.append("V").append(i).append(": ");
            builder.append(distances[i] == Double.POSITIVE_INFINITY
                    ? "unreachable"
                    : String.format("%.2f %s", distances[i], controller.weightUnit()));
            builder.append('\n');
        }
        outputArea.setText(builder.toString());
    }

    private void runWarshall() {
        if (!controller.hasGraph()) {
            outputArea.setText("No graph loaded.");
            return;
        }
        boolean[][] matrix = controller.warshall();
        StringBuilder builder = new StringBuilder("Warshall reachability matrix:\n");
        for (boolean[] row : matrix) {
            for (boolean cell : row) {
                builder.append(cell ? "T " : "F ");
            }
            builder.append('\n');
        }
        outputArea.setText(builder.toString());
    }

    private void runKruskal() {
        if (!controller.hasGraph()) {
            outputArea.setText("No graph loaded.");
            return;
        }
        KruskalResult result = controller.kruskal();
        StringBuilder builder = new StringBuilder("Kruskal result:\n");
        for (KruskalResult.MinimumEdge edge : result.getEdges()) {
            builder.append("V").append(edge.getVertexA()).append(" -- V").append(edge.getVertexB())
                    .append(" | ").append(String.format("%.2f", edge.getWeight()));
            if (!edge.getLabel().isEmpty()) {
                builder.append(" [").append(edge.getLabel()).append("]");
            }
            builder.append('\n');
        }
        builder.append("Total weight: ").append(String.format("%.2f", result.getTotalWeight())).append('\n');
        builder.append("Spanning tree: ").append(result.isSpanningTree());
        outputArea.setText(builder.toString());
    }
}
