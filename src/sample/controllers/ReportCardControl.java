package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Main;

import java.util.Locale;

public class ReportCardControl {
    private String category;
    private double amount;
    private String currency;

    @FXML private ImageView icon;
    @FXML private Label name, cost;

    public ReportCardControl(String category, double amount, String currency){
        this.category = category;
        this.amount = amount;
        this.currency = currency;
    }

    @FXML
    private void initialize(){
        name.setText(category);
        cost.setText(Main.formatMoney(amount, currency));
        icon.setImage(new Image("/sample/categories/" + category.toLowerCase(Locale.ROOT) + ".png"));
    }
}
