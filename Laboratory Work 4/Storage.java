import java.util.Comparator;
import java.util.List;

abstract class Storage<T extends Factory> {
    public abstract void addElement(T element);

    public abstract boolean removeElement(String serialNumber);

    public abstract T getElement(String serialNumber);

    public abstract List<T> getAllElements();

    public abstract int size();

    public abstract void clear();

    public abstract boolean contains(String serialNumber);

    public abstract void displayAll();

    public abstract List<T> findElements(java.util.function.Predicate<T> condition);

    public abstract void sort(Comparator<T> comparator);

    public abstract boolean updateElement(String serialNumber, T newElement);
}
