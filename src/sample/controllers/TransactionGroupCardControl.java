package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import sample.models.Transactions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class TransactionGroupCardControl {
    private final Stage thisStage;

    @FXML private GridPane grid;
    @FXML private Label day_of_month, day_of_week, month_year, total;

    private ArrayList<Transactions> transactionsInADay;
    private MainFrameControl mainFrameControl;

    public TransactionGroupCardControl(ArrayList<Transactions> transactionList, MainFrameControl mFC) {
        this.transactionsInADay = transactionList;
        this.mainFrameControl = mFC;
        thisStage = new Stage();
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    @FXML
    private void initialize() {
        calculateTotal();
        populateTransactionGroup();
    }

    public void calculateTotal(){
        double total = 0;
        for (Transactions t : transactionsInADay) {
            total += t.getAmount();
        }

        this.total.setText(String.valueOf(total));

    }


    /**
     * Populate My Order pane
     */
    private void populateTransactionGroup() {
        if (transactionsInADay != null && transactionsInADay.size() > 0){
            day_of_month.setText(String.valueOf(transactionsInADay.get(0).getDate().getDayOfMonth()));
            day_of_week.setText(StringUtils.capitalize(transactionsInADay.get(0).getDate().getDayOfWeek().toString().toLowerCase(Locale.ROOT)));
            String monYear = StringUtils.capitalize(transactionsInADay.get(0).getDate().getMonth().toString().toLowerCase(Locale.ROOT));
            monYear += " " + String.valueOf(transactionsInADay.get(0).getDate().getYear());
            month_year.setText(monYear);
            int column = 0;
            int row = 1;
            int number_of_item = 3;
            try {
                Collections.reverse(transactionsInADay);
                for (Transactions t : transactionsInADay) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/sample/views/TransactionCard.fxml"));

                    TransactionCardControl transactionCardControl = new TransactionCardControl(t, mainFrameControl);
                    fxmlLoader.setController(transactionCardControl);
                    AnchorPane pane = fxmlLoader.load();

                    if (column == 1) {
                        column = 0;
                        ++row;
                    }

                    grid.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }









}
