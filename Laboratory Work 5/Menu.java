import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Menu {
    private Scanner scanner;
    private Storage<Factory> listStorage;
    private Storage<Factory> mapStorage;
    private ReadFile readFile;
    private WriteFile writeFile;
    private XMLReadFile xmlReadFile;
    private XMLWriteFile xmlWriteFile;
    private JSONReadFile jsonReadFile;
    private JSONWriteFile jsonWriteFile;
    private DeviceDirector deviceDirector;
    private SimpleDateFormat dateFormat;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.listStorage = new ListObjects<>();
        this.mapStorage = new MapObjects<>();
        this.readFile = new ReadFile();
        this.writeFile = new WriteFile();
        this.xmlReadFile = new XMLReadFile();
        this.xmlWriteFile = new XMLWriteFile();
        this.jsonReadFile = new JSONReadFile();
        this.jsonWriteFile = new JSONWriteFile();
        this.deviceDirector = new DeviceDirector();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("\n=== Система управления устройствами ===");
            System.out.println("1. Чтение из текстового файла");
            System.out.println("2. Показать все устройства");
            System.out.println("3. Добавить новое устройство");
            System.out.println("4. Удалить устройство");
            System.out.println("5. Обновить устройство");
            System.out.println("6. Поиск устройств");
            System.out.println("7. Сортировка устройств");
            System.out.println("8. Запись в текстовый файл (с шифрованием)");
            System.out.println("9. Статистика устройств");
            System.out.println("10. Чтение из XML файла");
            System.out.println("11. Запись в XML файл");
            System.out.println("12. Чтение из JSON файла");
            System.out.println("13. Запись в JSON файл");
            System.out.println("14. Шифрование данных");
            System.out.println("15. Архивация");
            System.out.println("16. Демонстрация Builder Pattern");
            System.out.println("0. Выход");
            System.out.print("Выберите опцию: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> readFromFile();
                    case 2 -> displayDevices();
                    case 3 -> addDevice();
                    case 4 -> removeDevice();
                    case 5 -> updateDevice();
                    case 6 -> searchDevices();
                    case 7 -> sortDevices();
                    case 8 -> writeToFile();
                    case 9 -> showStatistics();
                    case 10 -> readFromXML();
                    case 11 -> writeToXML();
                    case 12 -> readFromJSON();
                    case 13 -> writeToJSON();
                    case 14 -> encryptionMenu();
                    case 15 -> archiveMenu();
                    case 16 -> showBuilderDemoMenu();
                    case 0 -> {
                        System.out.println("Завершение работы.");
                        return;
                    }
                    default -> System.out.println("Неверная опция!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число!");
            }
        }
    }

    private void encryptionMenu() {
        while (true) {
            System.out.println("\n=== Шифрование данных ===");
            System.out.println("1. Зашифровать output.txt");
            System.out.println("2. Тест шифрования строки");
            System.out.println("0. Назад");
            System.out.print("Выберите опцию: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> encryptOutputFile();
                    case 2 -> testEncryption();
                    case 0 -> { return; }
                    default -> System.out.println("Неверная опция!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число!");
            }
        }
    }

    private void archiveMenu() {
        while (true) {
            System.out.println("\n=== Архивация ===");
            System.out.println("1. Создать ZIP архив файла");
            System.out.println("2. Создать ZIP архив нескольких файлов");
            System.out.println("3. Извлечь ZIP архив");
            System.out.println("4. Создать JAR архив файла");
            System.out.println("0. Назад");
            System.out.print("Выберите опцию: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> createZipArchive();
                    case 2 -> createMultiFileZipArchive();
                    case 3 -> extractZipArchive();
                    case 4 -> createJarArchive();
                    case 0 -> { return; }
                    default -> System.out.println("Неверная опция!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число!");
            }
        }
    }

    private void showBuilderDemoMenu() {
        while (true) {
            System.out.println("\n=== Демонстрация Builder Pattern ===");
            System.out.println("1. Собрать стандартный офисный компьютер");
            System.out.println("2. Собрать игровой компьютер");
            System.out.println("3. Собрать бюджетный планшет");
            System.out.println("4. Собрать премиум планшет");
            System.out.println("5. Собрать офисный ноутбук");
            System.out.println("6. Собрать игровой ноутбук");
            System.out.println("7. Кастомная сборка компьютера");
            System.out.println("8. Кастомная сборка планшета");
            System.out.println("9. Кастомная сборка ноутбука");
            System.out.println("10. Показать все собранные устройства");
            System.out.println("11. Информация о шагах сборки");
            System.out.println("0. Назад");
            System.out.print("Выберите опцию: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> buildStandardOfficeComputer();
                    case 2 -> buildGamingComputer();
                    case 3 -> buildBudgetTablet();
                    case 4 -> buildPremiumTablet();
                    case 5 -> buildOfficeLaptop();
                    case 6 -> buildGamingLaptop();
                    case 7 -> buildCustomComputer();
                    case 8 -> buildCustomTablet();
                    case 9 -> buildCustomLaptop();
                    case 10 -> displayBuilderDevices();
                    case 11 -> showBuildStepsInfo();
                    case 0 -> { return; }
                    default -> System.out.println("Неверная опция!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число!");
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void readFromFile() {
        String filename = "C:\\Users\\stepa\\IdeaProjects\\Laboratory Work 3\\data\\input.txt";
        Storage<Factory> devices = readFile.readDevicesFromFile(filename);
        if (devices.size() > 0) {
            listStorage = devices;
            mapStorage.clear();
            for (Factory device : devices.getAllElements()) {
                mapStorage.addElement(device);
            }
            System.out.println("Успешно загружено " + devices.size() + " устройств.");
        } else {
            System.out.println("Устройства не загружены или файл не найден.");
        }
    }

    private void displayDevices() {
        System.out.println("\n=== Опции отображения ===");
        System.out.println("1. Показать из хранилища List");
        System.out.println("2. Показать из хранилища Map");
        System.out.print("Выберите опцию: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                listStorage.displayAll();
            } else if (choice == 2) {
                mapStorage.displayAll();
            } else {
                System.out.println("Неверная опция!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод!");
        }
    }

    private void addDevice() {
        System.out.println("\n=== Добавление нового устройства ===");
        System.out.println("1. Компьютер (через Builder Pattern)");
        System.out.println("2. Планшет (через Builder Pattern)");
        System.out.println("3. Ноутбук (через Builder Pattern)");
        System.out.print("Выберите тип устройства: ");

        try {
            int typeChoice = Integer.parseInt(scanner.nextLine());
            Factory device = null;

            switch (typeChoice) {
                case 1 -> device = createComputer();
                case 2 -> device = createTablet();
                case 3 -> device = createLaptop();
                default -> {
                    System.out.println("Неверный тип устройства!");
                    return;
                }
            }

            if (device != null) {
                listStorage.addElement(device);
                mapStorage.addElement(device);
                System.out.println("\nУстройство успешно собрано через Builder Pattern и добавлено!");
                System.out.println("Информация об устройстве:");
                device.getInfo();
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод!");
        } catch (Exception e) {
            System.out.println("Ошибка создания устройства: " + e.getMessage());
        }
    }

    private Factory createComputer() throws ParseException {
        System.out.println("\n=== Сборка компьютера через Builder Pattern ===");
        System.out.print("Модель: ");
        String model = scanner.nextLine();
        System.out.print("Номер модели: ");
        String modelNumber = scanner.nextLine();
        System.out.print("Серийный номер: ");
        String serialNumber = scanner.nextLine();
        System.out.print("Цена: ");
        int cost = Integer.parseInt(scanner.nextLine());
        System.out.print("Дата выпуска (дд/ММ/гггг): ");
        Date releaseDate = dateFormat.parse(scanner.nextLine());
        System.out.print("Корпус: ");
        String caseType = scanner.nextLine();
        System.out.print("Материнская плата: ");
        String motherboard = scanner.nextLine();
        System.out.print("Процессор: ");
        String processor = scanner.nextLine();
        System.out.print("Жесткий диск: ");
        String hardDrive = scanner.nextLine();
        System.out.print("Оперативная память: ");
        String ram = scanner.nextLine();
        System.out.print("Блок питания: ");
        String powerSupply = scanner.nextLine();

        return new ComputerBuilder()
                .setModel(model)
                .setModelNumber(modelNumber)
                .setSerialNumber(serialNumber)
                .setCost(cost)
                .setReleaseDate(releaseDate)
                .setCaseType(caseType)
                .setMotherboard(motherboard)
                .setProcessor(processor)
                .setHardDrive(hardDrive)
                .setRam(ram)
                .setPowerSupply(powerSupply)
                .build();
    }

    private Factory createTablet() throws ParseException {
        System.out.println("\n=== Сборка планшета через Builder Pattern ===");
        System.out.print("Модель: ");
        String model = scanner.nextLine();
        System.out.print("Номер модели: ");
        String modelNumber = scanner.nextLine();
        System.out.print("Серийный номер: ");
        String serialNumber = scanner.nextLine();
        System.out.print("Цена: ");
        int cost = Integer.parseInt(scanner.nextLine());
        System.out.print("Дата выпуска (дд/ММ/гггг): ");
        Date releaseDate = dateFormat.parse(scanner.nextLine());
        System.out.print("Корпус: ");
        String caseType = scanner.nextLine();
        System.out.print("Процессор: ");
        String processor = scanner.nextLine();
        System.out.print("WIFI модуль (да/нет): ");
        boolean wifiModule = parseBoolean(scanner.nextLine());
        System.out.print("Экран: ");
        String screen = scanner.nextLine();

        return new TabletBuilder()
                .setModel(model)
                .setModelNumber(modelNumber)
                .setSerialNumber(serialNumber)
                .setCost(cost)
                .setReleaseDate(releaseDate)
                .setCaseType(caseType)
                .setProcessor(processor)
                .setWifiModule(wifiModule)
                .setScreen(screen)
                .build();
    }

    private Factory createLaptop() throws ParseException {
        System.out.println("\n=== Сборка ноутбука через Builder Pattern ===");
        System.out.print("Модель: ");
        String model = scanner.nextLine();
        System.out.print("Номер модели: ");
        String modelNumber = scanner.nextLine();
        System.out.print("Серийный номер: ");
        String serialNumber = scanner.nextLine();
        System.out.print("Цена: ");
        int cost = Integer.parseInt(scanner.nextLine());
        System.out.print("Дата выпуска (дд/ММ/гггг): ");
        Date releaseDate = dateFormat.parse(scanner.nextLine());
        System.out.print("Корпус: ");
        String caseType = scanner.nextLine();
        System.out.print("Материнская плата: ");
        String motherboard = scanner.nextLine();
        System.out.print("Процессор: ");
        String processor = scanner.nextLine();
        System.out.print("Жесткий диск: ");
        String hardDrive = scanner.nextLine();
        System.out.print("Оперативная память: ");
        String ram = scanner.nextLine();
        System.out.print("Блок питания: ");
        String powerSupply = scanner.nextLine();
        System.out.print("Клавиатура: ");
        String keyboard = scanner.nextLine();
        System.out.print("Динамики: ");
        String speakers = scanner.nextLine();
        System.out.print("TouchPad (да/нет): ");
        boolean touchPad = parseBoolean(scanner.nextLine());

        return new LaptopBuilder()
                .setModel(model)
                .setModelNumber(modelNumber)
                .setSerialNumber(serialNumber)
                .setCost(cost)
                .setReleaseDate(releaseDate)
                .setCaseType(caseType)
                .setMotherboard(motherboard)
                .setProcessor(processor)
                .setHardDrive(hardDrive)
                .setRam(ram)
                .setPowerSupply(powerSupply)
                .setKeyboard(keyboard)
                .setSpeakers(speakers)
                .setTouchPad(touchPad)
                .build();
    }

    private void removeDevice() {
        System.out.print("Введите серийный номер для удаления: ");
        String serialNumber = scanner.nextLine();

        boolean listRemoved = listStorage.removeElement(serialNumber);
        boolean mapRemoved = mapStorage.removeElement(serialNumber);

        if (listRemoved && mapRemoved) {
            System.out.println("Устройство успешно удалено!");
        } else {
            System.out.println("Устройство не найдено!");
        }
    }

    private void updateDevice() {
        System.out.print("Введите серийный номер для обновления: ");
        String serialNumber = scanner.nextLine();

        Factory existing = listStorage.getElement(serialNumber);
        if (existing == null) {
            System.out.println("Устройство не найдено!");
            return;
        }

        System.out.println("Текущее устройство: " + existing);
        System.out.println("Введите новые данные:");

        try {
            Factory updated = null;
            if (existing instanceof Computers) {
                updated = createComputer();
            } else if (existing instanceof Tablets) {
                updated = createTablet();
            } else if (existing instanceof Laptops) {
                updated = createLaptop();
            }

            if (updated != null && updated.getSerialNumber().equals(serialNumber)) {
                listStorage.updateElement(serialNumber, updated);
                mapStorage.updateElement(serialNumber, updated);
                System.out.println("Устройство успешно обновлено!");
            } else {
                System.out.println("Ошибка обновления! Серийный номер нельзя изменить.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка обновления устройства: " + e.getMessage());
        }
    }

    private void searchDevices() {
        System.out.println("\n=== Опции поиска ===");
        System.out.println("1. По типу устройства");
        System.out.println("2. По диапазону цены");
        System.out.println("3. По названию модели");
        System.out.print("Выберите опцию: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            List<Factory> results = null;

            switch (choice) {
                case 1 -> {
                    System.out.println("1. Компьютер\n2. Планшет\n3. Ноутбук");
                    System.out.print("Выберите тип: ");
                    int typeChoice = Integer.parseInt(scanner.nextLine());
                    String type = switch (typeChoice) {
                        case 1 -> "Компьютер";
                        case 2 -> "Планшет";
                        case 3 -> "Ноутбук";
                        default -> null;
                    };
                    if (type != null) {
                        results = listStorage.findElements(device -> device.getDeviceType().equals(type));
                    }
                }
                case 2 -> {
                    System.out.print("Минимальная цена: ");
                    int minCost = Integer.parseInt(scanner.nextLine());
                    System.out.print("Максимальная цена: ");
                    int maxCost = Integer.parseInt(scanner.nextLine());
                    results = listStorage.findElements(device ->
                            device.getCost() >= minCost && device.getCost() <= maxCost);
                }
                case 3 -> {
                    System.out.print("Введите название модели: ");
                    String model = scanner.nextLine();
                    results = listStorage.findElements(device ->
                            device.getModel().toLowerCase().contains(model.toLowerCase()));
                }
            }

            if (results != null && !results.isEmpty()) {
                System.out.println("Результаты поиска (" + results.size() + " устройств):");
                for (int i = 0; i < results.size(); i++) {
                    System.out.println((i + 1) + ". " + results.get(i));
                }
            } else {
                System.out.println("Устройства не найдены!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод!");
        }
    }

    private void sortDevices() {
        System.out.println("\n=== Опции сортировки ===");
        System.out.println("1. По цене (возрастание)");
        System.out.println("2. По цене (убывание)");
        System.out.println("3. По дате выпуска");
        System.out.println("4. По названию модели");
        System.out.println("5. Пользовательская сортировка (лямбда)");
        System.out.print("Выберите опцию: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    listStorage.sort(Comparator.comparingInt(Factory::getCost));
                    mapStorage.sort(Comparator.comparingInt(Factory::getCost));
                    System.out.println("Отсортировано по цене (возрастание)!");
                }
                case 2 -> {
                    listStorage.sort((d1, d2) -> Integer.compare(d2.getCost(), d1.getCost()));
                    mapStorage.sort((d1, d2) -> Integer.compare(d2.getCost(), d1.getCost()));
                    System.out.println("Отсортировано по цене (убывание)!");
                }
                case 3 -> {
                    listStorage.sort(Comparator.comparing(Factory::getReleaseDate));
                    mapStorage.sort(Comparator.comparing(Factory::getReleaseDate));
                    System.out.println("Отсортировано по дате выпуска!");
                }
                case 4 -> {
                    listStorage.sort(Comparator.comparing(Factory::getModel));
                    mapStorage.sort(Comparator.comparing(Factory::getModel));
                    System.out.println("Отсортировано по названию модели!");
                }
                case 5 -> {
                    System.out.println("1. По типу затем по цене");
                    System.out.println("2. По цене затем по модели");
                    System.out.print("Выберите пользовательскую сортировку: ");
                    int customChoice = Integer.parseInt(scanner.nextLine());

                    if (customChoice == 1) {
                        listStorage.sort((d1, d2) -> {
                            int typeCompare = d1.getDeviceType().compareTo(d2.getDeviceType());
                            return typeCompare != 0 ? typeCompare : Integer.compare(d1.getCost(), d2.getCost());
                        });
                        mapStorage.sort((d1, d2) -> {
                            int typeCompare = d1.getDeviceType().compareTo(d2.getDeviceType());
                            return typeCompare != 0 ? typeCompare : Integer.compare(d1.getCost(), d2.getCost());
                        });
                        System.out.println("Отсортировано по типу и цене!");
                    } else if (customChoice == 2) {
                        listStorage.sort((d1, d2) -> {
                            int costCompare = Integer.compare(d1.getCost(), d2.getCost());
                            return costCompare != 0 ? costCompare : d1.getModel().compareTo(d2.getModel());
                        });
                        mapStorage.sort((d1, d2) -> {
                            int costCompare = Integer.compare(d1.getCost(), d2.getCost());
                            return costCompare != 0 ? costCompare : d1.getModel().compareTo(d2.getModel());
                        });
                        System.out.println("Отсортировано по цене и модели!");
                    }
                }
                default -> System.out.println("Неверная опция!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод!");
        }
    }

    private void writeToFile() {
        String filename = "C:\\Users\\stepa\\IdeaProjects\\Laboratory Work 3\\data\\output.txt";
        boolean success = writeFile.writeDevicesToFile(filename, listStorage, true);
        if (success) {
            System.out.println("Устройства успешно записаны в файл (с шифрованием)!");
        } else {
            System.out.println("Ошибка записи устройств в файл!");
        }
    }

    private void showStatistics() {
        List<Factory> devices = listStorage.getAllElements();
        System.out.println("\n=== Статистика устройств ===");
        System.out.println("Всего устройств: " + devices.size());

        long computers = devices.stream().filter(d -> d instanceof Computers).count();
        long tablets = devices.stream().filter(d -> d instanceof Tablets).count();
        long laptops = devices.stream().filter(d -> d instanceof Laptops).count();

        System.out.println("Компьютеров: " + computers);
        System.out.println("Планшетов: " + tablets);
        System.out.println("Ноутбуков: " + laptops);

        if (!devices.isEmpty()) {
            double avgCost = devices.stream().mapToInt(Factory::getCost).average().orElse(0);
            int maxCost = devices.stream().mapToInt(Factory::getCost).max().orElse(0);
            int minCost = devices.stream().mapToInt(Factory::getCost).min().orElse(0);

            System.out.println("Средняя цена: $" + String.format("%.2f", avgCost));
            System.out.println("Максимальная цена: $" + maxCost);
            System.out.println("Минимальная цена: $" + minCost);
        }
    }

    private void readFromXML() {
        String filename = "C:\\Users\\stepa\\IdeaProjects\\Laboratory Work 3\\data\\input.xml";
        Storage<Factory> devices = xmlReadFile.readDevicesFromXML(filename);
        if (devices.size() > 0) {
            listStorage = devices;
            mapStorage.clear();
            for (Factory device : devices.getAllElements()) {
                mapStorage.addElement(device);
            }
            System.out.println("Успешно загружено " + devices.size() + " устройств из XML файла.");
        } else {
            System.out.println("Устройства не загружены или файл не найден.");
        }
    }

    private void writeToXML() {
        String filename = "C:\\Users\\stepa\\IdeaProjects\\Laboratory Work 3\\data\\output.xml";
        boolean success = xmlWriteFile.writeDevicesToXML(filename, listStorage);
        if (success) {
            System.out.println("Устройства успешно записаны в XML файл!");
        } else {
            System.out.println("Ошибка записи устройств в XML файл!");
        }
    }

    private void readFromJSON() {
        String filename = "C:\\Users\\stepa\\IdeaProjects\\Laboratory Work 3\\data\\input.json";
        Storage<Factory> devices = jsonReadFile.readDevicesFromJSON(filename);
        if (devices.size() > 0) {
            listStorage = devices;
            mapStorage.clear();
            for (Factory device : devices.getAllElements()) {
                mapStorage.addElement(device);
            }
            System.out.println("Успешно загружено " + devices.size() + " устройств из JSON файла.");
        } else {
            System.out.println("Устройства не загружены или файл не найден.");
        }
    }

    private void writeToJSON() {
        String filename = "C:\\Users\\stepa\\IdeaProjects\\Laboratory Work 3\\data\\output.json";
        boolean success = jsonWriteFile.writeDevicesToJSON(filename, listStorage);
        if (success) {
            System.out.println("Устройства успешно записаны в JSON файл!");
        } else {
            System.out.println("Ошибка записи устройств в JSON файл!");
        }
    }

    private void encryptOutputFile() {
        String sourceFile = "C:\\Users\\stepa\\IdeaProjects\\Laboratory Work 3\\data\\output.txt";
        System.out.print("Введите имя файла для сохранения шифра: ");
        String encryptedFile = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(encryptedFile))) {

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            String encryptedContent = Encryption.encrypt(content.toString());
            writer.write(encryptedContent);
            System.out.println("Файл output.txt успешно зашифрован в " + encryptedFile + "!");

        } catch (IOException e) {
            System.out.println("Ошибка шифрования файла: " + e.getMessage());
        }
    }

    private void testEncryption() {
        System.out.print("Введите строку для теста шифрования: ");
        String testString = scanner.nextLine();

        String encrypted = Encryption.encrypt(testString);

        System.out.println("Исходная строка: " + testString);
        System.out.println("Зашифрованная: " + encrypted);
        System.out.println("Шифрование успешно выполнено!");
    }

    private void createZipArchive() {
        String sourceFile = "C:\\Users\\stepa\\IdeaProjects\\Laboratory Work 3\\data\\output.txt";
        System.out.print("Введите имя ZIP архива: ");
        String zipFile = scanner.nextLine();

        boolean success = Archive.createZipArchive(sourceFile, zipFile);
        if (success) {
            System.out.println("ZIP архив успешно создан!");
        } else {
            System.out.println("Ошибка создания ZIP архива!");
        }
    }

    private void createMultiFileZipArchive() {
        System.out.print("Введите количество файлов для архивации: ");
        try {
            int count = Integer.parseInt(scanner.nextLine());
            List<String> files = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                System.out.print("Введите имя файла " + (i + 1) + ": ");
                files.add(scanner.nextLine());
            }

            System.out.print("Введите имя ZIP архива: ");
            String zipFile = scanner.nextLine();

            boolean success = Archive.createZipArchive(files, zipFile);
            if (success) {
                System.out.println("ZIP архив успешно создан!");
            } else {
                System.out.println("Ошибка создания ZIP архива!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат числа!");
        }
    }

    private void extractZipArchive() {
        System.out.print("Введите имя ZIP архива: ");
        String zipFile = scanner.nextLine();
        System.out.print("Введите папку для извлечения: ");
        String destDir = scanner.nextLine();

        boolean success = Archive.extractZipArchive(zipFile, destDir);
        if (success) {
            System.out.println("ZIP архив успешно извлечен!");
        } else {
            System.out.println("Ошибка извлечения ZIP архива!");
        }
    }

    private void createJarArchive() {
        String sourceFile = "C:\\Users\\stepa\\IdeaProjects\\Laboratory Work 3\\data\\output.txt";
        System.out.print("Введите имя JAR архива: ");
        String jarFile = scanner.nextLine();

        boolean success = Archive.createJarArchive(sourceFile, jarFile);
        if (success) {
            System.out.println("JAR архив успешно создан!");
        } else {
            System.out.println("Ошибка создания JAR архива!");
        }
    }

    // Методы для демонстрации Builder Pattern
    private void buildStandardOfficeComputer() {
        System.out.println("\n=== Сборка стандартного офисного компьютера ===");
        Computers computer = deviceDirector.buildStandardOfficeComputer();
        listStorage.addElement(computer);
        mapStorage.addElement(computer);

        System.out.println("Устройство успешно собрано и добавлено в хранилище!");
        System.out.println("\nИнформация об устройстве:");
        computer.getInfo();

        System.out.println("\nШаги сборки:");
        System.out.println("1. Установка корпуса: " + computer.getCaseType());
        System.out.println("2. Установка материнской платы: " + computer.getMotherboard());
        System.out.println("3. Установка процессора: " + computer.getProcessor());
        System.out.println("4. Установка жесткого диска: " + computer.getHardDrive());
        System.out.println("5. Установка оперативной памяти: " + computer.getRam());
        System.out.println("6. Установка блока питания: " + computer.getPowerSupply());
    }

    private void buildGamingComputer() {
        System.out.println("\n=== Сборка игрового компьютера ===");
        Computers computer = deviceDirector.buildGamingComputer();
        listStorage.addElement(computer);
        mapStorage.addElement(computer);

        System.out.println("Устройство успешно собрано и добавлено в хранилище!");
        System.out.println("\nИнформация об устройстве:");
        computer.getInfo();
    }

    private void buildBudgetTablet() {
        System.out.println("\n=== Сборка бюджетного планшета ===");
        Tablets tablet = deviceDirector.buildBudgetTablet();
        listStorage.addElement(tablet);
        mapStorage.addElement(tablet);

        System.out.println("Устройство успешно собрано и добавлено в хранилище!");
        System.out.println("\nИнформация об устройстве:");
        tablet.getInfo();

        System.out.println("\nШаги сборки:");
        System.out.println("1. Установка корпуса: " + tablet.getCaseType());
        System.out.println("2. Установка процессора: " + tablet.getProcessor());
        System.out.println("3. Установка WIFI модуля: " + (tablet.isWifiModule() ? "Да" : "Нет"));
        System.out.println("4. Установка экрана: " + tablet.getScreen());
    }

    private void buildPremiumTablet() {
        System.out.println("\n=== Сборка премиум планшета ===");
        Tablets tablet = deviceDirector.buildPremiumTablet();
        listStorage.addElement(tablet);
        mapStorage.addElement(tablet);

        System.out.println("Устройство успешно собрано и добавлено в хранилище!");
        System.out.println("\nИнформация об устройстве:");
        tablet.getInfo();
    }

    private void buildOfficeLaptop() {
        System.out.println("\n=== Сборка офисного ноутбука ===");
        Laptops laptop = deviceDirector.buildOfficeLaptop();
        listStorage.addElement(laptop);
        mapStorage.addElement(laptop);

        System.out.println("Устройство успешно собрано и добавлено в хранилище!");
        System.out.println("\nИнформация об устройстве:");
        laptop.getInfo();

        System.out.println("\nШаги сборки:");
        System.out.println("1. Установка корпуса: " + laptop.getCaseType());
        System.out.println("2. Установка материнской платы: " + laptop.getMotherboard());
        System.out.println("3. Установка процессора: " + laptop.getProcessor());
        System.out.println("4. Установка жесткого диска: " + laptop.getHardDrive());
        System.out.println("5. Установка оперативной памяти: " + laptop.getRam());
        System.out.println("6. Установка блока питания: " + laptop.getPowerSupply());
        System.out.println("7. Установка клавиатуры: " + laptop.getKeyboard());
        System.out.println("8. Установка динамиков: " + laptop.getSpeakers());
        System.out.println("9. Установка TouchPad: " + (laptop.isTouchPad() ? "Да" : "Нет"));
    }

    private void buildGamingLaptop() {
        System.out.println("\n=== Сборка игрового ноутбука ===");
        Laptops laptop = deviceDirector.buildGamingLaptop();
        listStorage.addElement(laptop);
        mapStorage.addElement(laptop);

        System.out.println("Устройство успешно собрано и добавлено в хранилище!");
        System.out.println("\nИнформация об устройстве:");
        laptop.getInfo();
    }

    private void buildCustomComputer() throws ParseException {
        System.out.println("\n=== Кастомная сборка компьютера ===");

        System.out.print("Модель: ");
        String model = scanner.nextLine();
        System.out.print("Номер модели: ");
        String modelNumber = scanner.nextLine();
        System.out.print("Серийный номер: ");
        String serialNumber = scanner.nextLine();
        System.out.print("Цена: ");
        int cost = Integer.parseInt(scanner.nextLine());
        System.out.print("Дата выпуска (дд/ММ/гггг): ");
        Date releaseDate = dateFormat.parse(scanner.nextLine());
        System.out.print("Корпус: ");
        String caseType = scanner.nextLine();
        System.out.print("Материнская плата: ");
        String motherboard = scanner.nextLine();
        System.out.print("Процессор: ");
        String processor = scanner.nextLine();
        System.out.print("Жесткий диск: ");
        String hardDrive = scanner.nextLine();
        System.out.print("Оперативная память: ");
        String ram = scanner.nextLine();
        System.out.print("Блок питания: ");
        String powerSupply = scanner.nextLine();

        Computers computer = deviceDirector.buildCustomComputer(
                model, modelNumber, serialNumber, cost, releaseDate,
                caseType, motherboard, processor, hardDrive, ram, powerSupply
        );

        listStorage.addElement(computer);
        mapStorage.addElement(computer);

        System.out.println("\nКомпьютер успешно собран по кастомной конфигурации!");
        System.out.println("Информация об устройстве:");
        computer.getInfo();
    }

    private void buildCustomTablet() throws ParseException {
        System.out.println("\n=== Кастомная сборка планшета ===");

        System.out.print("Модель: ");
        String model = scanner.nextLine();
        System.out.print("Номер модели: ");
        String modelNumber = scanner.nextLine();
        System.out.print("Серийный номер: ");
        String serialNumber = scanner.nextLine();
        System.out.print("Цена: ");
        int cost = Integer.parseInt(scanner.nextLine());
        System.out.print("Дата выпуска (дд/ММ/гггг): ");
        Date releaseDate = dateFormat.parse(scanner.nextLine());
        System.out.print("Корпус: ");
        String caseType = scanner.nextLine();
        System.out.print("Процессор: ");
        String processor = scanner.nextLine();
        System.out.print("WIFI модуль (да/нет): ");
        boolean wifiModule = parseBoolean(scanner.nextLine());
        System.out.print("Экран: ");
        String screen = scanner.nextLine();

        Tablets tablet = deviceDirector.buildCustomTablet(
                model, modelNumber, serialNumber, cost, releaseDate,
                caseType, processor, wifiModule, screen
        );

        listStorage.addElement(tablet);
        mapStorage.addElement(tablet);

        System.out.println("\nПланшет успешно собран по кастомной конфигурации!");
        System.out.println("Информация об устройстве:");
        tablet.getInfo();
    }

    private void buildCustomLaptop() throws ParseException {
        System.out.println("\n=== Кастомная сборка ноутбука ===");

        System.out.print("Модель: ");
        String model = scanner.nextLine();
        System.out.print("Номер модели: ");
        String modelNumber = scanner.nextLine();
        System.out.print("Серийный номер: ");
        String serialNumber = scanner.nextLine();
        System.out.print("Цена: ");
        int cost = Integer.parseInt(scanner.nextLine());
        System.out.print("Дата выпуска (дд/ММ/гггг): ");
        Date releaseDate = dateFormat.parse(scanner.nextLine());
        System.out.print("Корпус: ");
        String caseType = scanner.nextLine();
        System.out.print("Материнская плата: ");
        String motherboard = scanner.nextLine();
        System.out.print("Процессор: ");
        String processor = scanner.nextLine();
        System.out.print("Жесткий диск: ");
        String hardDrive = scanner.nextLine();
        System.out.print("Оперативная память: ");
        String ram = scanner.nextLine();
        System.out.print("Блок питания: ");
        String powerSupply = scanner.nextLine();
        System.out.print("Клавиатура: ");
        String keyboard = scanner.nextLine();
        System.out.print("Динамики: ");
        String speakers = scanner.nextLine();
        System.out.print("TouchPad (да/нет): ");
        boolean touchPad = parseBoolean(scanner.nextLine());

        Laptops laptop = deviceDirector.buildCustomLaptop(
                model, modelNumber, serialNumber, cost, releaseDate,
                caseType, motherboard, processor, hardDrive, ram, powerSupply,
                keyboard, speakers, touchPad
        );

        listStorage.addElement(laptop);
        mapStorage.addElement(laptop);

        System.out.println("\nНоутбук успешно собран по кастомной конфигурации!");
        System.out.println("Информация об устройстве:");
        laptop.getInfo();
    }

    private void displayBuilderDevices() {
        System.out.println("\n=== Устройства, собранные через Builder Pattern ===");
        System.out.println("Всего устройств: " + listStorage.size());

        if (listStorage.size() == 0) {
            System.out.println("Устройств еще не собрано.");
            return;
        }

        System.out.println("\nСписок устройств:");
        listStorage.displayAll();

        List<Factory> devices = listStorage.getAllElements();
        long computers = devices.stream().filter(d -> d instanceof Computers).count();
        long tablets = devices.stream().filter(d -> d instanceof Tablets).count();
        long laptops = devices.stream().filter(d -> d instanceof Laptops).count();

        System.out.println("\nСтатистика:");
        System.out.println("Компьютеров: " + computers);
        System.out.println("Планшетов: " + tablets);
        System.out.println("Ноутбуков: " + laptops);
    }

    private void showBuildStepsInfo() {
        System.out.println("\n=== Информация о шагах сборки Builder Pattern ===");
        System.out.println("Builder Pattern состоит из следующих компонентов:");
        System.out.println("\n1. Product (Продукт) - конечный объект:");
        System.out.println("   - Computers (компьютер)");
        System.out.println("   - Tablets (планшет)");
        System.out.println("   - Laptops (ноутбук)");

        System.out.println("\n2. Builder (Строитель) - абстрактный класс:");
        System.out.println("   - DeviceBuilder (определяет общие этапы сборки)");

        System.out.println("\n3. Concrete Builders (Конкретные строители):");
        System.out.println("   - ComputerBuilder (собирает компьютеры)");
        System.out.println("   - TabletBuilder (собирает планшеты)");
        System.out.println("   - LaptopBuilder (собирает ноутбуки)");

        System.out.println("\n4. Director (Директор) - управляет процессом сборки:");
        System.out.println("   - DeviceDirector (знает последовательность шагов)");

        System.out.println("\n=== Этапы сборки для каждого устройства ===");

        System.out.println("\nКомпьютер:");
        System.out.println("1. Установка корпуса");
        System.out.println("2. Установка материнской платы");
        System.out.println("3. Установка процессора");
        System.out.println("4. Установка жесткого диска");
        System.out.println("5. Установка оперативной памяти");
        System.out.println("6. Установка блока питания");

        System.out.println("\nПланшет:");
        System.out.println("1. Установка корпуса");
        System.out.println("2. Установка процессора");
        System.out.println("3. Установка WIFI модуля");
        System.out.println("4. Установка экрана");

        System.out.println("\nНоутбук:");
        System.out.println("1. Установка корпуса");
        System.out.println("2. Установка материнской плата");
        System.out.println("3. Установка процессора");
        System.out.println("4. Установка жесткого диска");
        System.out.println("5. Установка оперативной памяти");
        System.out.println("6. Установка блока питания");
        System.out.println("7. Установка клавиатуры");
        System.out.println("8. Установка динамиков");
        System.out.println("9. Установка TouchPad");

        System.out.println("\n=== Преимущества Builder Pattern ===");
        System.out.println("1. Пошаговая сборка сложных объектов");
        System.out.println("2. Изоляция кода сборки от бизнес-логики");
        System.out.println("3. Создание различных представлений объекта");
        System.out.println("4. Гибкость при добавлении новых компонентов");
        System.out.println("5. Возможность создания стандартных конфигураций");
    }

    private boolean parseBoolean(String value) {
        if (value == null) return false;
        String lower = value.toLowerCase();
        return lower.equals("yes") || lower.equals("true") || lower.equals("1") || lower.equals("да");
    }
}