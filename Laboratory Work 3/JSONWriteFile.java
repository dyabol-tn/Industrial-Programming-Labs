import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.List;

class JSONWriteFile {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public JSONWriteFile() {}

    public boolean writeDevicesToJSON(String filename, Storage<Factory> storage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            JSONObject rootObject = new JSONObject();
            JSONArray devicesArray = new JSONArray();

            List<Factory> devices = storage.getAllElements();
            for (Factory device : devices) {
                JSONObject deviceObject = new JSONObject();

                // Common fields
                deviceObject.put("type", device.getDeviceType());
                deviceObject.put("model", device.getModel());
                deviceObject.put("modelNumber", device.getModelNumber());
                deviceObject.put("serialNumber", device.getSerialNumber());
                deviceObject.put("cost", device.getCost());
                deviceObject.put("releaseDate", dateFormat.format(device.getReleaseDate()));

                // Specific fields
                if (device instanceof Computers) {
                    Computers computer = (Computers) device;
                    deviceObject.put("bodyFormFactor", computer.getBodyFormFactor());
                    deviceObject.put("wifiModule", computer.isWifiModule());
                } else if (device instanceof Tablets) {
                    Tablets tablet = (Tablets) device;
                    deviceObject.put("operatingSystem", tablet.getOperatingSystem());
                    deviceObject.put("chipNFC", tablet.isChipNFC());
                } else if (device instanceof Laptops) {
                    Laptops laptop = (Laptops) device;
                    deviceObject.put("numPad", laptop.isNumPad());
                    deviceObject.put("touchScreen", laptop.isTouchScreen());
                }

                devicesArray.put(deviceObject);
            }

            rootObject.put("devices", devicesArray);
            writer.write(rootObject.toString(4)); // 4 spaces for indentation
            writer.flush();

            System.out.println("Успешно записано " + devices.size() + " устройств в JSON файл " + filename);
            return true;

        } catch (Exception e) {
            System.err.println("Ошибка записи в JSON файл: " + e.getMessage());
            return false;
        }
    }
}