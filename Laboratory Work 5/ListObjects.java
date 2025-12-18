import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class ListObjects<T extends Factory> extends Storage<T> {
    private List<T> objects;

    public ListObjects() {
        this.objects = new ArrayList<>();
    }

    @Override
    public void addElement(T element) {
        if (element != null && !contains(element.getSerialNumber())) {
            objects.add(element);
        }
    }

    @Override
    public boolean removeElement(String serialNumber) {
        return objects.removeIf(obj -> obj.getSerialNumber().equals(serialNumber));
    }

    @Override
    public T getElement(String serialNumber) {
        return objects.stream()
                .filter(obj -> obj.getSerialNumber().equals(serialNumber))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<T> getAllElements() {
        return new ArrayList<>(objects);
    }

    @Override
    public int size() {
        return objects.size();
    }

    @Override
    public void clear() {
        objects.clear();
    }

    @Override
    public boolean contains(String serialNumber) {
        return objects.stream().anyMatch(obj -> obj.getSerialNumber().equals(serialNumber));
    }

    @Override
    public void displayAll() {
        if (objects.isEmpty()) {
            System.out.println("В хранилище нет устройств.");
            return;
        }
        System.out.println("=== Все устройства ===");
        for (int i = 0; i < objects.size(); i++) {
            System.out.println((i + 1) + ". " + objects.get(i));
        }
    }

    @Override
    public List<T> findElements(java.util.function.Predicate<T> condition) {
        List<T> result = new ArrayList<>();
        for (T obj : objects) {
            if (condition.test(obj)) {
                result.add(obj);
            }
        }
        return result;
    }

    @Override
    public void sort(Comparator<T> comparator) {
        objects.sort(comparator);
    }

    @Override
    public boolean updateElement(String serialNumber, T newElement) {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getSerialNumber().equals(serialNumber)) {
                objects.set(i, newElement);
                return true;
            }
        }
        return false;
    }
}
