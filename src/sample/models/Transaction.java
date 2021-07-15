package sample.models;

import java.text.DateFormat;
import java.util.Date;

public class Transaction {
    private double amount;
    private Category category;
    private String note;
    private String date;

    public Transaction(double amount, Category category, String note, String date) {
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
