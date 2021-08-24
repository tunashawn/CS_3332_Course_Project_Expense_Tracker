package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.utils.Utils;

import java.util.ArrayList;

public class ExchangerViewControl {

    @FXML Button            convert_btn;
    @FXML ImageView         input_icon, output_icon;
    @FXML ComboBox<String>  input_cbb, output_cbb;
    @FXML TextField         input_tf, output_tf;

    public ExchangerViewControl() {

    }

    @FXML private void initialize(){
        populateComboboxes();
        input_cbb.getSelectionModel().select("USD");
        output_cbb.getSelectionModel().select("EUR");
        convert_btn.setOnAction(event -> setConvert_btn());
        input_cbb.setOnAction(event -> setInputIcon());
        output_tf.setOnAction(event -> setOutputIcon());

        input_cbb.setOnAction(event -> setInputIcon());
        output_cbb.setOnAction(event -> setOutputIcon());
    }

    private void setInputIcon(){
        String selected = input_cbb.getSelectionModel().getSelectedItem();
        input_icon.setImage(new Image("sample/icons/currency/" + selected + ".png"));
    }

    private void setOutputIcon(){
        String selected = output_cbb.getSelectionModel().getSelectedItem();
        output_icon.setImage(new Image("sample/icons/currency/" + selected + ".png"));
    }


    private void setConvert_btn(){
        try {
            String inputCurrencyName = input_cbb.getSelectionModel().getSelectedItem();
            String outputCurrencyName = output_cbb.getSelectionModel().getSelectedItem();
            Double amount = Double.parseDouble(input_tf.getText());
            String output = Utils.convertCurrency(inputCurrencyName,amount, outputCurrencyName);
            output_tf.setText(output);
        } catch (NumberFormatException e) {

        }
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
