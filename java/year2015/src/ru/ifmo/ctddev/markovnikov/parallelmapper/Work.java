package ru.ifmo.ctddev.markovnikov.parallelmapper;

import java.util.function.Function;

class Work<T, R> {

    private T arg;
    private Function<? super T, ? extends R> function;
    private R result;

    Work(T arg, Function<? super T, ? extends R> function) {
        this.arg = arg;
        this.function = function;
    }

    public synchronized void process() {
        result = function.apply(arg);
        notifyAll();
    }

    public synchronized R getResult() throws InterruptedException {
        while (result == null) {
            wait();
        }
        return result;
    }

}
