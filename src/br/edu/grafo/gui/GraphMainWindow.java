package br.edu.grafo.gui;

import br.edu.grafo.gui.component.GraphSidebar;
import br.edu.grafo.gui.component.GraphStatusBar;
import br.edu.grafo.gui.design.DesignSystem;
import br.edu.grafo.gui.panel.AlgorithmsPanel;
import br.edu.grafo.gui.panel.GraphEditPanel;
import br.edu.grafo.gui.panel.LoadSavePanel;
import br.edu.grafo.gui.panel.OverviewPanel;
import br.edu.grafo.gui.panel.ShortestPathPanel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static br.edu.grafo.gui.component.SwingHelper.MONO_MD;
import static br.edu.grafo.gui.component.SwingHelper.MONO_SM;
import static br.edu.grafo.gui.component.SwingHelper.actionButton;
import static br.edu.grafo.gui.component.SwingHelper.lbl;

public class GraphMainWindow extends JFrame {
    private final GraphGuiController controller;
    private final CardLayout cardLayout;
    private final JPanel mainCards;
    private final GraphSidebar sidebar;
    private final GraphStatusBar statusBar;

    private final OverviewPanel overviewPanel;
    private final LoadSavePanel loadSavePanel;
    private final GraphEditPanel graphEditPanel;
    private final ShortestPathPanel shortestPathPanel;
    private final AlgorithmsPanel algorithmsPanel;

    public GraphMainWindow(GraphGuiController controller) {
        super("Graph Tasks GUI");
        this.controller = controller;
        this.cardLayout = new CardLayout();
        this.mainCards = new JPanel(cardLayout);
        this.sidebar = new GraphSidebar(controller);
        this.statusBar = new GraphStatusBar();

        this.overviewPanel = new OverviewPanel(controller);
        this.loadSavePanel = new LoadSavePanel(controller, this::refreshAll);
        this.graphEditPanel = new GraphEditPanel(controller, this::refreshAll);
        this.shortestPathPanel = new ShortestPathPanel(controller);
        this.algorithmsPanel = new AlgorithmsPanel(controller);

        buildFrame();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void buildFrame() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(DesignSystem.bg());
        root.add(buildTopbar(), BorderLayout.NORTH);
        root.add(sidebar, BorderLayout.WEST);
        root.add(buildMainArea(), BorderLayout.CENTER);
        root.add(statusBar, BorderLayout.SOUTH);
        setContentPane(root);
    }

    private JPanel buildTopbar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(DesignSystem.bg());
        bar.setPreferredSize(new Dimension(0, 44));
        bar.setBorder(new MatteBorder(0, 0, 1, 0, DesignSystem.rule()));

        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        brand.setOpaque(false);
        brand.add(lbl("[]", new Font(Font.SANS_SERIF, Font.BOLD, 16), DesignSystem.ink()));
        brand.add(lbl("GRAPH TASKS GUI", MONO_MD, DesignSystem.ink()));

        JPanel tabs = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tabs.setOpaque(false);
        tabs.add(tabButton("overview", "01 OVERVIEW"));
        tabs.add(tabButton("loadsave", "02 LOAD/SAVE"));
        tabs.add(tabButton("edit", "03 EDIT"));
        tabs.add(tabButton("path", "04 PATHS"));
        tabs.add(tabButton("algorithms", "05 ALGORITHMS"));

        bar.add(brand, BorderLayout.WEST);
        bar.add(tabs, BorderLayout.CENTER);
        return bar;
    }

    private JButton tabButton(String card, String label) {
        JButton button = actionButton(label);
        button.setFont(MONO_SM);
        button.setBackground(DesignSystem.bg2());
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width + 16, 44));
        button.addActionListener(e -> {
            cardLayout.show(mainCards, card);
            statusBar.setActivePanel(label.replaceFirst("^\\d+\\s+", ""));
        });
        return button;
    }

    private JPanel buildMainArea() {
        mainCards.setBackground(DesignSystem.bg());
        mainCards.add(overviewPanel, "overview");
        mainCards.add(loadSavePanel, "loadsave");
        mainCards.add(graphEditPanel, "edit");
        mainCards.add(shortestPathPanel, "path");
        mainCards.add(algorithmsPanel, "algorithms");
        cardLayout.show(mainCards, "overview");
        return mainCards;
    }

    private void refreshAll() {
        sidebar.refresh();
        graphEditPanel.refreshEdges();
        overviewPanel.showInfo();
        statusBar.setGraphStatus(controller.hasGraph() ? controller.currentGraphName() : "No graph");
    }
}
