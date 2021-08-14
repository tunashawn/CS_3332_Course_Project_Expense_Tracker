package sample.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import jdk.jshell.execution.Util;
import sample.Main;
import sample.models.Categories;
import sample.models.Transactions;
import sample.models.Wallets;
import sample.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ReportFrameControl {

    @FXML private BarChart<String, Number> bar_chart;
    @FXML private PieChart left_pie, right_pie;
    @FXML private ComboBox<String> wallet_combobox, time_range_combobox;
    @FXML private AnchorPane pane;
    @FXML private Button back_button, forward_button;
    @FXML private GridPane income_grid, expense_grid;

    private ArrayList<Wallets> walletList;
    private Wallets selectedWallet;
    private ArrayList<Transactions> transactionList;
    private ArrayList<Transactions> expenseList;
    private ArrayList<Transactions> incomeList;
    private ArrayList<PieChart.Data> expensePie = new ArrayList<>();
    private ArrayList<PieChart.Data> incomePie = new ArrayList<>();


    public ReportFrameControl(Wallets selectedWallet, ArrayList<Wallets> walletList) {
        this.walletList = walletList;
        this.selectedWallet = selectedWallet;
        this.transactionList = selectedWallet.getTransactionList();
    }


    private XYChart.Series<String, Number> expense_bar = new XYChart.Series<>();
    private XYChart.Series<String, Number> income_bar = new XYChart.Series<>();




    @FXML
    private void initialize() {


        populateWalletComboBox();

        processPieChartData(Utils.groupTransactionsByMonth(transactionList).get(0));

        populatePieChart();
        populateIncomeGrid();
        populateExpenseGrid();


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

        bar_chart.getData().addAll(expense_bar, income_bar);

        bar_chart.setTitle("This is bar chart");
    }

    private void populatePieChart(){
        right_pie.setLegendVisible(false);
        left_pie.setLegendVisible(false);

        if (expensePie != null) {
            ObservableList<PieChart.Data> dataExpensePie = FXCollections.observableArrayList();
            dataExpensePie.addAll(expensePie);
            right_pie.setData(dataExpensePie);
            right_pie.setTitle("Expenses");

        }

        if (incomePie != null) {
            ObservableList<PieChart.Data> dataIncomePie = FXCollections.observableArrayList();
            dataIncomePie.addAll(incomePie);
            left_pie.setData(dataIncomePie);
            left_pie.setTitle("Incomes");
        }
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

    private void processPieChartData(ArrayList<Transactions> transactionList){
        if (incomePie != null)
            incomePie.clear();

        if (expensePie != null)
            expensePie.clear();

        for (Categories e : Main.getExpenseCategories())
            expensePie.add(new PieChart.Data(e.getName(), 0));

        for (Categories i : Main.getIncomeCategories())
            incomePie.add(new PieChart.Data(i.getName(), 0));

        for (Transactions t : transactionList) {
            if (t.getAmount() >= 0)
                for (PieChart.Data data : incomePie) {
                    if (data.getName().equals(t.getCategory())) {
                        data.setPieValue(data.getPieValue() + t.getAmount());
                        break;
                    }
                }
            else
                for (PieChart.Data data : expensePie) {
                    if (data.getName().equals(t.getCategory())){
                        data.setPieValue(data.getPieValue() - t.getAmount());
                        break;
                    }
                }
        }

        incomePie.removeIf(data -> data.getPieValue() == 0);
        expensePie.removeIf(data -> data.getPieValue() == 0);
    }

    private void populateIncomeGrid(){
        try {
            int column = 0;
            int row = 1;
            for (PieChart.Data data : incomePie) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/sample/views/ReportCard.fxml"));

                ReportCardControl reportCardControl = new ReportCardControl(data.getName(), data.getPieValue(), selectedWallet.getCurrency());
                fxmlLoader.setController(reportCardControl);
                AnchorPane pane = fxmlLoader.load();

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                income_grid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateExpenseGrid(){
        try {
            int column = 0;
            int row = 1;
            for (PieChart.Data data : expensePie) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/sample/views/ReportCard.fxml"));

                ReportCardControl reportCardControl = new ReportCardControl(data.getName(), data.getPieValue(), selectedWallet.getCurrency());
                fxmlLoader.setController(reportCardControl);
                AnchorPane pane = fxmlLoader.load();

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                expense_grid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



