import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class WriteFile {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public WriteFile() {}

    public boolean writeDevicesToFile(String filename, Storage<Factory> storage) {
        return writeDevicesToFile(filename, storage, false);
    }

    public boolean writeDevicesToFile(String filename, Storage<Factory> storage, boolean encrypt) {
        try {
            StringBuilder content = new StringBuilder();
            List<Factory> devices = storage.getAllElements();

            for (int i = 0; i < devices.size(); i++) {
                Factory device = devices.get(i);

                content.append("Type: ").append(device.getDeviceType()).append("\n");
                content.append("Model: ").append(device.getModel()).append("\n");
                content.append("Model Number: ").append(device.getModelNumber()).append("\n");
                content.append("Serial Number: ").append(device.getSerialNumber()).append("\n");
                content.append("Cost: ").append(device.getCost()).append("\n");
                content.append("Release date: ").append(dateFormat.format(device.getReleaseDate())).append("\n");

                if (device instanceof Computers) {
                    Computers computer = (Computers) device;
                    content.append("Body Form Factor: ").append(computer.getBodyFormFactor()).append("\n");
                    content.append("Wifi Module: ").append(computer.isWifiModule() ? "yes" : "no").append("\n");
                } else if (device instanceof Tablets) {
                    Tablets tablet = (Tablets) device;
                    content.append("Operating system: ").append(tablet.getOperatingSystem()).append("\n");
                    content.append("Chip NFC: ").append(tablet.isChipNFC() ? "yes" : "no").append("\n");
                } else if (device instanceof Laptops) {
                    Laptops laptop = (Laptops) device;
                    content.append("Num Pad: ").append(laptop.isNumPad() ? "yes" : "no").append("\n");
                    content.append("Touch Screen: ").append(laptop.isTouchScreen() ? "yes" : "no").append("\n");
                }

                if (i < devices.size() - 1) {
                    content.append("\n");
                }
            }

            String finalContent = content.toString();
            if (encrypt) {
                finalContent = Encryption.encrypt(finalContent);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                writer.write(finalContent);
                writer.flush();
            }

            System.out.println("Успешно записано " + devices.size() + " устройств в файл " + filename +
                    (encrypt ? " (зашифровано)" : ""));
            return true;

        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
            return false;
        }
    }
}