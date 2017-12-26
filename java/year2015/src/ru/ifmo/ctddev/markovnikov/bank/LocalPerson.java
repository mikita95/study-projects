package ru.ifmo.ctddev.markovnikov.bank;

import java.util.HashMap;
import java.util.Map;

/**
 * LocalPerson provides an access to a {@link ru.ifmo.ctddev.markovnikov.bank.Person} stored on the same JVM.
 * Non-thread-safe.
 */
public class LocalPerson implements Person {
    private String name, surname, passportNumber;
    private Map<String, LocalAccount> accounts = new HashMap<>();

    /**
     * Creates a person with specified data.
     *
     * @param name      first name of a person.
     * @param surname       last name of a person.
     * @param passportNumber passport number of a person.
     */
    public LocalPerson(String name, String surname, String passportNumber) {
        this.name = name;
        this.surname = surname;
        this.passportNumber = passportNumber;
    }

    /**
     * Returns persons first name.
     *
     * @return first name.
     */
    public String getName() {
        return name;
    }

    /**
     * Return persons last name.
     *
     * @return last name.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Returns persons passport number.
     *
     * @return passport number.
     */
    public String getPassportNumber() {
        return passportNumber;
    }

    /**
     * Return person's account with specified identifier. LocalPerson's accounts are local too.
     *
     * @param id account id.
     * @return account if it is present, null otherwise.
     */
    public LocalAccount getAccount(String id) {
        return accounts.get(id);
    }

    /**
     * Creates an account with requested identifier and zero amount of money.
     *
     * @param id account id.
     */
    public void createAccount(String id) {
        System.out.println("Local account for " + name + " " + surname + " has been created");
        accounts.put(id, new LocalAccount(id));
    }

    /**
     * Checks whatever the person has an account with a specified identifier.
     *
     * @param id account id.
     * @return true if it is present, false otherwise.
     */
    public boolean accountExists(String id) {
        return accounts.containsKey(id);
    }
}
