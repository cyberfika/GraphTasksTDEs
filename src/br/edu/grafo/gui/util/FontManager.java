package br.edu.grafo.gui.util;

import java.awt.*;

public final class FontManager {
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final boolean IS_MAC = OS_NAME.contains("mac");
    private static final boolean IS_WINDOWS = OS_NAME.contains("win");

    private FontManager() {
    }

    public static Font getMonospacedFont(int size) {
        String family = IS_MAC ? "Monaco" : IS_WINDOWS ? "Consolas" : "Monospaced";
        return new Font(family, Font.PLAIN, size);
    }

    public static Font getMonospacedBoldFont(int size) {
        String family = IS_MAC ? "Monaco" : IS_WINDOWS ? "Consolas" : "Monospaced";
        return new Font(family, Font.BOLD, size);
    }

    public static Font getSansSerifFont(int size) {
        String family = IS_MAC ? "Helvetica Neue" : IS_WINDOWS ? "Segoe UI" : "SansSerif";
        return new Font(family, Font.PLAIN, size);
    }

    public static Font getSansSerifBoldFont(int size) {
        String family = IS_MAC ? "Helvetica Neue" : IS_WINDOWS ? "Segoe UI" : "SansSerif";
        return new Font(family, Font.BOLD, size);
    }

    public static Font getSerifBoldFont(int size) {
        return new Font("Serif", Font.BOLD, size);
    }
}
