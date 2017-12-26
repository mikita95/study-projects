package ru.ifmo.md.lesson4;
public class Not<E extends MyType<E>> extends Unary<E> {

    public Not(Expression3<E> value) {
        super(value);
    }

    protected E run(E x) throws Throwable {

        try {
            return x.not();
        } catch (Throwable e) {
            throw new DivisionByZero();
        }
    }
}