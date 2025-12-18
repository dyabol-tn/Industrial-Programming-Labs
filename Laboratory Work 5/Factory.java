import java.text.SimpleDateFormat;
import java.util.Date;

abstract class Factory {
    protected String model;
    protected String modelNumber;
    protected String serialNumber;
    protected int cost;
    protected Date releaseDate;
    protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Factory() {
    }

    public Factory(String model, String modelNumber, String serialNumber, int cost, Date releaseDate) {
        this.model = model;
        this.modelNumber = modelNumber;
        this.serialNumber = serialNumber;
        this.cost = cost;
        this.releaseDate = releaseDate;
    }

    abstract void getInfo();
    abstract String getDeviceType();

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return String.format("Модель: %s, Номер модели: %s, Серийный: %s, Цена: $%d, Дата выхода: %s",
                model, modelNumber, serialNumber, cost, dateFormat.format(releaseDate));
    }
}