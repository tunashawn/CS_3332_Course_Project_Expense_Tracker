package sample.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.apache.commons.lang3.StringUtils;
import sample.controllers.TransactionControl;
import sample.models.InterestRate;
import sample.models.Transactions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class Utils {


    public static NumberFormat formatter = new DecimalFormat("#,##0.00");


    public static String convertCurrency(String inputCurrencyName, Double inputAmount,
                                         String outputCurrencyName) {

        // Convert currency
        double amountWithUSD = 0.0;
        double outputCurrency = 0.0;
        CurrencyCode[] currencyCodes = CurrencyCode.values();
        for (CurrencyCode currencyCode : currencyCodes) {
            if (currencyCode.getCurrencyCode().equalsIgnoreCase(inputCurrencyName)) {
                amountWithUSD = inputAmount / currencyCode.getRate();
                break;
            }
        }
        for (CurrencyCode currencyCode : currencyCodes) {
            if (currencyCode.getCurrencyCode().equalsIgnoreCase(outputCurrencyName)) {
                outputCurrency = amountWithUSD * currencyCode.getRate();
                break;
            }
        }

        return formatter.format(outputCurrency);
    }

    /**
     * Input raw transaction list, return list of list of transaction
     * Each sub list stands for transaction in a specific month
     *
     * @param transactionList
     * @return
     */
    public static ArrayList<ArrayList<Transactions>> groupTransactionsByMonth(ArrayList<Transactions> transactionList) {
        ArrayList<ArrayList<Transactions>> trans_by_month = new ArrayList<ArrayList<Transactions>>();
        if (transactionList != null) {
            transactionList.sort(Collections.reverseOrder());

            LocalDate date = transactionList.get(0).getDate();
            ArrayList<Transactions> list = new ArrayList<>();

            for (Transactions t : transactionList) {
                if (t.getDate().getMonth() != date.getMonth()) {
                    trans_by_month.add(list);
                    list = new ArrayList<>();
                    date = t.getDate();
                }
                list.add(t);
            }
            trans_by_month.add(list);

        }
        return trans_by_month;
    }


    /**
     * Input raw transaction list, return list of list of transaction
     * Each sub list stands for transaction in a specific month
     *
     * @param transactionList
     * @return
     */
    public static ArrayList<ArrayList<Transactions>> groupTransactionsByWeek(ArrayList<Transactions> transactionList) {
        ArrayList<ArrayList<Transactions>> trans_by_week = new ArrayList<ArrayList<Transactions>>();
        if (transactionList != null) {
            transactionList.sort(Collections.reverseOrder());

            LocalDate date = transactionList.get(0).getDate();
            ArrayList<Transactions> list = new ArrayList<>();

            for (Transactions t : transactionList) {
                if (t.getDate().getMonth() != date.getMonth()) {
                    trans_by_week.add(list);
                    list = new ArrayList<>();
                    date = t.getDate();
                }
                list.add(t);
            }
            trans_by_week.add(list);

        }
        return trans_by_week;
    }


    public static ArrayList<ArrayList<Transactions>> groupTransactionsByDay(ArrayList<Transactions> transactionsInMonth, LocalDate date) {
        ArrayList<ArrayList<Transactions>> trans_by_day = new ArrayList<ArrayList<Transactions>>();
        ArrayList<Transactions> list = new ArrayList<>();
        for (Transactions t : transactionsInMonth) {
            if (t.getDate().getDayOfMonth() != date.getDayOfMonth()) {
                trans_by_day.add(list);
                list = new ArrayList<>();
                date = t.getDate();
            }
            list.add(t);
        }
        trans_by_day.add(list);
        return trans_by_day;
    }

    public static Tooltip getToolTip(String message) {
        Tooltip tt = new Tooltip();
        tt.setText(message);
        tt.setStyle("-fx-font-size: 20; "
                + "-fx-base: #AE3522; "
                + "-fx-text-fill: orange;");
        return tt;
    }

    public static String getMonthLabelValue(LocalDate current_date) {
        String mon_year = "";
        if (current_date.getYear() == LocalDate.now().getYear() && current_date.getMonthValue() == LocalDate.now().getMonthValue())
            mon_year = "This Month";
        else
            mon_year = StringUtils.capitalize(current_date.getMonth().toString().toLowerCase(Locale.ROOT)) + " " + current_date.getYear();
        return mon_year;
    }

    public static void showNothingToDisplay(AnchorPane grid) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(TransactionControl.class.getResource("/sample/views/NothingToDisplay.fxml"));
            AnchorPane pane = fxmlLoader.load();
            grid.getChildren().clear();
            grid.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showNothingToDisplay(GridPane grid) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(TransactionControl.class.getResource("/sample/views/NothingToDisplay.fxml"));
            AnchorPane pane = fxmlLoader.load();
            grid.getChildren().clear();
            grid.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static InterestRate calculateInterestRate(double amount, double interest_rate, LocalDate start_time, LocalDate end_time, String type_interest, String time_mode) {

        double result = 0.0;

        long period = start_time.until(end_time, ChronoUnit.DAYS);
        int n = 0;
        switch (type_interest) {
            case "Simple interest":
                switch (time_mode) {
                    case "Weekly":
                        n = (int) period / 7;
                        result = simpleInterest(amount,interest_rate, n, 7);
                        break;
                    case "Every 14 days":
                        n = (int) period / 14;
                        result = simpleInterest(amount,interest_rate, n, 14);
                        break;
                    case "Monthly":
                        n = (int) period / 30;
                        result = simpleInterest(amount,interest_rate, n, 30);
                        break;
                    case "Every 3 months":
                        n = (int) period / 90;
                        result = simpleInterest(amount,interest_rate, n, 90);
                        break;
                    case "Every 6 months":
                        n = (int) period / 180;
                        result = simpleInterest(amount,interest_rate, n, 180);
                        break;
                    case "Yearly":
                        n = (int) period / 365;
                        result = simpleInterest(amount,interest_rate, n, 365);
                        break;
                    default:
                        break;
                }
                break;
            case "Compound interest":
                switch (time_mode) {

                    case "Weekly":
                        n = (int) period / 7;
                        result = compoundInterest(amount, interest_rate, n, 7);
                        break;
                    case "Every 14 days":
                        n = (int) period / 14;
                        result = compoundInterest(amount, interest_rate, n, 14);
                        break;
                    case "Monthly":
                        n = (int) period / 30;
                        result = compoundInterest(amount, interest_rate, n, 30);
                        break;
                    case "Every 3 months":
                        n = (int) period / 90;
                        result = compoundInterest(amount, interest_rate, n, 90);
                        break;
                    case "Every 6 months":
                        n = (int) period / 180;
                        result = compoundInterest(amount, interest_rate, n, 180);
                        break;
                    case "Yearly":
                        n = (int) period / 365;
                        result = compoundInterest(amount, interest_rate, n, 365);
                        break;
                    default:
                        break;
                }
                break;
        }
        return new InterestRate(result, n, result - amount);
    }

//    private double compoundInterest(double amount, double rate, double ){
//
//    }

    public static double simpleInterest(double amount, double rate, int n, int time){

        return (amount * rate/100 * n) + amount;
    }

    public static double compoundInterest(double amount, double rate, int n, int time){

        for (int i = 0; i < n; i++) {
            amount *= (1 + rate/100);
        }
        return  amount;
    }

}