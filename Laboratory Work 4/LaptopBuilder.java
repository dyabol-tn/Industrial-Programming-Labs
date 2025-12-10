import java.util.Date;

public class LaptopBuilder extends DeviceBuilder {
    private boolean numPad;
    private boolean touchScreen;

    public LaptopBuilder setNumPad(boolean numPad) {
        this.numPad = numPad;
        return this;
    }

    public LaptopBuilder setTouchScreen(boolean touchScreen) {
        this.touchScreen = touchScreen;
        return this;
    }

    @Override
    public LaptopBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    @Override
    public LaptopBuilder setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
        return this;
    }

    @Override
    public LaptopBuilder setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    @Override
    public LaptopBuilder setCost(int cost) {
        this.cost = cost;
        return this;
    }

    @Override
    public LaptopBuilder setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    @Override
    public Laptops build() {
        validateRequiredFields();
        return new Laptops(model, modelNumber, serialNumber, cost,
                releaseDate, numPad, touchScreen);
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