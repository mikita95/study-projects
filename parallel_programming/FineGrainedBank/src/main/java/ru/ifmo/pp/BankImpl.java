package ru.ifmo.pp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Bank implementation.
 *
 * @author Markovnikov Nikita
 */

public class BankImpl implements Bank {
    /**
     * An array of accounts by index.
     */

    private final Account[] accounts;

    /**
     * Creates new bank instance.
     *
     * @param n the number of accounts (numbered from 0 to n-1).
     */
    public BankImpl(int n) {
        accounts = new Account[n];
        for (int i = 0; i < n; i++) {
            accounts[i] = new Account();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfAccounts() {
        return accounts.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getAmount(int index) {
        long amount;
        accounts[index].lock.lock();
        try {
            amount = accounts[index].amount;
        } finally {
            accounts[index].lock.unlock();
        }
        return amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTotalAmount() {
        long sum;
        for (int i = 0; i < accounts.length; i++)
            accounts[i].lock.lock();
        try {
            sum = 0;
            for (Account account : accounts) {
                sum += account.amount;
            }
        } finally {
            for (int i = accounts.length - 1; i >= 0; i--)
                accounts[i].lock.unlock();
        }
        return sum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long deposit(int index, long amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount: " + amount);
        Account account = accounts[index];
        long deposit;
        account.lock.lock();
        try {
            if (amount > MAX_AMOUNT || account.amount + amount > MAX_AMOUNT)
                throw new IllegalStateException("Overflow");
            account.amount += amount;
            deposit = account.amount;
        } finally {
            account.lock.unlock();
        }
        return deposit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long withdraw(int index, long amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount: " + amount);
        Account account = accounts[index];
        long withdraw;
        account.lock.lock();
        try {
            if (account.amount - amount < 0)
                throw new IllegalStateException("Underflow");
            account.amount -= amount;
            withdraw = account.amount;
        } finally {
            account.lock.unlock();
        }
        return withdraw;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transfer(int fromIndex, int toIndex, long amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount: " + amount);
        if (fromIndex == toIndex)
            throw new IllegalArgumentException("fromIndex == toIndex");
        Account from = accounts[fromIndex];
        Account to = accounts[toIndex];
        int min = Math.min(fromIndex, toIndex);
        int max = Math.max(fromIndex, toIndex);
        accounts[min].lock.lock();
        accounts[max].lock.lock();
        try {
            if (amount > from.amount) {
                throw new IllegalStateException("Underflow");
            } else if (amount > MAX_AMOUNT || to.amount + amount > MAX_AMOUNT)
                throw new IllegalStateException("Overflow");
            from.amount -= amount;
            to.amount += amount;
        } finally {
            accounts[max].lock.unlock();
            accounts[min].lock.unlock();
        }
    }

    /**
     * Private account data structure.
     */
    private static class Account {
        /**
         * Amount of funds in this account.
         */
        final Lock lock;
        long amount;

        private Account() {
            this.lock = new ReentrantLock();
        }
    }

}
