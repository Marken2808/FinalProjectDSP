package main.controllers;

import com.jfoenix.controls.JFXMasonryPane;
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
    private JFXMasonryPane semiPane;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private LineChart<Double, Double> lineGraph;

    @FXML
    private AreaChart<Double, Double> areaGraph;

    private MyGraph mathsGraph;
    private MyGraph areaMathsGraph;


    public void drawSemiCircleChart(String name, int achieve){
        StackPane stackPane = new StackPane();
        ObservableList<SemiCircleChart.Data> dataList = FXCollections.observableArrayList(
                new SemiCircleChart.Data(achieve, "Achieve", Color.LIMEGREEN),
                new SemiCircleChart.Data(10-achieve, "Total", Color.LIGHTGRAY)
        );

        SemiCircleChart chart = new SemiCircleChart(dataList, 90, 90, 90,80,0);

        Label label = new Label(name);
        label.setFont(new Font(20));
        stackPane.getChildren().addAll(chart, label);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        semiPane.getChildren().add(stackPane);
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
        drawSemiCircleChart("MATH",7);
        drawSemiCircleChart("PHYSICS",4);
        drawSemiCircleChart("CHEMISTRY",4);
        drawSemiCircleChart("ENGLISH",8);
        drawSemiCircleChart("HISTORY",5);
        drawSemiCircleChart("BIOLOGY",6);
        drawSemiCircleChart("GEOGRAPHY",3);
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
