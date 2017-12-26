package ru.ifmo.md.lesson4;
public abstract class Unary<E extends MyType<E>> implements Expression3<E> {
    private Expression3<E> a;

    public Unary(Expression3<E> a) {
        this.a = a;
    }

    public E  evaluate (E x, E y, E z) throws Throwable {
        E c = a.evaluate(x, y, z);
        return run(c);
    }

    protected abstract E  run(E c) throws Throwable;
}
