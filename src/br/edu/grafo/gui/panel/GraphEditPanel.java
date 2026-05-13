package br.edu.grafo.gui.panel;

import br.edu.grafo.application.EdgeDisplayItem;
import br.edu.grafo.gui.GraphGuiController;
import br.edu.grafo.gui.component.SwingHelper;
import br.edu.grafo.gui.design.DesignSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class GraphEditPanel extends JPanel {
    private final GraphGuiController controller;
    private final Runnable afterChange;
    private final JTextArea outputArea;
    private final DefaultListModel<EdgeDisplayItem> edgeModel;

    public GraphEditPanel(GraphGuiController controller, Runnable afterChange) {
        this.controller = controller;
        this.afterChange = afterChange;

        setLayout(new BorderLayout(0, 12));
        setBackground(DesignSystem.bg());
        setBorder(new EmptyBorder(18, 18, 18, 18));

        edgeModel = new DefaultListModel<>();

        JPanel top = new JPanel(new GridLayout(2, 1, 0, 12));
        top.setOpaque(false);
        top.add(buildAddPanel());
        top.add(buildRemovePanel());

        outputArea = SwingHelper.resultArea();

        add(top, BorderLayout.NORTH);
        add(SwingHelper.resultScroll(outputArea), BorderLayout.CENTER);
    }

    private JPanel buildAddPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panel.setOpaque(false);

        JTextField originField = SwingHelper.textField(4);
        JTextField destinationField = SwingHelper.textField(4);
        JTextField weightField = SwingHelper.textField(6);
        JTextField labelField = SwingHelper.textField(12);
        JButton addButton = SwingHelper.actionButton("Add Edge");

        panel.add(SwingHelper.formLabel("Origin"));
        panel.add(originField);
        panel.add(SwingHelper.formLabel("Destination"));
        panel.add(destinationField);
        panel.add(SwingHelper.formLabel("Weight"));
        panel.add(weightField);
        panel.add(SwingHelper.formLabel("Label"));
        panel.add(labelField);
        panel.add(addButton);

        addButton.addActionListener(e -> {
            if (!controller.hasGraph()) {
                outputArea.setText("No graph loaded.");
                return;
            }
            try {
                controller.addEdge(
                        Integer.parseInt(originField.getText().trim()),
                        Integer.parseInt(destinationField.getText().trim()),
                        Double.parseDouble(weightField.getText().trim()),
                        labelField.getText().trim()
                );
                outputArea.setText("Edge processed.");
                refreshEdges();
                afterChange.run();
            } catch (NumberFormatException ex) {
                outputArea.setText("Invalid edge input.");
            }
        });
        return panel;
    }

    private JPanel buildRemovePanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.setOpaque(false);

        JList<EdgeDisplayItem> edgeList = new JList<>(edgeModel);
        SwingHelper.styleList(edgeList);
        JButton refreshButton = SwingHelper.actionButton("Refresh Edge List");
        JButton removeButton = SwingHelper.actionButton("Remove Selected Edge");

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttons.setOpaque(false);
        buttons.add(refreshButton);
        buttons.add(removeButton);

        panel.add(buttons, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(edgeList);
        scrollPane.getViewport().setBackground(DesignSystem.surface());
        panel.add(scrollPane, BorderLayout.CENTER);

        refreshButton.addActionListener(e -> refreshEdges());
        removeButton.addActionListener(e -> {
            EdgeDisplayItem selected = edgeList.getSelectedValue();
            if (selected == null) {
                outputArea.setText("Select an edge to remove.");
                return;
            }
            controller.removeDisplayedEdge(selected);
            outputArea.setText("Edge removed.");
            refreshEdges();
            afterChange.run();
        });

        return panel;
    }

    public void refreshEdges() {
        edgeModel.clear();
        if (!controller.hasGraph()) {
            return;
        }
        List<EdgeDisplayItem> edges = controller.listEdges();
        for (EdgeDisplayItem edge : edges) {
            edgeModel.addElement(edge);
        }
    }
}
