package ru.ifmo.ctddev.markovnikov.parallelmapper;

import java.util.LinkedList;
import java.util.Queue;

class WorkQueue {

    private final Queue<Work<?, ?>> workQueue;

    WorkQueue() {
        workQueue = new LinkedList<>();
    }

    public synchronized void add(Work<?, ?> work) {
        workQueue.add(work);
        notifyAll();
    }

    public synchronized Work<?, ?> next() throws InterruptedException {
        while (workQueue.isEmpty()) {
            wait();
        }
        return workQueue.poll();
    }

}
