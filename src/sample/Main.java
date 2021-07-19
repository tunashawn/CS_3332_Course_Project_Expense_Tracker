package sample;

import javafx.application.Application;

import javafx.stage.Stage;
import sample.controllers.MainFrameControl;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        MainFrameControl mainFrameControl = new MainFrameControl();
        mainFrameControl.showStage();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
