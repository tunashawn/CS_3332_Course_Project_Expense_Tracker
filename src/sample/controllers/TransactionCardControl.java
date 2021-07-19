package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.models.Transactions;


public class TransactionCardControl {
    private final Stage thisStage;
    @FXML
    AnchorPane itemcard;
    @FXML private ImageView icon;
    @FXML private Label title, note, price;

    private Transactions transaction;
    public TransactionCardControl(Transactions transaction) {
        this.transaction= transaction;
        thisStage = new Stage();
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    @FXML
    private void initialize() {
        title.setText(transaction.getCategory());
        note.setText(transaction.getNote());
        if (transaction.getType() == 1)
            price.setStyle("-fx-text-fill: green");
        price.setText(String.valueOf(transaction.getAmount()));

        icon.setImage(new Image("sample/categories/food.png"));
        itemcard.setOnMouseClicked(event -> clickOnCard());
    }

    @FXML private void clickOnCard(){
        System.out.println("clicked");
    }


    public void setData(){

    }


}