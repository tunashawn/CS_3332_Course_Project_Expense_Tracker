package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainFrameControl {
    private final Stage thisStage;

    @FXML JFXButton new_button;
    @FXML JFXButton transaction_button;
    @FXML JFXButton report_button;
    @FXML JFXButton planning_button;
    @FXML JFXButton more_button;
    @FXML JFXButton exit_button;

    @FXML
    Label title_label;
    @FXML AnchorPane content_panel;
    @FXML
    Pane detail_pane;

    @FXML AnchorPane root;

    private double xOffset = 0;
    private double yOffset = 0;

    public MainFrameControl() {
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/MainFrame.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.initStyle(StageStyle.UNDECORATED);

            thisStage.setTitle("Expend Tracker");
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
        openTransactionView();

        exit_button.setOnAction((event -> exit()));

        transaction_button.setOnAction(event -> {
            try {
                openTransactionView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        report_button.setOnAction(event -> {
            try {
                openReportView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    private void openTransactionView() throws IOException {
        title_label.setText("Transactions");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/views/TransactionsView.fxml"));

        TransactionControl transactionControl = new TransactionControl(this);
        fxmlLoader.setController(transactionControl);
        AnchorPane pane = fxmlLoader.load();
        transactionControl.setData();
        detail_pane.getChildren().clear();
        detail_pane.getChildren().add(pane);
    }

    private void openReportView() throws IOException {
        title_label.setText("Reports");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/views/ReportView.fxml"));

        ReportFrameControl reportFrameControl = new ReportFrameControl(this);
        fxmlLoader.setController(reportFrameControl);
        AnchorPane pane = fxmlLoader.load();
        reportFrameControl.setData();
        detail_pane.getChildren().clear();
        detail_pane.getChildren().add(pane);
    }

    private void exit(){
        thisStage.close();
    }
}
