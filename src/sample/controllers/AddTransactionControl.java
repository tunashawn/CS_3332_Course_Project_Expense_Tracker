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
        warning.setVisible(false);
        cancel_button.setOnAction(event -> setCancel_button());
        save_button.setOnAction(event -> setSave_button());

        for (Categories i: mainFrameControl.getCategories()){
            category_combobox.getItems().add(i.getName());
        }
        expense.setSelected(true);

        income.setOnAction(event -> {
            type = 1;
            expense.setSelected(false);
        });
        expense.setOnAction(event -> {
            type = -1;
            income.setSelected(false);
        });
    }

    private void setSave_button(){
        try{
            double amount = Double.parseDouble(amount_textfield.getText());
            String category = null;
            for (Categories i: mainFrameControl.getCategories()){
                if (i.getName().equals(category_combobox.getValue())){
                    category = i.getName();
                    break;
                }
            }
            String note = note_textfield.getText();
            LocalDate date = date_datepicker.getValue();
            Transactions newTransaction = new Transactions(amount, type, category, note, date);
            // Now create new transaction
            mainFrameControl.createNewTransaction(newTransaction);
            System.out.println(newTransaction);
            thisStage.close();
        } catch (NumberFormatException ignored) {
            warning.setVisible(true);
        }

    }


    private void setCancel_button(){
        thisStage.close();
    }
}
