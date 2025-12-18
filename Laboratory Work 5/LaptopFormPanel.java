import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LaptopFormPanel extends JPanel {
    private JTextField modelField;
    private JTextField modelNumberField;
    private JTextField serialNumberField;
    private JTextField costField;
    private JTextField dateField;
    private JTextField caseTypeField;
    private JTextField motherboardField;
    private JTextField processorField;
    private JTextField hardDriveField;
    private JTextField ramField;
    private JTextField powerSupplyField;
    private JTextField keyboardField;
    private JTextField speakersField;
    private JCheckBox touchPadCheckBox;
    private JCheckBox numPadCheckBox;
    private JCheckBox touchScreenCheckBox;

    private SimpleDateFormat dateFormat;

    public LaptopFormPanel() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Основная информация
        JPanel basicPanel = createBasicInfoPanel();
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        add(basicPanel, gbc);

        // Компоненты ноутбука
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Корпус:"), gbc);

        gbc.gridx = 1;
        caseTypeField = new JTextField(25);
        add(caseTypeField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Материнская плата:"), gbc);

        gbc.gridx = 1;
        motherboardField = new JTextField(25);
        add(motherboardField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Процессор:"), gbc);

        gbc.gridx = 1;
        processorField = new JTextField(25);
        add(processorField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Жесткий диск:"), gbc);

        gbc.gridx = 1;
        hardDriveField = new JTextField(25);
        add(hardDriveField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Оперативная память:"), gbc);

        gbc.gridx = 1;
        ramField = new JTextField(25);
        add(ramField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Блок питания:"), gbc);

        gbc.gridx = 1;
        powerSupplyField = new JTextField(25);
        add(powerSupplyField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Клавиатура:"), gbc);

        gbc.gridx = 1;
        keyboardField = new JTextField(25);
        add(keyboardField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Динамики:"), gbc);

        gbc.gridx = 1;
        speakersField = new JTextField(25);
        add(speakersField, gbc);
        row++;

        // Чекбоксы
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("TouchPad:"), gbc);

        gbc.gridx = 1;
        touchPadCheckBox = new JCheckBox("Да");
        add(touchPadCheckBox, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("NumPad:"), gbc);

        gbc.gridx = 1;
        numPadCheckBox = new JCheckBox("Да");
        add(numPadCheckBox, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Сенсорный экран:"), gbc);

        gbc.gridx = 1;
        touchScreenCheckBox = new JCheckBox("Да");
        add(touchScreenCheckBox, gbc);
    }

    private JPanel createBasicInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Основная информация"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Модель:"), gbc);

        gbc.gridx = 1;
        modelField = new JTextField(25);
        panel.add(modelField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Номер модели:"), gbc);

        gbc.gridx = 1;
        modelNumberField = new JTextField(25);
        panel.add(modelNumberField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Серийный номер:"), gbc);

        gbc.gridx = 1;
        serialNumberField = new JTextField(25);
        panel.add(serialNumberField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Цена ($):"), gbc);

        gbc.gridx = 1;
        costField = new JTextField(25);
        panel.add(costField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Дата выпуска (дд/мм/гггг):"), gbc);

        gbc.gridx = 1;
        dateField = new JTextField(dateFormat.format(new Date()), 25);
        panel.add(dateField, gbc);

        return panel;
    }

    public Laptops createDevice() throws ParseException, NumberFormatException, IllegalStateException {
        // Валидация полей
        if (modelField.getText().trim().isEmpty()) {
            throw new IllegalStateException("Поле 'Модель' не может быть пустым");
        }
        if (serialNumberField.getText().trim().isEmpty()) {
            throw new IllegalStateException("Поле 'Серийный номер' не может быть пустым");
        }
        if (costField.getText().trim().isEmpty()) {
            throw new IllegalStateException("Поле 'Цена' не может быть пустым");
        }

        // Парсинг данных
        String model = modelField.getText().trim();
        String modelNumber = modelNumberField.getText().trim();
        String serialNumber = serialNumberField.getText().trim();
        int cost = Integer.parseInt(costField.getText().trim());
        Date releaseDate = dateFormat.parse(dateField.getText().trim());

        // Создание устройства через Builder
        return new LaptopBuilder()
                .setModel(model)
                .setModelNumber(modelNumber)
                .setSerialNumber(serialNumber)
                .setCost(cost)
                .setReleaseDate(releaseDate)
                .setCaseType(caseTypeField.getText().trim())
                .setMotherboard(motherboardField.getText().trim())
                .setProcessor(processorField.getText().trim())
                .setHardDrive(hardDriveField.getText().trim())
                .setRam(ramField.getText().trim())
                .setPowerSupply(powerSupplyField.getText().trim())
                .setKeyboard(keyboardField.getText().trim())
                .setSpeakers(speakersField.getText().trim())
                .setTouchPad(touchPadCheckBox.isSelected())
                .setNumPad(numPadCheckBox.isSelected())
                .setTouchScreen(touchScreenCheckBox.isSelected())
                .build();
    }

    public void clearForm() {
        modelField.setText("");
        modelNumberField.setText("");
        serialNumberField.setText("");
        costField.setText("");
        dateField.setText(dateFormat.format(new Date()));
        caseTypeField.setText("");
        motherboardField.setText("");
        processorField.setText("");
        hardDriveField.setText("");
        ramField.setText("");
        powerSupplyField.setText("");
        keyboardField.setText("");
        speakersField.setText("");
        touchPadCheckBox.setSelected(false);
        numPadCheckBox.setSelected(false);
        touchScreenCheckBox.setSelected(false);
    }
}