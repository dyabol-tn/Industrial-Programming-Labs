import java.util.Date;

class Computers extends Factory {
    private String caseType;
    private String motherboard;
    private String processor;
    private String hardDrive;
    private String ram;
    private String powerSupply;
    private String bodyFormFactor;
    private boolean wifiModule;

    public Computers() {
    }
    //bd
    public Computers(String model, String modelNumber, String serialNumber, int cost,
                     Date releaseDate, String caseType, String motherboard, String processor,
                     String hardDrive, String ram, String powerSupply) {
        super(model, modelNumber, serialNumber, cost, releaseDate);
        this.caseType = caseType;
        this.motherboard = motherboard;
        this.processor = processor;
        this.hardDrive = hardDrive;
        this.ram = ram;
        this.powerSupply = powerSupply;
        this.bodyFormFactor = caseType;
        this.wifiModule = false;
    }

    public Computers(String model, String modelNumber, String serialNumber, int cost,
                     Date releaseDate, String bodyFormFactor, boolean wifiModule) {
        super(model, modelNumber, serialNumber, cost, releaseDate);
        this.bodyFormFactor = bodyFormFactor;
        this.wifiModule = wifiModule;
        this.caseType = bodyFormFactor;
    }

    @Override
    public String getDeviceType() {
        return "COMPUTER";
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
        this.bodyFormFactor = caseType;
    }

    public String getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
    }

    public String getBodyFormFactor() {
        return bodyFormFactor;
    }

    public void setBodyFormFactor(String bodyFormFactor) {
        this.bodyFormFactor = bodyFormFactor;
        this.caseType = bodyFormFactor;
    }

    public boolean isWifiModule() {
        return wifiModule;
    }

    public void setWifiModule(boolean wifiModule) {
        this.wifiModule = wifiModule;
    }

    @Override
    public void getInfo() {
        System.out.println("\n=== Информация о компьютере ===");
        System.out.println("Тип устройства: " + getDeviceType());
        System.out.println("Модель: " + model);
        System.out.println("Номер модели: " + modelNumber);
        System.out.println("Серийный номер: " + serialNumber);
        System.out.println("Цена: $" + cost);
        System.out.println("Дата выпуска: " + dateFormat.format(releaseDate));
        System.out.println("Корпус: " + caseType);
        System.out.println("Форм-фактор: " + bodyFormFactor);
        System.out.println("Материнская плата: " + motherboard);
        System.out.println("Процессор: " + processor);
        System.out.println("Жесткий диск: " + hardDrive);
        System.out.println("Оперативная память: " + ram);
        System.out.println("Блок питания: " + powerSupply);
        System.out.println("WiFi модуль: " + (wifiModule ? "Да" : "Нет"));
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Корпус: %s, Форм-фактор: %s, Материнская плата: %s, Процессор: %s, Жесткий диск: %s, ОЗУ: %s, БП: %s, WiFi: %s",
                caseType, bodyFormFactor, motherboard, processor, hardDrive, ram, powerSupply,
                wifiModule ? "Да" : "Нет");
    }
}