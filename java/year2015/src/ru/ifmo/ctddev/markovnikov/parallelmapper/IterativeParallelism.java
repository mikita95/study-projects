package ru.ifmo.ctddev.markovnikov.parallelmapper;

import info.kgeorgiy.java.advanced.concurrent.ScalarIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author nikita markovnikov
 * This class executes functions like minimum, maximum and cheching predicates on the list parallel
 */
public class IterativeParallelism implements ScalarIP {
    private ParallelMapper parallelMapper;
    /**
     * Creates an instance of IterativeParallelism
     */
    public IterativeParallelism() {
    }

    /**
     * Creates an instance of IterativeParallelism that uses ParallelMapper anb the work is executing parallel.
     * @param parallelMapper given ParallelMapper that gives thread pool.
     */
    public IterativeParallelism(ParallelMapper parallelMapper) {
        this.parallelMapper = parallelMapper;
    }


    /**
     * Divides list into parts
     * @param threads number of parts
     * @param list list to be divided
     * @param <T> type of elements in the list
     * @return list of subLists
     */
    private <T> List<List<? extends T>> cut(int threads, List<? extends T> list) {
        if (threads < 1) {
            threads = 1;
        } else if (threads > list.size()) {
            threads = list.size();
        }
        int chunkSize = (int) Math.ceil(1d * list.size() / threads);
        int left = 0;
        int right = Math.min(left + chunkSize, list.size());
        List<List<? extends T>> parts = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            parts.add(list.subList(left, right));
            chunkSize = (int) Math.ceil(1d * (list.size() - right) / (threads - i - 1));
            left = right;
            right = Math.min(list.size(), right + chunkSize);
        }
        return parts;
    }

    /**
     * This class execute works and saves result
     * @param <R> type of result
     */
    private static class SubListWorker<T, R> implements Runnable {

        private List<? extends T> subList;
        private Function<List<? extends T>, R> function;
        private R result;

        private SubListWorker(List<? extends T> subList, Function<List<? extends T>, R> function) {
            this.subList = subList;
            this.function = function;
        }

        public R getResult() {
            return result;
        }

        @Override
        public void run() {
            result = function.apply(subList);
        }
    }

    /**
     * Executes work on the list parallel in fixed number of threads
     * @param threads number of threads in which work must be done.
     * @param list list on which work must be done
     * @param function function must be done on the list
     * @param merger function that tells how to merge results of the parts
     * @param <T> type of elements
     * @param <R> type of the result
     * @return result of the function
     * @throws InterruptedException if thread was interrupted during the work
     * @see java.util.function.Function
     * @see java.util.List
     */
    private <T, R> R doParallel(int threads, List<? extends T> list, Function<List<? extends T>, R> function, Function<List<R>, R> merger) throws InterruptedException {
        List<List<? extends T>> subLists = cut(threads, list);
        if (parallelMapper != null) {
            List<R> results = parallelMapper.map(function, subLists);
            return merger.apply(results);
        } else {
            List<Thread> threadList = new ArrayList<>();
            List<SubListWorker<T, R>> workers = new ArrayList<>();
            for (List<? extends T> part : subLists) {
                SubListWorker<T, R> subListWorker = new SubListWorker<>(part, function);
                workers.add(subListWorker);
                Thread thread = new Thread(subListWorker);
                threadList.add(thread);
                thread.start();
            }
            for (Thread thread : threadList) {
                thread.join();
            }
            return merger.apply(workers.stream().map(SubListWorker::getResult).collect(Collectors.toList()));
        }
    }

    /**
     * Returns the minimum element of the list according to the comparator.
     * Returns null if the list is empty.
     *
     * @param threads    Number of threads to be used for list processing.
     * @param list       A list to be processed.
     * @param comparator comparator to be used to compare list elements.
     * @param <T>        The type of elements in the list.
     * @return The smallest element in the list or null if list is empty.
     * @throws java.lang.InterruptedException If any thread was interrupted during it's task.
     */
    @Override
    public <T> T minimum(int threads, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        return doParallel(threads, list, job -> Collections.min(job, comparator), results -> Collections.min(results, comparator));
    }

    /**
     * Returns the maximum element of the list according to the comparator.
     * Returns null if the list is empty.
     *
     * @param threads    Number of threads to be used for list processing.
     * @param list       A list to be processed
     * @param comparator comparator to be used to compare list elements.
     * @param <T>        The type of elements in the list.
     * @return The smallest element in the list or null if list is empty.
     * @throws java.lang.InterruptedException If any thread was interrupted during it's task.
     */
    @Override
    public <T> T maximum(int threads, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        return doParallel(threads, list, job -> Collections.max(job, comparator), results -> Collections.max(results, comparator));
    }

    /**
     * Returns whether all elements of the given list satisfy
     * provided {@link java.util.function.Predicate}. Returns true if the list is empty.
     *
     * @param threads Number of threads to be used for list processing.
     * @param list The list to be tested.
     * @param predicate A predicate to test elements of this list against.
     * @param <T> The type of elements in the list.
     * @return True if all elements in the list satisfy the predicate or the list is empty, false otherwise
     * @throws java.lang.InterruptedException If any thread was interrupted during it's task.
     */
    @Override
    public <T> boolean all(int threads, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return doParallel(threads, list, job -> job.stream().allMatch(predicate), results -> results.stream().allMatch(Predicate.isEqual(true)));
    }

    /**
     * Returns whether any elements of the given list satisfy
     * the provided predicate. Returns false if the list is empty.
     *
     * @param threads Number of threads to be used for list processing.
     * @param list The list to be tested.
     * @param predicate  A predicate to test elements of this list against.
     * @param <T> The type of elements in the list.
     * @return True if any elements of the list satisfy the predicate,false otherwise
     * @throws java.lang.InterruptedException If any thread was interrupted during it's task.
     */
    @Override
    public <T> boolean any(int threads, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return doParallel(threads, list,
                job -> job.stream().anyMatch(predicate), results -> results.stream().anyMatch(Predicate.isEqual(true)));
    }



}