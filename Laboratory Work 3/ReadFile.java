import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class ReadFile {
    public ReadFile() {
    }

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
