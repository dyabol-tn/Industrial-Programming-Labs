import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.showMainMenu();
    }
}

abstract class Factory {
    protected String model;
    protected String modelNumber;
    protected String serialNumber;
    protected int cost;
    protected Date releaseDate;
    protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Factory() {}

    public Factory(String model, String modelNumber, String serialNumber, int cost, Date releaseDate) {
        this.model = model;
        this.modelNumber = modelNumber;
        this.serialNumber = serialNumber;
        this.cost = cost;
        this.releaseDate = releaseDate;
    }

    abstract void getInfo();
    abstract String getDeviceType();

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getModelNumber() {
        return modelNumber;
    }
    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return String.format("Модель: %s, Номер модели: %s, Серийный: %s, Цена: $%d, Дата выхода: %s",
                model, modelNumber, serialNumber, cost, dateFormat.format(releaseDate));
    }
}

class Computers extends Factory {
    private String bodyFormFactor;
    private boolean wifiModule;

    public Computers() {}

    public Computers(String model, String modelNumber, String serialNumber, int cost, Date releaseDate, String bodyFormFactor, boolean wifiModule) {
        super(model, modelNumber, serialNumber, cost, releaseDate);
        this.bodyFormFactor = bodyFormFactor;
        this.wifiModule = wifiModule;
    }

    @Override
    public String getDeviceType() {
        return "Компьютер";
    }

    public String getBodyFormFactor() {
        return bodyFormFactor;
    }
    public void setBodyFormFactor(String bodyFormFactor) {
        this.bodyFormFactor = bodyFormFactor;
    }

    public boolean isWifiModule() {
        return wifiModule;
    }
    public void setWifiModule(boolean wifiModule) {
        this.wifiModule = wifiModule;
    }

    public void getInfo() {
        System.out.println("Модель компьютера: " + model);
        System.out.println("Номер модели: " + modelNumber);
        System.out.println("Серийный номер: " + serialNumber);
        System.out.println("Цена: " + cost);
        System.out.println("Дата релиза: " + releaseDate);
        System.out.println("Форм-фактор корпуса: " + bodyFormFactor);
        System.out.println("Наличие модуля WI-FI: " + wifiModule);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Форм-фактор: %s, Wi-Fi: %s",
                bodyFormFactor, wifiModule ? "Да" : "Нет");
    }
}

class Tablets extends Factory {
    private String operatingSystem;
    private boolean chipNFC;

    public Tablets() {}

    public Tablets(String model, String modelNumber, String serialNumber, int cost, Date releaseDate, String operatingSystem, boolean chipNFC) {
        super(model, modelNumber, serialNumber, cost, releaseDate);
        this.operatingSystem = operatingSystem;
        this.chipNFC = chipNFC;
    }

    @Override
    public String getDeviceType() {
        return "Планшет";
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }
    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public boolean isChipNFC() {
        return chipNFC;
    }
    public void setChipNFC(boolean chipNFC) {
        this.chipNFC = chipNFC;
    }

    public void getInfo() {
        System.out.println("Модель планшета: " + model);
        System.out.println("Номер модели: " + modelNumber);
        System.out.println("Серийный номер: " + serialNumber);
        System.out.println("Цена: " + cost);
        System.out.println("Дата релиза: " + releaseDate);
        System.out.println("Операционная система: " + operatingSystem);
        System.out.println("Наличие NFC: " + chipNFC);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", ОС: %s, NFC: %s",
                operatingSystem, chipNFC ? "Да" : "Нет");
    }
}

class Laptops extends Factory {
    private boolean numPad;
    private boolean touchScreen;

    public Laptops() {}

    public Laptops(String model, String modelNumber, String serialNumber, int cost, Date releaseDate, boolean numPad, boolean touchScreen) {
        super(model, modelNumber, serialNumber, cost, releaseDate);
        this.numPad = numPad;
        this.touchScreen = touchScreen;
    }

    @Override
    public String getDeviceType() {
        return "Ноутбук";
    }

    public boolean isNumPad() {
        return numPad;
    }
    public void setNumPad(boolean numPad) {
        this.numPad = numPad;
    }

    public boolean isTouchScreen() {
        return touchScreen;
    }
    public void setTouchScreen(boolean touchScreen) {
        this.touchScreen = touchScreen;
    }

    public void getInfo() {
        System.out.println("Модель ноутбука: " + model);
        System.out.println("Номер модели: " + modelNumber);
        System.out.println("Серийный номер: " + serialNumber);
        System.out.println("Цена: " + cost);
        System.out.println("Дата релиза: " + releaseDate);
        System.out.println("Наличие цифрового блока: " + numPad);
        System.out.println("Наличие сенсорного экрана: " + touchScreen);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Цифровой блок: %s, Сенсорный экран: %s",
                numPad ? "Да" : "Нет", touchScreen ? "Да" : "Нет");
    }
}

abstract class Storage<T extends Factory> {
    public abstract void addElement(T element);
    public abstract boolean removeElement(String serialNumber);
    public abstract T getElement(String serialNumber);
    public abstract List<T> getAllElements();
    public abstract int size();
    public abstract void clear();
    public abstract boolean contains(String serialNumber);
    public abstract void displayAll();
    public abstract List<T> findElements(java.util.function.Predicate<T> condition);
    public abstract void sort(Comparator<T> comparator);
    public abstract boolean updateElement(String serialNumber, T newElement);
}

class ListObjects<T extends Factory> extends Storage<T> {
    private List<T> objects;

    public ListObjects() {
        this.objects = new ArrayList<>();
    }

    @Override
    public void addElement(T element) {
        if (element != null && !contains(element.getSerialNumber())) {
            objects.add(element);
        }
    }

    @Override
    public boolean removeElement(String serialNumber) {
        return objects.removeIf(obj -> obj.getSerialNumber().equals(serialNumber));
    }

    @Override
    public T getElement(String serialNumber) {
        return objects.stream()
                .filter(obj -> obj.getSerialNumber().equals(serialNumber))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<T> getAllElements() {
        return new ArrayList<>(objects);
    }

    @Override
    public int size() {
        return objects.size();
    }

    @Override
    public void clear() {
        objects.clear();
    }

    @Override
    public boolean contains(String serialNumber) {
        return objects.stream().anyMatch(obj -> obj.getSerialNumber().equals(serialNumber));
    }

    @Override
    public void displayAll() {
        if (objects.isEmpty()) {
            System.out.println("В хранилище нет устройств.");
            return;
        }
        System.out.println("=== Все устройства ===");
        for (int i = 0; i < objects.size(); i++) {
            System.out.println((i + 1) + ". " + objects.get(i));
        }
    }

    @Override
    public List<T> findElements(java.util.function.Predicate<T> condition) {
        List<T> result = new ArrayList<>();
        for (T obj : objects) {
            if (condition.test(obj)) {
                result.add(obj);
            }
        }
        return result;
    }

    @Override
    public void sort(Comparator<T> comparator) {
        objects.sort(comparator);
    }

    @Override
    public boolean updateElement(String serialNumber, T newElement) {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getSerialNumber().equals(serialNumber)) {
                objects.set(i, newElement);
                return true;
            }
        }
        return false;
    }
}

class MapObjects<T extends Factory> extends Storage<T> {
    private Map<String, T> objects;

    public MapObjects() {
        this.objects = new LinkedHashMap<>();
    }

    @Override
    public void addElement(T element) {
        if (element != null && !objects.containsKey(element.getSerialNumber())) {
            objects.put(element.getSerialNumber(), element);
        }
    }

    @Override
    public boolean removeElement(String serialNumber) {
        return objects.remove(serialNumber) != null;
    }

    @Override
    public T getElement(String serialNumber) {
        return objects.get(serialNumber);
    }

    @Override
    public List<T> getAllElements() {
        return new ArrayList<>(objects.values());
    }

    @Override
    public int size() {
        return objects.size();
    }

    @Override
    public void clear() {
        objects.clear();
    }

    @Override
    public boolean contains(String serialNumber) {
        return objects.containsKey(serialNumber);
    }

    @Override
    public void displayAll() {
        if (objects.isEmpty()) {
            System.out.println("В хранилище нет устройств.");
            return;
        }
        System.out.println("=== Все устройства ===");
        int index = 1;
        for (T obj : objects.values()) {
            System.out.println(index + ". " + obj);
            index++;
        }
    }

    @Override
    public List<T> findElements(java.util.function.Predicate<T> condition) {
        List<T> result = new ArrayList<>();
        for (T obj : objects.values()) {
            if (condition.test(obj)) {
                result.add(obj);
            }
        }
        return result;
    }

    @Override
    public void sort(Comparator<T> comparator) {
        List<T> sortedList = new ArrayList<>(objects.values());
        sortedList.sort(comparator);

        objects.clear();
        for (T obj : sortedList) {
            objects.put(obj.getSerialNumber(), obj);
        }
    }

    @Override
    public boolean updateElement(String serialNumber, T newElement) {
        if (objects.containsKey(serialNumber)) {
            objects.put(serialNumber, newElement);
            return true;
        }
        return false;
    }
}

class ReadFile {
    public ReadFile() {}

    public Storage<Factory> readDevicesFromFile(String filename) {
        ListObjects<Factory> storage = new ListObjects<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            Map<String, String> deviceData = new HashMap<>();
            String currentDeviceType = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    processDevice(deviceData, currentDeviceType, storage);
                    deviceData.clear();
                    currentDeviceType = null;
                    continue;
                }

                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim().toLowerCase();
                    String value = parts[1].trim();

                    if (key.equals("type")) {
                        currentDeviceType = value.toUpperCase();
                    } else {
                        deviceData.put(key, value);
                    }
                }
            }

            processDevice(deviceData, currentDeviceType, storage);

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }

        return storage;
    }

    private void processDevice(Map<String, String> data, String deviceType, Storage<Factory> storage) {
        if (data.isEmpty() || deviceType == null) return;

        try {
            Factory device = createDevice(deviceType, data);
            if (device != null) {
                storage.addElement(device);
            }
        } catch (Exception e) {
            System.err.println("Ошибка обработки устройства: " + e.getMessage() + ". Данные: " + data);
        }
    }

    private Factory createDevice(String deviceType, Map<String, String> data) throws ParseException {
        String model = data.getOrDefault("model", "Неизвестно");
        String modelNumber = data.getOrDefault("model number", "Неизвестно");
        String serialNumber = data.getOrDefault("serial number", "Неизвестно");
        int cost = parseInt(data.getOrDefault("cost", "0"));
        Date releaseDate = parseDate(data.getOrDefault("release date", "01/01/2000"));

        switch (deviceType) {
            case "COMPUTER":
                return new Computers(model, modelNumber, serialNumber, cost, releaseDate,
                        data.getOrDefault("body form factor", "Неизвестно"),
                        parseBoolean(data.getOrDefault("wifi module", "false")));
            case "TABLET":
                return new Tablets(model, modelNumber, serialNumber, cost, releaseDate,
                        data.getOrDefault("operating system", "Неизвестно"),
                        parseBoolean(data.getOrDefault("chip nfc", "false")));
            case "LAPTOP":
                return new Laptops(model, modelNumber, serialNumber, cost, releaseDate,
                        parseBoolean(data.getOrDefault("num pad", "false")),
                        parseBoolean(data.getOrDefault("touch screen", "false")));
            default:
                throw new IllegalArgumentException("Неизвестный тип устройства: " + deviceType);
        }
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(dateStr);
    }

    private boolean parseBoolean(String value) {
        if (value == null) return false;
        String lower = value.toLowerCase();
        return lower.equals("yes") || lower.equals("true") || lower.equals("1") || lower.equals("да");
    }
}

class WriteFile {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public WriteFile() {}

    public boolean writeDevicesToFile(String filename, Storage<Factory> storage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            List<Factory> devices = storage.getAllElements();

            for (int i = 0; i < devices.size(); i++) {
                Factory device = devices.get(i);

                writer.write("Type: " + device.getDeviceType());
                writer.newLine();
                writer.write("Model: " + device.getModel());
                writer.newLine();
                writer.write("Model Number: " + device.getModelNumber());
                writer.newLine();
                writer.write("Serial Number: " + device.getSerialNumber());
                writer.newLine();
                writer.write("Cost: " + device.getCost());
                writer.newLine();
                writer.write("Release date: " + dateFormat.format(device.getReleaseDate()));
                writer.newLine();

                if (device instanceof Computers) {
                    Computers computer = (Computers) device;
                    writer.write("Body Form Factor: " + computer.getBodyFormFactor());
                    writer.newLine();
                    writer.write("Wifi Module: " + (computer.isWifiModule() ? "yes" : "no"));
                    writer.newLine();
                } else if (device instanceof Tablets) {
                    Tablets tablet = (Tablets) device;
                    writer.write("Operating system: " + tablet.getOperatingSystem());
                    writer.newLine();
                    writer.write("Chip NFC: " + (tablet.isChipNFC() ? "yes" : "no"));
                    writer.newLine();
                } else if (device instanceof Laptops) {
                    Laptops laptop = (Laptops) device;
                    writer.write("Num Pad: " + (laptop.isNumPad() ? "yes" : "no"));
                    writer.newLine();
                    writer.write("Touch Screen: " + (laptop.isTouchScreen() ? "yes" : "no"));
                    writer.newLine();
                }

                if (i < devices.size() - 1) {
                    writer.newLine();
                }
            }

            writer.flush();
            System.out.println("Успешно записано " + devices.size() + " устройств в файл " + filename);
            return true;

        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
            return false;
        }
    }
}

class Menu {
    private Scanner scanner;
    private Storage<Factory> listStorage;
    private Storage<Factory> mapStorage;
    private ReadFile readFile;
    private WriteFile writeFile;
    private SimpleDateFormat dateFormat;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.listStorage = new ListObjects<>();
        this.mapStorage = new MapObjects<>();
        this.readFile = new ReadFile();
        this.writeFile = new WriteFile();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("\n=== Система управления устройствами ===");
            System.out.println("1. Чтение из файла");
            System.out.println("2. Показать все устройства");
            System.out.println("3. Добавить новое устройство");
            System.out.println("4. Удалить устройство");
            System.out.println("5. Обновить устройство");
            System.out.println("6. Поиск устройств");
            System.out.println("7. Сортировка устройств");
            System.out.println("8. Запись в файл");
            System.out.println("9. Статистика устройств");
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