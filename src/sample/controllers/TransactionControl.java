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
import java.util.ArrayList;
import java.util.Collections;

public class TransactionControl {
    private final Stage thisStage;


    @FXML private GridPane grid;
    @FXML private Label wallet_name, balance_label, month_label, income, expense, total_label;
    @FXML private JFXButton previous_month, next_month;

    private MainFrameControl mainFrameControl;
    private ArrayList<Transactions> transactionList;
    private ArrayList<ArrayList<Transactions>> groupItemList = new ArrayList<ArrayList<Transactions>>();
    private Wallets w;

    public TransactionControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;
        this.w = mainFrameControl.getSelectedWallet();
        if (w != null) {
            transactionList = w.getTransactionList();
        }
        thisStage = new Stage();
        createGroupItemList();
    }

    public void showStage() {
        thisStage.show();
    }

    @FXML
    private void initialize() throws IOException {
        setData();
        populateTransactionHistory();
    }

    public void createGroupItemList(){
        if (transactionList != null && transactionList.size() > 0) {
            transactionList.sort(Collections.reverseOrder());

            String date = transactionList.get(0).getDateAsString();
            ArrayList<Transactions> list = new ArrayList<>();
            for (Transactions t : transactionList) {
                if (!t.getDateAsString().equals(date)) {
                    groupItemList.add(list);
                    list = new ArrayList<>();
                    date = t.getDateAsString();
                }
                list.add(t);
            }
            groupItemList.add(list);
        }
    }



    /**
     * Populate My Order pane
     */
    public void populateTransactionHistory() throws IOException {
        if (transactionList != null && transactionList.size() > 0){
            String date = transactionList.get(0).getDateAsString();
            int column = 0;
            int row = 1;
            for (ArrayList<Transactions> g : groupItemList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/sample/views/TransactionGroupCard.fxml"));

                TransactionGroupCardControl transactionGroupCardControl = new TransactionGroupCardControl(g, mainFrameControl);
                fxmlLoader.setController(transactionGroupCardControl);
                AnchorPane pane = fxmlLoader.load();

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                grid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(15, 0, 5, 100));
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

}
