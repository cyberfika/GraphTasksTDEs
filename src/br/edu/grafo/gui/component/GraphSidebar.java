package br.edu.grafo.gui.component;

import br.edu.grafo.gui.design.DesignSystem;
import br.edu.grafo.gui.GraphGuiController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.List;

public final class GraphSidebar extends JPanel {
    private final GraphGuiController controller;
    private final JTextArea detailsArea;

    public GraphSidebar(GraphGuiController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(DesignSystem.bg());
        setPreferredSize(new Dimension(280, 0));
        setBorder(new MatteBorder(0, 0, 0, 1, DesignSystem.rule()));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(DesignSystem.bg());
        header.setBorder(new EmptyBorder(12, 14, 12, 14));
        header.add(SwingHelper.lbl("GRAPH CONTEXT", SwingHelper.MONO_XS, DesignSystem.faint()));
        header.add(Box.createVerticalStrut(8));
        header.add(SwingHelper.lbl("Current Graph", SwingHelper.SANS_BD, DesignSystem.ink()));
        add(header, BorderLayout.NORTH);

        detailsArea = SwingHelper.resultArea();
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        add(SwingHelper.resultScroll(detailsArea), BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        if (!controller.hasGraph()) {
            detailsArea.setText("No graph loaded.\n\nUse the Load/Save panel to create a graph, load a .bin file, or load the Curitiba walk graph.");
            return;
        }

        List<String> names = controller.listVertexNames();
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(controller.currentGraphName()).append('\n');
        builder.append("Vertices: ").append(controller.vertexCount()).append('\n');
        builder.append("Edges: ").append(controller.edgeCount()).append('\n');
        builder.append("Unique connections: ").append(controller.uniqueConnectionCount()).append('\n');
        builder.append("Density: ").append(String.format("%.2f%%", controller.densityPercent())).append("\n\n");
        builder.append("All vertices:\n");
        for (String name : names) {
            builder.append(" - ").append(name).append('\n');
        }
        detailsArea.setText(builder.toString());
        detailsArea.setForeground(DesignSystem.ink());
        detailsArea.setCaretPosition(0);
    }
}
