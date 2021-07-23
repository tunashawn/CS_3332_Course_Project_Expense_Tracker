package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Transactions;
import sample.models.Wallets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class TransactionControl {
    @FXML private GridPane grid;
    @FXML private Label wallet_name, balance_label, month_label, income, expense, total_label;
    @FXML private JFXButton previous_month, next_month;

    private MainFrameControl mainFrameControl;
    private ArrayList<Transactions> transactionList;
    private ArrayList<ArrayList<Transactions>> groupItemList = new ArrayList<ArrayList<Transactions>>();
    private Wallets w;
    private LocalDate current_date;

    public TransactionControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;
        this.w = mainFrameControl.getSelectedWallet();
        if (w != null) {
            transactionList = w.getTransactionList();
        }
        current_date = LocalDate.now();

    }

    @FXML
    private void initialize() throws IOException {

        setData();
        populateTransactionHistory();
        previous_month.setOnAction(event -> decreaseMonth());
        next_month.setOnAction(event -> increaseMonth());
    }

    public void createGroupItemList(){
        if (transactionList != null && transactionList.size() > 0) {
            transactionList.sort(Collections.reverseOrder());

            LocalDate date = transactionList.get(0).getDate();
            ArrayList<Transactions> list = new ArrayList<>();
            for (Transactions t : transactionList) {
                if (t.getDate().getMonth() != date.getMonth() ) {
                    groupItemList.add(list);
                    list = new ArrayList<>();
                    date = t.getDate();
                }
                list.add(t);
            }
            groupItemList.add(list);
            System.out.println("list:");
            System.out.println(list);
        }
    }



    /**
     * Populate My Order pane
     */
    public void populateTransactionHistory() throws IOException {
        createGroupItemList();
        if (transactionList != null && transactionList.size() > 0){
            int column = 0;
            int row = 1;
            for (ArrayList<Transactions> g : groupItemList) {

                if (g.get(0).getDate().getMonth() == current_date.getMonth() && g.get(0).getDate().getYear() == current_date.getYear()){
                    g.sort(Collections.reverseOrder());
                    LocalDate date = g.get(0).getDate();
                    ArrayList<Transactions> list = new ArrayList<>();
                    ArrayList<ArrayList<Transactions>> trans_of_month = new ArrayList<ArrayList<Transactions>>();;
                    for (Transactions t : g) {
                        if (t.getDate().getDayOfMonth() != date.getDayOfMonth() ) {
                            trans_of_month.add(list);
                            list = new ArrayList<>();
                            date = t.getDate();
                        }
                        list.add(t);
                    }
                    trans_of_month.add(list);

                    updateLeftPanel(g);

                    for (ArrayList<Transactions> t : trans_of_month) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/sample/views/TransactionGroupCard.fxml"));

                        TransactionGroupCardControl transactionGroupCardControl = new TransactionGroupCardControl(t, mainFrameControl);

                        fxmlLoader.setController(transactionGroupCardControl);
                        AnchorPane pane = fxmlLoader.load();

                        if (column == 1) {
                            column = 0;
                            ++row;
                        }

                        grid.add(pane, column++, row);
                        GridPane.setMargin(pane, new Insets(15, 0, 5, 100));
                    }
                    break;
                }
            }
        } else
            showNothingToDisplay();
    }

    private void showNothingToDisplay(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/NothingToDisplay.fxml"));
            AnchorPane pane = fxmlLoader.load();
            grid.getChildren().clear();
            grid.add(pane,0,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setData(){
        if (w != null) {
            wallet_name.setText(w.getName());
            balance_label.setText(Main.formatMoney(w.getBalance(),w.getCurrency()));
        }

    }

    private void increaseMonth(){
        try {
            current_date = current_date.plusMonths(1);
            populateTransactionHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void decreaseMonth(){
        try {
            current_date = current_date.minusMonths(1);
            populateTransactionHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateLeftPanel(ArrayList<Transactions> list){
        month_label.setText(current_date.getMonth().toString());
        double total_income = 0;
        double total_expense = 0;
        for (Transactions t : list) {
            if (t.getAmount() > 0)
                total_income += t.getAmount();
            else
                total_expense += t.getAmount();
        }
        income.setText(String.valueOf(total_income));
        expense.setText(String.valueOf(total_expense));
        total_label.setText(String.valueOf(total_expense + total_income));
    }

}
