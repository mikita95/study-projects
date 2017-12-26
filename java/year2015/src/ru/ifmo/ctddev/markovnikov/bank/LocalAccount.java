package ru.ifmo.ctddev.markovnikov.bank;

/**
 * LocalAccount provides an access to a {@link ru.ifmo.ctddev.markovnikov.bank.Account} stored on the same JVM.
 * Non-thread-safe.
 */
public class LocalAccount implements Account {
    private int amount;
    private String id;
    /**
     * Creates an account with a specified id and zero amount of money.
     *
     * @param id account identifier.
     */
    public LocalAccount(String id) {
        this.id = id;
    }

    /**
     * Return account id.
     *
     * @return identifier.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Return account amount of money.
     *
     * @return amount of money.
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Changes an amount of money on a specified value.
     *
     * @param amount new amount.
     */
    public void changeAmount(int amount) {
        System.out.println("Changing amount of money for local account " + id);
        this.amount += amount;
    }
}
