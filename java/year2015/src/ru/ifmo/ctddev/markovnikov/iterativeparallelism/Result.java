package ru.ifmo.ctddev.markovnikov.iterativeparallelism;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * This class is used as a storage to be accessed from multiple threads by IterativeParallelism,
 * it stores the result of any reduce operation.
 *
 * @author nikita markovnikov
 * @see ru.ifmo.ctddev.markovnikov.iterativeparallelism.IterativeParallelism
 */
class Result<T> {
    private T value;
    private List<T> results;
    private BiFunction<T, T, T> biFunction;

    /**
     * Constructs new instance of Result initialized to initValue.
     *
     * @param initValue The initial initValue, usually the identity element.
     * @param size The amount of chunks initial list was divided into.
     * @param biFunction BiFunction used to combine subList results.
     */
    public Result(T initValue, int size, BiFunction<T, T, T> biFunction) {
        this.value = initValue;
        this.results = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            results.add(null);
        }
        this.biFunction = biFunction;
    }

    /**
     * Returns combined result of subList reductions.
     *
     * @return The result of combining subList reductions.
     */
    public T getValue() {
        for (T result : results) {
            value = biFunction.apply(value, result);
        }
        return value;
    }

    /**
     * Updates the value according to the result of finished thread.
     *
     * @param t The result of thread's task.
     * @param threads The number of thread that finished it's task.
     */
    public synchronized void modify(T t, int threads) {
        results.set(threads, t);
    }
}