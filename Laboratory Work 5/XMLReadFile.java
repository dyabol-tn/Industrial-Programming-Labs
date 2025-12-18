import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XMLReadFile {

    public XMLReadFile() {}

    public Storage<Factory> readDevicesFromXML(String filename) {
        ListObjects<Factory> storage = new ListObjects<>();

        try {
            System.out.println("Чтение XML файла: " + filename);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            document.getDocumentElement().normalize();

            System.out.println("Корневой элемент: " + document.getDocumentElement().getNodeName());

            NodeList deviceList = document.getElementsByTagName("device");
            System.out.println("Найдено элементов <device>: " + deviceList.getLength());

            for (int i = 0; i < deviceList.getLength(); i++) {
                Node deviceNode = deviceList.item(i);
                // ИСПРАВЛЕНО: используем константу Node.ELEMENT_NODE вместо магического числа 1
                if (deviceNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element deviceElement = (Element) deviceNode;
                    Factory device = createDeviceFromXML(deviceElement);
                    if (device != null) {
                        storage.addElement(device);
                        System.out.println("Успешно создано устройство #" + (i+1) + ": " + device.getModel());
                    }
                }
            }

            System.out.println("Всего загружено устройств: " + storage.size());

        } catch (Exception e) {
            System.err.println("Ошибка чтения XML файла: " + e.getMessage());
            e.printStackTrace();
        }

        return storage;
    }

    private Factory createDeviceFromXML(Element deviceElement) throws ParseException {
        String type = getElementValue(deviceElement, "type");
        String model = getElementValue(deviceElement, "model");
        String modelNumber = getElementValue(deviceElement, "modelNumber");
        String serialNumber = getElementValue(deviceElement, "serialNumber");
        int cost = Integer.parseInt(getElementValue(deviceElement, "cost"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date releaseDate = dateFormat.parse(getElementValue(deviceElement, "releaseDate"));

        System.out.println("Создание устройства типа: " + type + ", модель: " + model);

        switch (type.toUpperCase()) {
            case "COMPUTER":
                String formFactor = getElementValue(deviceElement, "bodyFormFactor");
                boolean wifi = Boolean.parseBoolean(getElementValue(deviceElement, "wifiModule"));
                return new Computers(model, modelNumber, serialNumber, cost, releaseDate, formFactor, wifi);
            case "TABLET":
                String os = getElementValue(deviceElement, "operatingSystem");
                boolean nfc = Boolean.parseBoolean(getElementValue(deviceElement, "chipNFC"));
                return new Tablets(model, modelNumber, serialNumber, cost, releaseDate, os, nfc);
            case "LAPTOP":
                boolean numPad = Boolean.parseBoolean(getElementValue(deviceElement, "numPad"));
                boolean touchScreen = Boolean.parseBoolean(getElementValue(deviceElement, "touchScreen"));
                return new Laptops(model, modelNumber, serialNumber, cost, releaseDate, numPad, touchScreen);
            default:
                System.err.println("Неизвестный тип устройства: " + type);
                return null;
        }
    }

    private String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            System.err.println("Элемент <" + tagName + "> не найден!");
            return "";
        }
    }
}