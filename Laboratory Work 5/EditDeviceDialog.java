import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class EditDeviceDialog extends JDialog {
    private Factory device;
    private boolean updated = false;
    private SimpleDateFormat dateFormat;

    private JTextField modelField;
    private JTextField modelNumberField;
    private JTextField serialNumberField;
    private JTextField costField;
    private JTextField dateField;

    public EditDeviceDialog(JFrame parent, Factory device) {
        super(parent, "Редактирование устройства", true);
        this.device = device;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        initializeUI();
    }

    private void initializeUI() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Форма
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Заполняем поля текущими значениями
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Модель:"), gbc);

        gbc.gridx = 1;
        modelField = new JTextField(device.getModel(), 20);
        formPanel.add(modelField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Номер модели:"), gbc);

        gbc.gridx = 1;
        modelNumberField = new JTextField(device.getModelNumber(), 20);
        formPanel.add(modelNumberField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Серийный номер:"), gbc);

        gbc.gridx = 1;
        serialNumberField = new JTextField(device.getSerialNumber(), 20);
        serialNumberField.setEditable(false); // Серийный номер нельзя менять
        serialNumberField.setBackground(Color.LIGHT_GRAY);
        formPanel.add(serialNumberField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Цена ($):"), gbc);

        gbc.gridx = 1;
        costField = new JTextField(String.valueOf(device.getCost()), 20);
        formPanel.add(costField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Дата выпуска:"), gbc);

        gbc.gridx = 1;
        dateField = new JTextField(dateFormat.format(device.getReleaseDate()), 20);
        formPanel.add(dateField, gbc);

        // Добавляем специфичные поля в зависимости от типа устройства
        if (device instanceof Computers) {
            Computers computer = (Computers) device;
            row++;

            gbc.gridx = 0;
            gbc.gridy = row;
            formPanel.add(new JLabel("WiFi модуль:"), gbc);

            gbc.gridx = 1;
            JCheckBox wifiCheckBox = new JCheckBox("Да");
            wifiCheckBox.setSelected(computer.isWifiModule());
            formPanel.add(wifiCheckBox, gbc);
        } else if (device instanceof Tablets) {
            Tablets tablet = (Tablets) device;
            row++;

            gbc.gridx = 0;
            gbc.gridy = row;
            formPanel.add(new JLabel("NFC чип:"), gbc);

            gbc.gridx = 1;
            JCheckBox nfcCheckBox = new JCheckBox("Да");
            nfcCheckBox.setSelected(tablet.isChipNFC());
            formPanel.add(nfcCheckBox, gbc);
        }

        // Кнопки
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");

        saveButton.addActionListener(e -> {
            try {
                // Валидация данных
                if (modelField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Поле 'Модель' не может быть пустым",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (costField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Поле 'Цена' не может быть пустым",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Обновление данных устройства
                device.setModel(modelField.getText().trim());
                device.setModelNumber(modelNumberField.getText().trim());
                device.setCost(Integer.parseInt(costField.getText().trim()));
                device.setReleaseDate(dateFormat.parse(dateField.getText().trim()));

                updated = true;
                dispose();

                JOptionPane.showMessageDialog(this,
                        "Изменения успешно сохранены",
                        "Успех",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Неверный формат цены. Укажите целое число",
                        "Ошибка формата",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Ошибка: " + ex.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Сборка
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public boolean isUpdated() {
        return updated;
    }
}