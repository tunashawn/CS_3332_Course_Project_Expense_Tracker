package sample.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class ReportFrameControl {

    @FXML private StackedBarChart<String, Number> stacked_bar_chart;
    @FXML private PieChart left_pie, right_pie;
    @FXML private AnchorPane pane;
    private final MainFrameControl mainFrameControl;


    public ReportFrameControl(MainFrameControl controller) {
        this.mainFrameControl = controller;
    }


    private XYChart.Series<String, Number> expense_bar = new XYChart.Series<>();
    private XYChart.Series<String, Number> income_bar = new XYChart.Series<>();


    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final StackedBarChart<String, Number> sbc = new StackedBarChart<>(xAxis, yAxis);
    final XYChart.Series<String, Number> incoming = new XYChart.Series<>();
    final XYChart.Series<String, Number> outgoing = new XYChart.Series<>();

    @FXML
    private void initialize() {
        xAxis.setLabel("Month");
        xAxis.setCategories(FXCollections.observableArrayList(
                Arrays.asList("Jan", "Feb", "Mar")));
        yAxis.setLabel("Value");
        incoming.setName("Incoming");
        incoming.getData().add(new XYChart.Data("Jan", 25601.34));
        incoming.getData().add(new XYChart.Data("Feb2", 20148.82));
        incoming.getData().add(new XYChart.Data("Mar2", 10000));
        outgoing.setName("Outgoing");
        outgoing.getData().add(new XYChart.Data("Jan", -7401.85));
        outgoing.getData().add(new XYChart.Data("Feb2", -1941.19));
        outgoing.getData().add(new XYChart.Data("Mar2", -5263.37));
        sbc.getData().addAll(incoming, outgoing);
        pane.getChildren().add(sbc);

//        xAxis.setLabel("Month");
//        xAxis.setCategories(FXCollections.observableArrayList(
//                Arrays.asList("Jan", "Feb", "Mar")));
//        yAxis.setLabel("Value");
//
//
//
//        expense_bar.setName("Expense");
//        expense_bar.getData().add(new XYChart.Data<String, Number>("Jan", -245));
//        expense_bar.getData().add(new XYChart.Data<String, Number>("Feb", -500));
//        expense_bar.getData().add(new XYChart.Data<String, Number>("Mar", -69));
//
//
//
//        income_bar.setName("Income");
//        income_bar.getData().add(new XYChart.Data<String, Number>("Jan", 270));
//        income_bar.getData().add(new XYChart.Data<String, Number>("Feb", 40));
//        income_bar.getData().add(new XYChart.Data<String, Number>("Mar", 300));
//
//        stacked_bar_chart.getData().addAll(income_bar,expense_bar);
//
//        stacked_bar_chart.setTitle("This is bar chart");
    }
}



