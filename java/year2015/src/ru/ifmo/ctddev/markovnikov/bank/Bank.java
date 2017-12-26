package ru.ifmo.ctddev.markovnikov.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Bank is an interface to a remote data structure, providing an access to information about
 * persons, registered in that bank.
 */
public interface Bank extends Remote {
    /**
     * Returns local version of {@link ru.ifmo.ctddev.markovnikov.bank.Person} by it's passport number.
     * All changes in it, including changes in it's accounts won't affect anything on the server.
     *
     * @param passportNumber passport number of requested person
     * @return requested person or null if person not present.
     * @throws java.rmi.RemoteException
     */
    public Person getLocalPerson(String passportNumber) throws RemoteException;

    /**
     * Returns remote version of {@link ru.ifmo.ctddev.markovnikov.bank.Person} by it's passport number.
     * All it's methods will be executed on server and it's data will be changed. Note that it
     * doesn't return null if person not present, you should check it's presence with {@code personExists}
     * method.
     *
     * @param passportNumber passport number of requested person.
     * @return stub ro a requested person, you can work with.
     * @throws java.rmi.RemoteException
     */
    public Person getRemotePerson(String passportNumber) throws RemoteException;

    /**
     * Creates a record about a specified person, according the provided data. If a person with the
     * same passport number already exists, it's data will be changed.
     *
     * @param firstName      first name of person.
     * @param lastName       last name of a person.
     * @param passportNumber passport number of a person.
     * @throws java.rmi.RemoteException
     */
    public void createPerson(String firstName, String lastName, String passportNumber) throws RemoteException;

    /**
     * Checks the presence of a person with specified passport number.
     *
     * @param passportNumber passport number of a person.
     * @return true if person is present, false otherwise
     * @throws java.rmi.RemoteException
     */
    public boolean personExists(String passportNumber) throws RemoteException;
}
