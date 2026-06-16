package br.edu.grafo.gui.panel;

import br.edu.grafo.gui.GraphGuiController;
import br.edu.grafo.gui.component.SwingHelper;
import br.edu.grafo.gui.design.DesignSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CliqueTestPanel extends JPanel {
    private final GraphGuiController controller;
    private final JList<String> vertexList;
    private final DefaultListModel<String> listModel;
    private final JLabel selectedLabel;
    private final JTextArea outputArea;
    private final List<Integer> selectedVertices;

    public CliqueTestPanel(GraphGuiController controller) {
        this.controller = controller;
        this.selectedVertices = new ArrayList<>();
        this.listModel = new DefaultListModel<>();
        this.vertexList = new JList<>(listModel);
        this.selectedLabel = SwingHelper.lbl("Selected Vertices: none", SwingHelper.SANS_BD, DesignSystem.ink());
        this.outputArea = SwingHelper.resultArea();

        setLayout(new BorderLayout(0, 12));
        setBackground(DesignSystem.bg());
        setBorder(new EmptyBorder(18, 18, 18, 18));

        // Top section: vertex selection
        JPanel topSection = createTopSection();
        add(topSection, BorderLayout.NORTH);

        // Center: output area
        add(SwingHelper.resultScroll(outputArea), BorderLayout.CENTER);

        refreshVertexList();
    }

    private JPanel createTopSection() {
        JPanel top = new JPanel(new BorderLayout(12, 12));
        top.setOpaque(false);

        // Left: vertex list with selection
        JPanel listPanel = new JPanel(new BorderLayout(0, 8));
        listPanel.setOpaque(false);
        listPanel.add(SwingHelper.lbl("Available Vertices", SwingHelper.MONO_XS, DesignSystem.faint()),
                      BorderLayout.NORTH);

        vertexList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vertexList.setBackground(DesignSystem.bg());
        vertexList.setForeground(DesignSystem.ink());
        JScrollPane listScroll = new JScrollPane(vertexList);
        listScroll.setPreferredSize(new Dimension(150, 200));
        listPanel.add(listScroll, BorderLayout.CENTER);

        // Buttons to add/remove vertices
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        buttonPanel.setOpaque(false);
        JButton addButton = SwingHelper.actionButton("Add →");
        JButton removeButton = SwingHelper.actionButton("← Remove All");
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        listPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Right: selected vertices and test buttons
        JPanel rightPanel = new JPanel(new BorderLayout(0, 12));
        rightPanel.setOpaque(false);
        rightPanel.add(selectedLabel, BorderLayout.NORTH);

        JPanel testButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        testButtonsPanel.setOpaque(false);
        JButton componentesButton = SwingHelper.actionButton("Find Components");
        JButton cliqueButton = SwingHelper.actionButton("Test Clique");
        JButton maximalButton = SwingHelper.actionButton("Test Maximal");
        testButtonsPanel.add(componentesButton);
        testButtonsPanel.add(cliqueButton);
        testButtonsPanel.add(maximalButton);
        rightPanel.add(testButtonsPanel, BorderLayout.CENTER);

        top.add(listPanel, BorderLayout.WEST);
        top.add(rightPanel, BorderLayout.CENTER);

        // Add listeners
        addButton.addActionListener(e -> addSelectedVertex());
        removeButton.addActionListener(e -> removeAllVertices());
        componentesButton.addActionListener(e -> runFindComponents());
        cliqueButton.addActionListener(e -> runTestClique());
        maximalButton.addActionListener(e -> runTestMaximal());

        return top;
    }

    private void addSelectedVertex() {
        if (!controller.hasGraph()) {
            outputArea.setText("No graph loaded.");
            return;
        }
        int index = vertexList.getSelectedIndex();
        if (index < 0) {
            outputArea.setText("Please select a vertex from the list.");
            return;
        }

        Integer vertexId = index;
        if (!selectedVertices.contains(vertexId)) {
            selectedVertices.add(vertexId);
            updateSelectedLabel();
        }
    }

    private void removeAllVertices() {
        selectedVertices.clear();
        updateSelectedLabel();
        outputArea.setText("");
    }

    private void updateSelectedLabel() {
        if (selectedVertices.isEmpty()) {
            selectedLabel.setText("Selected Vertices: none");
        } else {
            selectedLabel.setText("Selected Vertices: " + selectedVertices);
        }
    }

    private void runFindComponents() {
        if (!controller.hasGraph()) {
            outputArea.setText("No graph loaded.");
            return;
        }
        StringBuilder builder = new StringBuilder();
        int numComponents = controller.findComponents();
        builder.append("Total components: ").append(numComponents).append('\n');
        outputArea.setText(builder.toString());
    }

    private void runTestClique() {
        if (!controller.hasGraph()) {
            outputArea.setText("No graph loaded.");
            return;
        }
        if (selectedVertices.isEmpty()) {
            outputArea.setText("Please select at least one vertex to test.");
            return;
        }
        boolean isClique = controller.isClique(selectedVertices);
        outputArea.setText("Vertices " + selectedVertices + " form a clique: " + isClique);
    }

    private void runTestMaximal() {
        if (!controller.hasGraph()) {
            outputArea.setText("No graph loaded.");
            return;
        }
        if (selectedVertices.isEmpty()) {
            outputArea.setText("Please select at least one vertex to test.");
            return;
        }
        boolean isMaximal = controller.isMaximal(selectedVertices);
        outputArea.setText("Vertices " + selectedVertices + " form a maximal clique: " + isMaximal);
    }

    public void refreshVertexList() {
        listModel.clear();
        selectedVertices.clear();
        updateSelectedLabel();

        if (controller.hasGraph()) {
            List<String> names = controller.listVertexNames();
            for (String name : names) {
                listModel.addElement(name);
            }
        }
    }
}
