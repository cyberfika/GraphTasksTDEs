package br.edu.grafo.app;

import br.edu.grafo.gui.GraphGuiController;
import br.edu.grafo.gui.GraphMainWindow;
import br.edu.grafo.gui.design.DesignSystem;

import javax.swing.*;

public class GraphDesktopApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
        }
        DesignSystem.applyToUIManager();

        SwingUtilities.invokeLater(() -> new GraphMainWindow(new GraphGuiController()));
    }
}
