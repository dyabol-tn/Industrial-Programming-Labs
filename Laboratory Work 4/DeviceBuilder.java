import java.util.Date;

public abstract class DeviceBuilder<T extends Factory> {
    protected String model;
    protected String modelNumber;
    protected String serialNumber;
    protected int cost;
    protected Date releaseDate;

    public abstract DeviceBuilder<T> setModel(String model);
    public abstract DeviceBuilder<T> setModelNumber(String modelNumber);
    public abstract DeviceBuilder<T> setSerialNumber(String serialNumber);
    public abstract DeviceBuilder<T> setCost(int cost);
    public abstract DeviceBuilder<T> setReleaseDate(Date releaseDate);

    public abstract T build();

    protected void validateRequiredFields() {
        if (model == null || model.isEmpty()) {
            throw new IllegalStateException("Модель не может быть пустой");
        }
        if (serialNumber == null || serialNumber.isEmpty()) {
            throw new IllegalStateException("Серийный номер не может быть пустым");
        }
        if (cost <= 0) {
            throw new IllegalStateException("Цена должна быть положительной");
        }
        if (releaseDate == null) {
            throw new IllegalStateException("Дата выпуска не может быть null");
        }
    }
}