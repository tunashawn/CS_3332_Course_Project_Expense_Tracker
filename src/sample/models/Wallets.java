package sample.models;


import java.io.Serializable;
import java.util.ArrayList;

public class Wallets implements Serializable {
    private String name;
    private String icon_name;
    private String currency;
    private double balance;
    private double total_income;
    private double total_expense;
    private ArrayList<Transactions> transactionList = new ArrayList<>();

    public Wallets(String name, String icon_name, String currency, double balance, double total_income, double total_expense, ArrayList<Transactions> transactionList) {
        this.name = name;
        this.icon_name = icon_name;
        this.currency = currency;
        this.balance = balance;
        this.total_income = total_income;
        this.total_expense = total_expense;
        this.transactionList = transactionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_name() {
        return icon_name;
    }

    public void setIcon_name(String icon_name) {
        this.icon_name = icon_name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotal_income() {
        return total_income;
    }

    public void setTotal_income(double total_income) {
        this.total_income = total_income;
    }

    public double getTotal_expense() {
        return total_expense;
    }

    public void setTotal_expense(double total_expense) {
        this.total_expense = total_expense;
    }

    public ArrayList<Transactions> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(ArrayList<Transactions> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public String toString() {
        String tran = "";
        for (Transactions transactions : transactionList) {
            tran += transactions;
        }
        return "Wallets{" +
                "name='" + name + '\'' +
                ", icon_name='" + icon_name + '\'' +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                ", total_income=" + total_income +
                ", total_expense=" + total_expense +
                ", transactionList=" + transactionList +
                '}';
    }
}
