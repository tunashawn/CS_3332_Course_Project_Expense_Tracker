package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.models.Wallets;

import java.io.IOException;
import java.util.Optional;

public class WalletsDetailControl {
    @FXML
    ImageView icon;
    @FXML Label currency;
    @FXML TextField balance, name_label;
    @FXML JFXButton save_button, delete_button,
            wallet_button,
            visa_button,
            paypal_button,
            mastercard_button,
            google_button,
            america_button;
    private String chose_icon = "wallet";
    private MainFrameControl mainFrameControl;
    private MyWalletControl myWalletControl;
    private Wallets w;

    public WalletsDetailControl(MainFrameControl mainFrameControl, MyWalletControl myWalletControl, Wallets w) {
        this.mainFrameControl = mainFrameControl;
        this.myWalletControl = myWalletControl;
        this.w = w;
    }

    @FXML
    private void initialize(){
        chose_icon =  w.getIcon_name();
        icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));
        name_label.setText(w.getName());
        currency.setText(w.getCurrency());
        balance.setText(String.valueOf(w.getBalance()));

        wallet_button.setOnAction(event -> {chose_icon = "wallet"; icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));});
        visa_button.setOnAction(event -> {chose_icon = "visa"; icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));});
        paypal_button.setOnAction(event -> {chose_icon = "paypal"; icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));});
        mastercard_button.setOnAction(event -> {chose_icon = "mastercard"; icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));});
        google_button.setOnAction(event -> {chose_icon = "google_wallet"; icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));});
        america_button.setOnAction(event -> {chose_icon = "american_express"; icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));});

        save_button.setOnAction(event -> setSave_button());
        delete_button.setOnAction(event -> {
            try {
                setDelete_button();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setSave_button(){
        try {
            String name = name_label.getText();
            double balance = Double.parseDouble(this.balance.getText());

            w.setName(name);
            w.setBalance(balance);
            w.setIcon_name(chose_icon);

            myWalletControl.populateWalletList();
            if (mainFrameControl.getSelectedWallet().equals(w))
                myWalletControl.setSelected_panel(w);

            myWalletControl.showNothingToDisplay();
        } catch (NumberFormatException | IOException ignored){

        }
    }

    private void setDelete_button() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "CONFIRM DELETE THIS WALLET?", ButtonType.YES, ButtonType.CANCEL);
        Optional<ButtonType> pressed = alert.showAndWait();
        ButtonType button = pressed.orElse(ButtonType.CANCEL);
        if (button == ButtonType.YES){
            myWalletControl.deleteAWallet(w);
            myWalletControl.showNothingToDisplay();
        }

    }
}
