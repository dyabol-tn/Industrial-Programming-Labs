import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class DeviceDetailsDialog extends JDialog {
    private Factory device;
    private SimpleDateFormat dateFormat;

    public DeviceDetailsDialog(JFrame parent, Factory device) {
        super(parent, "Детали устройства", true);
        this.device = device;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        initializeUI();
    }

    private void initializeUI() {
        setSize(700, 500);
        setLocationRelativeTo(getParent());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Текстовая область с деталями
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        detailsArea.setText(getDeviceDetails());
        detailsArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(detailsArea);

        // Кнопка закрытия
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());

        // Кнопка копирования
        JButton copyButton = new JButton("Копировать в буфер");
        copyButton.addActionListener(e -> {
            String details = getDeviceDetails();
            Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new java.awt.datatransfer.StringSelection(details), null);
            JOptionPane.showMessageDialog(this,
                    "Данные скопированы в буфер обмена",
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(copyButton);
        buttonPanel.add(closeButton);

        // Сборка
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private String getDeviceDetails() {
        StringBuilder details = new StringBuilder();
        details.append("=".repeat(60)).append("\n");
        details.append("ДЕТАЛЬНАЯ ИНФОРМАЦИЯ ОБ УСТРОЙСТВЕ\n");
        details.append("=".repeat(60)).append("\n\n");

        // Общая информация
        details.append("[ ОБЩАЯ ИНФОРМАЦИЯ ]\n");
        details.append("-".repeat(40)).append("\n");
        details.append(String.format("%-20s: %s\n", "Тип устройства", device.getDeviceType()));
        details.append(String.format("%-20s: %s\n", "Модель", device.getModel()));
        details.append(String.format("%-20s: %s\n", "Номер модели", device.getModelNumber()));
        details.append(String.format("%-20s: %s\n", "Серийный номер", device.getSerialNumber()));
        details.append(String.format("%-20s: $%d\n", "Цена", device.getCost()));
        details.append(String.format("%-20s: %s\n", "Дата выпуска", dateFormat.format(device.getReleaseDate())));
        details.append("\n");

        // Специфичная информация
        if (device instanceof Computers) {
            Computers computer = (Computers) device;
            details.append("[ КОМПОНЕНТЫ КОМПЬЮТЕРА ]\n");
            details.append("-".repeat(40)).append("\n");
            details.append(String.format("%-20s: %s\n", "Корпус", computer.getCaseType()));
            details.append(String.format("%-20s: %s\n", "Форм-фактор", computer.getBodyFormFactor()));
            details.append(String.format("%-20s: %s\n", "Материнская плата", computer.getMotherboard()));
            details.append(String.format("%-20s: %s\n", "Процессор", computer.getProcessor()));
            details.append(String.format("%-20s: %s\n", "Жесткий диск", computer.getHardDrive()));
            details.append(String.format("%-20s: %s\n", "Оперативная память", computer.getRam()));
            details.append(String.format("%-20s: %s\n", "Блок питания", computer.getPowerSupply()));
            details.append(String.format("%-20s: %s\n", "WiFi модуль", computer.isWifiModule() ? "Да" : "Нет"));

        } else if (device instanceof Tablets) {
            Tablets tablet = (Tablets) device;
            details.append("[ ХАРАКТЕРИСТИКИ ПЛАНШЕТА ]\n");
            details.append("-".repeat(40)).append("\n");
            details.append(String.format("%-20s: %s\n", "Корпус", tablet.getCaseType()));
            details.append(String.format("%-20s: %s\n", "Процессор", tablet.getProcessor()));
            details.append(String.format("%-20s: %s\n", "Экран", tablet.getScreen()));
            details.append(String.format("%-20s: %s\n", "ОС", tablet.getOperatingSystem()));
            details.append(String.format("%-20s: %s\n", "WiFi модуль", tablet.isWifiModule() ? "Да" : "Нет"));
            details.append(String.format("%-20s: %s\n", "NFC чип", tablet.isChipNFC() ? "Да" : "Нет"));

        } else if (device instanceof Laptops) {
            Laptops laptop = (Laptops) device;
            details.append("[ ХАРАКТЕРИСТИКИ НОУТБУКА ]\n");
            details.append("-".repeat(40)).append("\n");
            details.append(String.format("%-20s: %s\n", "Корпус", laptop.getCaseType()));
            details.append(String.format("%-20s: %s\n", "Материнская плата", laptop.getMotherboard()));
            details.append(String.format("%-20s: %s\n", "Процессор", laptop.getProcessor()));
            details.append(String.format("%-20s: %s\n", "Жесткий диск", laptop.getHardDrive()));
            details.append(String.format("%-20s: %s\n", "Оперативная память", laptop.getRam()));
            details.append(String.format("%-20s: %s\n", "Блок питания", laptop.getPowerSupply()));
            details.append(String.format("%-20s: %s\n", "Клавиатура", laptop.getKeyboard()));
            details.append(String.format("%-20s: %s\n", "Динамики", laptop.getSpeakers()));
            details.append(String.format("%-20s: %s\n", "TouchPad", laptop.isTouchPad() ? "Да" : "Нет"));
            details.append(String.format("%-20s: %s\n", "NumPad", laptop.isNumPad() ? "Да" : "Нет"));
            details.append(String.format("%-20s: %s\n", "Сенсорный экран", laptop.isTouchScreen() ? "Да" : "Нет"));
        }

        details.append("\n").append("=".repeat(60)).append("\n");

        return details.toString();
    }
}