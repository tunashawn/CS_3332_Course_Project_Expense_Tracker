package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class TransactionCardControl {
    private MainFrameControl mainFrameControl;
    private final Stage thisStage;

    public TransactionCardControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;

        thisStage = new Stage();

        // Load the FXML file
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

    }

    public void setData(){

    }


}