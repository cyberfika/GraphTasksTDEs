package br.edu.grafo.gui.panel;

import br.edu.grafo.gui.GraphGuiController;
import br.edu.grafo.gui.component.SwingHelper;
import br.edu.grafo.gui.design.DesignSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class OverviewPanel extends JPanel {
    private final GraphGuiController controller;
    private final JTextArea outputArea;

    public OverviewPanel(GraphGuiController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 12));
        setBackground(DesignSystem.bg());
        setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setOpaque(false);

        JButton infoButton = SwingHelper.actionButton("Show Graph Info");
        JButton verticesButton = SwingHelper.actionButton("Show Vertex Names");
        actions.add(infoButton);
        actions.add(verticesButton);

        outputArea = SwingHelper.resultArea();
        add(actions, BorderLayout.NORTH);
        add(SwingHelper.resultScroll(outputArea), BorderLayout.CENTER);

        infoButton.addActionListener(e -> showInfo());
        verticesButton.addActionListener(e -> showVertices());
    }

    public void showInfo() {
        if (!controller.hasGraph()) {
            outputArea.setText("No graph loaded.");
            return;
        }
        outputArea.setText(controller.graphInfoText());
    }

    public void showVertices() {
        if (!controller.hasGraph()) {
            outputArea.setText("No graph loaded.");
            return;
        }
        List<String> names = controller.listVertexNames();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            builder.append(i).append(" - ").append(names.get(i)).append('\n');
        }
        outputArea.setText(builder.toString());
        outputArea.setCaretPosition(0);
    }
}
