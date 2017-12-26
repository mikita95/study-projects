package ru.ifmo.ctddev.markovnikov.helloudp;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Realization of UDP Client that sends messages to server and gets Hello answer
 */
public class HelloUDPClient implements HelloClient {

    private Semaphore semaphore;
    private ExecutorService threadPool;
    private AtomicInteger stopper;

    private class Sender implements Runnable {
        private int requests;
        private String prefix;
        private int threadId;
        private InetAddress address;
        private int port;
        private int threads;

        Sender(int requests, String prefix, int threadId, InetAddress address, int port, int threads) {
            this.requests = requests;
            this.prefix = prefix;
            this.threadId = threadId;
            this.address = address;
            this.port = port;
            this.threads = threads;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                try (final DatagramSocket socket = new DatagramSocket()) {
                    socket.setSoTimeout(100);
                    byte[] inputBuffer = new byte[socket.getReceiveBufferSize()];
                    int current = 0;
                    while (current != requests) {
                        String message = (prefix + threadId + "_" + current);
                        byte[] msg = message.getBytes();
                        System.out.println(message);
                        socket.send(new DatagramPacket(msg, msg.length, address, port));
                        DatagramPacket reply = new DatagramPacket(inputBuffer, inputBuffer.length);
                        try {
                            socket.receive(reply);
                            byte[] data = reply.getData();
                            String response = new String(data, 0, reply.getLength());
                            String[] res = response.split("_");
                            if (!response.equals("Hello, " + message) || Integer.parseInt(res[res.length - 1]) >= requests) {
                                throw new NumberFormatException();
                            }
                            System.out.println(response);
                            current++;
                        } catch (SocketTimeoutException | NumberFormatException ignored) {
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException ignored) {
                }
                semaphore.release();
                if (semaphore.availablePermits() == threads) {
                    semaphore.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stopper.decrementAndGet();
            if (stopper.get() == 0) {
                threadPool.shutdownNow();
            }
        }

    }

    /**
     * Start client's job to send messages to server and getting "Hello"
     * @param host host to connect
     * @param port port that used to connect
     * @param prefix prefix of sending messages
     * @param requests amount of sending messages
     * @param threads amount of threads
     */
    @Override
    public void start(String host, int port, String prefix, int requests, int threads) {
        threadPool = Executors.newFixedThreadPool(threads);
        stopper = new AtomicInteger(threads);
        try {
            semaphore = new Semaphore(threads + 1);
            semaphore.acquire();
            final InetAddress address = InetAddress.getByName(host);
            for (int i = 0; i < threads; i++) {
                threadPool.submit(new Sender(requests, prefix, i, address, port, threads));
            }
            Thread.sleep(100);
            semaphore.acquire(threads + 1);
        } catch (UnknownHostException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}