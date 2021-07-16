package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TransactionControl {
    private final Stage thisStage;

    private final MainFrameControl mainFrameControl;


    @FXML
    private Label lblFromController1;
    @FXML
    private TextField txtToFirstController;
    @FXML
    private Button btnSetLayout1Text;
    @FXML private GridPane grid;

    public TransactionControl(MainFrameControl controller) {
        this.mainFrameControl = controller;
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/TransactionsView.fxml"));

            loader.setController(this);

            thisStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Show the stage that was loaded in the constructor
     */
    public void showStage() {
        thisStage.show();
    }

    @FXML
    private void initialize() {
        populateTransactionHistory();
    }

    public void setData(){

    }



    /**
     * Populate My Order pane
     */
    public void populateTransactionHistory() {
            int column = 0;
            int row = 1;
            try {
                for (int i = 0; i <=7; i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/sample/views/TransactionGroupCard.fxml"));

                    TransactionGroupCardControl transactionGroupCardControl = new TransactionGroupCardControl(mainFrameControl);
                    fxmlLoader.setController(transactionGroupCardControl);
                    AnchorPane pane = fxmlLoader.load();
                    transactionGroupCardControl.setData();

                    if (column == 1) {
                        column = 0;
                        ++row;
                    }

                    grid.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(15, 0, 5, 200));
                }

        } catch (IOException e) {
                e.printStackTrace();
            }


    }

}
