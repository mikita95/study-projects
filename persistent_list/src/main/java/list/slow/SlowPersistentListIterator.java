package list.slow;

import list.PersistentListIterator;

import java.util.List;
import java.util.NoSuchElementException;

public class SlowPersistentListIterator<E> implements PersistentListIterator<E> {
    private int position;
    private List<E> list;

    public SlowPersistentListIterator(List<E> list, int index) {
        this.position = index;
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return list.size() - 1 > position;
    }

    @Override
    public boolean hasPrevious() {
        return position > 1;
    }

    @Override
    public void next() {
        if (!hasNext())
            throw new NoSuchElementException("There is no the next element in the list");
        position++;
    }

    @Override
    public void previous() {
        if (!hasPrevious())
            throw new NoSuchElementException("There is no the next element in the list");
        position--;
    }

    @Override
    public E getValue() {
        if (list.size() <= position)
            throw new NoSuchElementException("There is no such element in the list");
        return list.get(position);
    }
}
