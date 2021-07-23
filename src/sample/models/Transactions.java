package sample.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transactions implements Comparable<Transactions>, Serializable {
    private double amount;
    private String currency;
    private String category;
    private String note;
    private String date;

    public Transactions(double amount, String currency, String category, String note, LocalDate date) {
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.note = note;
        this.date = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    @Override
    public int compareTo(Transactions o) {
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
