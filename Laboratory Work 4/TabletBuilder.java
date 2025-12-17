import java.util.Date;

public class TabletBuilder extends DeviceBuilder<Tablets> {
    private String caseType;
    private String processor;
    private boolean wifiModule;
    private String screen;
    private String operatingSystem = "Unknown";
    private boolean chipNFC = false;

    public TabletBuilder setCaseType(String caseType) {
        this.caseType = caseType;
        return this;
    }

    public TabletBuilder setProcessor(String processor) {
        this.processor = processor;
        return this;
    }

    public TabletBuilder setWifiModule(boolean wifiModule) {
        this.wifiModule = wifiModule;
        return this;
    }

    public TabletBuilder setScreen(String screen) {
        this.screen = screen;
        return this;
    }

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
        Tablets tablet = new Tablets(model, modelNumber, serialNumber, cost, releaseDate,
                caseType, processor, wifiModule, screen);
        tablet.setOperatingSystem(operatingSystem);
        tablet.setChipNFC(chipNFC);
        return tablet;
    }
}