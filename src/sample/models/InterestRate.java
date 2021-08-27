package sample.models;

public class InterestRate {
    double amount;
    int period;
    double interest_amount;

    public InterestRate(double amount, int period, double interest_amount) {
        this.amount = amount;
        this.period = period;
        this.interest_amount = interest_amount;
    }

    public double getAmount() {
        return amount;
    }

    public int getPeriod() {
        return period;
    }

    public double getInterest_amount() {
        return interest_amount;
    }
}
