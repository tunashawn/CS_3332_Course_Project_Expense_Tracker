package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.models.Transaction;

import java.io.IOException;
import java.util.ArrayList;

public class TransactionGroupCardControl {
    private final Stage thisStage;

    @FXML private GridPane grid;
    @FXML private Label day_number, day, date, total;

    private ArrayList<Transaction> transactionList;

    public TransactionGroupCardControl(ArrayList<Transaction> transactionList) {
        this.transactionList = transactionList;
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/TransactionGroupCard.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    @FXML
    private void initialize() {
        populateTransactionGroup();
    }

    public void setData(){

    }


    /**
     * Populate My Order pane
     */
    private void populateTransactionGroup() {
        if (transactionList != null){
            int column = 0;
            int row = 1;
            int number_of_item = 3;
            try {
                for (int i = 0; i < number_of_item; i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/sample/views/TransactionCard.fxml"));

                    TransactionCardControl transactionCardControl = new TransactionCardControl(transactionList);
                    fxmlLoader.setController(transactionCardControl);
                    AnchorPane pane = fxmlLoader.load();
                    transactionCardControl.setData();

                    if (column == 1) {
                        column = 0;
                        ++row;
                    }

                    grid.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

}
