package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.models.Wallets;

public class WalletCardControl {

    @FXML private Label name_label, balance_label;
    @FXML private ImageView icon;
    @FXML private AnchorPane selected_panel;

    Wallets wallet;

    public WalletCardControl(Wallets wallet) {
        this.wallet = wallet;
    }

    @FXML
    private void initialize(){
        name_label.setText(wallet.getName());
        balance_label.setText(String.valueOf(wallet.getBalance()));

    }
}
