package br.edu.grafo.gui.panel;

import br.edu.grafo.application.ShortestPathResult;
import br.edu.grafo.gui.GraphGuiController;
import br.edu.grafo.gui.component.SwingHelper;
import br.edu.grafo.gui.design.DesignSystem;
import br.edu.grafo.model.DirectedGraph;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ShortestPathPanel extends JPanel {
    private final GraphGuiController controller;
    private final JTextArea outputArea;

    public ShortestPathPanel(GraphGuiController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 12));
        setBackground(DesignSystem.bg());
        setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        controls.setOpaque(false);

        JTextField sourceField = SwingHelper.textField(16);
        JTextField destinationField = SwingHelper.textField(16);
        JButton runButton = SwingHelper.actionButton("Run Shortest Path");

        controls.add(SwingHelper.formLabel("Source"));
        controls.add(sourceField);
        controls.add(SwingHelper.formLabel("Destination"));
        controls.add(destinationField);
        controls.add(runButton);

        outputArea = SwingHelper.resultArea();
        add(controls, BorderLayout.NORTH);
        add(SwingHelper.resultScroll(outputArea), BorderLayout.CENTER);

        runButton.addActionListener(e -> {
            if (!controller.hasGraph()) {
                outputArea.setText("No graph loaded.");
                return;
            }

            DirectedGraph graph = controller.graph();
            int source = resolveVertex(sourceField.getText().trim());
            int destination = resolveVertex(destinationField.getText().trim());

            if (source < 0 || destination < 0) {
                outputArea.setText("Invalid source or destination.");
                return;
            }

            ShortestPathResult result = controller.shortestPath(source, destination);
            outputArea.setText(formatResult(graph, result));
        });
    }

    private int resolveVertex(String value) {
        if (value.isEmpty()) {
            return -1;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
            return controller.findVertexByName(value);
        }
    }

    private String formatResult(DirectedGraph graph, ShortestPathResult result) {
        if (!result.isReachable()) {
            return "No path found.";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Total distance: ")
                .append(String.format("%.2f %s", result.getTotalDistance(), controller.weightUnit()))
                .append('\n');
        builder.append("Path:\n");
        for (int vertex : result.getPath()) {
            builder.append(" - ").append(vertexLabel(graph, vertex)).append('\n');
        }
        return builder.toString();
    }

    private String vertexLabel(DirectedGraph graph, int vertex) {
        String info = graph.getInformation(vertex);
        if (info != null && !info.isEmpty() && !info.equals("V" + vertex)) {
            return "V" + vertex + " (" + info + ")";
        }
        return "V" + vertex;
    }
}
