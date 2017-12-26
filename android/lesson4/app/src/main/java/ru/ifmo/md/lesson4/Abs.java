package ru.ifmo.md.lesson4;
public class Abs<E extends MyType<E>> extends Unary<E> {

    public Abs(Expression3<E> value) {
        super(value);
    }

    protected E run(E x) throws Throwable {
        try {
            return x.abs();
        } catch (Throwable e) {
            throw new DivisionByZero();
        }
    }
}