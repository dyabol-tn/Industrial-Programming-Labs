import org.junit.jupiter.api.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeviceManagementTest {

    private ListObjects<Factory> listStorage;
    private MapObjects<Factory> mapStorage;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() throws Exception {
        listStorage = new ListObjects<>();
        mapStorage = new MapObjects<>();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Создаем тестовые устройства
        Computers computer = new Computers(
                "Dell XPS",
                "XPS8930",
                "SN001",
                1200,
                dateFormat.parse("15/05/2023"),
                "Tower",
                true
        );

        Tablets tablet = new Tablets(
                "iPad Pro",
                "A2377",
                "SN002",
                999,
                dateFormat.parse("20/10/2023"),
                "iPadOS",
                true
        );

        Laptops laptop = new Laptops(
                "MacBook Pro",
                "M2",
                "SN003",
                1999,
                dateFormat.parse("01/07/2023"),
                true,
                true
        );

        listStorage.addElement(computer);
        listStorage.addElement(tablet);
        listStorage.addElement(laptop);

        mapStorage.addElement(computer);
        mapStorage.addElement(tablet);
        mapStorage.addElement(laptop);
    }

    // ========== Тесты для классов устройств ==========

    @Test
    void testComputerCreation() throws Exception {
        Date expectedDate = dateFormat.parse("01/01/2023");
        Computers computer = new Computers(
                "Test PC",
                "TPC001",
                "SN100",
                500,
                expectedDate,
                "Mini Tower",
                false
        );

        assertEquals("Test PC", computer.getModel());
        assertEquals("TPC001", computer.getModelNumber());
        assertEquals("SN100", computer.getSerialNumber());
        assertEquals(500, computer.getCost());
        assertEquals(expectedDate, computer.getReleaseDate()); // Исправлено: сравниваем Date, а не String
        assertEquals("Mini Tower", computer.getBodyFormFactor());
        assertFalse(computer.isWifiModule());
        assertEquals("Компьютер", computer.getDeviceType());
    }

    @Test
    void testTabletCreation() throws Exception {
        Date expectedDate = dateFormat.parse("01/02/2023");
        Tablets tablet = new Tablets(
                "Test Tablet",
                "TT001",
                "SN200",
                300,
                expectedDate,
                "Android",
                true
        );

        assertEquals("Test Tablet", tablet.getModel());
        assertEquals("Android", tablet.getOperatingSystem());
        assertTrue(tablet.isChipNFC());
        assertEquals(expectedDate, tablet.getReleaseDate()); // Исправлено: сравниваем Date
        assertEquals("Планшет", tablet.getDeviceType());
    }

    @Test
    void testLaptopCreation() throws Exception {
        Date expectedDate = dateFormat.parse("01/03/2023");
        Laptops laptop = new Laptops(
                "Test Laptop",
                "TL001",
                "SN300",
                800,
                expectedDate,
                true,
                false
        );

        assertEquals("Test Laptop", laptop.getModel());
        assertTrue(laptop.isNumPad());
        assertFalse(laptop.isTouchScreen());
        assertEquals(expectedDate, laptop.getReleaseDate()); // Исправлено: сравниваем Date
        assertEquals("Ноутбук", laptop.getDeviceType());
    }

    // ========== Тесты для ListObjects ==========

    @Test
    void testListObjectsAddElement() throws Exception {
        assertEquals(3, listStorage.size());

        Computers newComputer = new Computers();
        newComputer.setModel("New PC");
        newComputer.setSerialNumber("SN999");
        newComputer.setModelNumber("NEW001");
        newComputer.setCost(500);
        newComputer.setReleaseDate(dateFormat.parse("01/01/2024"));
        newComputer.setBodyFormFactor("Tower");
        newComputer.setWifiModule(true);

        listStorage.addElement(newComputer);
        assertEquals(4, listStorage.size());
    }

    @Test
    void testListObjectsRemoveElement() {
        assertTrue(listStorage.removeElement("SN001"));
        assertEquals(2, listStorage.size());
        assertFalse(listStorage.removeElement("NONEXISTENT"));
    }

    @Test
    void testListObjectsGetElement() {
        Factory device = listStorage.getElement("SN002");
        assertNotNull(device);
        assertEquals("iPad Pro", device.getModel());

        assertNull(listStorage.getElement("NONEXISTENT"));
    }

    @Test
    void testListObjectsContains() {
        assertTrue(listStorage.contains("SN001"));
        assertFalse(listStorage.contains("NONEXISTENT"));
    }

    @Test
    void testListObjectsFindElements() {
        // Поиск по типу
        List<Factory> computers = listStorage.findElements(
                d -> d.getDeviceType().equals("Компьютер")
        );
        assertEquals(1, computers.size());

        // Поиск по диапазону цены
        List<Factory> expensiveDevices = listStorage.findElements(
                d -> d.getCost() > 1500
        );
        assertEquals(1, expensiveDevices.size());
    }

    @Test
    void testListObjectsSort() {
        listStorage.sort((d1, d2) -> Integer.compare(d1.getCost(), d2.getCost()));
        List<Factory> devices = listStorage.getAllElements();

        for (int i = 0; i < devices.size() - 1; i++) {
            assertTrue(devices.get(i).getCost() <= devices.get(i + 1).getCost());
        }
    }

    // ========== Тесты для MapObjects ==========

    @Test
    void testMapObjectsAddElement() throws Exception {
        assertEquals(3, mapStorage.size());

        Computers newComputer = new Computers();
        newComputer.setModel("New PC");
        newComputer.setSerialNumber("SN999");
        newComputer.setModelNumber("NEW001");
        newComputer.setCost(500);
        newComputer.setReleaseDate(dateFormat.parse("01/01/2024"));
        newComputer.setBodyFormFactor("Tower");
        newComputer.setWifiModule(true);

        mapStorage.addElement(newComputer);
        assertEquals(4, mapStorage.size());
    }

    @Test
    void testMapObjectsRemoveElement() {
        assertTrue(mapStorage.removeElement("SN001"));
        assertEquals(2, mapStorage.size());
        assertFalse(mapStorage.removeElement("NONEXISTENT"));
    }

    @Test
    void testMapObjectsGetElement() {
        Factory device = mapStorage.getElement("SN002");
        assertNotNull(device);
        assertEquals("iPad Pro", device.getModel());

        assertNull(mapStorage.getElement("NONEXISTENT"));
    }

    @Test
    void testMapObjectsUpdateElement() throws Exception {
        Computers updatedComputer = new Computers(
                "Updated Dell",
                "XPS8930",
                "SN001",  // Тот же серийный номер
                1500,
                dateFormat.parse("15/05/2023"),
                "Tower",
                true
        );

        assertTrue(mapStorage.updateElement("SN001", updatedComputer));
        Factory device = mapStorage.getElement("SN001");
        assertEquals("Updated Dell", device.getModel());
        assertEquals(1500, device.getCost());
    }

    // ========== Тесты для чтения/записи файлов ==========

    @Test
    void testWriteFileCreation() throws IOException {
        WriteFile writeFile = new WriteFile();
        String testFile = "test_output.txt";

        boolean success = writeFile.writeDevicesToFile(testFile, listStorage, false);
        assertTrue(success);

        File file = new File(testFile);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        file.delete();
    }

    @Test
    void testReadFile() throws Exception {
        // Сначала создаем тестовый файл
        String testContent = "Type: Компьютер\nModel: Test PC\nModel Number: T001\nSerial Number: SN999\nCost: 1000\nRelease date: 01/01/2023\nBody Form Factor: Tower\nWifi Module: yes\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test_input.txt"))) {
            writer.write(testContent);
        }

        ReadFile readFile = new ReadFile();
        Storage<Factory> storage = readFile.readDevicesFromFile("test_input.txt");

        assertTrue(storage.size() > 0);

        new File("test_input.txt").delete();
    }

    // ========== Тесты для XML ==========

    @Test
    void testXMLWriteFile() throws Exception {
        XMLWriteFile xmlWriteFile = new XMLWriteFile();
        String testFile = "test_output.xml";

        boolean success = xmlWriteFile.writeDevicesToXML(testFile, listStorage);
        assertTrue(success);

        File file = new File(testFile);
        assertTrue(file.exists());

        // Проверяем, что файл валидный XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        assertEquals("devices", document.getDocumentElement().getNodeName());

        file.delete();
    }

    // ========== Тесты для JSON ==========

    @Test
    void testJSONWriteFile() throws Exception {
        JSONWriteFile jsonWriteFile = new JSONWriteFile();
        String testFile = "test_output.json";

        boolean success = jsonWriteFile.writeDevicesToJSON(testFile, listStorage);
        assertTrue(success);

        File file = new File(testFile);
        assertTrue(file.exists());

        // Проверяем, что файл содержит JSON
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String content = reader.readLine();
            assertNotNull(content);
            assertTrue(content.contains("devices"));
        }

        file.delete();
    }

    // ========== Тесты для шифрования ==========

    @Test
    void testEncryptionDecryption() {
        String originalText = "Hello, World! This is a test message for encryption.";

        String encrypted = Encryption.encrypt(originalText);
        assertNotNull(encrypted);
        assertNotEquals(originalText, encrypted);

        String decrypted = Encryption.decrypt(encrypted);
        assertEquals(originalText, decrypted);
    }

    @Test
    void testEncryptionEmptyString() {
        String encrypted = Encryption.encrypt("");
        String decrypted = Encryption.decrypt(encrypted);
        assertEquals("", decrypted);
    }

    @Test
    void testEncryptionSpecialCharacters() {
        String text = "Test with special chars: !@#$%^&*()_+{}|:\"<>?~`";
        String encrypted = Encryption.encrypt(text);
        String decrypted = Encryption.decrypt(encrypted);
        assertEquals(text, decrypted);
    }

    // ========== Тесты для архивации ==========

    @Test
    void testCreateZipArchive() throws IOException {
        // Создаем тестовый файл для архивации
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test_for_zip.txt"))) {
            writer.write("Test content for zip archive");
        }

        boolean success = Archive.createZipArchive("test_for_zip.txt", "test_archive.zip");
        assertTrue(success);

        File zipFile = new File("test_archive.zip");
        assertTrue(zipFile.exists());

        // Очистка
        new File("test_for_zip.txt").delete();
        zipFile.delete();
    }

    @Test
    void testExtractZipArchive() throws IOException {
        // Сначала создаем архив
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("extract_test.txt"))) {
            writer.write("Content to extract");
        }

        Archive.createZipArchive("extract_test.txt", "extract_test.zip");

        // Извлекаем архив
        boolean success = Archive.extractZipArchive("extract_test.zip", "extracted");
        assertTrue(success);

        File extractedFile = new File("extracted/extract_test.txt");
        assertTrue(extractedFile.exists());

        // Очистка
        new File("extract_test.txt").delete();
        new File("extract_test.zip").delete();
        deleteDirectory(new File("extracted"));
    }

    private void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteDirectory(child);
                }
            }
        }
        dir.delete();
    }

    // ========== Тесты для меню и вспомогательных методов ==========

    @Test
    void testParseBooleanHelper() {
        // Метод parseBoolean из класса Menu
        assertTrue(parseBoolean("yes"));
        assertTrue(parseBoolean("YES"));
        assertTrue(parseBoolean("true"));
        assertTrue(parseBoolean("TRUE"));
        assertTrue(parseBoolean("1"));
        assertTrue(parseBoolean("да"));
        assertTrue(parseBoolean("ДА"));

        assertFalse(parseBoolean("no"));
        assertFalse(parseBoolean("false"));
        assertFalse(parseBoolean("0"));
        assertFalse(parseBoolean("нет"));
        assertFalse(parseBoolean("random"));
        assertFalse(parseBoolean(""));
        assertFalse(parseBoolean(null));
    }

    // Вспомогательный метод для тестирования (аналогичный методу в Menu)
    private boolean parseBoolean(String value) {
        if (value == null) return false;
        String lower = value.toLowerCase();
        return lower.equals("yes") || lower.equals("true") || lower.equals("1") || lower.equals("да");
    }

    // ========== Тесты для фабрики устройств ==========

    @Test
    void testFactoryToString() throws Exception {
        Computers computer = new Computers(
                "Test",
                "T001",
                "SN999",
                1000,
                dateFormat.parse("01/01/2023"),
                "Tower",
                true
        );

        String str = computer.toString();
        assertTrue(str.contains("Test"));
        assertTrue(str.contains("T001"));
        assertTrue(str.contains("SN999"));
        assertTrue(str.contains("1000"));
        assertTrue(str.contains("01/01/2023"));
    }

    @Test
    void testStorageInterfaceMethods() {
        // Тестируем методы интерфейса Storage через ListObjects
        assertEquals(3, listStorage.size());

        List<Factory> allDevices = listStorage.getAllElements();
        assertEquals(3, allDevices.size());

        listStorage.clear();
        assertEquals(0, listStorage.size());
        assertTrue(listStorage.getAllElements().isEmpty());
    }

    // ========== Тесты для статистики ==========

    @Test
    void testDeviceStatistics() {
        List<Factory> devices = listStorage.getAllElements();

        long computers = devices.stream().filter(d -> d instanceof Computers).count();
        long tablets = devices.stream().filter(d -> d instanceof Tablets).count();
        long laptops = devices.stream().filter(d -> d instanceof Laptops).count();

        assertEquals(1, computers);
        assertEquals(1, tablets);
        assertEquals(1, laptops);

        if (!devices.isEmpty()) {
            double avgCost = devices.stream().mapToInt(Factory::getCost).average().orElse(0);
            assertTrue(avgCost > 0);

            int maxCost = devices.stream().mapToInt(Factory::getCost).max().orElse(0);
            int minCost = devices.stream().mapToInt(Factory::getCost).min().orElse(0);

            assertTrue(maxCost >= minCost);
        }
    }

    // ========== Тесты для обновления устройства ==========

    @Test
    void testUpdateDevice() throws Exception {
        Computers updated = new Computers(
                "Updated Model",
                "UM001",
                "SN001",  // Тот же серийный номер
                1500,
                dateFormat.parse("20/12/2023"),
                "Mini Tower",
                false
        );

        assertTrue(listStorage.updateElement("SN001", updated));

        Factory device = listStorage.getElement("SN001");
        assertEquals("Updated Model", device.getModel());
        assertEquals(1500, device.getCost());
    }

    // ========== Тесты для поиска по разным критериям ==========

    @Test
    void testSearchByModel() {
        List<Factory> results = listStorage.findElements(
                d -> d.getModel().toLowerCase().contains("pro")
        );

        // Должны найти iPad Pro и MacBook Pro
        assertEquals(2, results.size());
    }

    @Test
    void testSearchByCostRange() {
        List<Factory> results = listStorage.findElements(
                d -> d.getCost() >= 1000 && d.getCost() <= 1500
        );

        // Должны найти Dell XPS за 1200
        assertEquals(1, results.size());
        assertEquals("Dell XPS", results.get(0).getModel());
    }

    // ========== Тесты для граничных случаев ==========

    @Test
    void testAddNullDevice() {
        int initialSize = listStorage.size();
        listStorage.addElement(null);
        assertEquals(initialSize, listStorage.size());
    }

    @Test
    void testAddDuplicateSerialNumber() throws Exception {
        Computers duplicate = new Computers(
                "Duplicate",
                "DUP001",
                "SN001",  // Уже существует
                500,
                dateFormat.parse("01/01/2023"),
                "Tower",
                true
        );

        int initialSize = listStorage.size();
        listStorage.addElement(duplicate);
        assertEquals(initialSize, listStorage.size());  // Не должно добавиться
    }

    @Test
    void testEmptyStorageOperations() {
        ListObjects<Factory> emptyStorage = new ListObjects<>();

        assertEquals(0, emptyStorage.size());
        assertTrue(emptyStorage.getAllElements().isEmpty());
        assertFalse(emptyStorage.contains("ANY"));
        assertNull(emptyStorage.getElement("ANY"));
        assertFalse(emptyStorage.removeElement("ANY"));

        // Поиск в пустом хранилище
        List<Factory> results = emptyStorage.findElements(d -> true);
        assertTrue(results.isEmpty());
    }

    // ========== Тесты для сериализации/десериализации ==========

    @Test
    void testDeviceSerialization() throws Exception {
        Computers computer = new Computers(
                "Serializable",
                "SER001",
                "SER999",
                777,
                dateFormat.parse("07/07/2023"),
                "Test Form",
                true
        );

        // Проверяем, что все методы getter работают
        assertNotNull(computer.getModel());
        assertNotNull(computer.getModelNumber());
        assertNotNull(computer.getSerialNumber());
        assertNotNull(computer.getReleaseDate()); // Возвращает Date
        assertNotNull(computer.getDeviceType());
        assertNotNull(computer.getBodyFormFactor());
    }

    @AfterEach
    void tearDown() {
        // Очищаем временные файлы
        deleteTestFiles();
    }

    private void deleteTestFiles() {
        String[] testFiles = {
                "test_output.txt",
                "test_input.txt",
                "test_output.xml",
                "test_input.xml",
                "test_output.json",
                "test_archive.zip",
                "test_for_zip.txt",
                "extract_test.txt",
                "extract_test.zip"
        };

        for (String filename : testFiles) {
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
        }

        deleteDirectory(new File("extracted"));
    }
}