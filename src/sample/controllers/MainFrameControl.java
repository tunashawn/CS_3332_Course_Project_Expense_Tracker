package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.models.Categories;
import sample.models.Transactions;
import sample.models.Wallets;

import java.io.*;
import java.util.ArrayList;

public class MainFrameControl {
    private final Stage thisStage;

    @FXML Button wallet_button;
    @FXML JFXButton new_transaction_button;
    @FXML JFXButton transaction_button;
    @FXML JFXButton report_button;
    @FXML JFXButton planning_button;
    @FXML JFXButton more_button;
    @FXML
    ImageView wallet_icon;
    @FXML
    Label title_label;
    @FXML AnchorPane content_panel;
    @FXML
    Pane detail_pane;

    @FXML AnchorPane root;

    private ArrayList<Wallets> walletList = new ArrayList<>();
    private ArrayList<Transactions> transactionsOfSelectedWallet = new ArrayList<>();
    private ArrayList<Categories> categories = new ArrayList<>();
    private Wallets selectedWallet;


    public MainFrameControl() throws IOException {
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



        categories.add(new Categories("Food", new Image("sample/icons/tomato_96px.png")));
        categories.add(new Categories("Chicken", new Image("sample/icons/calendar_icon.png")));
    }

    public void showStage() {
        thisStage.showAndWait();
    }


    /**
     * The initialize() method allows you set setup your scene, adding actions, configuring nodes, etc.
     */
    @FXML
    private void initialize() {

        deserializeTransactions();

        openTransactionView();

        thisStage.setOnCloseRequest(event -> serializeTransactions());

        wallet_button.setOnAction(event -> openMyWallets());

        new_transaction_button.setOnAction(event -> openCreateNewTransaction());

        transaction_button.setOnAction(event -> openTransactionView());

        report_button.setOnAction(event -> openReportView());


    }

    private void openMyWallets() {
        try {
            title_label.setText("My Wallets");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/MyWalletsView.fxml"));
            MyWalletControl myWalletControl = new MyWalletControl(this, walletList);
            fxmlLoader.setController(myWalletControl);
            AnchorPane pane = fxmlLoader.load();
            detail_pane.getChildren().clear();
            detail_pane.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // CREATE NEW TRANSACTION FRAME
    private void openCreateNewTransaction() {
        AddTransactionControl addTransactionControl = new AddTransactionControl(this);
        addTransactionControl.showStage();
    }

    private void openTransactionView() {
        try {
            title_label.setText("Transactions");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/TransactionsView.fxml"));
            TransactionControl transactionControl = new TransactionControl(this);
            fxmlLoader.setController(transactionControl);
            AnchorPane pane = fxmlLoader.load();
            detail_pane.getChildren().clear();
            detail_pane.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openReportView() {
        try {
            title_label.setText("Reports");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/ReportView.fxml"));

            ReportFrameControl reportFrameControl = new ReportFrameControl(this);
            fxmlLoader.setController(reportFrameControl);
            AnchorPane pane = fxmlLoader.load();
            reportFrameControl.setData();
            detail_pane.getChildren().clear();
            detail_pane.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void serializeTransactions() {
        try {
            walletList.remove(selectedWallet);
            walletList.add(0,selectedWallet);
            FileOutputStream file = new FileOutputStream("Transaction Record.ser");
            ObjectOutputStream writer = new ObjectOutputStream(file);
            for (Wallets obj : walletList) {
                writer.writeObject(obj);
            }
            writer.close();
            file.close();
            System.out.println("Serializing completed");
        } catch (Exception ex) {
            System.err.println("failed to write " + "Transaction Record.ser" + ", "+ ex);
        }
    }

    private void deserializeTransactions() {
        try {
            FileInputStream file = new FileInputStream("Transaction Record.ser");
            ObjectInputStream reader = new ObjectInputStream(file);
            while (true) {
                try {
                    Wallets obj = (Wallets) reader.readObject();
                    walletList.add(obj);

                } catch (Exception ex) {
                    System.err.println("end of reader file ");
                    break;
                }
            }
            setSelectedWallet(walletList.get(0));
            walletList.forEach(System.out::println);
        } catch (Exception ex) {
            System.err.println("failed to read " + "Transaction Record.ser" + ", "+ ex);
        }
    }



    public void createNewTransaction(Transactions t){
        transactionsOfSelectedWallet.add(t);
    }

    public void deleteTransaction(Transactions t){
        transactionsOfSelectedWallet.remove(t);
    }

    public ArrayList<Transactions> getTransactionsOfSelectedWallet(){
        return transactionsOfSelectedWallet;
    }

    public ArrayList<Categories> getCategories(){
        return categories;
    }

    public void setSelectedWallet(Wallets w){
        if (w != null) {
            selectedWallet = w;
            wallet_icon.setImage(new Image("sample/icons/wallets/" + w.getIcon_name() + ".png"));
        } else {
            selectedWallet = null;
            wallet_icon.setImage(new Image("sample/icons/wallets/wallet.png"));
        }
    }

    public void addNewWallet(Wallets w){
        walletList.add(w);
    }

    public Wallets getSelectedWallet(){
        return selectedWallet;
    }


}
