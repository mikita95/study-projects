package ru.ifmo.md.lesson4;
public class Multiply<E extends MyType<E>> extends Binary<E> {

    public Multiply(Expression3<E> x, Expression3<E> y) {
        super(x, y);
    }

    protected E run(E x, E y) throws Throwable {
        try {
            return x.mul(y);
        } catch (Throwable e) {
            throw new DivisionByZero();
        }
    }


}