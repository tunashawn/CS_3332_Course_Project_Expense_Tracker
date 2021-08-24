package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class ExchangerViewControl {

    @FXML private Button            convert_btn;
    @FXML private ImageView         input_icon, output_icon;
    @FXML private ComboBox<String>  input_cbb, output_cbb;
    @FXML private TextField         input_tf, output_tf;

    public ExchangerViewControl() {

    }

    @FXML private void initialize(){
        populateComboboxes();
        convert_btn.setOnAction(event -> setConvert_btn());
    }

    private void setConvert_btn(){

    }


    private void populateComboboxes(){
        ArrayList<String> list = new ArrayList<>();
        list.add("USD");
        list.add("EUR");
        list.add("GBP");
        list.add("INR");
        list.add("AUD");
        list.add("CAD");
        list.add("SGD");
        list.add("MYR");
        list.add("JPY");
        list.add("CNY");
        list.add("THB");
        list.add("HKD");
        list.add("VND");

        for (String s : list) {
            input_cbb.getItems().add(s);
            output_cbb.getItems().add(s);
        }
    }
}
