package br.edu.grafo.gui.design;

import javax.swing.*;
import java.awt.*;

/**
 * Sistema visual monocromatico da interface Swing.
 */
public final class DesignSystem {
    public static final Color BG = new Color(10, 10, 10);
    public static final Color BG_2 = new Color(18, 18, 18);
    public static final Color SURFACE = new Color(24, 24, 24);
    public static final Color SURFACE_2 = new Color(32, 32, 32);
    public static final Color INK = new Color(255, 255, 255);
    public static final Color INK_2 = new Color(232, 232, 232);
    public static final Color MUTED = new Color(200, 200, 200);
    public static final Color FAINT = new Color(166, 166, 166);
    public static final Color RULE = new Color(70, 70, 70);
    public static final Color RULE_2 = new Color(48, 48, 48);
    public static final Color ACCENT = INK;

    private DesignSystem() {
    }

    public static void applyToUIManager() {
        UIManager.put("Panel.background", bg());
        UIManager.put("OptionPane.background", bg());
        UIManager.put("OptionPane.messageForeground", ink());
        UIManager.put("Viewport.background", bg());
        UIManager.put("Label.foreground", ink());
        UIManager.put("CheckBox.foreground", ink());
        UIManager.put("RadioButton.foreground", ink());
        UIManager.put("TabbedPane.foreground", ink());
        UIManager.put("TitledBorder.titleColor", ink());

        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.foreground", Color.BLACK);
        UIManager.put("ComboBox.selectionBackground", Color.BLACK);
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);
        UIManager.put("TextField.background", surface());
        UIManager.put("TextField.foreground", ink());
        UIManager.put("TextField.caretForeground", ink());
        UIManager.put("TextArea.background", surface());
        UIManager.put("TextArea.foreground", ink());
        UIManager.put("TextArea.caretForeground", ink());
        UIManager.put("ScrollBar.background", bg());
        UIManager.put("Button.background", surface());
        UIManager.put("Button.foreground", ink());
        UIManager.put("List.background", surface());
        UIManager.put("List.foreground", ink());
    }

    public static Color bg() { return BG; }
    public static Color bg2() { return BG_2; }
    public static Color surface() { return SURFACE; }
    public static Color surface2() { return SURFACE_2; }
    public static Color ink() { return INK; }
    public static Color ink2() { return INK_2; }
    public static Color muted() { return MUTED; }
    public static Color faint() { return FAINT; }
    public static Color rule() { return RULE; }
    public static Color rule2() { return RULE_2; }
    public static Color accent() { return ACCENT; }
}
