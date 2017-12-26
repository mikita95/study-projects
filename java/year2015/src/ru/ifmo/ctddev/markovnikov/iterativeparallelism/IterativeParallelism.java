package ru.ifmo.ctddev.markovnikov.iterativeparallelism;

import info.kgeorgiy.java.advanced.concurrent.ScalarIP;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author nikita markovnikov
 * This class executes functions like minimum, maximum and cheching predicates on the list parallel
 */
public class IterativeParallelism implements ScalarIP {


    /**
     * Returns the minimum element of the list according to the comparator.
     * Returns null if the list is empty.
     *
     * @param threads    Number of threads to be used for list processing.
     * @param list       A list to be processed.
     * @param comparator comparator to be used to compare list elements.
     * @param <E>        The type of elements in the list.
     * @return The smallest element in the list or null if list is empty.
     * @throws java.lang.InterruptedException If any thread was interrupted during it's task.
     */
    public <E> E minimum(int threads, List<? extends E> list, Comparator<? super E> comparator) throws InterruptedException {
        if (list.isEmpty()) {
            return null;
        }
        BiFunction<E, E, E> biFunction = (x, y) -> comparator.compare(x, y) <= 0 ? x : y;
        return reduce(threads, biFunction, list, biFunction, () -> list.get(0));
    }

    /**
     * Returns the maximum element of the list according to the comparator.
     * Returns null if the list is empty.
     *
     * @param threads    Number of threads to be used for list processing.
     * @param list       A list to be processed
     * @param comparator comparator to be used to compare list elements.
     * @param <E>        The type of elements in the list.
     * @return The smallest element in the list or null if list is empty.
     * @throws java.lang.InterruptedException If any thread was interrupted during it's task.
     */
    public <E> E maximum(int threads, List<? extends E> list, Comparator<? super E> comparator) throws InterruptedException {
        if (list.isEmpty()) {
            return null;
        }
        BiFunction<E, E, E> biFunction = (x, y) -> comparator.compare(x, y) >= 0 ? x : y;
        return reduce(threads, biFunction, list, biFunction, () -> list.get(0));
    }

    /**
     * Returns whether all elements of the given list satisfy
     * provided {@link java.util.function.Predicate}. Returns true if the list is empty.
     *
     * @param threads Number of threads to be used for list processing.
     * @param list    The list to be tested.
     * @param p       A predicate to test elements of this list against.
     * @param <E>     The type of elements in the list.
     * @return True if all elements in the list satisfy the predicate or the list is empty, false otherwise
     * @throws java.lang.InterruptedException If any thread was interrupted during it's task.
     */
    public <E> boolean all(int threads, List<? extends E> list, Predicate<? super E> p) throws InterruptedException {
        BiFunction<E, Boolean, Boolean> biFunction = (x, y) -> p.test(x) && y;
        return reduce(threads, biFunction, list, (x, y) -> x && y, () -> true);
    }

    /**
     * Returns whether any elements of the given list satisfy
     * the provided predicate. Returns false if the list is empty.
     *
     * @param threads Number of threads to be used for list processing.
     * @param list    The list to be tested.
     * @param p       A predicate to test elements of this list against.
     * @param <E>     The type of elements in the list.
     * @return True if any elements of the list satisfy the predicate,false otherwise
     * @throws java.lang.InterruptedException If any thread was interrupted during it's task.
     */
    public <E> boolean any(int threads, List<? extends E> list, Predicate<? super E> p) throws InterruptedException {
        BiFunction<? super E, Boolean, Boolean> biFunction = (x, y) -> p.test(x) || y;
        return reduce(threads, biFunction, list, (x, y) -> x || y, () -> false);
    }

    private static <E, T> T reduce(int threads, BiFunction<E, T, T> operation, List<? extends E> list,
                                   BiFunction<T, T, T> join, Supplier<T> supplier) throws InterruptedException {
        int size = (int) Math.ceil(list.size() / threads);
        Result<T> result = new Result<>(supplier.get(), threads, join);
        int left = 0;
        int right = Math.min(left + size, list.size());
        ArrayList<Thread> threadArrayList = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            Runnable task = new SubTask<>(list.subList(left, right), operation, result, supplier.get(), i);
            Thread thread = new Thread(task);
            threadArrayList.add(thread);
            thread.start();
            size = (int) Math.ceil((list.size() - right) / (double) (threads - i - 1));
            left = right;
            right = Math.min(list.size(), right + size);
        }
        for (Thread thread : threadArrayList) {
            thread.join();
        }
        return result.getValue();
    }


}

