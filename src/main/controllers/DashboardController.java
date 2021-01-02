package main.controllers;

import com.jfoenix.controls.JFXMasonryPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.utils.CircleChart;
import main.utils.MyGraph;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Function;



public class DashboardController implements Initializable {

    @FXML
    private JFXMasonryPane semiPane;

    @FXML
    private StackPane doughnutPane;

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

    public void drawDoughnutChart(){
        StackPane stackPane = new StackPane();
        ObservableList<CircleChart.Data> dataList = FXCollections.observableArrayList(
                new CircleChart.Data(7, "MATH", Color.LIMEGREEN),
                new CircleChart.Data(4, "PHYSICS", Color.LIGHTBLUE),
                new CircleChart.Data(4, "CHEMISTRY", Color.BISQUE),
                new CircleChart.Data(8, "ENGLISH", Color.LIGHTPINK),
                new CircleChart.Data(5, "HISTORY", Color.LAVENDER),
                new CircleChart.Data(6, "BIOLOGY", Color.HONEYDEW),
                new CircleChart.Data(3, "GEOGRAPHY", Color.TOMATO)


        );

        CircleChart chart = new CircleChart(360, dataList, 100, 100, 100,70,2);

        Label label = new Label("Student\nOverall");
        label.setFont(new Font(20));
        stackPane.getChildren().addAll(chart, label);
        doughnutPane.getChildren().add(stackPane);
    }

    public void drawSemiCircleChart(String name, int achieve){
        StackPane stackPane = new StackPane();
        VBox vBox = new VBox();

        ObservableList<CircleChart.Data> dataList = FXCollections.observableArrayList(
                new CircleChart.Data(achieve, "Achieve : "+achieve, Color.LIMEGREEN),
                new CircleChart.Data(10-achieve, "Total", Color.LIGHTGRAY)
        );

        CircleChart chart = new CircleChart(180, dataList, 0, 0, 100,85,0);

        Label labelName = new Label(name);
        Label labelAchieve = new Label(achieve+"");

        vBox.getChildren().addAll(labelName, labelAchieve);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
//        vBox.setStyle("-fx-border-color: black; -fx-border-width: 1");
        labelName.setFont(new Font(15));
//        labelAchieve.setPadding(new Insets(0,5,0,5));
//        labelAchieve.setStyle("-fx-background-radius: 100; -fx-background-color: lawngreen");
//        stackPane.setStyle("-fx-border-color: black; -fx-border-width: 1");
        stackPane.setMargin(vBox,new Insets(60,60,0,60));
        stackPane.getChildren().addAll(chart, vBox);
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

        String[] subjects = new String[]{"MATH","PHYSICS","CHEMISTRY","ENGLISH","HISTORY","BIOLOGY","GEOGRAPHY"};
        for(String subject : subjects) {
            int rand = new Random().nextInt(10);
            drawSemiCircleChart(subject, rand);
        }
        drawDoughnutChart();
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
