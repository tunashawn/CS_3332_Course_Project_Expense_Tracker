package sample.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Categories;
import sample.models.Transactions;
import sample.models.Wallets;
import sample.utils.Utils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.UnaryOperator;


public class AddTransactionControl {
    private final Stage thisStage;

    @FXML Button cancel_button;
    @FXML Button save_button;
    @FXML Button delete_btn;

    @FXML TextField amount_textfield;
    @FXML ComboBox<String> category_combobox, wallet_cbb;
    @FXML TextField note_textfield;
    @FXML DatePicker date_datepicker;
    @FXML RadioButton income, expense;
    @FXML Label warning;
    @FXML Label currency_label;
    @FXML ImageView category_icon;
    @FXML Label title;
    private int type = -1;

    private boolean edit_mode = false;
    Transactions editingTransaction;
    private MainFrameControl mainFrameControl;

    public AddTransactionControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/AddTransactionFrame.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Add Transaction");
            thisStage.getIcons().add(new Image("/sample/icons/app_logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AddTransactionControl(MainFrameControl mainFrameControl, Transactions transaction) {
        this.mainFrameControl = mainFrameControl;
        edit_mode = true;
        editingTransaction = transaction;
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/AddTransactionFrame.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Editing Transaction");
            thisStage.getIcons().add(new Image("/sample/icons/app_logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }


    private DecimalFormat format = new DecimalFormat("#.0;-#.0");

    /**
     * The initialize() method allows you set setup your scene, adding actions, configuring nodes, etc.
     */
    @FXML
    private void initialize() throws IOException {

        for (Wallets wallets : mainFrameControl.getWalletsList()) {
            wallet_cbb.getItems().add(wallets.getName());
        }

        wallet_cbb.getSelectionModel().select(mainFrameControl.getSelectedWallet().getName());

        wallet_cbb.setOnAction(event -> setCurrencyForSelectedWallet());

        String CURRENCY = getCurrencyLetter(mainFrameControl.getSelectedWallet().getCurrency());

        currency_label.setText(CURRENCY);

        warning.setVisible(false);

        cancel_button.setOnAction(event -> setCancel_button());

        save_button.setOnAction(event -> setSave_button());

        expense.setSelected(true);

        date_datepicker.setValue(LocalDate.now());
        if (mainFrameControl.getPrevious_transaction() != null){
            income.setSelected(mainFrameControl.getPrevious_transaction().getAmount() >= 0);
            expense.setSelected(mainFrameControl.getPrevious_transaction().getAmount() < 0);
            date_datepicker.setValue(mainFrameControl.getPrevious_transaction().getDate());
        }

        if (expense.isSelected()) {
            type = -1;
            category_combobox.setPromptText("Select category");
            for (Categories i: Main.getExpenseCategories()){
                category_combobox.getItems().add(i.getName());
            }
        } else {
            type = 1;
            category_combobox.setPromptText("Select category");
            for (Categories i: Main.getIncomeCategories()){
                category_combobox.getItems().add(i.getName());
            }
        }

        income.setOnAction(event -> setIncomeAction());
        expense.setOnAction(event -> setExpenseAction());

        delete_btn.setVisible(false);
        delete_btn.setOnAction(event -> setDelete_btn());

        category_combobox.setOnAction(event -> {
            for (Categories c: Main.getExpenseCategories()){
                if (c.getName().equals(category_combobox.getValue())) {
                    category_icon.setImage(c.getIcon());
                    break;
                }
            }
            for (Categories c: Main.getIncomeCategories()){
                if (c.getName().equals(category_combobox.getValue())){
                    category_icon.setImage(c.getIcon());
                    break;
                }
            }
        });
        if (edit_mode){
            setData(editingTransaction);

        }
    }

    private void setCurrencyForSelectedWallet(){
        for (Wallets wallets : mainFrameControl.getWalletsList()) {
            if (wallets.getName().equals(wallet_cbb.getSelectionModel().getSelectedItem())){
                currency_label.setText(getCurrencyLetter(wallets.getCurrency()));
                break;
            }
        }
    }

    private void setIncomeAction(){
        type = 1;
        expense.setSelected(false);
        category_combobox.getItems().clear();
        for (Categories i: Main.getIncomeCategories()){
            category_combobox.getItems().add(i.getName());
        }
    }
    private void setExpenseAction(){
        type = -1;
        income.setSelected(false);
        category_combobox.getItems().clear();
        for (Categories i: Main.getExpenseCategories()){
            category_combobox.getItems().add(i.getName());
        }
    }

    private String getCurrencyLetter(String currency){
        String CURRENCY = "";
        switch (currency){
            case "Vietnam Dong": CURRENCY = "₫"; break;
            case "US Dollar": CURRENCY = "$"; break;
            case "Pound": CURRENCY = "£"; break;
            case "Euro": CURRENCY = "€"; break;
            case "Yuan Renminbi": CURRENCY = "¥"; break;
            case "Yen": CURRENCY = "JP¥"; break;
            case "Won": CURRENCY = "₩"; break;
        }
        return CURRENCY;
    }

    private void setSave_button(){
        try{
            String category = category_combobox.getValue();
            String note = note_textfield.getText();
            LocalDate date = date_datepicker.getValue();
            double amount = Math.abs(Double.parseDouble(amount_textfield.getText()));
            if (type == -1)
                amount = 0 - amount;
            if (category != null && date != null) {

                for (Wallets wallets : mainFrameControl.getWalletsList()) {
                    if (wallets.getName().equals(wallet_cbb.getSelectionModel().getSelectedItem())) {
                        Transactions newTransaction = new Transactions(amount, wallets.getCurrency(), category, note, date);
                        wallets.addNewTransaction(newTransaction);
                        mainFrameControl.setPrevious_transaction(newTransaction);
                        mainFrameControl.setTransactionSelectingDate(date);
                        break;
                    }
                }



                if (edit_mode){
                    mainFrameControl.getSelectedWallet().deleteATransaction(editingTransaction);
                }
                mainFrameControl.refreshView();


                thisStage.close();
            }
        } catch (NumberFormatException ignored) {
            warning.setVisible(true);
        }

    }

    public void setData(Transactions t){
        amount_textfield.setText(String.valueOf(Math.abs(t.getAmount())));

        wallet_cbb.setDisable(true);

        save_button.setText("Save");

        delete_btn.setVisible(true);

        edit_mode = true;

        title.setText("Edit Transaction");

        currency_label.setText(getCurrencyLetter(mainFrameControl.getSelectedWallet().getCurrency()));

        for (Categories c: Main.getExpenseCategories()){
            if (c.getName().equals(t.getCategory())) {
                category_icon.setImage(c.getIcon());
                break;
            }
        }

        for (Categories c: Main.getIncomeCategories()){
            if (c.getName().equals(t.getCategory())) {
                category_icon.setImage(c.getIcon());
                break;
            }
        }

        income.setSelected(false);
        expense.setSelected(false);

        if (t.getAmount() < 0){
            expense.setSelected(true);
            setExpenseAction();
        }
        else{
            income.setSelected(true);
            setIncomeAction();
        }

        note_textfield.setText(t.getNote());
        date_datepicker.setValue(t.getDate());

        category_combobox.getSelectionModel().select(t.getCategory());
    }


    private void setCancel_button(){
        thisStage.close();
    }

    private void setDelete_btn(){
        Alert alert = new Alert(Alert.AlertType.WARNING, "DELETE THIS TRANSACTION?", ButtonType.YES, ButtonType.CANCEL);
        Optional<ButtonType> pressed = alert.showAndWait();
        ButtonType button = pressed.orElse(ButtonType.CANCEL);
        if (button == ButtonType.YES){
            mainFrameControl.getSelectedWallet().deleteATransaction(editingTransaction);
            mainFrameControl.refreshView();
            thisStage.close();
        }

    }
}
