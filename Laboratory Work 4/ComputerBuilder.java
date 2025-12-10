import java.util.Date;

public class ComputerBuilder extends DeviceBuilder {
    private String bodyFormFactor;
    private boolean wifiModule;

    public ComputerBuilder setBodyFormFactor(String bodyFormFactor) {
        this.bodyFormFactor = bodyFormFactor;
        return this;
    }

    public ComputerBuilder setWifiModule(boolean wifiModule) {
        this.wifiModule = wifiModule;
        return this;
    }

    @Override
    public ComputerBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    @Override
    public ComputerBuilder setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
        return this;
    }

    @Override
    public ComputerBuilder setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    @Override
    public ComputerBuilder setCost(int cost) {
        this.cost = cost;
        return this;
    }

    @Override
    public ComputerBuilder setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    @Override
    public Computers build() {
        validateRequiredFields();
        return new Computers(model, modelNumber, serialNumber, cost,
                releaseDate, bodyFormFactor, wifiModule);
    }

    private void validateRequiredFields() {
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