package ru.ifmo.ctddev.markovnikov.helloudp;

public class HelloUDPServerException extends Exception{
    public HelloUDPServerException(String message) {
        super(message);
    }

    public HelloUDPServerException(String message, Throwable cause) {
        super(message, cause);
    }
}