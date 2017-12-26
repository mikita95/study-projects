package ru.ifmo.md.lesson4;
public class Plus<E extends MyType<E>> extends Unary<E> {

    public Plus(Expression3<E> value) {
        super(value);
    }

    protected E run(E x) throws Throwable {

        try {
            return x.plus();
        } catch (Throwable e) {
            throw new DivisionByZero();
        }
    }
}