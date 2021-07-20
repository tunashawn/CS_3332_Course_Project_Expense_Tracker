package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sample.models.Wallets;

import java.io.IOException;
import java.util.ArrayList;

public class MyWalletControl {

    @FXML private AnchorPane selected_panel;
    @FXML private GridPane grid;
    @FXML private Button new_wallet_button;
    @FXML private AnchorPane detail_panel;


    private ArrayList<Wallets> walletList;
    private MainFrameControl mainFrameControl;


    public MyWalletControl(MainFrameControl mainFrameControl, ArrayList<Wallets> walletList) {
        this.mainFrameControl = mainFrameControl;
        this.walletList = walletList;
    }

    @FXML
    private void initialize() throws IOException {
        if (walletList != null && walletList.size() > 0) {
            setSelected_panel(mainFrameControl.getSelectedWallet());
            populateWalletList();
        }

        new_wallet_button.setOnAction(event -> setNew_wallet_button());
    }

    private void setNew_wallet_button() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/AddNewWalletView.fxml"));
            AddNewWalletControl control = new AddNewWalletControl(mainFrameControl, this);
            fxmlLoader.setController(control);
            AnchorPane pane = fxmlLoader.load();
            detail_panel.getChildren().clear();
            detail_panel.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openWalletDetail(Wallets wallet) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/WalletsDetailView.fxml"));
            WalletsDetailControl control = new WalletsDetailControl(mainFrameControl, this, wallet);
            fxmlLoader.setController(control);
            AnchorPane pane = fxmlLoader.load();
            detail_panel.getChildren().clear();
            detail_panel.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSelected_panel(Wallets w) throws IOException {
        selected_panel.getChildren().clear();
        if (w != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/WalletCardView.fxml"));

            WalletCardControl walletCardControl = new WalletCardControl(w, this);
            fxmlLoader.setController(walletCardControl);
            AnchorPane pane = fxmlLoader.load();

            selected_panel.getChildren().clear();
            selected_panel.getChildren().add(pane);

            mainFrameControl.setSelectedWallet(w);
        }
    }


    public void populateWalletList() throws IOException {
        grid.getChildren().clear();
        if (walletList != null){
            int column = 0;
            int row = 1;
            for (Wallets w : walletList) {
                if (w != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/sample/views/WalletCardView.fxml"));

                    WalletCardControl walletCardControl = new WalletCardControl(w, this);
                    fxmlLoader.setController(walletCardControl);
                    AnchorPane pane = fxmlLoader.load();

                    if (column == 1) {
                        column = 0;
                        ++row;
                    }

                    grid.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(15, 0, 0, 0));
                }
            }
        }
    }

    public void clearDetailPanel(){
        detail_panel.getChildren().clear();
    }

    public void deleteAWallet(Wallets w) throws IOException {

        selected_panel.getChildren().clear();

        if (mainFrameControl.getSelectedWallet().equals(w) && walletList.size() > 1) {
            walletList.remove(w);
            mainFrameControl.setSelectedWallet(walletList.get(0));
            setSelected_panel(mainFrameControl.getSelectedWallet());
        } else{
            walletList.remove(w);
            setSelected_panel(null);
            mainFrameControl.setSelectedWallet(null);
        }
        clearDetailPanel();
        populateWalletList();
    }
}
