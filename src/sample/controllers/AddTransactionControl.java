package sample.controllers;


import com.jfoenix.controls.JFXButton;
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

import java.io.IOException;
import java.time.LocalDate;


public class AddTransactionControl {
    private final Stage thisStage;

    @FXML private JFXButton cancel_button;
    @FXML private JFXButton save_button;
    @FXML private TextField amount_textfield;
    @FXML private ComboBox<String> category_combobox;
    @FXML private TextField note_textfield;
    @FXML private DatePicker date_datepicker;
    @FXML private AnchorPane root;
    @FXML private RadioButton income, expense;
    @FXML private Label warning;
    @FXML private Label currency_label;
    @FXML private ImageView category_icon;

    private int type = -1;


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

    public void showStage() {
        thisStage.showAndWait();
    }


    /**
     * The initialize() method allows you set setup your scene, adding actions, configuring nodes, etc.
     */
    @FXML
    private void initialize() throws IOException {
        String CURRENCY = "";
        switch (mainFrameControl.getSelectedWallet().getCurrency()){
            case "Vietnam Dong": CURRENCY = "₫"; break;
            case "US Dollar": CURRENCY = "$"; break;
            case "Pound": CURRENCY = "£"; break;
            case "Euro": CURRENCY = "€"; break;
            case "Yuan Renminbi": CURRENCY = "¥"; break;
            case "Yen": CURRENCY = "JP¥"; break;
            case "Won": CURRENCY = "₩"; break;
        }
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
            for (Categories i: Main.getExpenseCategories()){
                category_combobox.getItems().add(i.getName());
            }
        } else {
            for (Categories i: Main.getIncomeCategories()){
                category_combobox.getItems().add(i.getName());
            }
        }

        income.setOnAction(event -> {
            type = 1;
            expense.setSelected(false);
            category_combobox.getItems().clear();
            for (Categories i: Main.getIncomeCategories()){
                category_combobox.getItems().add(i.getName());
            }
        });
        expense.setOnAction(event -> {
            type = -1;
            income.setSelected(false);
            category_combobox.getItems().clear();
            for (Categories i: Main.getExpenseCategories()){
                category_combobox.getItems().add(i.getName());
            }
        });

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
                Transactions newTransaction = new Transactions(amount, mainFrameControl.getSelectedWallet().getCurrency(), category, note, date);
                // Now create new transaction
                System.out.println(newTransaction);
                // Update balance
                mainFrameControl.getSelectedWallet().addNewTransaction(newTransaction);
                mainFrameControl.setPrevious_transaction(newTransaction);

                mainFrameControl.refreshView();

                thisStage.close();
            }
        } catch (NumberFormatException ignored) {
            warning.setVisible(true);
        }

    }


    private void setCancel_button(){
        thisStage.close();
    }
}
