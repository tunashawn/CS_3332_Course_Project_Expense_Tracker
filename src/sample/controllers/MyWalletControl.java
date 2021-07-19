package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MyWalletControl {

    @FXML private AnchorPane selected_panel;
    @FXML private GridPane grid;
    @FXML private Button new_wallet_button;
    @FXML private AnchorPane detail_panel;

    private MainFrameControl mainFrameControl;
    public MyWalletControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;
    }

    @FXML
    private void initialize(){
        new_wallet_button.setOnAction(event -> setNew_wallet_button());
    }

    private void setNew_wallet_button() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/AddNewWalletView.fxml"));
            AddNewWalletControl control = new AddNewWalletControl(mainFrameControl);
            fxmlLoader.setController(control);
            System.out.println(1);
            AnchorPane pane = fxmlLoader.load();
            detail_panel.getChildren().clear();
            detail_panel.getChildren().add(pane);
            System.out.println(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
