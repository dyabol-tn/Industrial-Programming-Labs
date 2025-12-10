import org.junit.jupiter.api.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BuilderPatternTest {
    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @AfterEach
    void tearDown() {
        dateFormat = null;
    }

    @Test
    void testComputerBuilderCreatesValidComputer() throws Exception {
        Date releaseDate = dateFormat.parse("15/05/2023");

        Computers computer = new ComputerBuilder()
                .setModel("Dell XPS")
                .setModelNumber("XPS8930")
                .setSerialNumber("SN001")
                .setCost(1200)
                .setReleaseDate(releaseDate)
                .setBodyFormFactor("Tower")
                .setWifiModule(true)
                .build();

        assertNotNull(computer);
        assertEquals("Dell XPS", computer.getModel());
        assertEquals("XPS8930", computer.getModelNumber());
        assertEquals("SN001", computer.getSerialNumber());
        assertEquals(1200, computer.getCost());
        assertEquals(releaseDate, computer.getReleaseDate());
        assertEquals("Tower", computer.getBodyFormFactor());
        assertTrue(computer.isWifiModule());
        assertEquals("Компьютер", computer.getDeviceType());
    }

    @Test
    void testComputerBuilderMissingModelThrowsException() throws Exception {
        Date releaseDate = dateFormat.parse("15/05/2023");

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new ComputerBuilder()
                    .setModel("")
                    .setModelNumber("XPS8930")
                    .setSerialNumber("SN001")
                    .setCost(1200)
                    .setReleaseDate(releaseDate)
                    .setBodyFormFactor("Tower")
                    .setWifiModule(true)
                    .build();
        });

        assertEquals("Модель не может быть пустой", exception.getMessage());
    }

    @Test
    void testComputerBuilderMissingSerialNumberThrowsException() throws Exception {
        Date releaseDate = dateFormat.parse("15/05/2023");

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new ComputerBuilder()
                    .setModel("Dell XPS")
                    .setModelNumber("XPS8930")
                    .setSerialNumber("")
                    .setCost(1200)
                    .setReleaseDate(releaseDate)
                    .setBodyFormFactor("Tower")
                    .setWifiModule(true)
                    .build();
        });

        assertEquals("Серийный номер не может быть пустым", exception.getMessage());
    }

    @Test
    void testComputerBuilderInvalidCostThrowsException() throws Exception {
        Date releaseDate = dateFormat.parse("15/05/2023");

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new ComputerBuilder()
                    .setModel("Dell XPS")
                    .setModelNumber("XPS8930")
                    .setSerialNumber("SN001")
                    .setCost(0)
                    .setReleaseDate(releaseDate)
                    .setBodyFormFactor("Tower")
                    .setWifiModule(true)
                    .build();
        });

        assertEquals("Цена должна быть положительной", exception.getMessage());
    }

    @Test
    void testComputerBuilderMissingReleaseDateThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new ComputerBuilder()
                    .setModel("Dell XPS")
                    .setModelNumber("XPS8930")
                    .setSerialNumber("SN001")
                    .setCost(1200)
                    .setReleaseDate(null)
                    .setBodyFormFactor("Tower")
                    .setWifiModule(true)
                    .build();
        });

        assertEquals("Дата выпуска не может быть null", exception.getMessage());
    }

    @Test
    void testTabletBuilderCreatesValidTablet() throws Exception {
        Date releaseDate = dateFormat.parse("20/10/2023");

        Tablets tablet = new TabletBuilder()
                .setModel("iPad Pro")
                .setModelNumber("A2377")
                .setSerialNumber("SN002")
                .setCost(999)
                .setReleaseDate(releaseDate)
                .setOperatingSystem("iPadOS")
                .setChipNFC(true)
                .build();

        assertNotNull(tablet);
        assertEquals("iPad Pro", tablet.getModel());
        assertEquals("A2377", tablet.getModelNumber());
        assertEquals("SN002", tablet.getSerialNumber());
        assertEquals(999, tablet.getCost());
        assertEquals(releaseDate, tablet.getReleaseDate());
        assertEquals("iPadOS", tablet.getOperatingSystem());
        assertTrue(tablet.isChipNFC());
        assertEquals("Планшет", tablet.getDeviceType());
    }

    @Test
    void testTabletBuilderMissingModelThrowsException() throws Exception {
        Date releaseDate = dateFormat.parse("20/10/2023");

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new TabletBuilder()
                    .setModel("")
                    .setModelNumber("A2377")
                    .setSerialNumber("SN002")
                    .setCost(999)
                    .setReleaseDate(releaseDate)
                    .setOperatingSystem("iPadOS")
                    .setChipNFC(true)
                    .build();
        });

        assertEquals("Модель не может быть пустой", exception.getMessage());
    }

    @Test
    void testTabletBuilderMissingOperatingSystemIsAllowed() throws Exception {
        Date releaseDate = dateFormat.parse("20/10/2023");

        Tablets tablet = new TabletBuilder()
                .setModel("iPad Pro")
                .setModelNumber("A2377")
                .setSerialNumber("SN002")
                .setCost(999)
                .setReleaseDate(releaseDate)
                .setOperatingSystem(null)
                .setChipNFC(true)
                .build();

        assertNotNull(tablet);
        assertNull(tablet.getOperatingSystem());
    }

    @Test
    void testLaptopBuilderCreatesValidLaptop() throws Exception {
        Date releaseDate = dateFormat.parse("01/07/2023");

        Laptops laptop = new LaptopBuilder()
                .setModel("MacBook Pro")
                .setModelNumber("M2")
                .setSerialNumber("SN003")
                .setCost(1999)
                .setReleaseDate(releaseDate)
                .setNumPad(true)
                .setTouchScreen(true)
                .build();

        assertNotNull(laptop);
        assertEquals("MacBook Pro", laptop.getModel());
        assertEquals("M2", laptop.getModelNumber());
        assertEquals("SN003", laptop.getSerialNumber());
        assertEquals(1999, laptop.getCost());
        assertEquals(releaseDate, laptop.getReleaseDate());
        assertTrue(laptop.isNumPad());
        assertTrue(laptop.isTouchScreen());
        assertEquals("Ноутбук", laptop.getDeviceType());
    }

    @Test
    void testLaptopBuilderFluentInterface() throws Exception {
        Date releaseDate = dateFormat.parse("01/07/2023");

        LaptopBuilder builder = new LaptopBuilder();
        Laptops laptop = builder
                .setModel("MacBook Pro")
                .setModelNumber("M2")
                .setSerialNumber("SN003")
                .setCost(1999)
                .setReleaseDate(releaseDate)
                .setNumPad(true)
                .setTouchScreen(true)
                .build();

        assertNotNull(laptop);
        assertEquals("MacBook Pro", laptop.getModel());
    }
}