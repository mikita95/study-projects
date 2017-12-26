public class Const<E extends MyType<E>> implements Expression3<E> {
    private E number;

    public Const(E element) {
        number = element;
    }

    public E evaluate(E x, E y, E z) {

        return number;

    }

}