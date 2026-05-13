package br.edu.grafo.gui.panel;

import br.edu.grafo.gui.GraphGuiController;
import br.edu.grafo.gui.component.SwingHelper;
import br.edu.grafo.gui.design.DesignSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoadSavePanel extends JPanel {
    private final GraphGuiController controller;
    private final Runnable afterChange;
    private final JTextArea outputArea;
    private final JComboBox<String> savedGraphs;
    private final JTextField fileField;

    public LoadSavePanel(GraphGuiController controller, Runnable afterChange) {
        this.controller = controller;
        this.afterChange = afterChange;
        this.fileField = SwingHelper.textField(16);
        this.savedGraphs = SwingHelper.comboBox(controller.savedGraphNames());
        setLayout(new BorderLayout(0, 12));
        setBackground(DesignSystem.bg());
        setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel forms = new JPanel(new GridLayout(2, 1, 0, 12));
        forms.setOpaque(false);
        forms.add(buildCreatePanel());
        forms.add(buildLoadSavePanel());

        outputArea = SwingHelper.resultArea();
        add(forms, BorderLayout.NORTH);
        add(SwingHelper.resultScroll(outputArea), BorderLayout.CENTER);
    }

    private JPanel buildCreatePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panel.setOpaque(false);

        JTextField verticesField = SwingHelper.textField(8);
        JButton createButton = SwingHelper.actionButton("Create New Graph");
        JButton curitibaButton = SwingHelper.actionButton("Load Curitiba Walk Graph");
        JButton solarSystemButton = SwingHelper.actionButton("Load Solar System Graph");
        JButton solarHyperspaceButton = SwingHelper.actionButton("Load Solar Hyperspace Graph");

        panel.add(SwingHelper.lbl("Vertices", SwingHelper.SANS_BD, DesignSystem.ink()));
        panel.add(verticesField);
        panel.add(createButton);
        panel.add(curitibaButton);
        panel.add(solarSystemButton);
        panel.add(solarHyperspaceButton);

        createButton.addActionListener(e -> {
            try {
                int numVertices = Integer.parseInt(verticesField.getText().trim());
                controller.createGraph(numVertices);
                outputArea.setText("Graph created with " + numVertices + " vertices.");
                afterChange.run();
            } catch (NumberFormatException ex) {
                outputArea.setText("Invalid vertex count.");
            }
        });

        curitibaButton.addActionListener(e -> {
            controller.loadCuritibaGraph();
            outputArea.setText("Curitiba walk graph loaded.");
            afterChange.run();
        });

        solarSystemButton.addActionListener(e -> {
            controller.loadSolarSystemGraph();
            outputArea.setText("Solar system graph loaded.");
            afterChange.run();
        });

        solarHyperspaceButton.addActionListener(e -> {
            controller.loadSolarSystemHyperspaceGraph();
            outputArea.setText("Solar system hyperspace graph loaded.");
            afterChange.run();
        });

        return panel;
    }

    private JPanel buildLoadSavePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
        panel.setOpaque(false);

        JButton reloadButton = SwingHelper.actionButton("Refresh Saved List");
        JButton loadButton = SwingHelper.actionButton("Load .bin");
        JButton saveButton = SwingHelper.actionButton("Save .bin");
        JPanel loadRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JPanel saveRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        loadRow.setOpaque(false);
        saveRow.setOpaque(false);

        savedGraphs.setPreferredSize(new Dimension(260, 28));
        fileField.setPreferredSize(new Dimension(220, 28));

        loadRow.add(SwingHelper.lbl("Saved Graphs", SwingHelper.SANS_BD, DesignSystem.ink()));
        loadRow.add(savedGraphs);
        loadRow.add(loadButton);
        loadRow.add(reloadButton);

        saveRow.add(SwingHelper.lbl("Save As", SwingHelper.SANS_BD, DesignSystem.ink()));
        saveRow.add(fileField);
        saveRow.add(saveButton);

        panel.add(loadRow);
        panel.add(saveRow);

        reloadButton.addActionListener(e -> {
            refreshSavedGraphs();
            outputArea.setText("Saved graph list refreshed.");
        });

        loadButton.addActionListener(e -> {
            String candidate = "";
            if (savedGraphs.getSelectedItem() != null) {
                candidate = savedGraphs.getSelectedItem().toString();
            }
            if (candidate.isEmpty()) {
                outputArea.setText("Choose a saved graph to load.");
                return;
            }
            if (controller.loadGraph(candidate)) {
                outputArea.setText("Graph loaded: " + candidate);
                fileField.setText(candidate);
                afterChange.run();
            } else {
                outputArea.setText("Could not load graph: " + candidate);
            }
        });

        saveButton.addActionListener(e -> {
            if (!controller.hasGraph()) {
                outputArea.setText("No graph loaded.");
                return;
            }
            String candidate = fileField.getText().trim();
            if (candidate.isEmpty()) {
                if (controller.hasPersistableGraphName()) {
                    candidate = controller.currentGraphName();
                    fileField.setText(candidate);
                } else {
                    outputArea.setText("Choose a file name to save.");
                    return;
                }
            }
            controller.saveGraph(candidate);
            outputArea.setText("Graph saved: " + candidate);
            refreshSavedGraphs();
            afterChange.run();
        });

        return panel;
    }

    private void refreshSavedGraphs() {
        savedGraphs.setModel(new DefaultComboBoxModel<>(controller.savedGraphNames()));
    }
}
