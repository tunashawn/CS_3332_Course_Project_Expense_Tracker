package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportFrameControl {
    private final Stage thisStage;

    private final MainFrameControl mainFrameControl;


    public ReportFrameControl(MainFrameControl controller) {
        this.mainFrameControl = controller;
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/ReportView.fxml"));

            loader.setController(this);

            thisStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Show the stage that was loaded in the constructor
     */
    public void showStage() {
        thisStage.show();
    }

    @FXML
    private void initialize() {

        // Set the label to whatever the text entered on Layout1 is
//        lblFromController1.setText(controller1.getEnteredText());
//
//        // Set the action for the button
//        btnSetLayout1Text.setOnAction(event -> setTextOnLayout1());
    }

    public void setData(){

    }
}
