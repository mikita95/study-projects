package ru.ifmo.ctddev.markovnikov.bank;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Publishes a remote bank, which will serve clients requests.
 */
public class Server {
    private final static int PORT = 8888;

    /**
     * Runs the server.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Bank bank = new BankImpl();
        try {
            Naming.rebind("rmi://localhost/bank", UnicastRemoteObject.exportObject(bank, PORT));
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
