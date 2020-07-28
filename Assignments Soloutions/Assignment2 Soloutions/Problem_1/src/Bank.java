//package com.company;

import java.util.ArrayList;

public class Bank {

    private static ArrayList<Bank> allBanks = new ArrayList<Bank>();
    private String name;

    Bank(String name) {
        this.name = name;
        allBanks.add(this);
    }

    public static Bank getBankWithName(String name) {
        for (Bank currentBank : allBanks) {
            if (currentBank.getName().equals(name)) {
                return currentBank;
            }
        }
        return allBanks.get(0);
    }

    public static boolean isThereBankWithName(String name) {
        for (Bank currentBank : allBanks) {
            if (currentBank.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static int getAccountInterestFromName(String name) {
        if (name.equals("KOOTAH")) {
            return 10;
        }
        else if (name.equals("BOLAN")) {
            return 30;
        }
        else if (name.equals("VIZHE")){
            return 50;
        }
        else {
            return 0;
        }
    }

    public int getInterest(int type) {
        if (type == 0) {
            return 10;
        }
        else if (type == 1) {
            return 30;
        }
        else if (type == 2) {
            return 50;
        }
        else {
            return 0;
        }
    }

    public String getName() {
        return this.name;
    }
}
