package sample.utils;

import javafx.scene.control.Tooltip;
import org.apache.commons.lang3.StringUtils;
import sample.models.Transactions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class Utils {


    public static NumberFormat formatter = new DecimalFormat("#,##0.000");


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
            System.out.println("list:");
            System.out.println(list);
        }
        return  trans_by_month;
    }


    /**
     * Input raw transaction list, return list of list of transaction
     * Each sub list stands for transaction in a specific month
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
            System.out.println("list:");
            System.out.println(list);
        }
        return  trans_by_week;
    }



    public static ArrayList<ArrayList<Transactions>> groupTransactionsByDay(ArrayList<Transactions> transactionsInMonth, LocalDate date){
        ArrayList<ArrayList<Transactions>> trans_by_day = new ArrayList<ArrayList<Transactions>>();
        ArrayList<Transactions> list = new ArrayList<>();
        for (Transactions t : transactionsInMonth) {
            if (t.getDate().getDayOfMonth() != date.getDayOfMonth() ) {
                trans_by_day.add(list);
                list = new ArrayList<>();
                date = t.getDate();
            }
            list.add(t);
        }
        trans_by_day.add(list);
        return trans_by_day;
    }

    public static Tooltip getToolTip(String message){
        Tooltip tt = new Tooltip();
        tt.setText(message);
        tt.setStyle("-fx-font-size: 20; "
                + "-fx-base: #AE3522; "
                + "-fx-text-fill: orange;");
        return tt;
    }

    public static String getMonthLabelValue(LocalDate current_date){
        String mon_year = "";
        if (current_date.getYear() == LocalDate.now().getYear() && current_date.getMonthValue() == LocalDate.now().getMonthValue())
            mon_year = "This Month";
        else
            mon_year = StringUtils.capitalize(current_date.getMonth().toString().toLowerCase(Locale.ROOT)) + " " + current_date.getYear();
        return mon_year;
    }
}