import java.util.Date;

class Laptops extends Factory {
    private boolean numPad;
    private boolean touchScreen;

    public Laptops() {
    }

    public Laptops(String model, String modelNumber, String serialNumber, int cost, Date releaseDate, boolean numPad, boolean touchScreen) {
        super(model, modelNumber, serialNumber, cost, releaseDate);
        this.numPad = numPad;
        this.touchScreen = touchScreen;
    }

    @Override
    public String getDeviceType() {
        return "Ноутбук";
    }

    public boolean isNumPad() {
        return numPad;
    }

    public void setNumPad(boolean numPad) {
        this.numPad = numPad;
    }

    public boolean isTouchScreen() {
        return touchScreen;
    }

    public void setTouchScreen(boolean touchScreen) {
        this.touchScreen = touchScreen;
    }

    public void getInfo() {
        System.out.println("Модель ноутбука: " + model);
        System.out.println("Номер модели: " + modelNumber);
        System.out.println("Серийный номер: " + serialNumber);
        System.out.println("Цена: " + cost);
        System.out.println("Дата релиза: " + releaseDate);
        System.out.println("Наличие цифрового блока: " + numPad);
        System.out.println("Наличие сенсорного экрана: " + touchScreen);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Цифровой блок: %s, Сенсорный экран: %s",
                numPad ? "Да" : "Нет", touchScreen ? "Да" : "Нет");
    }
}
