package ru.ifmo.md.lesson4;
public class Add<E extends MyType<E>> extends Binary<E> {

    public Add(Expression3<E> x, Expression3<E> y) {
        super(x, y);
    }

    protected E run(E x, E y) throws Throwable {
        try {
            return x.add(y);
        } catch (Throwable e) {
            throw new DivisionByZero();
        }

    }

}