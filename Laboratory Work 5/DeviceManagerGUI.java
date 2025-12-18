import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeviceManagerGUI extends JFrame {
    private Storage<Factory> listStorage;
    private Storage<Factory> mapStorage;
    private DeviceDirector deviceDirector;
    private ExecutorService executorService;

    // Компоненты GUI
    private JTabbedPane tabbedPane;
    private JTable deviceTable;
    private DefaultTableModel deviceTableModel;
    private JTextArea logArea;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private TableRowSorter<DefaultTableModel> tableSorter;

    // Форматы
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat displayDateFormat;

    // Диалоги
    private ComputerFormPanel computerForm;
    private TabletFormPanel tabletForm;
    private LaptopFormPanel laptopForm;

    // Константы для путей файлов
    private String defaultDirectory = "data/";

    public DeviceManagerGUI() {
        // Инициализация хранилищ
        listStorage = new ListObjects<>();
        mapStorage = new MapObjects<>();
        deviceDirector = new DeviceDirector();
        executorService = Executors.newFixedThreadPool(3);

        // Инициализация форматов
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        displayDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Настройка окна
        setTitle("Управление устройствами - GUI версия");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Создаем главное меню
        setJMenuBar(createMenuBar());

        // Инициализация GUI
        initializeComponents();

        // Проверяем папку data
        checkDataDirectory();

        // Запускаем поток автосохранения
        startAutoSaveThread();

        // Загружаем тестовые данные при запуске (опционально)
        loadInitialData();
    }

    private void initializeComponents() {
        // Главный контейнер
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Создаем вкладки
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("📱 Устройства", createDeviceTab());
        tabbedPane.addTab("➕ Добавить", createAddDeviceTab());
        tabbedPane.addTab("🔍 Поиск", createSearchTab());
        tabbedPane.addTab("📁 Файлы", createFileOperationsTab());
        tabbedPane.addTab("📊 Статистика", createStatisticsTab());
        tabbedPane.addTab("🔨 Builder", createBuilderTab());
        tabbedPane.addTab("⚙️ Настройки", createSettingsTab());

        // Панель состояния
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEtchedBorder());

        statusLabel = new JLabel(" Готово. Устройств: 0");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("Готово");

        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(progressBar, BorderLayout.CENTER);

        // Лог операций
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Лог операций"));

        logArea = new JTextArea(8, 60);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setPreferredSize(new Dimension(800, 150));

        JButton clearLogBtn = new JButton("Очистить лог");
        clearLogBtn.addActionListener(e -> logArea.setText(""));

        logPanel.add(logScroll, BorderLayout.CENTER);
        logPanel.add(clearLogBtn, BorderLayout.EAST);

        // Сборка интерфейса
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        contentPane.add(logPanel, BorderLayout.SOUTH);
        contentPane.add(statusPanel, BorderLayout.NORTH);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Меню Файл
        JMenu fileMenu = new JMenu("Файл");

        JMenuItem openTxtItem = new JMenuItem("📄 Открыть TXT...");
        JMenuItem openXmlItem = new JMenuItem("📄 Открыть XML...");
        JMenuItem openJsonItem = new JMenuItem("📄 Открыть JSON...");
        JMenuItem saveTxtItem = new JMenuItem("💾 Сохранить как TXT...");
        JMenuItem saveXmlItem = new JMenuItem("💾 Сохранить как XML...");
        JMenuItem saveJsonItem = new JMenuItem("💾 Сохранить как JSON...");
        JMenuItem exitItem = new JMenuItem("🚪 Выход");

        openTxtItem.addActionListener(e -> readFileInThread("TXT"));
        openXmlItem.addActionListener(e -> readFileInThread("XML"));
        openJsonItem.addActionListener(e -> readFileInThread("JSON"));
        saveTxtItem.addActionListener(e -> writeFileInThread("TXT"));
        saveXmlItem.addActionListener(e -> writeFileInThread("XML"));
        saveJsonItem.addActionListener(e -> writeFileInThread("JSON"));
        exitItem.addActionListener(e -> exitApplication());

        fileMenu.add(openTxtItem);
        fileMenu.add(openXmlItem);
        fileMenu.add(openJsonItem);
        fileMenu.addSeparator();
        fileMenu.add(saveTxtItem);
        fileMenu.add(saveXmlItem);
        fileMenu.add(saveJsonItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Меню Устройства
        JMenu deviceMenu = new JMenu("Устройства");

        JMenuItem addComputerItem = new JMenuItem("🖥️ Добавить компьютер");
        JMenuItem addTabletItem = new JMenuItem("📱 Добавить планшет");
        JMenuItem addLaptopItem = new JMenuItem("💻 Добавить ноутбук");
        JMenuItem editItem = new JMenuItem("✏️ Редактировать");
        JMenuItem deleteItem = new JMenuItem("🗑️ Удалить");
        JMenuItem refreshItem = new JMenuItem("🔄 Обновить");
        JMenuItem clearAllItem = new JMenuItem("🧹 Очистить все");

        addComputerItem.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        addTabletItem.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        addLaptopItem.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        editItem.addActionListener(e -> editSelectedDevice());
        deleteItem.addActionListener(e -> deleteSelectedDevice());
        refreshItem.addActionListener(e -> refreshDeviceTable());
        clearAllItem.addActionListener(e -> clearAllDevices());

        deviceMenu.add(addComputerItem);
        deviceMenu.add(addTabletItem);
        deviceMenu.add(addLaptopItem);
        deviceMenu.addSeparator();
        deviceMenu.add(editItem);
        deviceMenu.add(deleteItem);
        deviceMenu.addSeparator();
        deviceMenu.add(refreshItem);
        deviceMenu.add(clearAllItem);

        // Меню Сервис
        JMenu serviceMenu = new JMenu("Сервис");

        JMenuItem encryptItem = new JMenuItem("🔒 Шифровать файл");
        JMenuItem decryptItem = new JMenuItem("🔓 Дешифровать файл");
        JMenuItem zipItem = new JMenuItem("🗜️ Создать ZIP архив");
        JMenuItem unzipItem = new JMenuItem("🗜️ Распаковать архив");

        encryptItem.addActionListener(e -> showEncryptDialog());
        decryptItem.addActionListener(e -> showDecryptDialog());
        zipItem.addActionListener(e -> showZipDialog());
        unzipItem.addActionListener(e -> showUnzipDialog());

        serviceMenu.add(encryptItem);
        serviceMenu.add(decryptItem);
        serviceMenu.add(zipItem);
        serviceMenu.add(unzipItem);

        // Меню Помощь
        JMenu helpMenu = new JMenu("Помощь");

        JMenuItem aboutItem = new JMenuItem("ℹ️ О программе");
        JMenuItem helpItem = new JMenuItem("❓ Справка");

        aboutItem.addActionListener(e -> showAboutDialog());
        helpItem.addActionListener(e -> showHelpDialog());

        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(deviceMenu);
        menuBar.add(serviceMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private JPanel createDeviceTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель инструментов
        JPanel toolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolPanel.setBorder(BorderFactory.createEtchedBorder());

        JButton refreshBtn = new JButton("🔄 Обновить");
        JButton addBtn = new JButton("➕ Добавить");
        JButton editBtn = new JButton("✏️ Редактировать");
        JButton deleteBtn = new JButton("🗑️ Удалить");
        JButton viewBtn = new JButton("👁️ Просмотр");
        JButton exportBtn = new JButton("📤 Экспорт");

        refreshBtn.addActionListener(e -> refreshDeviceTable());
        addBtn.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        editBtn.addActionListener(e -> editSelectedDevice());
        deleteBtn.addActionListener(e -> deleteSelectedDevice());
        viewBtn.addActionListener(e -> viewSelectedDevice());
        exportBtn.addActionListener(e -> exportToFile());

        // Устанавливаем всплывающие подсказки
        refreshBtn.setToolTipText("Обновить таблицу устройств");
        addBtn.setToolTipText("Добавить новое устройство");
        editBtn.setToolTipText("Редактировать выбранное устройство");
        deleteBtn.setToolTipText("Удалить выбранное устройство");
        viewBtn.setToolTipText("Просмотреть детали устройства");
        exportBtn.setToolTipText("Экспортировать устройства в файл");

        toolPanel.add(refreshBtn);
        toolPanel.add(addBtn);
        toolPanel.add(editBtn);
        toolPanel.add(deleteBtn);
        toolPanel.add(viewBtn);
        toolPanel.add(exportBtn);

        // Таблица устройств
        String[] columns = {"Тип", "Модель", "Модельный номер", "Серийный номер",
                "Цена ($)", "Дата выпуска", "Дополнительно"};

        deviceTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) return Integer.class;
                return String.class;
            }
        };

        deviceTable = new JTable(deviceTableModel);
        deviceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        deviceTable.setRowHeight(25);
        deviceTable.getTableHeader().setReorderingAllowed(false);

        // Настраиваем сортировку таблицы
        tableSorter = new TableRowSorter<>(deviceTableModel);
        deviceTable.setRowSorter(tableSorter);

        // Устанавливаем компараторы для сортировки
        tableSorter.setComparator(4, (o1, o2) -> {
            Integer i1 = Integer.parseInt(o1.toString());
            Integer i2 = Integer.parseInt(o2.toString());
            return i1.compareTo(i2);
        });

        // Контекстное меню для таблицы
        JPopupMenu tablePopup = new JPopupMenu();
        JMenuItem viewItem = new JMenuItem("👁️ Просмотр");
        JMenuItem editItem = new JMenuItem("✏️ Редактировать");
        JMenuItem deleteItem = new JMenuItem("🗑️ Удалить");
        JMenuItem copyItem = new JMenuItem("📋 Копировать данные");

        viewItem.addActionListener(e -> viewSelectedDevice());
        editItem.addActionListener(e -> editSelectedDevice());
        deleteItem.addActionListener(e -> deleteSelectedDevice());
        copyItem.addActionListener(e -> copyDeviceData());

        tablePopup.add(viewItem);
        tablePopup.add(editItem);
        tablePopup.add(deleteItem);
        tablePopup.addSeparator();
        tablePopup.add(copyItem);

        // Добавляем обработчик правой кнопки мыши
        deviceTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewSelectedDevice();
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = deviceTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        deviceTable.setRowSelectionInterval(row, row);
                        tablePopup.show(deviceTable, e.getX(), e.getY());
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(deviceTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Список устройств"));

        // Панель информации
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Информация о выбранном устройстве"));
        infoPanel.setPreferredSize(new Dimension(800, 120));

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Обновляем информацию при выборе строки
        deviceTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = deviceTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int modelRow = deviceTable.convertRowIndexToModel(selectedRow);
                    String serialNumber = (String) deviceTableModel.getValueAt(modelRow, 3);
                    Factory device = listStorage.getElement(serialNumber);
                    if (device != null) {
                        infoArea.setText(getDeviceSummary(device));
                    }
                }
            }
        });

        infoPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        // Сборка панели
        panel.add(toolPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAddDeviceTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Вкладки для разных типов устройств
        JTabbedPane deviceTypeTabs = new JTabbedPane(JTabbedPane.TOP);

        computerForm = new ComputerFormPanel();
        tabletForm = new TabletFormPanel();
        laptopForm = new LaptopFormPanel();

        deviceTypeTabs.addTab("🖥️ Компьютер", computerForm);
        deviceTypeTabs.addTab("📱 Планшет", tabletForm);
        deviceTypeTabs.addTab("💻 Ноутбук", laptopForm);

        // Панель предпросмотра
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setBorder(BorderFactory.createTitledBorder("Предпросмотр"));
        previewPanel.setPreferredSize(new Dimension(800, 150));

        JTextArea previewArea = new JTextArea();
        previewArea.setEditable(false);
        previewArea.setLineWrap(true);
        previewArea.setWrapStyleWord(true);
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        previewPanel.add(new JScrollPane(previewArea), BorderLayout.CENTER);

        // Панель кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton previewBtn = new JButton("👁️ Предпросмотр");
        JButton clearBtn = new JButton("🧹 Очистить форму");
        JButton addBtn = new JButton("➕ Добавить устройство");

        previewBtn.addActionListener(e -> updatePreview(previewArea, deviceTypeTabs.getSelectedIndex()));
        clearBtn.addActionListener(e -> clearForm(deviceTypeTabs.getSelectedIndex()));
        addBtn.addActionListener(e -> addDeviceFromForm(deviceTypeTabs.getSelectedIndex()));

        // Устанавливаем всплывающие подсказки
        previewBtn.setToolTipText("Предварительный просмотр устройства");
        clearBtn.setToolTipText("Очистить все поля формы");
        addBtn.setToolTipText("Добавить устройство в систему");

        buttonPanel.add(previewBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(addBtn);

        // Сборка панели
        panel.add(deviceTypeTabs, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(previewPanel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createSearchTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель критериев поиска
        JPanel criteriaPanel = new JPanel(new GridBagLayout());
        criteriaPanel.setBorder(BorderFactory.createTitledBorder("Критерии поиска"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Тип устройства
        gbc.gridx = 0;
        gbc.gridy = row;
        criteriaPanel.add(new JLabel("Тип устройства:"), gbc);

        gbc.gridx = 1;
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Все", "Компьютер", "Планшет", "Ноутбук"});
        criteriaPanel.add(typeCombo, gbc);
        row++;

        // Ключевые слова
        gbc.gridx = 0;
        gbc.gridy = row;
        criteriaPanel.add(new JLabel("Ключевое слово:"), gbc);

        gbc.gridx = 1;
        JTextField keywordField = new JTextField(20);
        criteriaPanel.add(keywordField, gbc);
        row++;

        // Диапазон цены
        gbc.gridx = 0;
        gbc.gridy = row;
        criteriaPanel.add(new JLabel("Диапазон цены:"), gbc);

        gbc.gridx = 1;
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JTextField minPriceField = new JTextField(8);
        JTextField maxPriceField = new JTextField(8);
        pricePanel.add(new JLabel("От:"));
        pricePanel.add(minPriceField);
        pricePanel.add(new JLabel("До:"));
        pricePanel.add(maxPriceField);
        criteriaPanel.add(pricePanel, gbc);
        row++;

        // Кнопки поиска
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel searchButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton searchButton = new JButton("🔍 Найти");
        JButton clearButton = new JButton("🧹 Очистить");

        searchButton.addActionListener(e -> performSearch(typeCombo, keywordField, minPriceField, maxPriceField));
        clearButton.addActionListener(e -> {
            typeCombo.setSelectedIndex(0);
            keywordField.setText("");
            minPriceField.setText("");
            maxPriceField.setText("");
        });

        searchButtonPanel.add(searchButton);
        searchButtonPanel.add(clearButton);
        criteriaPanel.add(searchButtonPanel, gbc);

        // Панель результатов
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Результаты поиска"));

        String[] resultColumns = {"Тип", "Модель", "Серийный номер", "Цена", "Дата", "Дополнительно"};
        DefaultTableModel resultsModel = new DefaultTableModel(resultColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable resultsTable = new JTable(resultsModel);
        resultsTable.setRowHeight(25);
        JScrollPane resultsScroll = new JScrollPane(resultsTable);

        resultsPanel.add(resultsScroll, BorderLayout.CENTER);

        // Сборка панели
        panel.add(criteriaPanel, BorderLayout.NORTH);
        panel.add(resultsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFileOperationsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель операций с файлами
        JPanel fileOpsPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        fileOpsPanel.setBorder(BorderFactory.createTitledBorder("Файловые операции"));

        // Группа операций чтения
        JPanel readGroup = createOperationGroup("Чтение файлов", new String[]{
                "📄 TXT", "📄 XML", "📄 JSON"
        }, new ActionListener[]{
                e -> readFileInThread("TXT"),
                e -> readFileInThread("XML"),
                e -> readFileInThread("JSON")
        });

        // Группа операций записи
        JPanel writeGroup = createOperationGroup("Запись файлов", new String[]{
                "💾 TXT", "💾 XML", "💾 JSON"
        }, new ActionListener[]{
                e -> writeFileInThread("TXT"),
                e -> writeFileInThread("XML"),
                e -> writeFileInThread("JSON")
        });

        // Группа шифрования
        JPanel cryptoGroup = createOperationGroup("Шифрование", new String[]{
                "🔒 Зашифровать", "🔓 Дешифровать", "🗜️ Создать ZIP"
        }, new ActionListener[]{
                e -> encryptFile(),
                e -> decryptFile(),
                e -> createZipArchive()
        });

        fileOpsPanel.add(readGroup);
        fileOpsPanel.add(writeGroup);
        fileOpsPanel.add(cryptoGroup);

        // Панель информации о файлах
        JPanel fileInfoPanel = new JPanel(new BorderLayout());
        fileInfoPanel.setBorder(BorderFactory.createTitledBorder("Информация о файлах"));
        fileInfoPanel.setPreferredSize(new Dimension(800, 200));

        JTextArea fileInfoArea = new JTextArea();
        fileInfoArea.setEditable(false);
        fileInfoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane fileInfoScroll = new JScrollPane(fileInfoArea);

        JButton refreshFileInfoBtn = new JButton("🔄 Обновить информацию");
        refreshFileInfoBtn.addActionListener(e -> updateFileInfo(fileInfoArea));

        fileInfoPanel.add(fileInfoScroll, BorderLayout.CENTER);
        fileInfoPanel.add(refreshFileInfoBtn, BorderLayout.SOUTH);

        // Сборка панели
        panel.add(fileOpsPanel, BorderLayout.NORTH);
        panel.add(fileInfoPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createOperationGroup(String title, String[] buttonLabels, ActionListener[] actions) {
        JPanel group = new JPanel(new BorderLayout());
        group.setBorder(BorderFactory.createTitledBorder(title));

        JPanel buttonPanel = new JPanel(new GridLayout(buttonLabels.length, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.addActionListener(actions[i]);
            buttonPanel.add(button);
        }

        group.add(buttonPanel, BorderLayout.CENTER);
        return group;
    }

    private JPanel createStatisticsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель статистики
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Статистика по типам
        JPanel typeStatsPanel = new JPanel(new BorderLayout());
        typeStatsPanel.setBorder(BorderFactory.createTitledBorder("Распределение по типам"));

        JTextArea typeStatsArea = new JTextArea();
        typeStatsArea.setEditable(false);
        typeStatsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        typeStatsPanel.add(new JScrollPane(typeStatsArea), BorderLayout.CENTER);

        // Статистика по ценам
        JPanel priceStatsPanel = new JPanel(new BorderLayout());
        priceStatsPanel.setBorder(BorderFactory.createTitledBorder("Статистика цен"));

        JTextArea priceStatsArea = new JTextArea();
        priceStatsArea.setEditable(false);
        priceStatsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        priceStatsPanel.add(new JScrollPane(priceStatsArea), BorderLayout.CENTER);

        // Статистика по годам
        JPanel yearStatsPanel = new JPanel(new BorderLayout());
        yearStatsPanel.setBorder(BorderFactory.createTitledBorder("Распределение по годам"));

        JTextArea yearStatsArea = new JTextArea();
        yearStatsArea.setEditable(false);
        yearStatsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        yearStatsPanel.add(new JScrollPane(yearStatsArea), BorderLayout.CENTER);

        // Панель управления
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBorder(BorderFactory.createTitledBorder("Управление"));

        JButton updateStatsBtn = new JButton("🔄 Обновить статистику");
        updateStatsBtn.addActionListener(e -> {
            updateTypeStatistics(typeStatsArea);
            updatePriceStatistics(priceStatsArea);
            updateYearStatistics(yearStatsArea);
        });

        JButton exportStatsBtn = new JButton("📤 Экспорт статистики");
        exportStatsBtn.addActionListener(e -> exportStatistics());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(updateStatsBtn);
        buttonPanel.add(exportStatsBtn);

        controlPanel.add(buttonPanel, BorderLayout.CENTER);

        statsPanel.add(typeStatsPanel);
        statsPanel.add(priceStatsPanel);
        statsPanel.add(yearStatsPanel);
        statsPanel.add(controlPanel);

        panel.add(statsPanel, BorderLayout.CENTER);

        // Обновляем статистику при первом открытии
        SwingUtilities.invokeLater(() -> {
            updateTypeStatistics(typeStatsArea);
            updatePriceStatistics(priceStatsArea);
            updateYearStatistics(yearStatsArea);
        });

        return panel;
    }

    private JPanel createBuilderTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель стандартных конфигураций
        JPanel standardPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        standardPanel.setBorder(BorderFactory.createTitledBorder("Стандартные конфигурации"));

        JButton[] standardButtons = {
                new JButton("🖥️ Офисный компьютер"),
                new JButton("🎮 Игровой компьютер"),
                new JButton("📱 Бюджетный планшет"),
                new JButton("📱 Премиум планшет"),
                new JButton("💻 Офисный ноутбук"),
                new JButton("💻 Игровой ноутбук")
        };

        String[] configTypes = {
                "officeComputer", "gamingComputer", "budgetTablet",
                "premiumTablet", "officeLaptop", "gamingLaptop"
        };

        for (int i = 0; i < standardButtons.length; i++) {
            final String configType = configTypes[i];
            standardButtons[i].addActionListener(e -> buildStandardDevice(configType));
            standardPanel.add(standardButtons[i]);
        }

        // Панель кастомной сборки
        JPanel customPanel = new JPanel(new BorderLayout());
        customPanel.setBorder(BorderFactory.createTitledBorder("Кастомная сборка"));

        JTextArea customLog = new JTextArea(15, 60);
        customLog.setEditable(false);
        customLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        customLog.setText("Здесь будет отображаться процесс сборки...\n");

        JScrollPane customScroll = new JScrollPane(customLog);

        customPanel.add(customScroll, BorderLayout.CENTER);

        // Панель управления
        JPanel builderControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton showStepsBtn = new JButton("📋 Показать шаги сборки");
        JButton clearLogBtn = new JButton("🧹 Очистить лог");

        showStepsBtn.addActionListener(e -> showBuildSteps(customLog));
        clearLogBtn.addActionListener(e -> customLog.setText(""));

        builderControlPanel.add(showStepsBtn);
        builderControlPanel.add(clearLogBtn);

        // Сборка панели
        panel.add(standardPanel, BorderLayout.NORTH);
        panel.add(customPanel, BorderLayout.CENTER);
        panel.add(builderControlPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSettingsTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Настройки интерфейса
        JPanel uiPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        uiPanel.setBorder(BorderFactory.createTitledBorder("Настройки интерфейса"));

        JCheckBox autoRefreshCheck = new JCheckBox("Автообновление таблицы");
        JCheckBox showTooltipsCheck = new JCheckBox("Показывать подсказки", true);
        JCheckBox confirmDeleteCheck = new JCheckBox("Подтверждать удаление", true);
        JCheckBox autoSaveCheck = new JCheckBox("Автосохранение при выходе");

        uiPanel.add(autoRefreshCheck);
        uiPanel.add(showTooltipsCheck);
        uiPanel.add(confirmDeleteCheck);
        uiPanel.add(autoSaveCheck);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(uiPanel, gbc);

        // Настройки файлов
        JPanel filePanel = new JPanel(new GridBagLayout());
        filePanel.setBorder(BorderFactory.createTitledBorder("Настройки файлов"));

        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(5, 5, 5, 5);
        fgbc.fill = GridBagConstraints.HORIZONTAL;

        int frow = 0;

        fgbc.gridx = 0;
        fgbc.gridy = frow;
        filePanel.add(new JLabel("Папка по умолчанию:"), fgbc);

        fgbc.gridx = 1;
        JTextField pathField = new JTextField(defaultDirectory, 30);
        filePanel.add(pathField, fgbc);
        frow++;

        fgbc.gridx = 0;
        fgbc.gridy = frow;
        filePanel.add(new JLabel("Формат по умолчанию:"), fgbc);

        fgbc.gridx = 1;
        JComboBox<String> formatCombo = new JComboBox<>(new String[]{"TXT", "XML", "JSON"});
        formatCombo.setSelectedItem("JSON");
        filePanel.add(formatCombo, fgbc);
        frow++;

        fgbc.gridx = 0;
        fgbc.gridy = frow;
        filePanel.add(new JLabel("Интервал автосохранения:"), fgbc);

        fgbc.gridx = 1;
        JComboBox<Integer> intervalCombo = new JComboBox<>(new Integer[]{5, 10, 15, 30, 60});
        intervalCombo.setSelectedItem(10);
        filePanel.add(intervalCombo, fgbc);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(filePanel, gbc);

        // Кнопки
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton saveSettingsBtn = new JButton("💾 Сохранить настройки");
        JButton defaultSettingsBtn = new JButton("↩️ Сбросить настройки");
        JButton applySettingsBtn = new JButton("✅ Применить");

        saveSettingsBtn.addActionListener(e -> saveSettings(pathField.getText(),
                (String)formatCombo.getSelectedItem(), (Integer)intervalCombo.getSelectedItem()));
        defaultSettingsBtn.addActionListener(e -> restoreDefaultSettings(pathField, formatCombo, intervalCombo));
        applySettingsBtn.addActionListener(e -> applySettings());

        buttonPanel.add(saveSettingsBtn);
        buttonPanel.add(defaultSettingsBtn);
        buttonPanel.add(applySettingsBtn);

        panel.add(buttonPanel, gbc);

        return panel;
    }

    // ================ МЕТОДЫ ДЛЯ РАБОТЫ С УСТРОЙСТВАМИ ================

    private void refreshDeviceTable() {
        SwingUtilities.invokeLater(() -> {
            deviceTableModel.setRowCount(0);
            List<Factory> devices = listStorage.getAllElements();

            for (Factory device : devices) {
                Object[] row = {
                        device.getDeviceType(),
                        device.getModel(),
                        device.getModelNumber(),
                        device.getSerialNumber(),
                        device.getCost(),
                        displayDateFormat.format(device.getReleaseDate()),
                        getAdditionalInfo(device)
                };
                deviceTableModel.addRow(row);
            }

            statusLabel.setText(" Устройств в базе: " + devices.size());
            logMessage("Таблица обновлена. Устройств: " + devices.size());

            deviceTable.revalidate();
            deviceTable.repaint();
        });
    }

    private String getAdditionalInfo(Factory device) {
        if (device instanceof Computers) {
            Computers computer = (Computers) device;
            return "Корпус: " + computer.getCaseType() +
                    (computer.isWifiModule() ? ", WiFi" : "");
        } else if (device instanceof Tablets) {
            Tablets tablet = (Tablets) device;
            return "ОС: " + tablet.getOperatingSystem() +
                    (tablet.isChipNFC() ? ", NFC" : "");
        } else if (device instanceof Laptops) {
            Laptops laptop = (Laptops) device;
            return (laptop.isTouchScreen() ? "TouchScreen" : "") +
                    (laptop.isNumPad() ? ", NumPad" : "");
        }
        return "";
    }

    private String getDeviceSummary(Factory device) {
        StringBuilder summary = new StringBuilder();
        summary.append("=== ОБЩАЯ ИНФОРМАЦИЯ ===\n");
        summary.append("Тип: ").append(device.getDeviceType()).append("\n");
        summary.append("Модель: ").append(device.getModel()).append("\n");
        summary.append("Модельный номер: ").append(device.getModelNumber()).append("\n");
        summary.append("Серийный номер: ").append(device.getSerialNumber()).append("\n");
        summary.append("Цена: $").append(device.getCost()).append("\n");
        summary.append("Дата выпуска: ").append(displayDateFormat.format(device.getReleaseDate())).append("\n");

        if (device instanceof Computers) {
            Computers computer = (Computers) device;
            summary.append("\n=== КОМПОНЕНТЫ ===\n");
            summary.append("Корпус: ").append(computer.getCaseType()).append("\n");
            summary.append("Материнская плата: ").append(computer.getMotherboard()).append("\n");
            summary.append("Процессор: ").append(computer.getProcessor()).append("\n");
            summary.append("Жесткий диск: ").append(computer.getHardDrive()).append("\n");
            summary.append("Оперативная память: ").append(computer.getRam()).append("\n");
            summary.append("Блок питания: ").append(computer.getPowerSupply()).append("\n");
            summary.append("WiFi модуль: ").append(computer.isWifiModule() ? "Да" : "Нет").append("\n");
        }

        return summary.toString();
    }

    private void deleteSelectedDevice() {
        int selectedRow = deviceTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = deviceTable.convertRowIndexToModel(selectedRow);
            String serialNumber = (String) deviceTableModel.getValueAt(modelRow, 3);
            String model = (String) deviceTableModel.getValueAt(modelRow, 1);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Вы уверены, что хотите удалить устройство?\n\n" +
                            "Модель: " + model + "\n" +
                            "Серийный номер: " + serialNumber,
                    "Подтверждение удаления",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean removedFromList = listStorage.removeElement(serialNumber);
                boolean removedFromMap = mapStorage.removeElement(serialNumber);

                if (removedFromList && removedFromMap) {
                    refreshDeviceTable();
                    logMessage("Устройство удалено: " + model + " (" + serialNumber + ")");
                    JOptionPane.showMessageDialog(this,
                            "Устройство успешно удалено",
                            "Успех",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Ошибка при удалении устройства",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Выберите устройство для удаления",
                    "Предупреждение",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editSelectedDevice() {
        int selectedRow = deviceTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = deviceTable.convertRowIndexToModel(selectedRow);
            String serialNumber = (String) deviceTableModel.getValueAt(modelRow, 3);
            Factory device = listStorage.getElement(serialNumber);

            if (device != null) {
                EditDeviceDialog dialog = new EditDeviceDialog(this, device);
                dialog.setVisible(true);

                if (dialog.isUpdated()) {
                    refreshDeviceTable();
                    logMessage("Устройство обновлено: " + device.getModel());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Выберите устройство для редактирования",
                    "Предупреждение",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void viewSelectedDevice() {
        int selectedRow = deviceTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = deviceTable.convertRowIndexToModel(selectedRow);
            String serialNumber = (String) deviceTableModel.getValueAt(modelRow, 3);
            Factory device = listStorage.getElement(serialNumber);

            if (device != null) {
                DeviceDetailsDialog dialog = new DeviceDetailsDialog(this, device);
                dialog.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Выберите устройство для просмотра",
                    "Предупреждение",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void copyDeviceData() {
        int selectedRow = deviceTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = deviceTable.convertRowIndexToModel(selectedRow);
            String serialNumber = (String) deviceTableModel.getValueAt(modelRow, 3);
            Factory device = listStorage.getElement(serialNumber);

            if (device != null) {
                String data = device.toString();
                Toolkit.getDefaultToolkit().getSystemClipboard()
                        .setContents(new java.awt.datatransfer.StringSelection(data), null);
                logMessage("Данные устройства скопированы в буфер обмена");
                JOptionPane.showMessageDialog(this,
                        "Данные скопированы в буфер обмена",
                        "Успех",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void addDeviceFromForm(int deviceType) {
        try {
            Factory newDevice = null;

            switch (deviceType) {
                case 0: // Компьютер
                    newDevice = computerForm.createDevice();
                    break;
                case 1: // Планшет
                    newDevice = tabletForm.createDevice();
                    break;
                case 2: // Ноутбук
                    newDevice = laptopForm.createDevice();
                    break;
            }

            if (newDevice != null) {
                // Проверяем уникальность серийного номера
                if (listStorage.contains(newDevice.getSerialNumber())) {
                    JOptionPane.showMessageDialog(this,
                            "Устройство с таким серийным номером уже существует!\n" +
                                    "Серийный номер: " + newDevice.getSerialNumber(),
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Добавляем в оба хранилища
                listStorage.addElement(newDevice);
                mapStorage.addElement(newDevice);

                // Обновляем таблицу
                refreshDeviceTable();

                // Показываем сообщение об успехе
                JOptionPane.showMessageDialog(this,
                        "Устройство успешно добавлено!\n\n" +
                                "Тип: " + newDevice.getDeviceType() + "\n" +
                                "Модель: " + newDevice.getModel() + "\n" +
                                "Серийный номер: " + newDevice.getSerialNumber(),
                        "Успех",
                        JOptionPane.INFORMATION_MESSAGE);

                logMessage("Добавлено новое устройство: " + newDevice.getDeviceType() +
                        " - " + newDevice.getModel());

                // Переключаемся на вкладку устройств
                tabbedPane.setSelectedIndex(0);

                // Очищаем форму
                clearForm(deviceType);
            }
        } catch (java.text.ParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка формата даты! Используйте формат ДД/ММ/ГГГГ\n" +
                            "Пример: 01/01/2023",
                    "Ошибка формата даты",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка формата числа! Укажите корректную цену\n" +
                            "Цена должна быть целым числом",
                    "Ошибка формата числа",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: " + ex.getMessage(),
                    "Ошибка создания устройства",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Неизвестная ошибка: " + ex.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void updatePreview(JTextArea previewArea, int deviceType) {
        try {
            Factory device = null;

            switch (deviceType) {
                case 0: device = computerForm.createDevice(); break;
                case 1: device = tabletForm.createDevice(); break;
                case 2: device = laptopForm.createDevice(); break;
            }

            if (device != null) {
                previewArea.setText(device.toString());
            }
        } catch (Exception ex) {
            previewArea.setText("Ошибка предпросмотра: " + ex.getMessage());
        }
    }

    private void clearForm(int deviceType) {
        switch (deviceType) {
            case 0: computerForm.clearForm(); break;
            case 1: tabletForm.clearForm(); break;
            case 2: laptopForm.clearForm(); break;
        }
    }

    // ================ МЕТОДЫ ДЛЯ РАБОТЫ С ФАЙЛАМИ ================

    private void readFileInThread(String format) {
        JFileChooser fileChooser = new JFileChooser(defaultDirectory);
        fileChooser.setDialogTitle("Выберите файл для чтения");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                format + " файлы", format.toLowerCase()));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Обновляем папку по умолчанию
            defaultDirectory = selectedFile.getParent();

            // СОЗДАЕМ ФИНАЛЬНЫЕ КОПИИ ДЛЯ ИСПОЛЬЗОВАНИЯ В ЛЯМБДА-ВЫРАЖЕНИИ
            final File fileToRead = selectedFile;
            final String fileFormat = format;

            // Запускаем в отдельном потоке
            executorService.execute(() -> {
                try {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setIndeterminate(true);
                        progressBar.setString("Чтение " + fileFormat + " файла...");
                    });

                    Storage<Factory> devices = null;

                    switch (fileFormat.toUpperCase()) {
                        case "TXT":
                            ReadFile reader = new ReadFile();
                            devices = reader.readDevicesFromFile(fileToRead.getAbsolutePath());
                            break;

                        case "XML":
                            XMLReadFile xmlReader = new XMLReadFile();
                            devices = xmlReader.readDevicesFromXML(fileToRead.getAbsolutePath());
                            break;

                        case "JSON":
                            JSONReadFile jsonReader = new JSONReadFile();
                            devices = jsonReader.readDevicesFromJSON(fileToRead.getAbsolutePath());
                            break;
                    }

                    if (devices != null && devices.size() > 0) {
                        // Очищаем текущие хранилища
                        listStorage.clear();
                        mapStorage.clear();

                        // Добавляем все устройства
                        for (Factory device : devices.getAllElements()) {
                            listStorage.addElement(device);
                            mapStorage.addElement(device);
                        }

                        Storage<Factory> finalDevices = devices;
                        SwingUtilities.invokeLater(() -> {
                            refreshDeviceTable();
                            logMessage("Загружено " + finalDevices.size() +
                                    " устройств из файла: " + fileToRead.getName());
                            JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                    "Успешно загружено " + finalDevices.size() + " устройств",
                                    "Успех",
                                    JOptionPane.INFORMATION_MESSAGE);
                        });
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            logMessage("Файл не содержит устройств или поврежден: " + fileToRead.getName());
                            JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                    "Файл не содержит устройств или поврежден",
                                    "Предупреждение",
                                    JOptionPane.WARNING_MESSAGE);
                        });
                    }
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> {
                        logMessage("Ошибка чтения файла: " + e.getMessage());
                        JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                "Ошибка чтения файла:\n" + e.getMessage(),
                                "Ошибка",
                                JOptionPane.ERROR_MESSAGE);
                    });
                } finally {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setIndeterminate(false);
                        progressBar.setString("Готово");
                    });
                }
            });
        }
    }

    private void writeFileInThread(String format) {
        if (listStorage.size() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Нет устройств для сохранения",
                    "Предупреждение",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser(defaultDirectory);
        fileChooser.setDialogTitle("Сохранить как");
        fileChooser.setSelectedFile(new File("devices." + format.toLowerCase()));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                format + " файлы", format.toLowerCase()));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // СОЗДАЕМ ФИНАЛЬНЫЕ КОПИИ
            final File fileToWrite = selectedFile;
            final String fileFormat = format;

            // Запускаем в отдельном потоке
            executorService.execute(() -> {
                try {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setIndeterminate(true);
                        progressBar.setString("Запись " + fileFormat + " файла...");
                    });

                    boolean success = false;

                    switch (fileFormat.toUpperCase()) {
                        case "TXT":
                            WriteFile writer = new WriteFile();
                            success = writer.writeDevicesToFile(fileToWrite.getAbsolutePath(), listStorage);
                            break;

                        case "XML":
                            XMLWriteFile xmlWriter = new XMLWriteFile();
                            success = xmlWriter.writeDevicesToXML(fileToWrite.getAbsolutePath(), listStorage);
                            break;

                        case "JSON":
                            JSONWriteFile jsonWriter = new JSONWriteFile();
                            success = jsonWriter.writeDevicesToJSON(fileToWrite.getAbsolutePath(), listStorage);
                            break;
                    }

                    if (success) {
                        SwingUtilities.invokeLater(() -> {
                            logMessage("Устройства сохранены в файл: " + fileToWrite.getName());
                            JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                    "Файл успешно сохранен\n" +
                                            "Устройств: " + listStorage.size(),
                                    "Успех",
                                    JOptionPane.INFORMATION_MESSAGE);
                        });
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            logMessage("Ошибка записи файла: " + fileToWrite.getName());
                            JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                    "Ошибка записи файла",
                                    "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    }
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> {
                        logMessage("Ошибка записи файла: " + e.getMessage());
                        JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                "Ошибка записи файла:\n" + e.getMessage(),
                                "Ошибка",
                                JOptionPane.ERROR_MESSAGE);
                    });
                } finally {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setIndeterminate(false);
                        progressBar.setString("Готово");
                    });
                }
            });
        }
    }

    private void encryptFile() {
        JFileChooser fileChooser = new JFileChooser(defaultDirectory);
        fileChooser.setDialogTitle("Выберите файл для шифрования");

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            JFileChooser saveChooser = new JFileChooser(defaultDirectory);
            saveChooser.setDialogTitle("Сохранить зашифрованный файл как");
            saveChooser.setSelectedFile(new File(selectedFile.getName() + ".enc"));

            if (saveChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File outputFile = saveChooser.getSelectedFile();

                executorService.execute(() -> {
                    try {
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setIndeterminate(true);
                            progressBar.setString("Шифрование файла...");
                        });

                        // Читаем содержимое файла
                        StringBuilder content = new StringBuilder();
                        try (java.io.BufferedReader reader = new java.io.BufferedReader(
                                new java.io.FileReader(selectedFile))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                content.append(line).append("\n");
                            }
                        }

                        // Шифруем содержимое
                        String encryptedContent = Encryption.encrypt(content.toString());

                        // Записываем зашифрованное содержимое
                        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(
                                new java.io.FileWriter(outputFile))) {
                            writer.write(encryptedContent);
                        }

                        SwingUtilities.invokeLater(() -> {
                            logMessage("Файл зашифрован: " + selectedFile.getName() +
                                    " -> " + outputFile.getName());
                            JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                    "Файл успешно зашифрован",
                                    "Успех",
                                    JOptionPane.INFORMATION_MESSAGE);
                        });
                    } catch (Exception e) {
                        SwingUtilities.invokeLater(() -> {
                            logMessage("Ошибка шифрования: " + e.getMessage());
                            JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                    "Ошибка шифрования:\n" + e.getMessage(),
                                    "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    } finally {
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setIndeterminate(false);
                            progressBar.setString("Готово");
                        });
                    }
                });
            }
        }
    }

    private void decryptFile() {
        JFileChooser fileChooser = new JFileChooser(defaultDirectory);
        fileChooser.setDialogTitle("Выберите файл для дешифрования");

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            JFileChooser saveChooser = new JFileChooser(defaultDirectory);
            saveChooser.setDialogTitle("Сохранить дешифрованный файл как");
            saveChooser.setSelectedFile(new File(selectedFile.getName().replace(".enc", ".dec")));

            if (saveChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File outputFile = saveChooser.getSelectedFile();

                executorService.execute(() -> {
                    try {
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setIndeterminate(true);
                            progressBar.setString("Дешифрование файла...");
                        });

                        // Читаем зашифрованное содержимое
                        StringBuilder content = new StringBuilder();
                        try (java.io.BufferedReader reader = new java.io.BufferedReader(
                                new java.io.FileReader(selectedFile))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                content.append(line).append("\n");
                            }
                        }

                        // Дешифруем содержимое
                        String decryptedContent = Encryption.decrypt(content.toString());

                        // Записываем дешифрованное содержимое
                        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(
                                new java.io.FileWriter(outputFile))) {
                            writer.write(decryptedContent);
                        }

                        SwingUtilities.invokeLater(() -> {
                            logMessage("Файл дешифрован: " + selectedFile.getName() +
                                    " -> " + outputFile.getName());
                            JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                    "Файл успешно дешифрован",
                                    "Успех",
                                    JOptionPane.INFORMATION_MESSAGE);
                        });
                    } catch (Exception e) {
                        SwingUtilities.invokeLater(() -> {
                            logMessage("Ошибка дешифрования: " + e.getMessage());
                            JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                    "Ошибка дешифрования:\n" + e.getMessage(),
                                    "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    } finally {
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setIndeterminate(false);
                            progressBar.setString("Готово");
                        });
                    }
                });
            }
        }
    }

    private void createZipArchive() {
        JFileChooser fileChooser = new JFileChooser(defaultDirectory);
        fileChooser.setDialogTitle("Выберите файлы для архивации");
        fileChooser.setMultiSelectionEnabled(true);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();

            JFileChooser saveChooser = new JFileChooser(defaultDirectory);
            saveChooser.setDialogTitle("Сохранить ZIP архив как");
            saveChooser.setSelectedFile(new File("archive.zip"));

            if (saveChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File outputFile = saveChooser.getSelectedFile();

                executorService.execute(() -> {
                    try {
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setIndeterminate(true);
                            progressBar.setString("Создание ZIP архива...");
                        });

                        // Создаем список файлов
                        java.util.List<String> files = new java.util.ArrayList<>();
                        for (File file : selectedFiles) {
                            files.add(file.getAbsolutePath());
                        }

                        // Создаем архив
                        boolean success = Archive.createZipArchive(files, outputFile.getAbsolutePath());

                        SwingUtilities.invokeLater(() -> {
                            if (success) {
                                logMessage("Создан ZIP архив: " + outputFile.getName() +
                                        " (файлов: " + files.size() + ")");
                                JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                        "ZIP архив успешно создан\n" +
                                                "Файлов: " + files.size(),
                                        "Успех",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                logMessage("Ошибка создания ZIP архива");
                                JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                        "Ошибка создания ZIP архива",
                                        "Ошибка",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        });
                    } catch (Exception e) {
                        SwingUtilities.invokeLater(() -> {
                            logMessage("Ошибка создания архива: " + e.getMessage());
                            JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                    "Ошибка создания архива:\n" + e.getMessage(),
                                    "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    } finally {
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setIndeterminate(false);
                            progressBar.setString("Готово");
                        });
                    }
                });
            }
        }
    }

    // ================ МЕТОДЫ ПОИСКА И ФИЛЬТРАЦИИ ================

    private void performSearch(JComboBox<String> typeCombo, JTextField keywordField,
                               JTextField minPriceField, JTextField maxPriceField) {
        // СОЗДАЕМ ФИНАЛЬНЫЕ КОПИИ ДЛЯ ИСПОЛЬЗОВАНИЯ В ЛЯМБДА-ВЫРАЖЕНИИ
        final String type = (String) typeCombo.getSelectedItem();
        final String keyword = keywordField.getText().trim().toLowerCase();
        final String minPriceText = minPriceField.getText().trim();
        final String maxPriceText = maxPriceField.getText().trim();

        // Запускаем поиск в отдельном потоке
        executorService.execute(() -> {
            List<Factory> results = new ArrayList<>();
            List<Factory> allDevices = listStorage.getAllElements();

            for (Factory device : allDevices) {
                boolean matches = true;

                // Фильтр по типу
                if (!type.equals("Все") && !device.getDeviceType().equals(type.toUpperCase())) {
                    matches = false;
                }

                // Фильтр по ключевому слову
                if (matches && !keyword.isEmpty()) {
                    boolean keywordMatch = device.getModel().toLowerCase().contains(keyword) ||
                            device.getModelNumber().toLowerCase().contains(keyword) ||
                            device.getSerialNumber().toLowerCase().contains(keyword);

                    if (!keywordMatch) {
                        matches = false;
                    }
                }

                // Фильтр по цене
                if (matches && (!minPriceText.isEmpty() || !maxPriceText.isEmpty())) {
                    try {
                        int minPrice = minPriceText.isEmpty() ? 0 : Integer.parseInt(minPriceText);
                        int maxPrice = maxPriceText.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(maxPriceText);
                        int price = device.getCost();

                        if (price < minPrice || price > maxPrice) {
                            matches = false;
                        }
                    } catch (NumberFormatException e) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                    "Неверный формат цены",
                                    "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                        return;
                    }
                }

                if (matches) {
                    results.add(device);
                }
            }

            SwingUtilities.invokeLater(() -> showSearchResults(results));
        });
    }

    private void showSearchResults(List<Factory> results) {
        JDialog resultsDialog = new JDialog(this, "Результаты поиска", true);
        resultsDialog.setSize(900, 500);
        resultsDialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Заголовок
        JLabel titleLabel = new JLabel("Найдено устройств: " + results.size());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Таблица результатов
        String[] columns = {"Тип", "Модель", "Серийный номер", "Цена ($)", "Дата выпуска"};
        DefaultTableModel resultsModel = new DefaultTableModel(columns, 0);

        JTable resultsTable = new JTable(resultsModel);
        resultsTable.setRowHeight(25);

        for (Factory device : results) {
            Object[] row = {
                    device.getDeviceType(),
                    device.getModel(),
                    device.getSerialNumber(),
                    device.getCost(),
                    displayDateFormat.format(device.getReleaseDate())
            };
            resultsModel.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Кнопки
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Закрыть");
        JButton exportButton = new JButton("Экспорт результатов");

        closeButton.addActionListener(e -> resultsDialog.dispose());
        exportButton.addActionListener(e -> exportSearchResults(results));

        buttonPanel.add(exportButton);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        resultsDialog.add(mainPanel);
        resultsDialog.setVisible(true);
    }

    // ================ МЕТОДЫ ДЛЯ СТАТИСТИКИ ================

    private void updateTypeStatistics(JTextArea typeStatsArea) {
        List<Factory> devices = listStorage.getAllElements();

        long computers = devices.stream().filter(d -> d instanceof Computers).count();
        long tablets = devices.stream().filter(d -> d instanceof Tablets).count();
        long laptops = devices.stream().filter(d -> d instanceof Laptops).count();

        int total = devices.size();

        StringBuilder stats = new StringBuilder();
        stats.append("=== РАСПРЕДЕЛЕНИЕ ПО ТИПАМ ===\n\n");
        stats.append(String.format("Компьютеры: %d (%.1f%%)\n", computers,
                total > 0 ? (computers * 100.0 / total) : 0));
        stats.append(String.format("Планшеты:   %d (%.1f%%)\n", tablets,
                total > 0 ? (tablets * 100.0 / total) : 0));
        stats.append(String.format("Ноутбуки:   %d (%.1f%%)\n", laptops,
                total > 0 ? (laptops * 100.0 / total) : 0));
        stats.append(String.format("\nВсего:      %d устройств", total));

        typeStatsArea.setText(stats.toString());
    }

    private void updatePriceStatistics(JTextArea priceStatsArea) {
        List<Factory> devices = listStorage.getAllElements();

        if (devices.isEmpty()) {
            priceStatsArea.setText("Нет данных для анализа");
            return;
        }

        int minPrice = devices.stream().mapToInt(Factory::getCost).min().orElse(0);
        int maxPrice = devices.stream().mapToInt(Factory::getCost).max().orElse(0);
        double avgPrice = devices.stream().mapToInt(Factory::getCost).average().orElse(0);
        double totalValue = devices.stream().mapToInt(Factory::getCost).sum();

        // Группировка по диапазонам цен
        Map<String, Integer> priceGroups = new LinkedHashMap<>();
        priceGroups.put("$0-500", 0);
        priceGroups.put("$501-1000", 0);
        priceGroups.put("$1001-2000", 0);
        priceGroups.put("$2000+", 0);

        for (Factory device : devices) {
            int price = device.getCost();
            if (price <= 500) {
                priceGroups.put("$0-500", priceGroups.get("$0-500") + 1);
            } else if (price <= 1000) {
                priceGroups.put("$501-1000", priceGroups.get("$501-1000") + 1);
            } else if (price <= 2000) {
                priceGroups.put("$1001-2000", priceGroups.get("$1001-2000") + 1);
            } else {
                priceGroups.put("$2000+", priceGroups.get("$2000+") + 1);
            }
        }

        StringBuilder stats = new StringBuilder();
        stats.append("=== СТАТИСТИКА ЦЕН ===\n\n");
        stats.append(String.format("Минимальная цена: $%d\n", minPrice));
        stats.append(String.format("Максимальная цена: $%d\n", maxPrice));
        stats.append(String.format("Средняя цена: $%.2f\n", avgPrice));
        stats.append(String.format("Общая стоимость: $%.2f\n\n", totalValue));
        stats.append("=== РАСПРЕДЕЛЕНИЕ ПО ЦЕНАМ ===\n\n");

        for (Map.Entry<String, Integer> entry : priceGroups.entrySet()) {
            double percentage = devices.size() > 0 ? (entry.getValue() * 100.0 / devices.size()) : 0;
            stats.append(String.format("%-15s %3d (%.1f%%)\n",
                    entry.getKey(), entry.getValue(), percentage));
        }

        priceStatsArea.setText(stats.toString());
    }

    private void updateYearStatistics(JTextArea yearStatsArea) {
        List<Factory> devices = listStorage.getAllElements();

        if (devices.isEmpty()) {
            yearStatsArea.setText("Нет данных для анализа");
            return;
        }

        Map<Integer, Integer> yearCount = new TreeMap<>();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        for (Factory device : devices) {
            int year = Integer.parseInt(yearFormat.format(device.getReleaseDate()));
            yearCount.put(year, yearCount.getOrDefault(year, 0) + 1);
        }

        StringBuilder stats = new StringBuilder();
        stats.append("=== РАСПРЕДЕЛЕНИЕ ПО ГОДАМ ===\n\n");

        for (Map.Entry<Integer, Integer> entry : yearCount.entrySet()) {
            double percentage = devices.size() > 0 ? (entry.getValue() * 100.0 / devices.size()) : 0;
            stats.append(String.format("%4d год: %3d устройств (%.1f%%)\n",
                    entry.getKey(), entry.getValue(), percentage));
        }

        yearStatsArea.setText(stats.toString());
    }

    // ================ МЕТОДЫ ДЛЯ BUILDER PATTERN ================

    private void buildStandardDevice(String configType) {
        // СОЗДАЕМ ФИНАЛЬНУЮ КОПИЮ
        final String config = configType;

        executorService.execute(() -> {
            try {
                SwingUtilities.invokeLater(() -> {
                    progressBar.setIndeterminate(true);
                    progressBar.setString("Сборка устройства...");
                });

                Factory device = null;

                switch (config) {
                    case "officeComputer":
                        device = deviceDirector.buildStandardOfficeComputer();
                        break;
                    case "gamingComputer":
                        device = deviceDirector.buildGamingComputer();
                        break;
                    case "budgetTablet":
                        device = deviceDirector.buildBudgetTablet();
                        break;
                    case "premiumTablet":
                        device = deviceDirector.buildPremiumTablet();
                        break;
                    case "officeLaptop":
                        device = deviceDirector.buildOfficeLaptop();
                        break;
                    case "gamingLaptop":
                        device = deviceDirector.buildGamingLaptop();
                        break;
                }

                if (device != null) {
                    // Добавляем в оба хранилища
                    listStorage.addElement(device);
                    mapStorage.addElement(device);

                    Factory finalDevice = device;
                    SwingUtilities.invokeLater(() -> {
                        refreshDeviceTable();
                        logMessage("Собрано стандартное устройство: " + finalDevice.getModel());
                        JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                "Устройство успешно собрано и добавлено!\n\n" +
                                        "Конфигурация: " + config + "\n" +
                                        "Модель: " + finalDevice.getModel(),
                                "Успех",
                                JOptionPane.INFORMATION_MESSAGE);
                    });
                }
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    logMessage("Ошибка сборки устройства: " + e.getMessage());
                    JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                            "Ошибка сборки устройства:\n" + e.getMessage(),
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                });
            } finally {
                SwingUtilities.invokeLater(() -> {
                    progressBar.setIndeterminate(false);
                    progressBar.setString("Готово");
                });
            }
        });
    }

    private void showBuildSteps(JTextArea customLog) {
        StringBuilder steps = new StringBuilder();
        steps.append("=== ШАГИ СБОРКИ ДЛЯ РАЗНЫХ УСТРОЙСТВ ===\n\n");

        steps.append("🖥️ КОМПЬЮТЕР:\n");
        steps.append("1. Установка корпуса\n");
        steps.append("2. Установка материнской платы\n");
        steps.append("3. Установка процессора\n");
        steps.append("4. Установка жесткого диска\n");
        steps.append("5. Установка оперативной памяти\n");
        steps.append("6. Установка блока питания\n");
        steps.append("7. Подключение кабелей\n");
        steps.append("8. Тестирование системы\n\n");

        steps.append("📱 ПЛАНШЕТ:\n");
        steps.append("1. Установка корпуса\n");
        steps.append("2. Установка процессора\n");
        steps.append("3. Установка экрана\n");
        steps.append("4. Установка аккумулятора\n");
        steps.append("5. Установка WiFi модуля\n");
        steps.append("6. Установка NFC чипа\n");
        steps.append("7. Сборка корпуса\n");
        steps.append("8. Тестирование\n\n");

        steps.append("💻 НОУТБУК:\n");
        steps.append("1. Установка корпуса\n");
        steps.append("2. Установка материнской платы\n");
        steps.append("3. Установка процессора\n");
        steps.append("4. Установка системы охлаждения\n");
        steps.append("5. Установка жесткого диска\n");
        steps.append("6. Установка оперативной памяти\n");
        steps.append("7. Установка клавиатуры\n");
        steps.append("8. Установка TouchPad\n");
        steps.append("9. Установка экрана\n");
        steps.append("10. Тестирование\n");

        customLog.setText(steps.toString());
    }

    // ================ ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ================

    private void logMessage(String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String logEntry = "[" + timestamp + "] " + message;

        SwingUtilities.invokeLater(() -> {
            logArea.append(logEntry + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });

        // Также выводим в консоль для отладки
        System.out.println(logEntry);
    }

    private void startAutoSaveThread() {
        Thread autoSaveThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(300000); // 5 минут

                    if (listStorage.size() > 0) {
                        logMessage("Автосохранение выполнено");
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        autoSaveThread.setDaemon(true);
        autoSaveThread.start();
    }

    private void checkDataDirectory() {
        File dataDir = new File(defaultDirectory);
        if (!dataDir.exists()) {
            if (dataDir.mkdirs()) {
                logMessage("Создана папка данных: " + defaultDirectory);
            }
        }
    }

    private void loadInitialData() {
        // Попытка загрузить тестовые данные
        File testFile = new File(defaultDirectory + "devices.json");
        if (testFile.exists()) {
            executorService.execute(() -> {
                try {
                    JSONReadFile reader = new JSONReadFile();
                    Storage<Factory> devices = reader.readDevicesFromJSON(testFile.getAbsolutePath());

                    if (devices != null && devices.size() > 0) {
                        listStorage = devices;
                        mapStorage.clear();
                        for (Factory device : devices.getAllElements()) {
                            mapStorage.addElement(device);
                        }

                        SwingUtilities.invokeLater(() -> {
                            refreshDeviceTable();
                            logMessage("Загружены тестовые данные из devices.json");
                        });
                    }
                } catch (Exception e) {
                    logMessage("Не удалось загрузить тестовые данные: " + e.getMessage());
                }
            });
        }
    }

    private void clearAllDevices() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Вы уверены, что хотите удалить ВСЕ устройства?\n" +
                        "Всего устройств: " + listStorage.size(),
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            listStorage.clear();
            mapStorage.clear();
            refreshDeviceTable();
            logMessage("Все устройства удалены");
            JOptionPane.showMessageDialog(this,
                    "Все устройства удалены",
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void exportToFile() {
        if (listStorage.size() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Нет устройств для экспорта",
                    "Предупреждение",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] options = {"TXT", "XML", "JSON", "Отмена"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Выберите формат экспорта:",
                "Экспорт устройств",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice >= 0 && choice < 3) {
            writeFileInThread(options[choice]);
        }
    }

    private void exportSearchResults(List<Factory> results) {
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Нет результатов для экспорта",
                    "Предупреждение",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser(defaultDirectory);
        fileChooser.setDialogTitle("Экспорт результатов поиска");
        fileChooser.setSelectedFile(new File("search_results.txt"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File outputFile = fileChooser.getSelectedFile();

            executorService.execute(() -> {
                try {
                    StringBuilder content = new StringBuilder();
                    content.append("=== РЕЗУЛЬТАТЫ ПОИСКА ===\n");
                    content.append("Дата: ").append(new Date()).append("\n");
                    content.append("Найдено устройств: ").append(results.size()).append("\n\n");

                    for (Factory device : results) {
                        content.append("Тип: ").append(device.getDeviceType()).append("\n");
                        content.append("Модель: ").append(device.getModel()).append("\n");
                        content.append("Серийный номер: ").append(device.getSerialNumber()).append("\n");
                        content.append("Цена: $").append(device.getCost()).append("\n");
                        content.append("Дата выпуска: ").append(displayDateFormat.format(device.getReleaseDate())).append("\n");
                        content.append("---\n");
                    }

                    try (java.io.BufferedWriter writer = new java.io.BufferedWriter(
                            new java.io.FileWriter(outputFile))) {
                        writer.write(content.toString());
                    }

                    SwingUtilities.invokeLater(() -> {
                        logMessage("Результаты поиска экспортированы в: " + outputFile.getName());
                        JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                "Результаты поиска успешно экспортированы",
                                "Успех",
                                JOptionPane.INFORMATION_MESSAGE);
                    });
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> {
                        logMessage("Ошибка экспорта результатов: " + e.getMessage());
                        JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                "Ошибка экспорта результатов:\n" + e.getMessage(),
                                "Ошибка",
                                JOptionPane.ERROR_MESSAGE);
                    });
                }
            });
        }
    }

    private void exportStatistics() {
        if (listStorage.size() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Нет данных для экспорта статистики",
                    "Предупреждение",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser(defaultDirectory);
        fileChooser.setDialogTitle("Экспорт статистики");
        fileChooser.setSelectedFile(new File("statistics_" +
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File outputFile = fileChooser.getSelectedFile();

            executorService.execute(() -> {
                try {
                    List<Factory> devices = listStorage.getAllElements();

                    StringBuilder stats = new StringBuilder();
                    stats.append("=== СТАТИСТИКА УСТРОЙСТВ ===\n");
                    stats.append("Дата генерации: ").append(new Date()).append("\n");
                    stats.append("Всего устройств: ").append(devices.size()).append("\n\n");

                    // Статистика по типам
                    long computers = devices.stream().filter(d -> d instanceof Computers).count();
                    long tablets = devices.stream().filter(d -> d instanceof Tablets).count();
                    long laptops = devices.stream().filter(d -> d instanceof Laptops).count();

                    stats.append("=== РАСПРЕДЕЛЕНИЕ ПО ТИПАМ ===\n");
                    stats.append(String.format("Компьютеры: %d (%.1f%%)\n", computers,
                            devices.size() > 0 ? (computers * 100.0 / devices.size()) : 0));
                    stats.append(String.format("Планшеты:   %d (%.1f%%)\n", tablets,
                            devices.size() > 0 ? (tablets * 100.0 / devices.size()) : 0));
                    stats.append(String.format("Ноутбуки:   %d (%.1f%%)\n", laptops,
                            devices.size() > 0 ? (laptops * 100.0 / devices.size()) : 0));
                    stats.append("\n");

                    // Статистика по ценам
                    int minPrice = devices.stream().mapToInt(Factory::getCost).min().orElse(0);
                    int maxPrice = devices.stream().mapToInt(Factory::getCost).max().orElse(0);
                    double avgPrice = devices.stream().mapToInt(Factory::getCost).average().orElse(0);

                    stats.append("=== СТАТИСТИКА ЦЕН ===\n");
                    stats.append(String.format("Минимальная цена: $%d\n", minPrice));
                    stats.append(String.format("Максимальная цена: $%d\n", maxPrice));
                    stats.append(String.format("Средняя цена: $%.2f\n\n", avgPrice));

                    try (java.io.BufferedWriter writer = new java.io.BufferedWriter(
                            new java.io.FileWriter(outputFile))) {
                        writer.write(stats.toString());
                    }

                    SwingUtilities.invokeLater(() -> {
                        logMessage("Статистика экспортирована в: " + outputFile.getName());
                        JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                "Статистика успешно экспортирована",
                                "Успех",
                                JOptionPane.INFORMATION_MESSAGE);
                    });
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> {
                        logMessage("Ошибка экспорта статистики: " + e.getMessage());
                        JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                "Ошибка экспорта статистики:\n" + e.getMessage(),
                                "Ошибка",
                                JOptionPane.ERROR_MESSAGE);
                    });
                }
            });
        }
    }

    private void updateFileInfo(JTextArea fileInfoArea) {
        File dataDir = new File(defaultDirectory);
        StringBuilder info = new StringBuilder();

        info.append("=== ИНФОРМАЦИЯ О ФАЙЛАХ ===\n");
        info.append("Папка: ").append(dataDir.getAbsolutePath()).append("\n\n");

        if (dataDir.exists() && dataDir.isDirectory()) {
            File[] files = dataDir.listFiles((dir, name) ->
                    name.endsWith(".txt") || name.endsWith(".xml") ||
                            name.endsWith(".json") || name.endsWith(".zip"));

            if (files != null && files.length > 0) {
                info.append("Найдено файлов: ").append(files.length).append("\n");
                info.append("------------------------\n");

                for (File file : files) {
                    info.append(String.format("%-30s %10d байт\n",
                            file.getName(), file.length()));
                }
            } else {
                info.append("Файлы не найдены\n");
            }
        } else {
            info.append("Папка не существует\n");
        }

        fileInfoArea.setText(info.toString());
    }

    private void saveSettings(String path, String format, Integer interval) {
        defaultDirectory = path;
        logMessage("Настройки сохранены: путь=" + path + ", формат=" + format + ", интервал=" + interval);
        JOptionPane.showMessageDialog(this,
                "Настройки успешно сохранены",
                "Успех",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void restoreDefaultSettings(JTextField pathField, JComboBox<String> formatCombo,
                                        JComboBox<Integer> intervalCombo) {
        pathField.setText("data/");
        formatCombo.setSelectedItem("JSON");
        intervalCombo.setSelectedItem(10);
        logMessage("Настройки сброшены к значениям по умолчанию");
    }

    private void applySettings() {
        logMessage("Настройки применены");
        JOptionPane.showMessageDialog(this,
                "Настройки применены",
                "Успех",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showEncryptDialog() {
        JOptionPane.showMessageDialog(this,
                "Для шифрования файла выберите файл в диалоговом окне",
                "Шифрование файла",
                JOptionPane.INFORMATION_MESSAGE);
        encryptFile();
    }

    private void showDecryptDialog() {
        JOptionPane.showMessageDialog(this,
                "Для дешифрования файла выберите зашифрованный файл",
                "Дешифрование файла",
                JOptionPane.INFORMATION_MESSAGE);
        decryptFile();
    }

    private void showZipDialog() {
        JOptionPane.showMessageDialog(this,
                "Для создания архива выберите файлы",
                "Создание ZIP архива",
                JOptionPane.INFORMATION_MESSAGE);
        createZipArchive();
    }

    private void showUnzipDialog() {
        JFileChooser fileChooser = new JFileChooser(defaultDirectory);
        fileChooser.setDialogTitle("Выберите архив для распаковки");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "ZIP архивы", "zip"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File zipFile = fileChooser.getSelectedFile();

            JFileChooser dirChooser = new JFileChooser(defaultDirectory);
            dirChooser.setDialogTitle("Выберите папку для распаковки");
            dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (dirChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File destDir = dirChooser.getSelectedFile();

                executorService.execute(() -> {
                    try {
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setIndeterminate(true);
                            progressBar.setString("Распаковка архива...");
                        });

                        boolean success = Archive.extractZipArchive(zipFile.getAbsolutePath(),
                                destDir.getAbsolutePath());

                        SwingUtilities.invokeLater(() -> {
                            if (success) {
                                logMessage("Архив распакован: " + zipFile.getName() +
                                        " -> " + destDir.getAbsolutePath());
                                JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                        "Архив успешно распакован",
                                        "Успех",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                logMessage("Ошибка распаковки архива: " + zipFile.getName());
                                JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                        "Ошибка распаковки архива",
                                        "Ошибка",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        });
                    } catch (Exception e) {
                        SwingUtilities.invokeLater(() -> {
                            logMessage("Ошибка распаковки: " + e.getMessage());
                            JOptionPane.showMessageDialog(DeviceManagerGUI.this,
                                    "Ошибка распаковки:\n" + e.getMessage(),
                                    "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    } finally {
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setIndeterminate(false);
                            progressBar.setString("Готово");
                        });
                    }
                });
            }
        }
    }

    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Вы уверены, что хотите выйти?\n" +
                        "Несохраненные данные могут быть потеряны.",
                "Подтверждение выхода",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Останавливаем executor service
            executorService.shutdown();

            // Сохраняем данные перед выходом (опционально)
            if (listStorage.size() > 0) {
                try {
                    File autoSaveFile = new File(defaultDirectory + "autosave.json");
                    JSONWriteFile writer = new JSONWriteFile();
                    writer.writeDevicesToJSON(autoSaveFile.getAbsolutePath(), listStorage);
                    logMessage("Автосохранение выполнено перед выходом");
                } catch (Exception e) {
                    logMessage("Ошибка автосохранения: " + e.getMessage());
                }
            }

            System.exit(0);
        }
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "Управление устройствами v2.0\n\n" +
                        "Графический интерфейс для системы управления устройствами\n" +
                        "с использованием Builder Pattern и многопоточных операций\n\n" +
                        "© 2024 Система управления устройствами",
                "О программе",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHelpDialog() {
        JOptionPane.showMessageDialog(this,
                "📖 СПРАВКА ПО ПРОГРАММЕ\n\n" +
                        "1. Вкладка 'Устройства' - просмотр и управление устройствами\n" +
                        "2. Вкладка 'Добавить' - создание новых устройств через формы\n" +
                        "3. Вкладка 'Поиск' - поиск и фильтрация устройств\n" +
                        "4. Вкладка 'Файлы' - операции с файлами (многопоточные)\n" +
                        "5. Вкладка 'Статистика' - анализ и статистика данных\n" +
                        "6. Вкладка 'Builder' - стандартные конфигурации устройств\n" +
                        "7. Вкладка 'Настройки' - настройки программы\n\n" +
                        "💡 Подсказка: Используйте контекстное меню (правая кнопка мыши)\n" +
                        "для быстрого доступа к функциям в таблице устройств.",
                "Справка",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // Устанавливаем Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Настраиваем стили
            UIManager.put("TabbedPane.selected", Color.LIGHT_GRAY);
            UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 12));
            UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 12));
            UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 12));
            UIManager.put("TextArea.font", new Font("Arial", Font.PLAIN, 12));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Создаем и показываем GUI
        SwingUtilities.invokeLater(() -> {
            DeviceManagerGUI gui = new DeviceManagerGUI();
            gui.setVisible(true);

            // Показываем приветственное сообщение
            JOptionPane.showMessageDialog(gui,
                    "Добро пожаловать в систему управления устройствами!\n\n" +
                            "Для начала работы вы можете:\n" +
                            "1. Загрузить устройства из файла (Файл → Открыть)\n" +
                            "2. Добавить новое устройство (Вкладка 'Добавить')\n" +
                            "3. Использовать стандартные конфигурации (Вкладка 'Builder')",
                    "Добро пожаловать!",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
}