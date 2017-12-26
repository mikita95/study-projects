package ru.ifmo.ctddev.markovnikov.parallelmapper;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author nikita markovnikov
 * This class devides some work on fixed number of threads and execute it parallel.
 */

public class ParallelMapperImpl implements ParallelMapper {

    private List<Thread> threadList;
    private WorkQueue workQueue;

    /**
     * Creates an instance of {@code ParallelMapperImpl} with specified number of {@code threads}.
     * @param threads number of threads that you want to use
     */
    public ParallelMapperImpl(int threads) {
        threadList = new ArrayList<>();
        workQueue = new WorkQueue();
        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(new ThreadPool(workQueue));
            threadList.add(thread);
            thread.start();
        }
    }

    /**
     * Executes function on every element of given list parallel.
     * @param function function that you want to apply on every element of the list
     * @param list list that you want to map
     * @param <T> type of elements in the list
     * @param <R> type of the returning result of the function
     * @return list of elements that were mapped
     * @throws InterruptedException if close was called before execution were finished
     * @see java.util.function.Function
     * @see java.util.List
     */
    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> function, List<? extends T> list) throws InterruptedException {
        List<Work<? super T, ? extends R>> workList = new ArrayList<>();
        for (T arg : list) {
            Work<? super T, ? extends R> work = new Work<>(arg, function);
            workQueue.add(work);
            workList.add(work);
        }
        List<R> result = new ArrayList<>();
        for (Work<? super T, ? extends R> work : workList) {
            result.add(work.getResult());
        }
        return result;
    }

    /**
     * Closes all threads that are active
     * @throws InterruptedException if one threads cannot be closed
     */
    @Override
    public void close() throws InterruptedException {
        threadList.forEach(java.lang.Thread::interrupt);
    }
}
