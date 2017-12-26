package ru.ifmo.md.lesson4;
public class Divide<E extends MyType<E>> extends Binary<E> {

    public Divide(Expression3<E> x, Expression3<E> y) {
        super(x, y);
    }

    protected E run(E x, E y) throws Throwable {

        try {
            return x.divide(y);
        } catch (Throwable e) {
            throw new DivisionByZero();
        }
    }


}