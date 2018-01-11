package list.fatcopy;

import list.PersistentListIterator;

import java.util.NoSuchElementException;

public class FatCopyPersistentListIterator<E> implements PersistentListIterator<E> {
    private Node<E> current;
    private int version;

    FatCopyPersistentListIterator(Node<E> current, int version) {
        this.current = current;
        this.version = version;
    }

    @Override
    public boolean hasNext() {
        assert current != null;
        return current.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        assert current != null;
        return current.hasPrevious();
    }

    @Override
    public void next() {
        if (current == null || !current.hasNext())
            throw new NoSuchElementException();
        current = current.
                getNext().
                getInnerNode(version);
    }

    @Override
    public void previous() {
        if (current == null || !current.hasNext())
            throw new NoSuchElementException();
        current = current.
                getPrevious().
                getInnerNode(version);
    }

    @Override
    public E getValue() {
        if (current == null)
            throw new NoSuchElementException("There is no such element in the list");
        return current.getValue();
    }
}
