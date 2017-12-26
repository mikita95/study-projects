package utils;

@SuppressWarnings("UnusedDeclaration")
public class SemanticsPool {
    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    private static class Node {
        private Node next;
        private String data;

        public Node(String s) {
            data = s;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    private Node head, tail;

    public void add(String string) {
        if (head == null) {
            head = new Node(string);
            tail = head;
        } else {
            tail.next = new Node(string);
            tail = tail.next;
        }
    }

    public String pop() {
        String result = head.data;
        head = head.next;
        return result;
    }

    public String peek() {
        return head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

}