package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class AttendanceController implements Initializable {


    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private BarChart<?, ?> barChart;


    public void drawLineChart(){
        lineChart.getData().addAll(dataChart());
    }

    public void drawBarChart(){
        barChart.getData().addAll(dataChart());
    }

    public XYChart.Series dataChart(){
        XYChart.Series series = new XYChart.Series();
        //Add Data
        series.getData().add(new XYChart.Data("1", 23));
        series.getData().add(new XYChart.Data("2", 14));
        series.getData().add(new XYChart.Data("3", 15));
        series.getData().add(new XYChart.Data("4", 23));
        series.getData().add(new XYChart.Data("5", 14));
        series.getData().add(new XYChart.Data("6", 15));

        return series;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawBarChart();
        drawLineChart();
    }
}
