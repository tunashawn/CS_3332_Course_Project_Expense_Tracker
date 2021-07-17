package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.models.Category;
import sample.models.Transaction;

import java.io.IOException;
import java.util.ArrayList;

public class MainFrameControl {
    private final Stage thisStage;

    @FXML JFXButton new_transaction_button;
    @FXML JFXButton transaction_button;
    @FXML JFXButton report_button;
    @FXML JFXButton planning_button;
    @FXML JFXButton more_button;

    @FXML
    Label title_label;
    @FXML AnchorPane content_panel;
    @FXML
    Pane detail_pane;

    @FXML AnchorPane root;




    private ArrayList<Transaction> transactionList = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();

    public MainFrameControl() {
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/MainFrame.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Expend Tracker");
            thisStage.getIcons().add(new Image("/sample/icons/app_logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        categories.add(new Category("Food", new Image("sample/icons/tomato_96px.png")));
        categories.add(new Category("Chicken", new Image("sample/icons/calendar_icon.png")));
    }

    public void showStage() {
        thisStage.showAndWait();
    }


    /**
     * The initialize() method allows you set setup your scene, adding actions, configuring nodes, etc.
     */
    @FXML
    private void initialize() throws IOException {
        openTransactionView();

        new_transaction_button.setOnAction(event -> {
            try {
                openCreateNewTransaction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        transaction_button.setOnAction(event -> {
            try {
                openTransactionView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        report_button.setOnAction(event -> {
            try {
                openReportView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    // CREATE NEW TRANSACTION FRAME
    private void openCreateNewTransaction() throws IOException {
        AddTransactionControl addTransactionControl = new AddTransactionControl(this);
        addTransactionControl.showStage();
    }

    private void openTransactionView() throws IOException {
        title_label.setText("Transactions");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/views/TransactionsView.fxml"));

        TransactionControl transactionControl = new TransactionControl(this);
        fxmlLoader.setController(transactionControl);
        AnchorPane pane = fxmlLoader.load();
        detail_pane.getChildren().clear();
        detail_pane.getChildren().add(pane);
    }

    private void openReportView() throws IOException {
        title_label.setText("Reports");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/views/ReportView.fxml"));

        ReportFrameControl reportFrameControl = new ReportFrameControl(this);
        fxmlLoader.setController(reportFrameControl);
        AnchorPane pane = fxmlLoader.load();
        reportFrameControl.setData();
        detail_pane.getChildren().clear();
        detail_pane.getChildren().add(pane);
    }

    public void createNewTransaction(Transaction t){
        transactionList.add(t);
    }
    public void deleteTransaction(Transaction t){
        transactionList.remove(t);
    }

    public ArrayList<Transaction> getTransactionList(){
        return transactionList;
    }

    public ArrayList<Category> getCategories(){
        return categories;
    }

}
