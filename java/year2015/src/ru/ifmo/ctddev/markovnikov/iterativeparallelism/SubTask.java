package ru.ifmo.ctddev.markovnikov.iterativeparallelism;

import java.util.List;
import java.util.function.BiFunction;

/**
 * This class executes task on subList
 * @author nikita markovnikov
 * @param <E> Type of list elements
 * @param <T> Type of result elements
 */

class SubTask<E, T> implements Runnable {
    private final Result<T> result;
    private T value;
    private final List<? extends E> list;
    private final BiFunction<E, T, T> biFunction;
    private final int threadNumber;

    /**
     * Constructs new instance of SubTask to be passed to thread.
     * Updates a result after the task is finished.
     *
     * @param list         list to be reduced.
     * @param biFunction   BiFunction to be used for list reduction.
     * @param result       Result instance to be updated, after this task is finished.
     * @param initValue Initial value to be stored in value, usually identity element for biFunction.
     * @param threads The number of thread this task is started in, used by modify method to determine the result order.
     */
    public SubTask(List<? extends E> list, BiFunction<E, T, T> biFunction, Result<T> result, T initValue, int threads) {
        this.list = list;
        this.value = initValue;
        this.biFunction = biFunction;
        this.result = result;
        this.threadNumber = threads;
    }

    /**
     * Performs the reduction over list with a biFunction.
     */
    @Override
    public void run() {
        for (E e : list) {
            value = biFunction.apply(e, value);
        }
        result.modify(value, threadNumber);
    }
}