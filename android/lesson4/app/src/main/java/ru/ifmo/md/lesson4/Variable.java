package ru.ifmo.md.lesson4;
public class Variable<E extends MyType<E>> implements Expression3<E> {


    private int flag;

    public Variable(String name) {

        if (name.equals("x")) {
            flag = 1;
        } else if (name.equals("y")) {
            flag = 2;
        } else if (name.equals("z")) {
            flag = 3;
        }
    }


    public E evaluate(E x, E y, E z) {

        if (flag == 1)
            return x;

        if (flag == 2)
            return y;

        if (flag == 3)
            return z;

        return null;
    }

}