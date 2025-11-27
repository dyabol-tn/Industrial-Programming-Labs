import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

class WriteFile {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public WriteFile() {
    }

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
