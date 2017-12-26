package ru.ifmo.ctddev.markovnikov.helloudp;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

/**
 * Realization of UDP Server that gets messages and answers Hello to them
 */
public class HelloUDPServer implements HelloServer {

    private Thread[] t;
    private DatagramSocket socketToReceive;

    /**
     * Start server
     * @param port port to send
     * @param numberOfThreads amount of threads to use
     */
    @Override
    public void start(int port, int numberOfThreads) {
        t = new Thread[numberOfThreads];
        try {
            socketToReceive = new DatagramSocket(port);
            int TIME_OUT = 50;
            socketToReceive.setSoTimeout(TIME_OUT);
            for (int i = 0; i < numberOfThreads; i++) {
                t[i] = new Thread(new Answer(socketToReceive));
                t[i].start();
            }
        } catch (IOException e) {
            System.out.println("Can't create socket");
        }
    }

    /**
     * Stops server
     */
    @Override
    public void close() {
        for (Thread thread : t) {
            thread.interrupt();
        }
        for (Thread thread : t) {
            try {
                thread.join();
            } catch (InterruptedException ignore) {
            }
        }
        if (socketToReceive != null) {
            socketToReceive.close();
        }
    }

    private class Answer implements Runnable {

        DatagramSocket socketToReceive;

        public Answer(DatagramSocket socket1) {
            this.socketToReceive = socket1;
        }

        @Override
        public void run() {
            int BUFFER_SIZE = 2048;
            byte[] inputBuffer = new byte[BUFFER_SIZE];
            try {
                inputBuffer = new byte[socketToReceive.getReceiveBufferSize()];
            } catch (SocketException e) {
                e.printStackTrace();
            }
            DatagramPacket packet = new DatagramPacket(inputBuffer, inputBuffer.length);
            try (DatagramSocket socketToSend = new DatagramSocket()) {
                while (!Thread.interrupted()) {
                    try {
                        socketToReceive.receive(packet);
                    } catch (SocketTimeoutException e) {
                        continue;
                    }
                    String s = "Hello, ";


                    byte[] answer1 = s.getBytes(Charset.forName("UTF-8"));
                    byte[] answer = new byte[answer1.length + packet.getLength()];
                    for (int i = 0; i < answer1.length; i++)
                        answer[i] = answer1[i];

                    for (int i = 0; i < packet.getLength(); i++) {
                        answer[s.length() + i] = packet.getData()[i];
                    }
                    packet.setData(answer);
                    packet.setLength(answer.length);
                    socketToSend.send(packet);
                }
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Takes port, threads amount and starts server
     * @param arg arguments: port and threads amount
     */
    public static void main(String[] arg) {
        int port = Integer.valueOf(arg[0]);
        int numberOfThreads = Integer.valueOf(arg[1]);
        new HelloUDPServer().start(port, numberOfThreads);
    }
}