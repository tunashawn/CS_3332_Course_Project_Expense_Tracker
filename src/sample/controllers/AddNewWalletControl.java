package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.models.Transactions;
import sample.models.Wallets;

import java.util.ArrayList;

public class AddNewWalletControl {
    @FXML private ImageView icon;
    @FXML private JFXComboBox<String> currency_combobox;
    @FXML private TextField balance, name_label;
    @FXML private JFXButton save_button,
                            wallet_button,
                            visa_button,
                            paypal_button,
                            mastercard_button,
                            google_button,
                            america_button;

    private ArrayList<String> currencyList = new ArrayList<>();
    private String chose_icon = "wallet";
    private MainFrameControl mainFrameControl;

    public AddNewWalletControl(MainFrameControl mainFrameControl) {
        populateCurrencyList();
        this.mainFrameControl = mainFrameControl;
    }

    @FXML
    private void initialize(){
        for (String s : currencyList) {
            currency_combobox.getItems().add(s);
        }

        wallet_button.setOnAction(event -> {chose_icon = "wallet"; icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));});
        visa_button.setOnAction(event -> {chose_icon = "visa"; icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));});
        paypal_button.setOnAction(event -> {chose_icon = "paypal"; icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));});
        mastercard_button.setOnAction(event -> {chose_icon = "mastercard"; icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));});
        google_button.setOnAction(event -> {chose_icon = "google_wallet"; icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));});
        america_button.setOnAction(event -> {chose_icon = "american_express"; icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));});
        save_button.setOnAction(event -> setSave_button());
    }

    private void setSave_button(){
        try {
            String name = name_label.getText();
            String currency = currency_combobox.getValue();
            double balance = Double.parseDouble(this.balance.getText());

            Wallets new_wallet = new Wallets(name, chose_icon,balance,balance, 0,new ArrayList<Transactions>());

            mainFrameControl.setSelectedWallet(new_wallet);
            mainFrameControl.wallet_icon.setImage(new Image("sample/icons/wallets/" + chose_icon + ".png"));
            mainFrameControl.addNewWallet(new_wallet);

        } catch (NumberFormatException ignored){

        }
    }

    private void populateCurrencyList(){
        currencyList.add("Vietnam Dong");
        currencyList.add("US Dollar");
        currencyList.add("Pound");
        currencyList.add("Euro");
        currencyList.add("Yuan Renminbi");
        currencyList.add("Yen");
        currencyList.add("Won");
    }
}
