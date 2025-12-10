import org.json.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JSONReadFile {

    public JSONReadFile() {}

    public Storage<Factory> readDevicesFromJSON(String filename) {
        ListObjects<Factory> storage = new ListObjects<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONObject jsonObject = new JSONObject(jsonString.toString());
            JSONArray devicesArray = jsonObject.getJSONArray("devices");

            for (int i = 0; i < devicesArray.length(); i++) {
                JSONObject deviceJson = devicesArray.getJSONObject(i);
                Factory device = createDeviceFromJSON(deviceJson);
                if (device != null) {
                    storage.addElement(device);
                }
            }

        } catch (Exception e) {
            System.err.println("Ошибка чтения JSON файла: " + e.getMessage());
        }

        return storage;
    }

    private Factory createDeviceFromJSON(JSONObject deviceJson) throws ParseException, JSONException {
        String type = deviceJson.getString("type");
        String model = deviceJson.getString("model");
        String modelNumber = deviceJson.getString("modelNumber");
        String serialNumber = deviceJson.getString("serialNumber");
        int cost = deviceJson.getInt("cost");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date releaseDate = dateFormat.parse(deviceJson.getString("releaseDate"));

        switch (type.toUpperCase()) {
            case "COMPUTER":
                String formFactor = deviceJson.getString("bodyFormFactor");
                boolean wifi = deviceJson.getBoolean("wifiModule");
                return new Computers(model, modelNumber, serialNumber, cost, releaseDate, formFactor, wifi);
            case "TABLET":
                String os = deviceJson.getString("operatingSystem");
                boolean nfc = deviceJson.getBoolean("chipNFC");
                return new Tablets(model, modelNumber, serialNumber, cost, releaseDate, os, nfc);
            case "LAPTOP":
                boolean numPad = deviceJson.getBoolean("numPad");
                boolean touchScreen = deviceJson.getBoolean("touchScreen");
                return new Laptops(model, modelNumber, serialNumber, cost, releaseDate, numPad, touchScreen);
            default:
                return null;
        }
    }
}