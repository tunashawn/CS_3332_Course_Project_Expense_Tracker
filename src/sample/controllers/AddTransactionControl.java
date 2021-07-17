package sample.controllers;


import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.models.Category;
import sample.models.Transaction;

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
        cancel_button.setOnAction(event -> setCancel_button());
        save_button.setOnAction(event -> setSave_button());

        for (Category i: mainFrameControl.getCategories()){
            category_combobox.getItems().add(i.getName());
        }
    }

    private void setSave_button(){
        try{
            int amount = Integer.parseInt(amount_textfield.getText());
            Category category = null;
            for (Category i: mainFrameControl.getCategories()){
                if (i.getName().equals(category_combobox.getValue())){
                    category = i;
                    break;
                }
            }
            String note = note_textfield.getText();
            LocalDate date = date_datepicker.getValue();
            Transaction newTransaction = new Transaction(amount, category, note, date);
            // Now create new transaction
            mainFrameControl.createNewTransaction(newTransaction);
            thisStage.close();
        } catch (NumberFormatException ignored) {

        }

    }


    private void setCancel_button(){
        thisStage.close();
    }
}
