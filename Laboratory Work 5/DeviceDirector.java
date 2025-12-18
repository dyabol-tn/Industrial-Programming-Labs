import java.util.Date;

public class DeviceDirector {

    public Computers buildStandardOfficeComputer() {
        return new ComputerBuilder()
                .setModel("Офисный компьютер Dell")
                .setModelNumber("DELL-OFFICE-001")
                .setSerialNumber("SN-DELL-OFFICE-001")
                .setCost(800)
                .setReleaseDate(new Date())
                .setCaseType("Mid Tower")
                .setMotherboard("ASUS Prime H510M-E")
                .setProcessor("Intel Core i5-11400")
                .setHardDrive("512GB SSD")
                .setRam("16GB DDR4")
                .setPowerSupply("500W 80+ Bronze")
                .setWifiModule(true)
                .build();
    }

    public Computers buildGamingComputer() {
        return new ComputerBuilder()
                .setModel("Игровой компьютер ASUS")
                .setModelNumber("ASUS-GAME-001")
                .setSerialNumber("SN-ASUS-GAME-001")
                .setCost(2000)
                .setReleaseDate(new Date())
                .setCaseType("Full Tower с RGB подсветкой")
                .setMotherboard("ASUS ROG Strix Z790-E")
                .setProcessor("Intel Core i9-13900K")
                .setHardDrive("2TB NVMe SSD + 4TB HDD")
                .setRam("32GB DDR5 6000MHz")
                .setPowerSupply("850W 80+ Gold")
                .setWifiModule(true)
                .build();
    }

    public Tablets buildBudgetTablet() {
        return new TabletBuilder()
                .setModel("Samsung Galaxy Tab A8")
                .setModelNumber("SM-X200")
                .setSerialNumber("SN-SAMSUNG-TAB-001")
                .setCost(300)
                .setReleaseDate(new Date())
                .setCaseType("Металлический корпус")
                .setProcessor("Unisoc Tiger T618")
                .setWifiModule(true)
                .setScreen("10.5\" IPS, 1920x1200")
                .setOperatingSystem("Android 11")
                .setChipNFC(true)
                .build();
    }

    public Tablets buildPremiumTablet() {
        return new TabletBuilder()
                .setModel("Apple iPad Pro")
                .setModelNumber("A2764")
                .setSerialNumber("SN-APPLE-IPAD-001")
                .setCost(1200)
                .setReleaseDate(new Date())
                .setCaseType("Алюминиевый монокорпус")
                .setProcessor("Apple M2")
                .setWifiModule(true)
                .setScreen("12.9\" Liquid Retina XDR")
                .setOperatingSystem("iPadOS 16")
                .setChipNFC(true)
                .build();
    }

    public Laptops buildOfficeLaptop() {
        return new LaptopBuilder()
                .setModel("Lenovo ThinkPad E14")
                .setModelNumber("20YAS01E00")
                .setSerialNumber("SN-LENOVO-001")
                .setCost(900)
                .setReleaseDate(new Date())
                .setCaseType("Углеродное волокно")
                .setMotherboard("Lenovo Custom")
                .setProcessor("Intel Core i5-1235U")
                .setHardDrive("512GB SSD")
                .setRam("16GB DDR4")
                .setPowerSupply("65W адаптер")
                .setKeyboard("С подсветкой, рус/англ")
                .setSpeakers("Dolby Audio, 2x2W")
                .setTouchPad(true)
                .setNumPad(true)
                .setTouchScreen(false)
                .build();
    }

    public Laptops buildGamingLaptop() {
        return new LaptopBuilder()
                .setModel("ASUS ROG Strix G15")
                .setModelNumber("G513RM-HQ040")
                .setSerialNumber("SN-ASUS-ROG-001")
                .setCost(1800)
                .setReleaseDate(new Date())
                .setCaseType("Пластик с металлическими вставками")
                .setMotherboard("ASUS ROG Custom")
                .setProcessor("AMD Ryzen 9 6900HX")
                .setHardDrive("1TB NVMe SSD")
                .setRam("32GB DDR5")
                .setPowerSupply("240W адаптер")
                .setKeyboard("RGB с механическими переключателями")
                .setSpeakers("Smart Amp, Dolby Atmos")
                .setTouchPad(true)
                .setNumPad(true)
                .setTouchScreen(true)
                .build();
    }

    public Computers buildCustomComputer(String model, String modelNumber, String serialNumber,
                                         int cost, Date releaseDate, String caseType,
                                         String motherboard, String processor, String hardDrive,
                                         String ram, String powerSupply) {
        return new ComputerBuilder()
                .setModel(model)
                .setModelNumber(modelNumber)
                .setSerialNumber(serialNumber)
                .setCost(cost)
                .setReleaseDate(releaseDate)
                .setCaseType(caseType)
                .setMotherboard(motherboard)
                .setProcessor(processor)
                .setHardDrive(hardDrive)
                .setRam(ram)
                .setPowerSupply(powerSupply)
                .setWifiModule(true)
                .build();
    }

    public Tablets buildCustomTablet(String model, String modelNumber, String serialNumber,
                                     int cost, Date releaseDate, String caseType,
                                     String processor, boolean wifiModule, String screen) {
        return new TabletBuilder()
                .setModel(model)
                .setModelNumber(modelNumber)
                .setSerialNumber(serialNumber)
                .setCost(cost)
                .setReleaseDate(releaseDate)
                .setCaseType(caseType)
                .setProcessor(processor)
                .setWifiModule(wifiModule)
                .setScreen(screen)
                .setOperatingSystem("Android")
                .setChipNFC(true)
                .build();
    }

    public Laptops buildCustomLaptop(String model, String modelNumber, String serialNumber,
                                     int cost, Date releaseDate, String caseType,
                                     String motherboard, String processor, String hardDrive,
                                     String ram, String powerSupply, String keyboard,
                                     String speakers, boolean touchPad) {
        return new LaptopBuilder()
                .setModel(model)
                .setModelNumber(modelNumber)
                .setSerialNumber(serialNumber)
                .setCost(cost)
                .setReleaseDate(releaseDate)
                .setCaseType(caseType)
                .setMotherboard(motherboard)
                .setProcessor(processor)
                .setHardDrive(hardDrive)
                .setRam(ram)
                .setPowerSupply(powerSupply)
                .setKeyboard(keyboard)
                .setSpeakers(speakers)
                .setTouchPad(touchPad)
                .setNumPad(true)
                .setTouchScreen(true)
                .build();
    }
}