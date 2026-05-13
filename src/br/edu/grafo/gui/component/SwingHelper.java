package br.edu.grafo.gui.component;

import br.edu.grafo.gui.design.DesignSystem;
import br.edu.grafo.gui.util.FontManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public final class SwingHelper {
    public static final Font MONO_MD = FontManager.getMonospacedFont(12);
    public static final Font MONO_SM = FontManager.getMonospacedFont(11);
    public static final Font MONO_XS = FontManager.getMonospacedFont(10);
    public static final Font SANS_BD = FontManager.getSansSerifBoldFont(13);
    public static final Font SANS_MD = FontManager.getSansSerifFont(12);
    public static final Font SERIF_XL = FontManager.getSerifBoldFont(24);

    private SwingHelper() {
    }

    public static JLabel lbl(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    public static JTextArea resultArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(MONO_SM);
        area.setBackground(DesignSystem.surface());
        area.setForeground(DesignSystem.ink());
        area.setCaretColor(DesignSystem.ink());
        area.setBorder(new EmptyBorder(14, 18, 14, 18));
        return area;
    }

    public static JLabel formLabel(String text) {
        return lbl(text, SANS_BD, DesignSystem.ink());
    }

    public static JButton actionButton(String text) {
        JButton button = new JButton(text);
        button.setFont(SANS_BD);
        button.setForeground(DesignSystem.ink());
        button.setBackground(DesignSystem.surface2());
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 12, 8, 12));
        return button;
    }

    public static JTextField textField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(SANS_MD);
        field.setForeground(DesignSystem.ink());
        field.setCaretColor(DesignSystem.ink());
        field.setBackground(DesignSystem.surface());
        field.setBorder(new LineBorder(DesignSystem.rule(), 1));
        return field;
    }

    public static <T> JComboBox<T> comboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setFont(SANS_MD);
        comboBox.setForeground(Color.BLACK);
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(new LineBorder(DesignSystem.rule(), 1));
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel component = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    component.setBackground(Color.BLACK);
                    component.setForeground(Color.WHITE);
                } else {
                    component.setBackground(Color.WHITE);
                    component.setForeground(Color.BLACK);
                }
                component.setOpaque(true);
                return component;
            }
        });
        return comboBox;
    }

    public static <T> void styleList(JList<T> list) {
        list.setFont(SANS_MD);
        list.setForeground(DesignSystem.ink());
        list.setBackground(DesignSystem.surface());
        list.setSelectionForeground(DesignSystem.ink());
        list.setSelectionBackground(DesignSystem.surface2());
    }

    public static JScrollPane resultScroll(JTextArea area) {
        JScrollPane scroll = new JScrollPane(area);
        scroll.getViewport().setBackground(DesignSystem.surface());
        styleScrollBar(scroll.getVerticalScrollBar());
        return scroll;
    }

    public static void styleScrollBar(JScrollBar scrollBar) {
        scrollBar.setBackground(DesignSystem.bg());
        scrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = DesignSystem.rule();
                trackColor = DesignSystem.bg();
            }
        });
    }
}
