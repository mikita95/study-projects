
package ru.ifmo.ctddev.markovnikov.arrayset;

import java.util.*;

public class ArraySet<E> extends AbstractSet<E> implements SortedSet<E> {

    private Comparator<? super E> comparator;
    private List<E> list;
    protected boolean defaultComparator;

    public ArraySet() {
        list = new ArrayList<>();
        this.comparator = null;
    }

    public ArraySet(Collection<? extends E> collection, Comparator<? super E> comparator) {
        List<E> col = new ArrayList<>(collection);
        this.comparator = comparator;
        Collections.sort(col, comparator);
        List<E> tempSet = new ArrayList<>();
        if (col.size() > 0) {
            tempSet.add(col.get(0));
        }
        for (int i = 1; i < col.size(); ++i) {
            E item = col.get(i);
            if (comparator.compare(tempSet.get(tempSet.size() - 1), item) != 0) {
                tempSet.add(item);
            }
        }
        list = new ArrayList<>(tempSet);
        defaultComparator = false;
    }

    protected ArraySet(List<E> list, Comparator<? super E> comparator, boolean defaultComparator) {
        this.list = list;
        this.comparator = comparator;
        this.defaultComparator = defaultComparator;
    }

    public ArraySet(Collection<E> collection) {
        this(collection, new Comparator<E>() {
            @Override
            @SuppressWarnings("unchecked")
            public int compare(E o1, E o2) {
                return ((Comparable<? super E>) o1).compareTo(o2);
            }
        });
        defaultComparator = true;
    }

    @Override
    public Comparator<? super E> comparator() {
        return defaultComparator ? null : comparator;
    }

    @Override
    public SortedSet<E> subSet(E e, E e1) {
        return tailSet(e).headSet(e1);
    }

    @Override
    public SortedSet<E> headSet(E e) {
        int index = Collections.binarySearch(list, e, comparator);
        if (index < 0) {
            index = -index - 1;
        }
        return new ArraySet<>(list.subList(0, index), comparator, defaultComparator);
    }

    @Override
    public SortedSet<E> tailSet(E e) {
        int index = Collections.binarySearch(list, e, comparator);
        if (index < 0) {
            index = - 1 - index;
        }
        return new ArraySet<>(list.subList(index, list.size()), comparator, defaultComparator);
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.get(0);
    }

    @Override
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.get(list.size() - 1);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        return Collections.binarySearch(list, (E) o, comparator) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Iterator<E> iterator = list.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                return iterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove is not supported");
            }
        };
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("add is not supported");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("remove is not supported");
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException("add all is no supported");
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException("retain all is not supported");
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException("remove all is not supported");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear is not supported");
    }
}
