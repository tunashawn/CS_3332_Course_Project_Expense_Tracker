package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.models.Categories;
import sample.models.Transactions;
import sample.models.Wallets;
import sample.utils.Utils;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainFrameControl {
    private final Stage thisStage;

    @FXML
    Button wallet_button;
    @FXML
    Button new_transaction_button;
    @FXML
    Button transaction_button;
    @FXML
    Button report_button;
    @FXML
    Button more_button;
    @FXML
    ImageView wallet_icon;
    @FXML
    Label title_label;
    @FXML
    Pane detail_pane;


    private ArrayList<Wallets> walletList = new ArrayList<>();
    private ArrayList<Categories> categories = new ArrayList<>();
    private Wallets selectedWallet;
    private String selecting_view = "";
    private LocalDate trans_selecting_date;

    private Transactions previous_transaction;

    public MainFrameControl() throws IOException {
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/MainFrame.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Expense Tracker");
            thisStage.getIcons().add(new Image("/sample/icons/app_logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        thisStage.setOnHidden(event -> System.exit(0));


        wallet_button.setOnAction(event -> openMyWallets());
        wallet_button.setTooltip(Utils.getToolTip("Wallets"));

        new_transaction_button.setOnAction(event -> openCreateNewTransaction());
        new_transaction_button.setTooltip(Utils.getToolTip("Add New Transactions"));

        transaction_button.setOnAction(event -> openTransactionView());
        transaction_button.setTooltip(Utils.getToolTip("Transactions"));

        report_button.setOnAction(event -> openReportView(selectedWallet));
        report_button.setTooltip(Utils.getToolTip("Reports"));

        more_button.setOnAction(event -> openToolsView());
        more_button.setTooltip(Utils.getToolTip("Tools"));

    }

    public void openMyWallets() {
        try {
            title_label.setText("My Wallets");
            selecting_view = "My Wallets";
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
        if (selectedWallet != null) {
            AddTransactionControl addTransactionControl = new AddTransactionControl(this);
            addTransactionControl.showStage();
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "PLEASE CREATE A WALLET BEFORE USING THIS FUNCTION!", ButtonType.OK);
            alert.setTitle("ERROR!");

            alert.getDialogPane().setStyle("-fx-font-size: 15; -fx-font-weight: bold");
            alert.show();
        }
    }

    public void openTransactionView() {
        try {
            title_label.setText("Transactions");
            selecting_view = "Transactions";
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

    public void openReportView(Wallets selectedWallet) {
        try {
            title_label.setText("Reports");
            selecting_view = "Reports";
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/ReportView.fxml"));

            ReportFrameControl reportFrameControl = new ReportFrameControl(this, selectedWallet, walletList);
            fxmlLoader.setController(reportFrameControl);
            AnchorPane pane = fxmlLoader.load();
            detail_pane.getChildren().clear();
            detail_pane.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openToolsView(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/ToolsView.fxml"));
            ToolsViewControl e = new ToolsViewControl();
            fxmlLoader.setController(e);
            AnchorPane pane = fxmlLoader.load();
            detail_pane.getChildren().clear();
            detail_pane.getChildren().add(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void serializeTransactions() {
        try {
            walletList.remove(selectedWallet);
            walletList.add(0, selectedWallet);
            FileOutputStream file = new FileOutputStream("Transaction Record.ser");
            ObjectOutputStream writer = new ObjectOutputStream(file);
            for (Wallets obj : walletList) {
                if (obj != null)
                    writer.writeObject(obj);
            }
            writer.close();
            file.close();
            System.out.println("Serializing completed");
        } catch (Exception ex) {
            System.err.println("failed to write " + "Transaction Record.ser" + ", " + ex);
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
        } catch (Exception ex) {
            System.err.println("failed to read " + "Transaction Record.ser" + ", " + ex);
        }
    }


    public ArrayList<Categories> getCategories() {
        return categories;
    }

    public void addNewWallet(Wallets w) {
        walletList.add(w);
    }

    public Wallets getSelectedWallet() {
        return selectedWallet;
    }

    public void setSelectedWallet(Wallets w) {
        if (w != null) {
            selectedWallet = w;
            wallet_icon.setImage(new Image("sample/icons/wallets/" + w.getIcon_name() + ".png"));
        } else {
            selectedWallet = null;
            wallet_icon.setImage(new Image("sample/icons/wallets/wallet.png"));
        }
    }

    public String getSelecting_view() {
        return selecting_view;
    }

    public Transactions getPrevious_transaction() {
        return previous_transaction;
    }

    public void setPrevious_transaction(Transactions t) {
        previous_transaction = t;
    }

    public void refreshView() {
        switch (getSelecting_view()) {
            case "My Wallets" -> openMyWallets();
            case "Transactions" -> openTransactionView();
            case "Reports" -> openReportView(selectedWallet);
        }
    }

    public LocalDate getTransactionSelectingDate() {
        return trans_selecting_date;
    }

    public void setTransactionSelectingDate(LocalDate date) {
        trans_selecting_date = date;
    }

    public ArrayList<Wallets> getWalletsList(){
        return this.walletList;
    }
}
