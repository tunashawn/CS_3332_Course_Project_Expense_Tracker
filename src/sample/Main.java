package sample;

import javafx.application.Application;

import javafx.stage.Stage;
import sample.controllers.MainFrameControl;

import java.text.DecimalFormat;

public class Main extends Application {



    public static String formatMoney(double amount, String currency) {
        String CURRENCY = "";
        switch (currency){
            case "Vietnam Dong": CURRENCY = "₫"; break;
            case "US Dollar": CURRENCY = "$"; break;
            case "Pound": CURRENCY = "£"; break;
            case "Euro": CURRENCY = "€"; break;
            case "Yuan Renminbi": CURRENCY = "¥"; break;
            case "Yen": CURRENCY = "JP¥"; break;
            case "Won": CURRENCY = "₩"; break;
        }
        if (CURRENCY.equals("₫")) {
            DecimalFormat df = new DecimalFormat("#,###");
            return df.format(amount) + " " + CURRENCY;
        }
        DecimalFormat df = new DecimalFormat("#,###.#");
        return CURRENCY + " " + df.format(amount);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        MainFrameControl mainFrameControl = new MainFrameControl();
        mainFrameControl.showStage();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
