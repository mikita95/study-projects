package list.fatcopy;

import list.PersistentListInterface;
import list.PersistentListIterator;

import java.util.ArrayList;
import java.util.List;

public class FatCopyPersistentList<E> implements PersistentListInterface<E> {
    private List<TwoFatNode<E>> heads;
    private List<TwoFatNode<E>> tails;
    private int previousVersion;
    private int currentVersion;


    public FatCopyPersistentList() {
        clear();
    }

    @Override
    public void add(int index, E value) {
        previousVersion = currentVersion;
        currentVersion++;
        innerAdd(index, value);
        fillEdges();
    }

    @Override
    public void replace(int index, E newValue) {
        previousVersion = currentVersion;
        currentVersion++;
        innerEdit(index, newValue);
        fillEdges();
    }

    private void fillEdges() {
        if (heads.size() < currentVersion + 1)
            heads.add(heads.get(previousVersion));
        if (tails.size() < currentVersion + 1)
            tails.add(tails.get(previousVersion));
    }

    @Override
    public PersistentListIterator<E> getListHead(int version) {
        return new FatCopyPersistentListIterator<>(getHeadInnerNode(version), version);
    }

    @Override
    public PersistentListIterator<E> getListTail(int version) {
        return new FatCopyPersistentListIterator<>(getTailInnerNode(version), version);
    }

    @Override
    public PersistentListIterator<E> get(int index, int version) {
        return new FatCopyPersistentListIterator<>(getInnerNode(index, version), version);
    }

    @Override
    public int getListVersion() {
        return currentVersion + 1;
    }

    @Override
    public void clear() {
        previousVersion = currentVersion = -1;
        heads = new ArrayList<>();
        tails = new ArrayList<>();
    }

    @Override
    public boolean isEmpty() {
        return previousVersion == -1 || heads.get(previousVersion) == null;
    }

    private Node<E> getHeadInnerNode(int version) {
        if (heads.size() < version)
            throw new IllegalArgumentException();
        return heads.
                get(version).
                getInnerNode(version);
    }

    private Node<E> getTailInnerNode(int version) {
        if (tails.size() < version)
            throw new IllegalArgumentException();
        return tails.
                get(version).
                getInnerNode(version);
    }

    private Node<E> getInnerNode(int index, int version) {
        int current = 0;
        Node<E> inner = getHeadInnerNode(version);
        while (current != index && inner != null && inner.hasNext()) {
            current++;
            inner = inner.getNext().getInnerNode(version);
        }
        if (current != index)
            throw new IllegalArgumentException("Index out of bound");
        return inner;
    }

    private List<TwoFatNode<E>> getHeads() {
        return heads;
    }

    private List<TwoFatNode<E>> getTails() {
        return tails;
    }

    private void initialize(E value) {
        final TwoFatNode<E> fatNode = new TwoFatNode<>();
        final Node<E> node = new Node<>(value, currentVersion, fatNode);
        fatNode.setFirst(node);
        heads.add(fatNode);
        tails.add(fatNode);
    }

    private void innerEdit(int index, E newValue) {
        if (isEmpty())
            throw new IndexOutOfBoundsException("List is empty");

        Node<E> inner = getInnerNode(index, previousVersion);
        TwoFatNode<E> brother = (TwoFatNode<E>) inner.getBrother();
        TwoFatNode<E> next = (TwoFatNode<E>) inner.getNext();
        TwoFatNode<E> previous = (TwoFatNode<E>) inner.getPrevious();

        if (brother.getSecond() != null) {
            TwoFatNode<E> fatNode = new TwoFatNode<>();
            Node<E> copyNode = new Node<>(newValue, currentVersion, fatNode);
            fatNode.setFirst(copyNode);

            TwoFatNode<E> right = handleTail(fatNode, next);
            TwoFatNode<E> left = handleHead(fatNode, previous);

            copyNode.setPrevious(left);
            copyNode.setNext(right);
        } else {
            Node<E> copyNode = new Node<>(newValue, currentVersion, brother);
            brother.setSecond(copyNode);

            TwoFatNode<E> right = handleTail(brother, next);
            TwoFatNode<E> left = handleHead(brother, previous);

            copyNode.setPrevious(left);
            copyNode.setNext(right);
        }
    }

    private void addHead(E value) {
        final TwoFatNode<E> fatNode = new TwoFatNode<>();
        final Node<E> node = new Node<>(value, currentVersion, fatNode);
        fatNode.setFirst(node);
        heads.add(fatNode);

        TwoFatNode<E> rightFatNode = handleTail(fatNode, heads.get(previousVersion));
        node.setNext(rightFatNode);
    }

    private void addTail(E value) {
        TwoFatNode<E> fatNode = new TwoFatNode<>();
        Node<E> node = new Node<>(value, currentVersion, fatNode);
        fatNode.setFirst(node);
        tails.add(fatNode);

        TwoFatNode<E> leftFatNode = handleHead(fatNode, tails.get(previousVersion));
        node.setPrevious(leftFatNode);
    }

    private void innerAdd(int index, E value) {
        if (isEmpty()) {
            initialize(value);
            return;
        }

        if (index == 0) {
            addHead(value);
            return;
        }

        Node<E> inner = getInnerNode(index - 1, previousVersion);
        TwoFatNode<E> brother = (TwoFatNode<E>) inner.getBrother();
        TwoFatNode<E> next = (TwoFatNode<E>) inner.getNext();

        if (!inner.hasNext()) {
            addTail(value);
            return;
        }

        TwoFatNode<E> newFatNode = new TwoFatNode<>();
        Node<E> newNode = new Node<>(value, currentVersion, newFatNode);
        TwoFatNode<E> newBrother = (TwoFatNode<E>) newNode.getBrother();
        newFatNode.setFirst(newNode);

        TwoFatNode<E> right = handleTail(newBrother, next);
        TwoFatNode<E> left = handleHead(newBrother, brother);

        newNode.setPrevious(left);
        newNode.setNext(right);
    }

    private void innerRemove(int index) {
        if (isEmpty()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        throw new UnsupportedOperationException();
    }

    private TwoFatNode<E> handleTail(TwoFatNode<E> previous, TwoFatNode<E> current) {
        if (current == null) {
            tails.add(previous);
            return null;
        }

        if (current.getSecond() != null) {
            TwoFatNode<E> newFatNode = new TwoFatNode<>();
            Node<E> copyNode = new Node<>(current.getSecond().getValue(), currentVersion, newFatNode);
            newFatNode.setFirst(copyNode);

            copyNode.setPrevious(previous);
            TwoFatNode<E> next = (TwoFatNode<E>) current.getSecond().
                    getNext();
            TwoFatNode<E> right = handleTail(newFatNode, next);

            copyNode.setNext(right);
            return newFatNode;
        } else {
            Node<E> copyNode = new Node<>(current.getFirst().
                    getValue(),
                    currentVersion, current);

            copyNode.setPrevious(previous);
            if (current.getFirst().getNext() == null)
                tails.add(current);

            copyNode.setNext(current.getFirst().getNext());
            current.setSecond(copyNode);
            return current;
        }
    }

    private TwoFatNode<E> handleHead(TwoFatNode<E> next, TwoFatNode<E> current) {
        if (current == null) {
            heads.add(next);
            return null;
        }

        if (current.getSecond() != null) {
            TwoFatNode<E> newFatNode = new TwoFatNode<>();
            Node<E> copyNode = new Node<E>(current.getSecond().getValue(), currentVersion, newFatNode);
            newFatNode.setFirst(copyNode);
            copyNode.setNext(next);

            TwoFatNode<E> previous = (TwoFatNode<E>) current.getSecond().
                    getPrevious();

            TwoFatNode<E> left = handleHead(newFatNode, previous);
            copyNode.setPrevious(left);
            return newFatNode;
        } else {
            Node<E> copyNode = new Node<>(current.getFirst().getValue(), currentVersion, current);
            if (current.getFirst().getPrevious() == null)
                heads.add(current);

            copyNode.setNext(next);
            TwoFatNode<E> previous = (TwoFatNode<E>) current.getFirst().
                    getPrevious();

            copyNode.setPrevious(previous);
            current.setSecond(copyNode);
            return current;
        }
    }


}
