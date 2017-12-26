package ru.ifmo.md.lesson4;
public class Mod<E extends MyType<E>> extends Binary<E> {


    public Mod(Expression3<E> x, Expression3<E> y) {

        super(x, y);

    }


    protected E run(E x, E y) throws Throwable {

        try {
            return x.mod(y);
        } catch (Throwable e) {
            throw new DivisionByZero();
        }
    }

}