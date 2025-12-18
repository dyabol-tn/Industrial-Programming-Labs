import java.util.Date;

class Laptops extends Factory {
    private String caseType;
    private String motherboard;
    private String processor;
    private String hardDrive;
    private String ram;
    private String powerSupply;
    private String keyboard;
    private String speakers;
    private boolean touchPad;
    private boolean numPad;
    private boolean touchScreen;

    public Laptops() {
    }

    //bd
    public Laptops(String model, String modelNumber, String serialNumber, int cost,
                   Date releaseDate, String caseType, String motherboard, String processor,
                   String hardDrive, String ram, String powerSupply, String keyboard,
                   String speakers, boolean touchPad) {
        super(model, modelNumber, serialNumber, cost, releaseDate);
        this.caseType = caseType;
        this.motherboard = motherboard;
        this.processor = processor;
        this.hardDrive = hardDrive;
        this.ram = ram;
        this.powerSupply = powerSupply;
        this.keyboard = keyboard;
        this.speakers = speakers;
        this.touchPad = touchPad;
        this.numPad = false;
        this.touchScreen = false;
    }

    public Laptops(String model, String modelNumber, String serialNumber, int cost,
                   Date releaseDate, boolean numPad, boolean touchScreen) {
        super(model, modelNumber, serialNumber, cost, releaseDate);
        this.numPad = numPad;
        this.touchScreen = touchScreen;
        this.caseType = "Unknown";
        this.motherboard = "Unknown";
        this.processor = "Unknown";
        this.hardDrive = "Unknown";
        this.ram = "Unknown";
        this.powerSupply = "Unknown";
        this.keyboard = "Unknown";
        this.speakers = "Unknown";
        this.touchPad = false;
    }

    @Override
    public String getDeviceType() {
        return "LAPTOP";
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
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

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getSpeakers() {
        return speakers;
    }

    public void setSpeakers(String speakers) {
        this.speakers = speakers;
    }

    public boolean isTouchPad() {
        return touchPad;
    }

    public void setTouchPad(boolean touchPad) {
        this.touchPad = touchPad;
    }

    public boolean isNumPad() {
        return numPad;
    }

    public void setNumPad(boolean numPad) {
        this.numPad = numPad;
    }

    public boolean isTouchScreen() {
        return touchScreen;
    }

    public void setTouchScreen(boolean touchScreen) {
        this.touchScreen = touchScreen;
    }

    @Override
    public void getInfo() {
        System.out.println("\n=== Информация о ноутбуке ===");
        System.out.println("Тип устройства: " + getDeviceType());
        System.out.println("Модель: " + model);
        System.out.println("Номер модели: " + modelNumber);
        System.out.println("Серийный номер: " + serialNumber);
        System.out.println("Цена: $" + cost);
        System.out.println("Дата выпуска: " + dateFormat.format(releaseDate));
        System.out.println("Корпус: " + caseType);
        System.out.println("Материнская плата: " + motherboard);
        System.out.println("Процессор: " + processor);
        System.out.println("Жесткий диск: " + hardDrive);
        System.out.println("Оперативная память: " + ram);
        System.out.println("Блок питания: " + powerSupply);
        System.out.println("Клавиатура: " + keyboard);
        System.out.println("Динамики: " + speakers);
        System.out.println("TouchPad: " + (touchPad ? "Да" : "Нет"));
        System.out.println("NumPad: " + (numPad ? "Да" : "Нет"));
        System.out.println("Сенсорный экран: " + (touchScreen ? "Да" : "Нет"));
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Корпус: %s, Материнская плата: %s, Процессор: %s, Жесткий диск: %s, ОЗУ: %s, БП: %s, Клавиатура: %s, Динамики: %s, TouchPad: %s, NumPad: %s, Сенсорный экран: %s",
                caseType, motherboard, processor, hardDrive, ram, powerSupply,
                keyboard, speakers, touchPad ? "Да" : "Нет", numPad ? "Да" : "Нет", touchScreen ? "Да" : "Нет");
    }
}