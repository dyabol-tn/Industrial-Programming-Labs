import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XMLReadFile {
    public XMLReadFile() {}

    public Storage<Factory> readDevicesFromXML(String filename) {
        ListObjects<Factory> storage = new ListObjects<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            document.getDocumentElement().normalize();

            NodeList deviceList = document.getElementsByTagName("device");

            for (int i = 0; i < deviceList.getLength(); i++) {
                Node deviceNode = deviceList.item(i);
                if (deviceNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element deviceElement = (Element) deviceNode;
                    Factory device = createDeviceFromXML(deviceElement);
                    if (device != null) {
                        storage.addElement(device);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Ошибка чтения XML файла: " + e.getMessage());
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
                return null;
        }
    }

    private String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}