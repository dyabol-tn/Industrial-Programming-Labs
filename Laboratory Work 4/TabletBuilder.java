import java.util.Date;

public class TabletBuilder extends DeviceBuilder {
    private String operatingSystem;
    private boolean chipNFC;

    public TabletBuilder setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public TabletBuilder setChipNFC(boolean chipNFC) {
        this.chipNFC = chipNFC;
        return this;
    }

    @Override
    public TabletBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    @Override
    public TabletBuilder setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
        return this;
    }

    @Override
    public TabletBuilder setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    @Override
    public TabletBuilder setCost(int cost) {
        this.cost = cost;
        return this;
    }

    @Override
    public TabletBuilder setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    @Override
    public Tablets build() {
        validateRequiredFields();
        return new Tablets(model, modelNumber, serialNumber, cost,
                releaseDate, operatingSystem, chipNFC);
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