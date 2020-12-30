package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.css.Size;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.utils.SemiCircleChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.renderer.DefaultPolarItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.function.Function;

class MyGraph {

    private XYChart<Double, Double> graph;
    private double range;

    public MyGraph(final XYChart<Double, Double> graph, final double range) {
        this.graph = graph;
        this.range = range;
    }

    public void plotLine(final Function<Double, Double> function) {
        final XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
        for (double x = -range; x <= range; x = x + 0.01) {
            plotPoint(x, function.apply(x), series);
        }
        graph.getData().add(series);
    }

    private void plotPoint(final double x, final double y,
                           final XYChart.Series<Double, Double> series) {
        series.getData().add(new XYChart.Data<Double, Double>(x, y));
    }

    public void clear() {
        graph.getData().clear();
    }
}

public class DashboardController implements Initializable {

    @FXML
    private StackPane semiPane;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private CategoryAxis xline;

    @FXML
    private NumberAxis yline;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private CategoryAxis xbar;

    @FXML
    private NumberAxis ybar;

    @FXML
    private LineChart<Double, Double> lineGraph;

    @FXML
    private AreaChart<Double, Double> areaGraph;

    @FXML
    private Button lineGraphButton;

    @FXML
    private Button areaGraphButton;

    @FXML
    private Button xyButton;

    @FXML
    private Button xyButton2;

    @FXML
    private Button squaredButton;

    @FXML
    private Button squaredButton2;

    @FXML
    private Button cubedButton;

    @FXML
    private Button cubedButton2;

    @FXML
    private Button clearButton;

    private MyGraph mathsGraph;
    private MyGraph areaMathsGraph;


    public void drawPieChart(){
        ObservableList<SemiCircleChart.Data> dataList = FXCollections.observableArrayList(
                new SemiCircleChart.Data(5, "BAD", Color.RED),
                new SemiCircleChart.Data(3, "NORMAL", Color.GOLD),
                new SemiCircleChart.Data(2, "GOOD", Color.GREEN)
        );

        SemiCircleChart chart = new SemiCircleChart(dataList, 300, 300, 300,200,2);

        Label label = new Label("DATANAME");
        label.setFont(new Font(50));

        semiPane.getChildren().addAll(chart, label);
    }

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



//    -------------------------

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        mathsGraph = new MyGraph(lineGraph, 10);
        areaMathsGraph = new MyGraph(areaGraph, 10);
        drawPieChart();
        drawLineChart();
        drawBarChart();
    }

//    -----------------------------EX----------------------------
    @FXML
    private void handleLineGraphButtonAction(final ActionEvent event) {
        lineGraph.setVisible(true);
        areaGraph.setVisible(false);
    }

    @FXML
    private void handleAreaGraphButtonAction(final ActionEvent event) {
        areaGraph.setVisible(true);
        lineGraph.setVisible(false);
    }

    @FXML
    private void handleXYButtonAction(final ActionEvent event) {
        plotLine(x -> x);
    }

    private void plotLine(Function<Double, Double> function) {
        if (lineGraph.isVisible()) {
            mathsGraph.plotLine(function);
        } else {
            areaMathsGraph.plotLine(function);
        }
    }

    @FXML
    private void handleXYButton2Action(final ActionEvent event) {
        plotLine(x -> x - 3);
    }

    @FXML
    private void handleSquaredButtonAction(final ActionEvent event) {
        plotLine(x -> Math.pow(x, 2));
    }

    @FXML
    private void handleSquaredButton2Action(final ActionEvent event) {
        plotLine(x -> Math.pow(x, 2) + 2);
    }

    @FXML
    private void handleCubedButtonAction(final ActionEvent event) {
        plotLine(x -> Math.pow(x, 3));
    }

    @FXML
    private void handleCubedButton2Action(final ActionEvent event) {
        plotLine(x -> Math.pow(x - 3, 3) - 1);
    }

    @FXML
    private void handleClearButtonAction(final ActionEvent event) {
        clear();
    }

    private void clear() {
        if (lineGraph.isVisible()) {
            mathsGraph.clear();
        } else {
            areaMathsGraph.clear();
        }
    }


}
