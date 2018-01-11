package list.fatcopy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class represents fat node of persistent double-linked list
 * @param <E> type of an inner value
 */
public class FatNode<E> {
    private List<Node<E>> nodes;

    FatNode(int size) {
        nodes = new ArrayList<>(Collections.nCopies(size, null));
    }

    @Override
    public String toString() {
        return String.format("Fat Node = {%s}",
                IntStream.range(0, size()).
                        mapToObj(i -> String.format("%d = %s", i, getNode(i).toString())).
                        collect(Collectors.joining(",\n")));
    }

    Node<E> getNode(int index) {
        return nodes.get(index);
    }

    void setNode(int index, Node<E> node) {
        if (size() < index)
            throw new IllegalArgumentException("Index out of bound");
        if (nodes.get(index) != null)
            throw new IllegalArgumentException(String.format("%d small node has been created", index));

        nodes.set(index, node);
    }

    Node<E> getInnerNode(int version) {
        for (int i = size() - 1; i >= 0; i--) {
            Node<E> node = getNode(i);
            if (node != null && node.getVersion() <= version)
                return node;
        }
        return null;
    }

    private int size() {
        return nodes.size();
    }
}
