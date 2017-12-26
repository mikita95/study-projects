package ru.ifmo.ctddev.markovnikov.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Wraps a {@link ru.ifmo.ctddev.markovnikov.bank.LocalPerson} to make it changeable from other JVMs with RMI.
 * Thread-safe.
 */
public class RemotePerson extends UnicastRemoteObject implements Person {
    private Map<LocalAccount, RemoteAccount> map = new HashMap<>();
    private LocalPerson person;
    /**
     * Creates a remote person, wrapping a {@link ru.ifmo.ctddev.markovnikov.bank.LocalPerson}.
     *
     * @param person local person to be wrapped
     */
    public RemotePerson(LocalPerson person) throws RemoteException {
        super();
        this.person = person;
    }

    /**
     * Return persons name.
     *
     * @return name.
     */
    public String getName() {
        return person.getName();
    }

    /**
     * Returns persons surname.
     *
     * @return surname.
     */
    public String getSurname() {
        return person.getSurname();
    }

    /**
     * Returns persons passport number.
     *
     * @return passport number.
     */
    public String getPassportNumber() {
        return person.getPassportNumber();
    }

    /**
     * Returns persons account with specified identifier. Account will be passed with RMI.
     * Account presence should be checked first with {@code accountExists} method.
     *
     * @param id account id.
     * @return requested account.
     * @throws java.rmi.RemoteException
     */
    public synchronized Account getAccount(String id) throws RemoteException {
        LocalAccount localAccount = person.getAccount(id);
        RemoteAccount remoteAccount;
        if (!map.containsKey(localAccount)) {
            remoteAccount = new RemoteAccount(localAccount);
            map.put(localAccount, remoteAccount);
        } else {
            remoteAccount = map.get(localAccount);

        }
        return remoteAccount;
    }

    /**
     * Creates an account with specified identifier and zero amount of money. If account already exists
     * it will be recreated.
     *
     * @param id account id.
     */
    public synchronized void createAccount(String id) {
        if (!person.accountExists(id)) person.createAccount(id);
    }

    /**
     * Checks whatever a person has an account with specified identifier.
     *
     * @param id account id.
     * @return true if account is present, false otherwise.
     */
    public synchronized boolean accountExists(String id) {
        if (person.accountExists(id)) return true;
        else return false;
    }
}
