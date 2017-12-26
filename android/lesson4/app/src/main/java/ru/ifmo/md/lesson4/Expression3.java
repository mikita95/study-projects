package ru.ifmo.md.lesson4;
public interface Expression3<E extends MyType<E>> {
    E evaluate(E x, E y, E z) throws Throwable;
}