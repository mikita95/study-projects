package list.slow;

import list.PersistentListInterface;
import list.PersistentListIterator;

import java.util.ArrayList;
import java.util.List;

public class SlowPersistentList<E> implements PersistentListInterface<E> {
    private List<List<E>> data;
    private int version;

    public SlowPersistentList() {
        clear();
    }

    @Override
    public PersistentListIterator<E> get(int index, int version) {
        if (data.size() < version + 1)
            throw new IndexOutOfBoundsException("Index out of bounds");
        return new SlowPersistentListIterator<>(data.get(version), index);
    }

    @Override
    public void add(int index, E value) {
        version++;
        data.add(data.size() == 0 ? new ArrayList<>() : new ArrayList<>(data.get(data.size() - 1)));
        data.get(data.size() - 1).add(index, value);
    }

    @Override
    public void replace(int index, E newValue) {
        version++;
        data.add(data.size() == 0 ? new ArrayList<>() : new ArrayList<>(data.get(data.size() - 1)));
        data.get(data.size() - 1).set(index, newValue);
    }

    @Override
    public PersistentListIterator<E> getListHead(int version) {
        return new SlowPersistentListIterator<>(data.get(version), 0);
    }

    @Override
    public PersistentListIterator<E> getListTail(int version) {
        return new SlowPersistentListIterator<>(data.get(version), data.get(version).size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return version == 0 || data.get(version).size() == 0;
    }

    @Override
    public int getListVersion() {
        return version ;
    }

    @Override
    public void clear() {
        version = 0;
        data = new ArrayList<>();
    }
}
