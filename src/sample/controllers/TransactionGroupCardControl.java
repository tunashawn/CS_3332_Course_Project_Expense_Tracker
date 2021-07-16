package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class TransactionGroupCardControl {
    private MainFrameControl mainFrameControl;
    private final Stage thisStage;

    @FXML private GridPane grid;


    public TransactionGroupCardControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;

        thisStage = new Stage();

        // Load the FXML file
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
        int column = 0;
        int row = 1;
        Random rand = new Random();
        int n = rand.nextInt((4 - 1) + 1) + 1;
        try {
            for (int i = 0; i <=n; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/sample/views/TransactionCard.fxml"));

                TransactionCardControl transactionCardControl = new TransactionCardControl(mainFrameControl);
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
