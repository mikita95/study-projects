package ru.ifmo.md.lesson4;
public abstract class Binary<E extends MyType<E>> implements Expression3<E> {
    private Expression3<E> a;
    private Expression3<E> b;
	
    public Binary (Expression3<E> a, Expression3<E> b) {

        this.a = a;
        this.b = b;

    }

    public E evaluate (E x, E y, E z) throws Throwable {
        E c1 = a.evaluate(x, y, z);

        E c2 = b.evaluate(x, y, z);
        return run(c1, c2);
    }


    protected abstract E run(E c1, E c2) throws Throwable;
}