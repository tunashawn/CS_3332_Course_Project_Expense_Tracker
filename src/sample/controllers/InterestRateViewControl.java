package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.models.InterestRate;
import sample.utils.Utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class InterestRateViewControl {
    @FXML TextField amount_tf, rate_tf, result_tf, interest_amount, num_of_period;
    @FXML DatePicker startdate_dp, enddate_dp;
    @FXML ComboBox<String> type_cbb, timemode_cbb;
    @FXML Button calculate_btn;

    public InterestRateViewControl(){

    }

    @FXML private void initialize(){
        calculate_btn.setOnAction(event -> Calculate());
        populateTimeMode();
        populateTypeInterest();
    }


    private void Calculate(){
        try {
            double amount = Double.parseDouble(amount_tf.getText());
            double rate = Double.parseDouble(rate_tf.getText());
            LocalDate start_date = startdate_dp.getValue();
            LocalDate end_date = enddate_dp.getValue();
            String type = type_cbb.getSelectionModel().getSelectedItem();
            String time_mode = timemode_cbb.getSelectionModel().getSelectedItem();

            InterestRate result = Utils.calculateInterestRate(amount, rate,start_date,end_date,type,time_mode);
            result_tf.setText(Utils.formatter.format(result.getAmount()));
            interest_amount.setText(Utils.formatter.format(result.getInterest_amount()));
            num_of_period.setText(String.valueOf(result.getPeriod()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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
