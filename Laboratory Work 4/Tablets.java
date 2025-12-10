import java.util.Date;

class Tablets extends Factory {
    private String operatingSystem;
    private boolean chipNFC;

    public Tablets() {
    }

    public Tablets(String model, String modelNumber, String serialNumber, int cost, Date releaseDate, String operatingSystem, boolean chipNFC) {
        super(model, modelNumber, serialNumber, cost, releaseDate);
        this.operatingSystem = operatingSystem;
        this.chipNFC = chipNFC;
    }

    @Override
    public String getDeviceType() {
        return "Планшет";
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

    public void getInfo() {
        System.out.println("Модель планшета: " + model);
        System.out.println("Номер модели: " + modelNumber);
        System.out.println("Серийный номер: " + serialNumber);
        System.out.println("Цена: " + cost);
        System.out.println("Дата релиза: " + releaseDate);
        System.out.println("Операционная система: " + operatingSystem);
        System.out.println("Наличие NFC: " + chipNFC);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", ОС: %s, NFC: %s",
                operatingSystem, chipNFC ? "Да" : "Нет");
    }
}
