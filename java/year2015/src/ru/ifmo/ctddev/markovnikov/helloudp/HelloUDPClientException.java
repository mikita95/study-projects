package ru.ifmo.ctddev.markovnikov.helloudp;

public class HelloUDPClientException extends Exception {
    public HelloUDPClientException(String message) {
        super(message);
    }

    public HelloUDPClientException(String message, Throwable cause) {
        super(message, cause);
    }
}