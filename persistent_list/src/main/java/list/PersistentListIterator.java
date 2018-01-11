package list;

/**
 * An interface of partly persistent linked list's iterator
 * @param <E> type of inner data of the list
 */
public interface PersistentListIterator<E> {

    /**
     * Returns the value of the current element
     * @return the value of the current element
     */
    E getValue();

    /**
     * Returns true if this list iterator has more elements when traversing the list in the forward direction.
     * @return true if this list iterator has more elements when traversing the list in the forward direction
     */
    boolean hasNext();

    /**
     * Returns true if this list iterator has more elements when traversing the list in the reverse direction.
     * @return true if this list iterator has more elements when traversing the list in the reverse direction
     */
    boolean hasPrevious();

    /**
     * Returns the next element in the list and advances the cursor position
     */
    void next();

    /**
     * Returns the previous element in the list and moves the cursor position backwards.
     */
    void previous();
}
