package ru.ifmo.ctddev.markovnikov.bank;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Application connects to a server and checks or creates a {@link ru.ifmo.ctddev.markovnikov.bank.Person}
 * and it's {@link ru.ifmo.ctddev.markovnikov.bank.Account} accordingly to specified arguments.
 * Can use both local an remote versions of persons.
 */
public class Client {
    /**
     * Runs the program with specified arguments.
     *
     * @param args first name, last name, passport number, account id and amount of money which
     *             will be set to the account if it exists.
     */
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Check arguments");
            return;
        }
        try {
            Bank bank = (Bank) Naming.lookup("rmi://localhost/bank");
            Person person;
            if (!bank.personExists(args[2])) {
                bank.createPerson(args[0], args[1], args[2]);
                person = bank.getRemotePerson(args[2]);
                System.out.println("Person " + person.getName() + " " + person.getSurname() + " has been created");
            } else {
                person = bank.getRemotePerson(args[2]);
            }
            Account account = person.getAccount(args[3]);
            if (!person.accountExists(args[3])) {
                person.createAccount(args[3]);
                System.out.println("Account " + person.getAccount(args[3]) +  " has been created");
            } else {
                int before = account.getAmount();
                account.changeAmount(Integer.parseInt(args[4]));
                System.out.println("Account changed from " + before + " to " + account.getAmount());
            }
        } catch (RemoteException e) {
            System.err.println("Remote operation error");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("Check your bank or connect with administrator");
            e.printStackTrace();
        } catch (NumberFormatException e1) {
            System.err.println("Wrong arguments");
        }
    }
}
