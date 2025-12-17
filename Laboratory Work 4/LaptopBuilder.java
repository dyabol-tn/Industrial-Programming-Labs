import java.util.Date;

public class LaptopBuilder extends DeviceBuilder<Laptops> {
    private String caseType;
    private String motherboard;
    private String processor;
    private String hardDrive;
    private String ram;
    private String powerSupply;
    private String keyboard;
    private String speakers;
    private boolean touchPad;
    private boolean numPad = false;
    private boolean touchScreen = false;

    public LaptopBuilder setCaseType(String caseType) {
        this.caseType = caseType;
        return this;
    }

    public LaptopBuilder setMotherboard(String motherboard) {
        this.motherboard = motherboard;
        return this;
    }

    public LaptopBuilder setProcessor(String processor) {
        this.processor = processor;
        return this;
    }

    public LaptopBuilder setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
        return this;
    }

    public LaptopBuilder setRam(String ram) {
        this.ram = ram;
        return this;
    }

    public LaptopBuilder setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
        return this;
    }

    public LaptopBuilder setKeyboard(String keyboard) {
        this.keyboard = keyboard;
        return this;
    }

    public LaptopBuilder setSpeakers(String speakers) {
        this.speakers = speakers;
        return this;
    }

    public LaptopBuilder setTouchPad(boolean touchPad) {
        this.touchPad = touchPad;
        return this;
    }

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
        Laptops laptop = new Laptops(model, modelNumber, serialNumber, cost, releaseDate,
                caseType, motherboard, processor, hardDrive, ram, powerSupply,
                keyboard, speakers, touchPad);
        laptop.setNumPad(numPad);
        laptop.setTouchScreen(touchScreen);
        return laptop;
    }
}