package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.models.Transaction;

import java.io.IOException;
import java.util.ArrayList;
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
    private ArrayList<ArrayList<Transaction>> groupItemList = new ArrayList<ArrayList<Transaction>>();

    public TransactionControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;
        thisStage = new Stage();
        createGroupItemList();
    }

    public void showStage() {
        thisStage.show();
    }

    @FXML
    private void initialize() throws IOException {
        populateTransactionHistory();
    }

    public void createGroupItemList(){
        if (mainFrameControl.getTransactionList() != null && mainFrameControl.getTransactionList().size() > 0) {
            this.transactionList = mainFrameControl.getTransactionList();

            transactionList.sort(Collections.reverseOrder());

            String date = transactionList.get(0).getDateAsString();
            ArrayList<Transaction> list = new ArrayList<>();
            for (Transaction t : transactionList) {
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
            for (ArrayList<Transaction> g : groupItemList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/sample/views/TransactionGroupCard.fxml"));

                TransactionGroupCardControl transactionGroupCardControl = new TransactionGroupCardControl(g);
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
        }
    }

}
