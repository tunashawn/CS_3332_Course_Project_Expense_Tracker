package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.models.Transaction;

import java.beans.EventHandler;
import java.io.IOException;
import java.util.ArrayList;


public class TransactionCardControl {
    private final Stage thisStage;
    @FXML
    AnchorPane itemcard;

    private ArrayList<Transaction> transactionList;
    public TransactionCardControl(ArrayList<Transaction> transactionList) {
        this.transactionList= transactionList;
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/TransactionCard.fxml"));
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
        itemcard.setOnMouseClicked(event -> clickOnCard());
    }

    @FXML private void clickOnCard(){
        System.out.println("clicked");
    }


    public void setData(){

    }


}