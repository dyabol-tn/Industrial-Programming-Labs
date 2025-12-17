import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import static org.junit.jupiter.api.Assertions.*;

class DeviceDirectorTest {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    void testBuildStandardOfficeComputer() {
        DeviceDirector director = new DeviceDirector();
        Computers computer = director.buildStandardOfficeComputer();

        assertNotNull(computer);
        assertEquals("Офисный компьютер Dell", computer.getModel());
        assertEquals("DELL-OFFICE-001", computer.getModelNumber());
        assertEquals("SN-DELL-OFFICE-001", computer.getSerialNumber());
        assertEquals(800, computer.getCost());
        assertEquals("COMPUTER", computer.getDeviceType());
        assertEquals("Mid Tower", computer.getCaseType());
        assertEquals("ASUS Prime H510M-E", computer.getMotherboard());
        assertEquals("Intel Core i5-11400", computer.getProcessor());
        assertEquals("512GB SSD", computer.getHardDrive());
        assertEquals("16GB DDR4", computer.getRam());
        assertEquals("500W 80+ Bronze", computer.getPowerSupply());
    }

    @Test
    void testBuildGamingComputer() {
        DeviceDirector director = new DeviceDirector();
        Computers computer = director.buildGamingComputer();

        assertNotNull(computer);
        assertEquals("Игровой компьютер ASUS", computer.getModel());
        assertEquals("ASUS-GAME-001", computer.getModelNumber());
        assertEquals(2000, computer.getCost());
        assertEquals("Full Tower с RGB подсветкой", computer.getCaseType());
        assertEquals("ASUS ROG Strix Z790-E", computer.getMotherboard());
        assertEquals("Intel Core i9-13900K", computer.getProcessor());
        assertEquals("2TB NVMe SSD + 4TB HDD", computer.getHardDrive());
        assertEquals("32GB DDR5 6000MHz", computer.getRam());
        assertEquals("850W 80+ Gold", computer.getPowerSupply());
    }

    @Test
    void testBuildBudgetTablet() {
        DeviceDirector director = new DeviceDirector();
        Tablets tablet = director.buildBudgetTablet();

        assertNotNull(tablet);
        assertEquals("Samsung Galaxy Tab A8", tablet.getModel());
        assertEquals("SM-X200", tablet.getModelNumber());
        assertEquals(300, tablet.getCost());
        assertEquals("TABLET", tablet.getDeviceType());
        assertEquals("Металлический корпус", tablet.getCaseType());
        assertEquals("Unisoc Tiger T618", tablet.getProcessor());
        assertTrue(tablet.isWifiModule());
        assertEquals("10.5\" IPS, 1920x1200", tablet.getScreen());
    }

    @Test
    void testBuildPremiumTablet() {
        DeviceDirector director = new DeviceDirector();
        Tablets tablet = director.buildPremiumTablet();

        assertNotNull(tablet);
        assertEquals("Apple iPad Pro", tablet.getModel());
        assertEquals("A2764", tablet.getModelNumber());
        assertEquals(1200, tablet.getCost());
        assertEquals("Алюминиевый монокорпус", tablet.getCaseType());
        assertEquals("Apple M2", tablet.getProcessor());
        assertTrue(tablet.isWifiModule());
        assertEquals("12.9\" Liquid Retina XDR", tablet.getScreen());
    }

    @Test
    void testBuildOfficeLaptop() {
        DeviceDirector director = new DeviceDirector();
        Laptops laptop = director.buildOfficeLaptop();

        assertNotNull(laptop);
        assertEquals("Lenovo ThinkPad E14", laptop.getModel());
        assertEquals("20YAS01E00", laptop.getModelNumber());
        assertEquals(900, laptop.getCost());
        assertEquals("LAPTOP", laptop.getDeviceType());
        assertEquals("Углеродное волокно", laptop.getCaseType());
        assertEquals("Lenovo Custom", laptop.getMotherboard());
        assertEquals("Intel Core i5-1235U", laptop.getProcessor());
        assertEquals("512GB SSD", laptop.getHardDrive());
        assertEquals("16GB DDR4", laptop.getRam());
        assertEquals("65W адаптер", laptop.getPowerSupply());
        assertEquals("С подсветкой, рус/англ", laptop.getKeyboard());
        assertEquals("Dolby Audio, 2x2W", laptop.getSpeakers());
        assertTrue(laptop.isTouchPad());
    }

    @Test
    void testBuildGamingLaptop() {
        DeviceDirector director = new DeviceDirector();
        Laptops laptop = director.buildGamingLaptop();

        assertNotNull(laptop);
        assertEquals("ASUS ROG Strix G15", laptop.getModel());
        assertEquals("G513RM-HQ040", laptop.getModelNumber());
        assertEquals(1800, laptop.getCost());
        assertEquals("Пластик с металлическими вставками", laptop.getCaseType());
        assertEquals("ASUS ROG Custom", laptop.getMotherboard());
        assertEquals("AMD Ryzen 9 6900HX", laptop.getProcessor());
        assertEquals("1TB NVMe SSD", laptop.getHardDrive());
        assertEquals("32GB DDR5", laptop.getRam());
        assertEquals("240W адаптер", laptop.getPowerSupply());
        assertEquals("RGB с механическими переключателями", laptop.getKeyboard());
        assertEquals("Smart Amp, Dolby Atmos", laptop.getSpeakers());
        assertTrue(laptop.isTouchPad());
    }

    @Test
    void testBuildCustomComputer() throws Exception {
        DeviceDirector director = new DeviceDirector();

        Computers computer = director.buildCustomComputer(
                "Тестовый ПК",
                "TEST-001",
                "SN-TEST-001",
                1000,
                dateFormat.parse("15/12/2023"),
                "Тестовый корпус",
                "Тестовая материнская плата",
                "Тестовый процессор",
                "Тестовый жесткий диск",
                "Тестовая память",
                "Тестовый блок питания"
        );

        assertNotNull(computer);
        assertEquals("Тестовый ПК", computer.getModel());
        assertEquals("TEST-001", computer.getModelNumber());
        assertEquals("SN-TEST-001", computer.getSerialNumber());
        assertEquals(1000, computer.getCost());
        assertEquals(dateFormat.parse("15/12/2023"), computer.getReleaseDate());
        assertEquals("Тестовый корпус", computer.getCaseType());
        assertEquals("Тестовая материнская плата", computer.getMotherboard());
        assertEquals("Тестовый процессор", computer.getProcessor());
        assertEquals("Тестовый жесткий диск", computer.getHardDrive());
        assertEquals("Тестовая память", computer.getRam());
        assertEquals("Тестовый блок питания", computer.getPowerSupply());
    }
}