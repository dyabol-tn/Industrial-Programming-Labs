import java.util.Date;

public class ComputerBuilder extends DeviceBuilder<Computers> {
    private String caseType;
    private String motherboard;
    private String processor;
    private String hardDrive;
    private String ram;
    private String powerSupply;
    private boolean wifiModule = false;

    public ComputerBuilder setCaseType(String caseType) {
        this.caseType = caseType;
        return this;
    }

    public ComputerBuilder setMotherboard(String motherboard) {
        this.motherboard = motherboard;
        return this;
    }

    public ComputerBuilder setProcessor(String processor) {
        this.processor = processor;
        return this;
    }

    public ComputerBuilder setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
        return this;
    }

    public ComputerBuilder setRam(String ram) {
        this.ram = ram;
        return this;
    }

    public ComputerBuilder setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
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
        Computers computer = new Computers(model, modelNumber, serialNumber, cost, releaseDate,
                caseType, motherboard, processor, hardDrive, ram, powerSupply);
        computer.setWifiModule(wifiModule);
        return computer;
    }
}