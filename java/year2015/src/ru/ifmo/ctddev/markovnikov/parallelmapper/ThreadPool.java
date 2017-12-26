package ru.ifmo.ctddev.markovnikov.parallelmapper;

class ThreadPool implements Runnable {

    private final WorkQueue workQueue;

    ThreadPool(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                workQueue.next().process();
            }
        } catch (InterruptedException e) {
            //do nothing
        }
    }
}
