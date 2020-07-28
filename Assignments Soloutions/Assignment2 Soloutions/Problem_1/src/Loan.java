//package com.company;

import java.util.ArrayList;

public class Loan {

    private static ArrayList<Loan> allLoans = new ArrayList<Loan>();
    private Customer customer;
    private int duration;
    private int remainingPayments;
    private int interest;
    private int amount;

    Loan(Customer customer, int duration, int interest, int amount) {
        this.customer = customer;
        this.duration = duration;
        this.remainingPayments = this.duration;
        this.interest = interest;
        this.amount = amount;
        allLoans.add(this);
    }

    public static void passMonth() {
        int currentSize = allLoans.size();
        for (int currentLoan = 0; currentLoan < allLoans.size(); currentLoan++) {
            allLoans.get(currentLoan).passMonthEach();
            if (currentSize != allLoans.size()) {
                currentLoan--;
                currentSize = allLoans.size();
            }
        }
    }

    private double getPaymentAmount() {
        return this.amount * (1.0 + this.interest / 100.0) / duration;
    }

    private void passMonthEach() {
        if (this.customer.canPayLoan(this.getPaymentAmount())) {
            this.customer.payLoan(this.getPaymentAmount());
            this.remainingPayments--;
        }
        else {
            this.customer.addNegativeScore();
        }
        if (this.remainingPayments == 0)
            allLoans.remove(this);
    }
}
