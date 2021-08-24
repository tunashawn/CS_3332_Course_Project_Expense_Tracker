package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Transactions;

import java.io.IOException;
import java.util.Optional;


public class TransactionCardControl {
    private final Stage thisStage;
    @FXML AnchorPane itemcard;
    @FXML ImageView icon;
    @FXML Label title, note, price;

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

        itemcard.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        openEditingTransaction(transaction);
                    }
                }
        );
    }

    private void openEditingTransaction(Transactions t){
        AddTransactionControl addTransactionControl = new AddTransactionControl(mainFrameControl, transaction);
        addTransactionControl.showStage();
    }


}