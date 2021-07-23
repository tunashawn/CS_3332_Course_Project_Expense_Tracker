package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Categories;
import sample.models.Transactions;


public class TransactionCardControl {
    private final Stage thisStage;
    @FXML
    AnchorPane itemcard;
    @FXML private ImageView icon;
    @FXML private Label title, note, price;
    @FXML private JFXButton delete_button;

    private MainFrameControl mainFrameControl;
    private Transactions transaction;

    public TransactionCardControl(Transactions transaction, MainFrameControl mainFrameControl) {
        this.transaction= transaction;
        this.mainFrameControl = mainFrameControl;
        thisStage = new Stage();
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    @FXML
    private void initialize() {
        title.setText(transaction.getCategory());

        note.setText(transaction.getNote());
        if (transaction.getAmount() >= 0)
            price.setStyle("-fx-text-fill: green");

        price.setText(Main.formatMoney(transaction.getAmount(), transaction.getCurrency()));

        icon.setImage(new Image("/sample/categories/" + transaction.getCategory().toLowerCase() + ".png"));

        delete_button.setOnAction(event -> setDelete_button());
    }


    public void setDelete_button(){
        mainFrameControl.getSelectedWallet().deleteATransaction(transaction);
        mainFrameControl.refreshView();
    }


}