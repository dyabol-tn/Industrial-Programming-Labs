import java.util.*;

class MapObjects<T extends Factory> extends Storage<T> {
    private Map<String, T> objects;

    public MapObjects() {
        this.objects = new LinkedHashMap<>();
    }

    @Override
    public void addElement(T element) {
        if (element != null && !objects.containsKey(element.getSerialNumber())) {
            objects.put(element.getSerialNumber(), element);
        }
    }

    @Override
    public boolean removeElement(String serialNumber) {
        return objects.remove(serialNumber) != null;
    }

    @Override
    public T getElement(String serialNumber) {
        return objects.get(serialNumber);
    }

    @Override
    public List<T> getAllElements() {
        return new ArrayList<>(objects.values());
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
        return objects.containsKey(serialNumber);
    }

    @Override
    public void displayAll() {
        if (objects.isEmpty()) {
            System.out.println("В хранилище нет устройств.");
            return;
        }
        System.out.println("=== Все устройства ===");
        int index = 1;
        for (T obj : objects.values()) {
            System.out.println(index + ". " + obj);
            index++;
        }
    }

    @Override
    public List<T> findElements(java.util.function.Predicate<T> condition) {
        List<T> result = new ArrayList<>();
        for (T obj : objects.values()) {
            if (condition.test(obj)) {
                result.add(obj);
            }
        }
        return result;
    }

    @Override
    public void sort(Comparator<T> comparator) {
        List<T> sortedList = new ArrayList<>(objects.values());
        sortedList.sort(comparator);

        objects.clear();
        for (T obj : sortedList) {
            objects.put(obj.getSerialNumber(), obj);
        }
    }

    @Override
    public boolean updateElement(String serialNumber, T newElement) {
        if (objects.containsKey(serialNumber)) {
            objects.put(serialNumber, newElement);
            return true;
        }
        return false;
    }
}
