import java.util.Date;

public abstract class DeviceBuilder {
    protected String model;
    protected String modelNumber;
    protected String serialNumber;
    protected int cost;
    protected Date releaseDate;

    // Обязательные шаги для всех устройств (объявляются абстрактно)
    public abstract DeviceBuilder setModel(String model);
    public abstract DeviceBuilder setModelNumber(String modelNumber);
    public abstract DeviceBuilder setSerialNumber(String serialNumber);
    public abstract DeviceBuilder setCost(int cost);
    public abstract DeviceBuilder setReleaseDate(Date releaseDate);

    // Абстрактный метод сборки продукта
    public abstract Factory build();
}