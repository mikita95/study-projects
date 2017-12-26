package ru.ifmo.ctddev.markovnikov.bank;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class is used to serve remote requests to {@link ru.ifmo.ctddev.markovnikov.bank.Bank}. Stores it's
 * data in a {@link java.util.Map}.
 */
public class BankImpl implements Bank, Serializable {
    private Map<String, LocalPerson> localPersonMap = new HashMap<>();
    private Map<LocalPerson, RemotePerson> remotePersonMap = new HashMap<>();

    /**
     * Tries to find an appropriate record about requested person and return a local
     * version of it to a client.
     *
     * @param passportNumber passport number of requested person
     * @return requested person.
     */
    public synchronized LocalPerson getLocalPerson(String passportNumber) {
        return localPersonMap.get(passportNumber);
    }

    /**
     * Tries to find an appropriate record about requested person and return a remote
     * version of it to a client.
     *
     * @param passportNumber passport number of requested person.
     * @return requested person
     * @throws java.rmi.RemoteException
     */
    public synchronized RemotePerson getRemotePerson(String passportNumber) throws RemoteException {
        LocalPerson localPerson = localPersonMap.get(passportNumber);
        RemotePerson remotePerson;
        if (!remotePersonMap.containsKey(localPerson)) {
            remotePerson = new RemotePerson(localPerson);
            remotePersonMap.put(localPerson, remotePerson);
        } else {
            remotePerson = remotePersonMap.get(localPerson);
        }
        return remotePerson;
    }

    /**
     * Creates a record about a person accordingly to a provided data.
     *
     * @param firstName      first name of person.
     * @param lastName       last name of a person.
     * @param passportNumber passport number of a person.
     */
    public synchronized void createPerson(String firstName, String lastName, String passportNumber) {
        if (!localPersonMap.containsKey(passportNumber))
            localPersonMap.put(passportNumber, new LocalPerson(firstName, lastName, passportNumber));
    }

    /**
     * Check a presence of a person with specified passport number.
     *
     * @param passportNumber passport number of a person.
     * @return true if it is present, false otherwise.
     */
    public synchronized boolean personExists(String passportNumber) {
        if (localPersonMap.containsKey(passportNumber)) return true;
        else return false;
    }
}
