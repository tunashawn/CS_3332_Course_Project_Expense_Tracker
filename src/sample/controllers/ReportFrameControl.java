package sample.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;

public class ReportFrameControl {

    @FXML private BarChart<String, Number> bar_chart;
    @FXML private PieChart left_pie, right_pie;
    @FXML private ComboBox<String> time_range_combobox;
    private final MainFrameControl mainFrameControl;


    public ReportFrameControl(MainFrameControl controller) {
        this.mainFrameControl = controller;
    }


    private XYChart.Series<String, Number> expense_bar = new XYChart.Series<>();
    private XYChart.Series<String, Number> income_bar = new XYChart.Series<>();




    @FXML
    private void initialize() {

        expense_bar.setName("Expense");
        expense_bar.getData().add(new XYChart.Data<String, Number>("Jan", -245));
        expense_bar.getData().add(new XYChart.Data<String, Number>("Feb", -500));
        expense_bar.getData().add(new XYChart.Data<String, Number>("Mar", -69));



        income_bar.setName("Income");
        income_bar.getData().add(new XYChart.Data<String, Number>("Jan", 270));
        income_bar.getData().add(new XYChart.Data<String, Number>("Feb", 40));
        income_bar.getData().add(new XYChart.Data<String, Number>("Mar", 300));

        bar_chart.getData().addAll(expense_bar, income_bar);

        bar_chart.setTitle("This is bar chart");

        ObservableList<PieChart.Data> data_left_pie = FXCollections.observableArrayList(
                new PieChart.Data("Clothes", 3),
                new PieChart.Data("Food",  30),
                new PieChart.Data("Drink", 40),
                new PieChart.Data("Medical", 27)
        );
        left_pie.setData(data_left_pie);
    }
}



