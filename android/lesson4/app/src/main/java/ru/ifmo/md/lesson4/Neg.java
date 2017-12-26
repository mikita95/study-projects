package ru.ifmo.md.lesson4;
public class Neg<E extends MyType<E>> extends Unary<E> {

    public Neg(Expression3<E> value) {
        super(value);
    }

    protected E run(E x) throws Throwable {

        try {
            return x.neg();
        } catch (Throwable e) {
            throw new DivisionByZero();
        }
    }
}