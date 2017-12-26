package ru.ifmo.md.lesson4;
public class Subtract<E extends MyType<E>> extends Binary<E> {

    public Subtract(Expression3<E> x, Expression3<E> y) {
        super(x, y);
    }

    protected E run(E x, E y) throws Throwable {

        try {
            return x.subtract(y);
        } catch (Throwable e) {
            throw new DivisionByZero();
        }
    }
}