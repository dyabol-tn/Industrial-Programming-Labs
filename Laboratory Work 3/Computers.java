import java.util.Date;

class Computers extends Factory {
    private String bodyFormFactor;
    private boolean wifiModule;

    public Computers() {
    }

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
