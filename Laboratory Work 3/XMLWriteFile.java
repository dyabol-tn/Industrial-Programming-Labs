import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

class XMLWriteFile {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public XMLWriteFile() {}

    public boolean writeDevicesToXML(String filename, Storage<Factory> storage) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("devices");
            doc.appendChild(rootElement);

            List<Factory> devices = storage.getAllElements();
            for (Factory device : devices) {
                Element deviceElement = doc.createElement("device");
                rootElement.appendChild(deviceElement);
                addElement(doc, deviceElement, "type", device.getDeviceType());
                addElement(doc, deviceElement, "model", device.getModel());
                addElement(doc, deviceElement, "modelNumber", device.getModelNumber());
                addElement(doc, deviceElement, "serialNumber", device.getSerialNumber());
                addElement(doc, deviceElement, "cost", String.valueOf(device.getCost()));
                addElement(doc, deviceElement, "releaseDate", dateFormat.format(device.getReleaseDate()));

                if (device instanceof Computers) {
                    Computers computer = (Computers) device;
                    addElement(doc, deviceElement, "bodyFormFactor", computer.getBodyFormFactor());
                    addElement(doc, deviceElement, "wifiModule", String.valueOf(computer.isWifiModule()));
                } else if (device instanceof Tablets) {
                    Tablets tablet = (Tablets) device;
                    addElement(doc, deviceElement, "operatingSystem", tablet.getOperatingSystem());
                    addElement(doc, deviceElement, "chipNFC", String.valueOf(tablet.isChipNFC()));
                } else if (device instanceof Laptops) {
                    Laptops laptop = (Laptops) device;
                    addElement(doc, deviceElement, "numPad", String.valueOf(laptop.isNumPad()));
                    addElement(doc, deviceElement, "touchScreen", String.valueOf(laptop.isTouchScreen()));
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);

            System.out.println("Успешно записано " + devices.size() + " устройств в XML файл " + filename);
            return true;

        } catch (Exception e) {
            System.err.println("Ошибка записи в XML файл: " + e.getMessage());
            return false;
        }
    }

    private void addElement(Document doc, Element parent, String tagName, String value) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(value));
        parent.appendChild(element);
    }
}