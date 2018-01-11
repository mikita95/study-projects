package list.fatcopy;

/**
 * Class represents simple node of a persistent double-linked list
 * @param <E> type of an inner value
 */
public class Node<E> {

    private FatNode<E> next;
    private FatNode<E> previous;
    private FatNode<E> brother;

    private int version;
    private E value;

    Node(E value, int version, FatNode<E> brother) {
        this.value = value;
        this.version = version;
        this.brother = brother;
    }

    void setNext(FatNode<E> next) {
        this.next = next;
    }
    void setPrevious(TwoFatNode<E> previous) {
        this.previous = previous;
    }

    FatNode<E> getBrother() {
        return brother;
    }

    FatNode<E> getNext() {
        return next;
    }

    FatNode<E> getPrevious() {
        return previous;
    }

    int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return String.format("Node = {value = %s, version = %d}", value, version);
    }

    E getValue() {
        return value;
    }

    public Node<E> next(int version) {
        if (next == null)
            throw new IllegalArgumentException();
        return next.getInnerNode(version);
    }

    boolean hasNext() {
        return next != null;
    }

    boolean hasPrevious() {
        return previous != null;
    }
}
