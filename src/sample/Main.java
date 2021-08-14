package sample;

import javafx.application.Application;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.controllers.MainFrameControl;
import sample.models.Categories;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main extends Application {


    public static String formatMoney(double amount, String currency) {
        String CURRENCY = "";
        switch (currency) {
            case "Vietnam Dong":
                CURRENCY = "₫";
                break;
            case "US Dollar":
                CURRENCY = "$";
                break;
            case "Pound":
                CURRENCY = "£";
                break;
            case "Euro":
                CURRENCY = "€";
                break;
            case "Yuan Renminbi":
                CURRENCY = "¥";
                break;
            case "Yen":
                CURRENCY = "JP¥";
                break;
            case "Won":
                CURRENCY = "₩";
                break;
        }
        if (CURRENCY.equals("₫")) {
            DecimalFormat df = new DecimalFormat("#,###");
            return df.format(amount) + " " + CURRENCY;
        }
        DecimalFormat df = new DecimalFormat("#,###.#");
        return CURRENCY + " " + df.format(amount);
    }

    public static ArrayList<Categories> getExpenseCategories() {
        ArrayList<Categories> categories = new ArrayList<>();
        categories.add(new Categories("Food", new Image("sample/categories/food.png")));
        categories.add(new Categories("Drink", new Image("sample/categories/drink.png")));
        categories.add(new Categories("Fast food", new Image("sample/categories/fast food.png")));
        categories.add(new Categories("Snack", new Image("sample/categories/snack.png")));
        categories.add(new Categories("Medical", new Image("sample/categories/medical.png")));
        categories.add(new Categories("Clothes", new Image("sample/categories/clothes.png")));
        categories.add(new Categories("Laundry", new Image("sample/categories/laundry.png")));
        categories.add(new Categories("Rental", new Image("sample/categories/rental.png")));
        categories.add(new Categories("Parking", new Image("sample/categories/parking.png")));
        categories.add(new Categories("Taxi", new Image("sample/categories/taxi.png")));
        return categories;
    }

    public static ArrayList<Categories> getIncomeCategories() {
        ArrayList<Categories> categories = new ArrayList<>();
        categories.add(new Categories("Salary", new Image("sample/categories/salary.png")));
        categories.add(new Categories("Gift", new Image("sample/categories/gift.png")));
        categories.add(new Categories("Lottery", new Image("sample/categories/lottery.png")));
        return categories;
    }



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        MainFrameControl mainFrameControl = new MainFrameControl();
        mainFrameControl.showStage();

    }
}
