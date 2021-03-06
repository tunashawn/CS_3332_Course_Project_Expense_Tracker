package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.models.Wallets;

import java.io.IOException;

public class WalletCardControl {

    @FXML Label name_label, balance_label;
    @FXML ImageView icon;
    @FXML AnchorPane selected_panel;

    private Wallets wallet;
    private  MyWalletControl myWalletControl;

    public WalletCardControl(Wallets wallet, MyWalletControl myWalletControl) {
        this.wallet = wallet;
        this.myWalletControl = myWalletControl;
    }

    @FXML
    private void initialize(){
        if (wallet != null) {
            name_label.setText(wallet.getName());
            balance_label.setText(Main.formatMoney(wallet.getBalance(),wallet.getCurrency()));
            icon.setImage(new Image("sample/icons/wallets/" + wallet.getIcon_name() + ".png"));
            selected_panel.setOnMouseClicked(event -> setSelected_panel());
        }
    }

    private void setSelected_panel(){
        myWalletControl.openWalletDetail(wallet);
        try {
            myWalletControl.setSelected_panel(this.wallet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
