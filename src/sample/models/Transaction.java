package sample.models;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Transaction implements Comparable<Transaction>{
    private double amount;
    private Category category;
    private String note;
    private LocalDate date;

    public Transaction(double amount, Category category, String note, LocalDate date) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public int compareTo(Transaction o) {
        return date.compareTo(o.getDate());
    }
}
