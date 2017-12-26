import java.util.HashMap;
import java.util.Optional;

/**
 * Created by nikita on 10.09.16.
 */
public class LRUCache<K, V> {
    private HashMap<K, Node<K, V>> map;
    private Optional<K> head;
    private K tail;
    private final int MAX_SIZE;

    public LRUCache(int capacity) {
        MAX_SIZE = capacity;
        map = new HashMap<>();
        head = Optional.empty();
        tail = null;
    }

    public final V get(K key) {
        int oldSize = size();
        Optional<Node<K, V>> node = Optional.ofNullable(map.get(key));

        moveToHead(node);

        assert size() == oldSize && (!node.isPresent() || head.map(x -> x.equals(key)).orElse(true));
        return node.map(Node::getValue).orElse(null);
    }

    private void moveToHead(Optional<Node<K, V>> node) {
        if (node.isPresent() && head.isPresent() && node.get().getKey().equals(head.get())) return;

        disconnectNode(node);

        head.ifPresent(x -> map.get(x).setPrev(node.map(y -> y).orElse(null)));
        if (size() > 1)
            node.ifPresent(x -> x.setNext(map.get(head.get())));
        node.ifPresent(x -> x.setPrev(null));
        head = Optional.ofNullable(node.map(Node::getKey).orElse(head.map(x -> x).orElse(null)));
    }

    public final int size() {
        int result = map.size();
        assert (0 <= result) && (result <= MAX_SIZE);
        return result;
    }

    private void disconnectNode(Optional<Node<K, V>> node) {
        if (node.isPresent()) {
            K nodeKey = node.get().getKey();

            if (nodeKey.equals(tail))
                tail = map.get(tail).getPrev().map(Node::getKey).orElse(tail);

            if (head.map(x -> x.equals(nodeKey)).orElse(false))
                head = Optional.of(map.get(head.get()).getNext().get().getKey());
        }
        node.ifPresent(x -> x.getNext().ifPresent(y -> y.setPrev(x.getPrev().map(z -> z).orElse(null))));
        node.ifPresent(x -> x.getPrev().ifPresent(y -> y.setNext(x.getNext().map(z -> z).orElse(null))));
    }

    public final void remove(K key) {
        disconnectNode(Optional.ofNullable(map.get(key)));
        map.remove(key);
    }

    public final void set(K key, V value) {
        assert map.size() <= MAX_SIZE;

        if (map.containsKey(key)) {
            get(key);
            return;
        }
        if (size() == MAX_SIZE)
            remove(tail);

        Node<K, V> newNode = new Node<>(key, value);
        head.ifPresent(x -> map.get(x).setPrev(newNode));
        head.ifPresent(x -> newNode.setNext(map.get(x)));
        head = Optional.of(key);
        map.put(key, newNode);

        if (size() == 1)
            tail = key;

        assert map.size() <= MAX_SIZE && map.get(head.get()).getValue() == map.get(key).getValue();
    }
}
