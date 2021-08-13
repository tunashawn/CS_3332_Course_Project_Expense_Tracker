package sample.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.models.Categories;
import sample.models.Transactions;
import sample.models.Wallets;
import sample.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class ReportFrameControl {

    @FXML private BarChart<String, Number> bar_chart;
    @FXML private PieChart left_pie, right_pie;
    @FXML private ComboBox<String> wallet_combobox, time_range_combobox;
    @FXML private AnchorPane pane;
    @FXML private Button back_button, forward_button;


    private ArrayList<Wallets> walletList;
    private Wallets selectedWallet;
    private ArrayList<Transactions> transactionList;
    private ArrayList<Transactions> expenseList;
    private ArrayList<Transactions> incomeList;

    public ReportFrameControl(Wallets selectedWallet, ArrayList<Wallets> walletList) {
        this.walletList = walletList;
        this.selectedWallet = selectedWallet;
        this.transactionList = selectedWallet.getTransactionList();
    }


    private XYChart.Series<String, Number> expense_bar = new XYChart.Series<>();
    private XYChart.Series<String, Number> income_bar = new XYChart.Series<>();




    @FXML
    private void initialize() {

        expense_bar.setName("Expense");
        expense_bar.getData().add(new XYChart.Data<String, Number>("Jan", -245));
        expense_bar.getData().add(new XYChart.Data<String, Number>("Feb", -500));
        expense_bar.getData().add(new XYChart.Data<String, Number>("Mar", -69));



        income_bar.setName("Income");
        income_bar.getData().add(new XYChart.Data<String, Number>("Jan", 270));
        income_bar.getData().add(new XYChart.Data<String, Number>("Feb", 40));
        income_bar.getData().add(new XYChart.Data<String, Number>("Mar", 300));

        bar_chart.getData().addAll(expense_bar, income_bar);

        bar_chart.setTitle("This is bar chart");

        ObservableList<PieChart.Data> data_left_pie = FXCollections.observableArrayList(
                new PieChart.Data("Clothes", 3),
                new PieChart.Data("Food",  30),
                new PieChart.Data("Drink", 40),
                new PieChart.Data("Medical", 27)
        );
        left_pie.setData(data_left_pie);
    }


    private void populateWalletComboBox(){
        ArrayList<String> wallet_names = new ArrayList<>();
        for (Wallets wallets : walletList) {
            wallet_names.add(wallets.getName());
        }
        Collections.sort(wallet_names);
        for (String wallet_name : wallet_names) {
            wallet_combobox.getItems().add(wallet_name);
        }
        wallet_combobox.getSelectionModel().select(selectedWallet.getName());
    }

    private void populateBarChart(){
        expense_bar.setName("Expense");
        for (Transactions transaction : expenseList) {
            expense_bar.getData().add(new XYChart.Data<String, Number>("Jan", -245));

        }


        income_bar.setName("Income");
        income_bar.getData().add(new XYChart.Data<String, Number>("Jan", 270));
    }

    private void separateIncomeAndExpense(){
        for (Transactions transactions : transactionList) {
            if (transactions.getAmount() >= 0)
                incomeList.add(transactions);
            else expenseList.add(transactions);
        }
    }

    private void populateTimeRangeCombobox(){
        time_range_combobox.getItems().add("Month");
        time_range_combobox.getItems().add("Quarter");
        time_range_combobox.getItems().add("Year");
        time_range_combobox.getItems().add("All");
        time_range_combobox.getSelectionModel().select("Month");
    }
}



