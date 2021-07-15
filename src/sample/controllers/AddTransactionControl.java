package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AddTransactionControl {
    private final Stage thisStage;

    @FXML Button cancel_button;
    @FXML Button save_button;

    @FXML AnchorPane root;

    private double xOffset = 0;
    private double yOffset = 0;

    public AddTransactionControl(MainFrameControl mainFrameControl) {
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/AddTransactionFrame.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.initStyle(StageStyle.UNDECORATED);

            thisStage.setTitle("Add Transaction");
            thisStage.getIcons().add(new Image("/sample/icons/app_logo.png"));

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    thisStage.setX(event.getScreenX() - xOffset);
                    thisStage.setY(event.getScreenY() - yOffset);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }


    /**
     * The initialize() method allows you set setup your scene, adding actions, configuring nodes, etc.
     */
    @FXML
    private void initialize() throws IOException {
        cancel_button.setOnAction(event -> setCancel_button());
        save_button.setOnAction(event -> setSave_button());
    }

    public void setData(){

    }

    private void setSave_button(){

    }

    private void setCancel_button(){
        thisStage.close();
    }
}
