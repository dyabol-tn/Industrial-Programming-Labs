import java.util.Date;

class Tablets extends Factory {
    private String caseType;
    private String processor;
    private boolean wifiModule;
    private String screen;
    private String operatingSystem;
    private boolean chipNFC;

    public Tablets() {
    }

    //bd
    public Tablets(String model, String modelNumber, String serialNumber, int cost,
                   Date releaseDate, String caseType, String processor,
                   boolean wifiModule, String screen) {
        super(model, modelNumber, serialNumber, cost, releaseDate);
        this.caseType = caseType;
        this.processor = processor;
        this.wifiModule = wifiModule;
        this.screen = screen;
        this.operatingSystem = "Unknown";
        this.chipNFC = false;
    }

    public Tablets(String model, String modelNumber, String serialNumber, int cost,
                   Date releaseDate, String operatingSystem, boolean chipNFC) {
        super(model, modelNumber, serialNumber, cost, releaseDate);
        this.operatingSystem = operatingSystem;
        this.chipNFC = chipNFC;
        this.caseType = "Unknown";
        this.processor = "Unknown";
        this.wifiModule = false;
        this.screen = "Unknown";
    }

    @Override
    public String getDeviceType() {
        return "TABLET";
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public boolean isWifiModule() {
        return wifiModule;
    }

    public void setWifiModule(boolean wifiModule) {
        this.wifiModule = wifiModule;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public boolean isChipNFC() {
        return chipNFC;
    }

    public void setChipNFC(boolean chipNFC) {
        this.chipNFC = chipNFC;
    }

    @Override
    public void getInfo() {
        System.out.println("\n=== Информация о планшете ===");
        System.out.println("Тип устройства: " + getDeviceType());
        System.out.println("Модель: " + model);
        System.out.println("Номер модели: " + modelNumber);
        System.out.println("Серийный номер: " + serialNumber);
        System.out.println("Цена: $" + cost);
        System.out.println("Дата выпуска: " + dateFormat.format(releaseDate));
        System.out.println("Корпус: " + caseType);
        System.out.println("Процессор: " + processor);
        System.out.println("WIFI модуль: " + (wifiModule ? "Да" : "Нет"));
        System.out.println("Экран: " + screen);
        System.out.println("Операционная система: " + operatingSystem);
        System.out.println("NFC чип: " + (chipNFC ? "Да" : "Нет"));
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Корпус: %s, Процессор: %s, WIFI: %s, Экран: %s, ОС: %s, NFC: %s",
                caseType, processor, wifiModule ? "Да" : "Нет", screen, operatingSystem, chipNFC ? "Да" : "Нет");
    }
}