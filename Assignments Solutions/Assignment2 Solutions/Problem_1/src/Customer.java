//package com.company;

import java.util.ArrayList;

public class Customer {

    private static ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    private String name;
    private double moneyInSafe;
    private ArrayList<Account> allActiveAccounts = new ArrayList<Account>();
    private int totalNumberOfAccountsCreated;
    private int negativeScore;

    Customer(String name, double moneyInSafe) {
        this.name = name;
        this.setMoneyInSafe(moneyInSafe);
        allCustomers.add(this);
    }

    public static Customer getCustomerByName(String name) {
        for (Customer currentCustomer : allCustomers) {
            if (currentCustomer.getName().equals(name)) {
                return currentCustomer;
            }
        }
        return allCustomers.get(0);
    }

    public String getName() {
        return this.name;
    }

    public void createNewAccount(Bank bank, int money, int duration, int type) {
        if (!Bank.isThereBankWithName(bank.getName())) {
            System.out.println("In dige banke koodoom keshvarie?");
            return;
        }
        if (this.getMoneyInSafe() < money) {
            System.out.println("Boro baba pool nadari!");
            return;
        }
        this.totalNumberOfAccountsCreated++;
        Account buffer = new Account(bank, this, this.totalNumberOfAccountsCreated, money, duration, bank.getInterest(type));
        this.allActiveAccounts.add(buffer);
        this.setMoneyInSafe(this.getMoneyInSafe() - money);
    }

    public void leaveAccount(int accountId) {
        boolean found = false;
        for (Account account : this.allActiveAccounts) {
            if (account.getId() == accountId) {
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Chizi zadi?!");
            return;
        }
        Account accountToLeave = getAccountWithId(accountId);
        this.allActiveAccounts.remove(accountToLeave);
        this.setMoneyInSafe(this.getMoneyInSafe() + accountToLeave.getAmountOfMoneyForLeaving());
        Account.deleteAccount(accountToLeave);
    }

    public boolean canPayLoan(double amount) {
        return (amount <= this.getMoneyInSafe());
    }

    public double getMoneyInSafe() {
        return this.moneyInSafe;
    }

    public void setMoneyInSafe(double moneyInSafe) {
        this.moneyInSafe = moneyInSafe;
    }

    public void getLoan(int duration, int interest, int money) {
        if (!this.canGetLoan()) {
            System.out.println("To yeki kheyli vazet bade!");
            return;
        }
        new Loan(this, duration, interest, money);
        this.setMoneyInSafe(this.getMoneyInSafe() + money);
    }

    public void payLoan(double amount) {
        this.setMoneyInSafe(this.getMoneyInSafe() - amount);
    }

    public boolean canGetLoan() {
        return (this.getNegativeScore() < 5);
    }

    public int getNegativeScore() {
        return this.negativeScore;
    }

    public void addNegativeScore() {
        this.negativeScore++;
    }

    public boolean hasActiveAccountInBank(Bank bank) {
        for (Account account : allActiveAccounts) {
            if (account.getBank() == bank) {
                return true;
            }
        }
        return false;
    }

    private Account getAccountWithId(int id) {
        for (Account account : allActiveAccounts) {
            if (account.getId() == id)
                return account;
        }
        return allActiveAccounts.get(0);
    }
}
