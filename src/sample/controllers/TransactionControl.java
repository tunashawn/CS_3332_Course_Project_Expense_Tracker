package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.apache.commons.lang3.StringUtils;
import sample.Main;
import sample.models.Transactions;
import sample.models.Wallets;
import sample.utils.Utils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class TransactionControl {
    @FXML
    private GridPane grid;
    @FXML
    private Label wallet_name, balance_label, month_label, income, expense, total_label;
    @FXML
    private JFXButton previous_month, next_month;

    private MainFrameControl mainFrameControl;
    private ArrayList<Transactions> transactionList;
    private ArrayList<ArrayList<Transactions>> trans_by_month = new ArrayList<ArrayList<Transactions>>();
    private Wallets wallet;
    private LocalDate current_date;

    public TransactionControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;
        this.wallet = mainFrameControl.getSelectedWallet();
        if (wallet != null) {
            transactionList = wallet.getTransactionList();
        }
        if (mainFrameControl.getTransactionSelectingDate() == null)
            current_date = LocalDate.now();
        else
            current_date = mainFrameControl.getTransactionSelectingDate();

    }

    @FXML
    private void initialize() throws IOException {

        setData();
        populateTransactionHistory();
        previous_month.setOnAction(event -> decreaseMonth());
        next_month.setOnAction(event -> increaseMonth());
    }


    /**
     * Populate My Order pane
     */
    public void populateTransactionHistory() throws IOException {
        trans_by_month = Utils.groupTransactionsByMonth(transactionList);

        if (transactionList != null && transactionList.size() > 0) {
            int column = 0;
            int row = 1;
            grid.getChildren().clear();
            for (ArrayList<Transactions> month : trans_by_month) {

                if (month.get(0).getDate().getMonth() == current_date.getMonth()
                    && month.get(0).getDate().getYear() == current_date.getYear()) {

                    month.sort(Collections.reverseOrder());

                    ArrayList<ArrayList<Transactions>> trans_by_day = Utils.groupTransactionsByDay(month, month.get(0).getDate());

                    updateLeftPanel(month);

                    grid.getChildren().clear();
                    for (ArrayList<Transactions> trans_of_day : trans_by_day) {

                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/sample/views/TransactionGroupCard.fxml"));

                        TransactionGroupCardControl transactionGroupCardControl = new TransactionGroupCardControl(trans_of_day, mainFrameControl);

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
                } else
                    showNothingToDisplay();
            }
        } else
            showNothingToDisplay();
    }

    private void showNothingToDisplay() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/NothingToDisplay.fxml"));
            AnchorPane pane = fxmlLoader.load();
            grid.getChildren().clear();
            grid.add(pane, 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        if (wallet != null) {
            wallet_name.setText(wallet.getName());
            balance_label.setText(Main.formatMoney(wallet.getBalance(), wallet.getCurrency()));
        }

    }
    private String getMonthLabelValue(){
        String mon_year = "";
        if (current_date.getYear() == LocalDate.now().getYear() && current_date.getMonthValue() == LocalDate.now().getMonthValue())
            mon_year = "This Month";
        else
            mon_year = StringUtils.capitalize(current_date.getMonth().toString().toLowerCase(Locale.ROOT)) + " " + current_date.getYear();
        return mon_year;
    }

    private void increaseMonth() {
        try {
            current_date = current_date.plusMonths(1);
            mainFrameControl.setTransactionSelectingDate(current_date);
            month_label.setText(getMonthLabelValue());
            income.setText("0");
            expense.setText("0");
            total_label.setText("0");
            populateTransactionHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void decreaseMonth() {
        try {
            current_date = current_date.minusMonths(1);
            mainFrameControl.setTransactionSelectingDate(current_date);
            month_label.setText(getMonthLabelValue());
            income.setText("0");
            expense.setText("0");
            total_label.setText("0");
            populateTransactionHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateLeftPanel(ArrayList<Transactions> list) {
        month_label.setText(getMonthLabelValue());
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
