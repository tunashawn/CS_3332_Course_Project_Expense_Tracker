package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ToolsViewControl {

    @FXML JFXButton exchanger_jbtn, interest_rate_jbtn;
    @FXML AnchorPane detail_panel;

    public ToolsViewControl() {
    }

    @FXML
    private void initialize(){
        exchanger_jbtn.setOnAction(event -> OpenExchanger());
        interest_rate_jbtn.setOnAction(event -> OpenInterestRate());
    }

    private void OpenExchanger(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/ExchangerView.fxml"));
            ExchangerViewControl e = new ExchangerViewControl();
            fxmlLoader.setController(e);
            AnchorPane pane = fxmlLoader.load();
            detail_panel.getChildren().clear();
            detail_panel.getChildren().add(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void OpenInterestRate(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/InterestRateView.fxml"));
            InterestRateViewControl e = new InterestRateViewControl();
            fxmlLoader.setController(e);
            AnchorPane pane = fxmlLoader.load();
            detail_panel.getChildren().clear();
            detail_panel.getChildren().add(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
