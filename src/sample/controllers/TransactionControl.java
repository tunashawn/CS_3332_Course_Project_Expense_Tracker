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
import sample.models.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TransactionControl {
    private final Stage thisStage;


    @FXML
    private Label lblFromController1;
    @FXML
    private TextField txtToFirstController;
    @FXML
    private Button btnSetLayout1Text;
    @FXML private GridPane grid;

    private MainFrameControl mainFrameControl;
    private ArrayList<Transaction> transactionList;
    private ArrayList<ArrayList<Transaction>> groupItemList;

    public TransactionControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/TransactionsView.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        createGroupItemList();
    }

    public void showStage() {
        thisStage.show();
    }

    @FXML
    private void initialize() {
        populateTransactionHistory();
        //createGroupItemList();
    }

    public void createGroupItemList(){
        if (mainFrameControl.getTransactionList() != null) {
            this.transactionList = mainFrameControl.getTransactionList();
            System.out.println("before");
            System.out.println(transactionList);
            Collections.sort(transactionList);
            System.out.println("after");
            System.out.println(transactionList);
        }
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

                    TransactionGroupCardControl transactionGroupCardControl = new TransactionGroupCardControl(transactionList);
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
