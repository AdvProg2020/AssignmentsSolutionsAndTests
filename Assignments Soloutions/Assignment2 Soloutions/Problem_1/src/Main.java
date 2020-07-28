//package com.company;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            command.trim();
            if (command.equals("Base dige, berid khonehatoon.")) {
                break;
            }
            else if (getMatcher(command, "^Does (.+) have active account in (.+)(\\?)$").find()) {
                Matcher matcher = getMatcher(command, "^Does (.+) have active account in (.+)?$");
                matcher.find();
                String customerName = matcher.group(1), bankName = matcher.group(2);
                bankName = bankName.substring(0, bankName.length() - 1);
                if (Customer.getCustomerByName(customerName).hasActiveAccountInBank(Bank.getBankWithName(bankName))) {
                    System.out.println("yes");
                }
                else {
                    System.out.println("no");
                }
            }
            else if (getMatcher(command, "^Print .+'s NOMRE MANFI count\\.$").find()) {
                String customerName = command.substring(6, command.length() - 21);
                System.out.println((int)Customer.getCustomerByName(customerName).getNegativeScore());
            }
            else if (getMatcher(command, "^Print .+'s GAVSANDOOGH money\\.$").find()) {
                String customerName = command.substring(6, command.length() - 21);
                System.out.println((int)Customer.getCustomerByName(customerName).getMoneyInSafe());
            }
            else if (getMatcher(command, "^Pass time by \\d+ unit\\.$").find()) {
                int timePassed = Integer.parseInt(command.substring(13, command.length() - 6));
                for (int unitsOfTime = 0; unitsOfTime < timePassed; unitsOfTime++)
                    passMonth();
            }
            else if (getMatcher(command, "^Pay a (\\d+) unit loan with %(\\d+) interest and (6|12) payments from (.+) to (.+)\\.$").find()) {
                Matcher matcher = getMatcher(command, "^Pay a (\\d+) unit loan with %(\\d+) interest and (6|12) payments from (.+) to (.+)\\.$");
                matcher.find();
                int amount = Integer.parseInt(matcher.group(1));
                int interest = Integer.parseInt(matcher.group(2));
                int duration = Integer.parseInt(matcher.group(3));
                String bankName = matcher.group(4);
                String customerName = matcher.group(5);
                if (!Bank.isThereBankWithName(bankName)) {
                    System.out.println("Gerefti maro nesfe shabi?");
                }
                else {
                    Customer.getCustomerByName(customerName).getLoan(duration, interest, amount);
                }
            }
            else if (getMatcher(command, "^Give (.+)'s money out of his account number (\\d+)\\.$").find()) {
                Matcher matcher = getMatcher(command, "^Give (.+)'s money out of his account number (\\d+)\\.$");
                matcher.find();
                String customerName = matcher.group(1);
                int accoutnId = Integer.parseInt(matcher.group(2));
                Customer.getCustomerByName(customerName).leaveAccount(accoutnId);
            }
            else if (getMatcher(command, "^Create bank .+\\.$").find()) {
                String bankName = command.substring(12, command.length() - 1);
                new Bank(bankName);
            }
            else if (getMatcher(command, "^Add a customer with name (.+) and (\\d+) unit initial money\\.$").find()) {
                Matcher matcher = getMatcher(command, "^Add a customer with name (.+) and (\\d+) unit initial money\\.$");
                matcher.find();
                new Customer(matcher.group(1), Integer.parseInt(matcher.group(2)));
            }
            else if (getMatcher(command, "^Create a (KOOTAH|BOLAN|VIZHE) account for (.+) in (.+), with duration (\\d+) and initial deposit of (\\d+)\\.$").find()) {
                Matcher matcher = getMatcher(command, "^Create a (KOOTAH|BOLAN|VIZHE) account for (.+) in (.+), with duration (\\d+) and initial deposit of (\\d+)\\.$");
                matcher.find();
                int type = 0;
                if (matcher.group(1).equals("KOOTAH")) {
                    type = 0;
                }
                else if (matcher.group(1).equals("BOLAN")) {
                    type = 1;
                }
                else if (matcher.group(1).equals("VIZHE")) {
                    type = 2;
                }
                String customerName = matcher.group(2), bankName = matcher.group(3);
                int duration = Integer.parseInt(matcher.group(4));
                int initialDeposit = Integer.parseInt(matcher.group(5));
                if (!Bank.isThereBankWithName(bankName)) {
                    System.out.println("In dige banke koodoom keshvarie?");
                }
                else {
                    Customer.getCustomerByName(customerName).createNewAccount(Bank.getBankWithName(bankName), initialDeposit, duration, type);
                }
            }
        }
    }

    private static void passMonth() {
        Loan.passMonth();
        Account.passMonth();
    }

    private static Matcher getMatcher(String string, String regex) {
        Pattern tmp = Pattern.compile(regex);
        return tmp.matcher(string);
    }
}
