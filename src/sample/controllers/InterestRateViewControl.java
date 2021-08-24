package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class InterestRateViewControl {
    @FXML private TextField amount_tf, rate_tf, result_tf;
    @FXML private DatePicker startdate_dp, enddate_dp;
    @FXML private ComboBox<String> type_cbb, timemode_cbb;
    @FXML private Button calculate_btn;

    public InterestRateViewControl(){

    }

    @FXML private void initialize(){
        calculate_btn.setOnAction(event -> Calculate());
        populateTimeMode();
        populateTypeInterest();
    }


    private void Calculate(){
        String amount = amount_tf.getText();

    }


    private void populateTimeMode(){
        ArrayList<String> timeList = new ArrayList<>();
        timeList.add("Weekly");
        timeList.add("Every 14 days");
        timeList.add("Monthly");
        timeList.add("Every 3 months");
        timeList.add("Every 6 months");
        timeList.add("Yearly");

        for (String s : timeList) {
            timemode_cbb.getItems().add(s);
        }

        timemode_cbb.getSelectionModel().select("Weekly");
    }

    private void populateTypeInterest(){
        type_cbb.getItems().add("Simple interest");
        type_cbb.getItems().add("Compound interest");
        type_cbb.getSelectionModel().select("Simple interest");
    }
}
