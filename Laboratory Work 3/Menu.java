import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Menu {
    private Scanner scanner;
    private Storage<Factory> listStorage;
    private Storage<Factory> mapStorage;
    private ReadFile readFile;
    private WriteFile writeFile;
    private XMLReadFile xmlReadFile;
    private XMLWriteFile xmlWriteFile;
    private JSONReadFile jsonReadFile;
    private JSONWriteFile jsonWriteFile;
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
            System.out.println("8. Запись в текстовый файл");
            System.out.println("9. Статистика устройств");
            System.out.println("10. Чтение из XML файла");
            System.out.println("11. Запись в XML файл");
            System.out.println("12. Чтение из JSON файла");
            System.out.println("13. Запись в JSON файл");
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

    private void readFromXML() {
        System.out.print("Введите имя XML файла: ");
        String filename = scanner.nextLine();
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
        System.out.print("Введите имя XML файла: ");
        String filename = scanner.nextLine();
        boolean success = xmlWriteFile.writeDevicesToXML(filename, listStorage);
        if (success) {
            System.out.println("Устройства успешно записаны в XML файл!");
        } else {
            System.out.println("Ошибка записи устройств в XML файл!");
        }
    }

    private void readFromJSON() {
        System.out.print("Введите имя JSON файла: ");
        String filename = scanner.nextLine();
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
        System.out.print("Введите имя JSON файла: ");
        String filename = scanner.nextLine();
        boolean success = jsonWriteFile.writeDevicesToJSON(filename, listStorage);
        if (success) {
            System.out.println("Устройства успешно записаны в JSON файл!");
        } else {
            System.out.println("Ошибка записи устройств в JSON файл!");
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
        System.out.println("1. Компьютер");
        System.out.println("2. Планшет");
        System.out.println("3. Ноутбук");
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
                System.out.println("Устройство успешно добавлено!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод!");
        } catch (Exception e) {
            System.out.println("Ошибка создания устройства: " + e.getMessage());
        }
    }

    private Factory createComputer() throws ParseException {
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
        System.out.print("Форм-фактор корпуса: ");
        String formFactor = scanner.nextLine();
        System.out.print("Модуль Wi-Fi (да/нет): ");
        boolean wifi = parseBoolean(scanner.nextLine());

        return new Computers(model, modelNumber, serialNumber, cost, releaseDate, formFactor, wifi);
    }

    private Factory createTablet() throws ParseException {
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
        System.out.print("Операционная система: ");
        String os = scanner.nextLine();
        System.out.print("NFC чип (да/нет): ");
        boolean nfc = parseBoolean(scanner.nextLine());

        return new Tablets(model, modelNumber, serialNumber, cost, releaseDate, os, nfc);
    }

    private Factory createLaptop() throws ParseException {
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
        System.out.print("Цифровая клавиатура (да/нет): ");
        boolean numpad = parseBoolean(scanner.nextLine());
        System.out.print("Сенсорный экран (да/нет): ");
        boolean touchScreen = parseBoolean(scanner.nextLine());

        return new Laptops(model, modelNumber, serialNumber, cost, releaseDate, numpad, touchScreen);
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
        boolean success = writeFile.writeDevicesToFile(filename, listStorage);
        if (success) {
            System.out.println("Устройства успешно записаны в файл!");
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

    private boolean parseBoolean(String value) {
        if (value == null) return false;
        String lower = value.toLowerCase();
        return lower.equals("yes") || lower.equals("true") || lower.equals("1") || lower.equals("да");
    }
}
