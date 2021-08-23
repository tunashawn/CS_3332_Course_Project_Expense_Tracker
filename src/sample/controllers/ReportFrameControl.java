package sample.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sample.Main;
import sample.models.Categories;
import sample.models.Transactions;
import sample.models.Wallets;
import sample.utils.Utils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReportFrameControl {
    private NumberAxis yAxis = new NumberAxis();
    private CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private BarChart<String, Number> bar_chart = new BarChart<String, Number>(xAxis, yAxis);
    @FXML
    private PieChart left_pie, right_pie;
    @FXML
    private ComboBox<String> wallet_combobox, time_range_combobox;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button back_button, forward_button;
    @FXML
    private GridPane income_grid, expense_grid;
    @FXML
    private Label time_label;

    private ArrayList<Wallets> walletList;
    private Wallets selectedWallet;
    private ArrayList<Transactions> expenseList;
    private ArrayList<Transactions> incomeList;
    private ArrayList<PieChart.Data> expensePie = new ArrayList<>();
    private ArrayList<PieChart.Data> incomePie = new ArrayList<>();
    private ArrayList<ArrayList<Transactions>> transactionsByMonth;
    private LocalDate selected_month;
    private XYChart.Series<String, Number> expense_bar = new XYChart.Series<>();
    private XYChart.Series<String, Number> income_bar = new XYChart.Series<>();

    private MainFrameControl mainFrameControl;

    public ReportFrameControl(MainFrameControl mainFrameControl, Wallets selectedWallet, ArrayList<Wallets> walletList) {
        this.walletList = walletList;
        this.selectedWallet = selectedWallet;
        this.mainFrameControl = mainFrameControl;
        if (selectedWallet.getTransactionList().size() > 0 && selectedWallet.getTransactionList() != null)
            transactionsByMonth = Utils.groupTransactionsByMonth(selectedWallet.getTransactionList());
    }

    @FXML
    private void initialize() {
        if (walletList.size() > 0) {
            populateWalletComboBox();
            wallet_combobox.setOnAction(event -> setSelectedWallet());
        }
        if (selectedWallet.getTransactionList().size() > 0 && selectedWallet.getTransactionList() != null) {
            selected_month = LocalDate.now();
            time_label.setText("This month");

            back_button.setOnAction(event -> decreaseMonth());
            forward_button.setOnAction(event -> increaseMonth());

            visualizeData();
        }
    }

    private void setSelectedWallet() {
        if (!wallet_combobox.getSelectionModel().getSelectedItem().equals(selectedWallet.getName())) {
            time_label.setText("This month");
            for (Wallets w : walletList) {
                if (w.getName().equals(wallet_combobox.getSelectionModel().getSelectedItem())) {
                    mainFrameControl.openReportView(w);

                    break;
                }
            }
        }
    }

    private void visualizeData() {
        populateBarChart();
        processPieChartData();
        populatePieChart();
        populateIncomeGrid();
        populateExpenseGrid();
    }

    private void increaseMonth() {
        selected_month = selected_month.plusMonths(1);
        time_label.setText(Utils.getMonthLabelValue(selected_month));
        visualizeData();
    }

    private void decreaseMonth() {
        selected_month = selected_month.minusMonths(1);
        time_label.setText(Utils.getMonthLabelValue(selected_month));
        visualizeData();
    }


    private void populateWalletComboBox() {
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

    private void populateBarChart() {
        bar_chart.getData().clear();
        expense_bar.getData().clear();
        income_bar.getData().clear();
        expense_bar.setName("Expense");
        income_bar.setName("Income");
        for (ArrayList<Transactions> transactions : transactionsByMonth) {
            if (transactions.get(0).getDate().getMonthValue() == selected_month.getMonthValue() &&
                    transactions.get(0).getDate().getYear() == selected_month.getYear()) {

                Collections.sort(transactions);
                int day = transactions.get(0).getDate().getDayOfMonth();
                double expense = 0.0;
                double income = 0.0;

                for (Transactions t : transactions) {
                    if (t.getDate().getDayOfMonth() == day) {
                        if (t.getAmount() >= 0)
                            income += t.getAmount();
                        else
                            expense += t.getAmount();
                    } else {
                        expense_bar.getData().add(new XYChart.Data<String, Number>(String.valueOf(day), expense));
                        income_bar.getData().add(new XYChart.Data<String, Number>(String.valueOf(day), income));
                        day = t.getDate().getDayOfMonth();
                        income = 0;
                        expense = 0;

                        if (t.getAmount() >= 0)
                            income = t.getAmount();
                        else
                            expense = t.getAmount();
                    }
                    expense_bar.getData().add(new XYChart.Data<String, Number>(String.valueOf(day), expense));
                    income_bar.getData().add(new XYChart.Data<String, Number>(String.valueOf(day), income));
                }
                break;
            }
        }
        bar_chart.getData().addAll(expense_bar, income_bar);
    }

    private void populatePieChart() {
        left_pie.getData().clear();

        right_pie.getData().clear();

        right_pie.setStartAngle(90.0);
        left_pie.setStartAngle(90.0);

        right_pie.setLegendVisible(false);
        left_pie.setLegendVisible(false);

        if (expensePie != null) {
            ObservableList<PieChart.Data> dataExpensePie = FXCollections.observableArrayList();
            dataExpensePie.addAll(expensePie);

            right_pie.setData(dataExpensePie);
        }

        if (incomePie != null) {
            ObservableList<PieChart.Data> dataIncomePie = FXCollections.observableArrayList();
            dataIncomePie.addAll(incomePie);
            left_pie.setData(dataIncomePie);
        }
    }


    private void processPieChartData() {
        incomeList = new ArrayList<>();
        expenseList = new ArrayList<>();
        incomePie = new ArrayList<>();
        expensePie = new ArrayList<>();

        for (Categories e : Main.getExpenseCategories()) {
            expensePie.add(new PieChart.Data(e.getName(), 0));
            expenseList.add(new Transactions(0, selectedWallet.getCurrency(), e.getName(), "", LocalDate.now()));
        }

        for (Categories i : Main.getIncomeCategories()) {
            incomePie.add(new PieChart.Data(i.getName(), 0));
            incomeList.add(new Transactions(0, selectedWallet.getCurrency(), i.getName(), "", LocalDate.now()));
        }

        for (ArrayList<Transactions> list : transactionsByMonth) {
            if (list.get(0).getDate().getMonthValue() == selected_month.getMonthValue() &&
                    list.get(0).getDate().getYear() == selected_month.getYear()) {
                for (Transactions t : list) {
                    if (t.getAmount() >= 0)
                        for (PieChart.Data data : incomePie) {
                            if (data.getName().equals(t.getCategory())) {
                                data.setPieValue(data.getPieValue() + t.getAmount());
                                // Increase income amount of the category
                                for (Transactions trans : incomeList) {
                                    if (trans.getCategory().equals(t.getCategory())) {
                                        trans.setAmount(trans.getAmount() + t.getAmount());
                                        break;
                                    }

                                }
                                break;
                            }
                        }
                    else
                        for (PieChart.Data data : expensePie) {
                            if (data.getName().equals(t.getCategory())) {
                                data.setPieValue(data.getPieValue() - t.getAmount());
                                // Decrease income amount of the category
                                for (Transactions trans : expenseList) {
                                    if (trans.getCategory().equals(t.getCategory())) {
                                        trans.setAmount(trans.getAmount() + t.getAmount());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                }
                break;
            }
        }

        incomePie.removeIf(data -> data.getPieValue() == 0);
        expensePie.removeIf(data -> data.getPieValue() == 0);
    }

    private void populateIncomeGrid() {
        income_grid.getChildren().clear();
        try {
            int column = 0;
            int row = 1;
//            incomeList.sort(new Comparator<Transactions>() {
//                @Override
//                public int compare(Transactions t1, Transactions t2) {
//                    if (t1.getAmount() < t2.getAmount())
//                        return -1;
//                    else if (t1.getAmount() > t2.getAmount())
//                        return 1;
//                    return 0;
//                }
//            });
            for (Transactions data : incomeList) {
                if (data.getAmount() > 0) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/sample/views/ReportCard.fxml"));

                    ReportCardControl reportCardControl = new ReportCardControl(data.getCategory(), data.getAmount(), selectedWallet.getCurrency());
                    fxmlLoader.setController(reportCardControl);
                    AnchorPane pane = fxmlLoader.load();

                    if (column == 1) {
                        column = 0;
                        ++row;
                    }

                    income_grid.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(0));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateExpenseGrid() {
        expense_grid.getChildren().clear();
        try {
            int column = 0;
            int row = 1;
            expenseList.sort(new Comparator<Transactions>() {
                @Override
                public int compare(Transactions t1, Transactions t2) {
                    if (t1.getAmount() < t2.getAmount())
                        return -1;
                    else if (t1.getAmount() > t2.getAmount())
                        return 1;
                    return 0;
                }
            });
            for (Transactions data : expenseList) {
                if (data.getAmount() < 0) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/sample/views/ReportCard.fxml"));

                    ReportCardControl reportCardControl = new ReportCardControl(data.getCategory(), data.getAmount(), selectedWallet.getCurrency());
                    fxmlLoader.setController(reportCardControl);
                    AnchorPane pane = fxmlLoader.load();

                    if (column == 1) {
                        column = 0;
                        ++row;
                    }

                    expense_grid.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(0));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



