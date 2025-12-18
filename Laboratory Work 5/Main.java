import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Устанавливаем Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Настраиваем внешний вид
            UIManager.put("TabbedPane.selected", java.awt.Color.LIGHT_GRAY);
            UIManager.put("Button.font", new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
            UIManager.put("Label.font", new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
            UIManager.put("TextField.font", new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
            UIManager.put("TextArea.font", new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Создаем и показываем GUI
        SwingUtilities.invokeLater(() -> {
            DeviceManagerGUI gui = new DeviceManagerGUI();
            gui.setVisible(true);
        });
    }
}