import java.util.Optional;

/**
 * Created by nikita on 10.09.16.
 */
public class Node<K, V> {
    private V value;
    private K key;

    private Node<K, V> next;
    private Node<K, V> prev;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
        this.prev = null;
    }

    public V getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }

    public Optional<Node<K, V>> getNext() {
        return Optional.ofNullable(next);
    }

    public Optional<Node<K, V>> getPrev() {
        return Optional.ofNullable(prev);
    }

    public void setNext(Node<K, V> next) {
        this.next = next;
    }

    public void setPrev(Node<K, V> prev) {
        this.prev = prev;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
