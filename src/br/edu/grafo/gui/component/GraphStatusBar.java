package br.edu.grafo.gui.component;

import br.edu.grafo.gui.design.DesignSystem;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class GraphStatusBar extends JPanel {
    private final JLabel panelLabel;
    private final JLabel graphLabel;
    private final JLabel clockLabel;

    public GraphStatusBar() {
        setLayout(new BorderLayout());
        setBackground(DesignSystem.bg());
        setPreferredSize(new Dimension(0, 24));
        setBorder(new MatteBorder(1, 0, 0, 0, DesignSystem.rule()));

        panelLabel = SwingHelper.lbl("OVERVIEW", SwingHelper.MONO_XS, DesignSystem.ink2());
        graphLabel = SwingHelper.lbl("NO GRAPH", SwingHelper.MONO_XS, DesignSystem.muted());
        clockLabel = SwingHelper.lbl(currentTime(), SwingHelper.MONO_XS, DesignSystem.muted());

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 3));
        left.setOpaque(false);
        left.add(SwingHelper.lbl("■", SwingHelper.MONO_XS, DesignSystem.ink()));
        left.add(SwingHelper.lbl("GRAPH TASKS", SwingHelper.MONO_XS, DesignSystem.ink()));
        left.add(graphLabel);
        left.add(SwingHelper.lbl("PANEL", SwingHelper.MONO_XS, DesignSystem.muted()));
        left.add(panelLabel);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 3));
        right.setOpaque(false);
        right.add(SwingHelper.lbl("BR.EDU.GRAFO.GUI", SwingHelper.MONO_XS, DesignSystem.muted()));
        right.add(clockLabel);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);

        new Timer(1000, e -> clockLabel.setText(currentTime())).start();
    }

    public void setActivePanel(String panelName) {
        panelLabel.setText(panelName.toUpperCase());
    }

    public void setGraphStatus(String status) {
        graphLabel.setText(status.toUpperCase());
    }

    private static String currentTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
