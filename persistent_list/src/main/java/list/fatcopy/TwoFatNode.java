package list.fatcopy;

import java.util.Optional;

public class TwoFatNode<E> extends FatNode<E> {

    public TwoFatNode() {
        super(2);
    }

    public Node<E> getFirst() {
        return this.getNode(0);
    }
    public Node<E> getSecond() {
        return this.getNode(1);
    }

    void setFirst(Node<E> first) {
        this.setNode(0, first);
    }

    void setSecond(Node<E> second) {
        this.setNode(1, second);
    }
}
