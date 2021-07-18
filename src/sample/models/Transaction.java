package sample.models;

import com.google.gson.Gson;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction implements Comparable<Transaction>, Serializable {
    private double amount;
    private String category;
    private String note;
    private String date;
    private int type;

    public Transaction(double amount, int type, String category, String note, LocalDate date) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.note = note;
        this.date = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDateAsString(){
        return date;
    }
    public LocalDate getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(this.date, formatter);
    }

    public void setDate(LocalDate date) {
        this.date = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int compareTo(Transaction o) {
        return getDate().compareTo(o.getDate());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", category='" + category + '\'' +
                ", note='" + note + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
