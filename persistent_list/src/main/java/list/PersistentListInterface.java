package list;

/**
 * An interface of partly persistent linked list
 * @param <E> type of inner data of the list
 */
public interface PersistentListInterface<E> {

    /**
     * Returns {@link PersistentListIterator} to the certain position of the certain version of the list
     * @param index of the head element
     * @param version number of the list
     * @return {@link PersistentListIterator} to the position of the version
     */
    PersistentListIterator<E> get(int index, int version);

    /**
     * Add a new element to the list and creates a new list's version
     * @param index of the position where add a new element to
     * @param value of the element
     */
    void add(int index, E value);

    /**
     * Edit the element in the list and creates a new list's version
     * @param index of the position where replace the element
     * @param newValue of the element
     */
    void replace(int index, E newValue);

    /**
     * Returns the head of the specified version of the list
     * @param version number of the list
     * @return {@link PersistentListIterator} to the position of the version
     */
    PersistentListIterator<E> getListHead(int version);

    /**
     * Returns the tail of the specified version of the list
     * @param version number of the list
     * @return {@link PersistentListIterator} to the position of the version
     */
    PersistentListIterator<E> getListTail(int version);

    /**
     * Returns true if this list contains no elements in any version.
     * @return if this list contains no elements
     */
    boolean isEmpty();

    /**
     * Returns the last version number of the list
     * @return the last version number of the list
     */
    int getListVersion();

    void clear();
}
