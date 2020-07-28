//package com.company;

import java.util.ArrayList;

public class Account {

    private static ArrayList<Account> allAccounts = new ArrayList<Account>();
    private Bank bank;
    private int id;
    private int money;
    private int remainingDuration;
    private int interest;
    private Customer customer;

    Account(Bank bank, Customer customer, int id, int money, int duration, int interest) {
        this.bank = bank;
        this.customer = customer;
        this.id = id;
        this.money = money;
        this.remainingDuration = duration;
        this.interest = interest;
        allAccounts.add(this);
    }

    public static void passMonth() {
        int currentSize = allAccounts.size();
        for (int currentAccount = 0; currentAccount < allAccounts.size(); currentAccount++) {
            allAccounts.get(currentAccount).passMonthEach();
            if (currentSize != allAccounts.size()) {
                currentAccount--;
                currentSize = allAccounts.size();
            }
        }
    }

    public static void deleteAccount(Account account) {
        allAccounts.remove(account);
    }

    public int getId() {
        return this.id;
    }

    public double getAmountOfMoneyForLeaving() {
        if (this.remainingDuration == 0) {
            return this.money * (1.0 + this.interest / 100.0);
        }
        else
            return this.money;
    }

    public Bank getBank() {
        return this.bank;
    }

    private void passMonthEach() {
        this.remainingDuration--;
        if (this.remainingDuration == 0) {
            this.customer.leaveAccount(this.getId());
        }
    }
}
