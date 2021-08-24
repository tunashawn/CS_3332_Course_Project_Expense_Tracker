package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class InterestRateViewControl {
    @FXML private TextField amount_tf, rate_tf, result_tf;
    @FXML private DatePicker startdate_dp, enddate_dp;
    @FXML private ComboBox<String> type_cbb, timemode_cbb;
    @FXML private Button calculate_btn;

    public InterestRateViewControl(){

    }

    @FXML private void initialize(){
        calculate_btn.setOnAction(event -> Calculate());
    }


    private void Calculate(){

    }

}
