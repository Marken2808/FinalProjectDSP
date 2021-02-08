package main.controllers;

import com.jfoenix.controls.JFXMasonryPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.utils.CircleChart;
import main.utils.MyGraph;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Function;



public class Dashboard implements Initializable {

    @FXML
    private LineChart<Double, Double> lineGraph;

    @FXML
    private AreaChart<Double, Double> areaGraph;

    @FXML
    private AnchorPane CalendarPane;

    @FXML
    private AnchorPane StudentPane;


    private MyGraph mathsGraph;
    private MyGraph areaMathsGraph;

//    public static Dashboard instance;
//    public Dashboard(){
//        instance = this;
//    }
//    public static Dashboard getInstance() {
//        if(instance == null){
//            instance = new Dashboard();
//        }
//        return instance;
//    }

    public void StudentView(){
        try {
            AnchorPane studentTab = FXMLLoader.load(getClass().getResource("/main/views/TabStudent.fxml"));
            StudentPane.getChildren().addAll(studentTab);
            StudentPane.setTopAnchor(studentTab,0.0);
            StudentPane.setBottomAnchor(studentTab,0.0);
            StudentPane.setRightAnchor(studentTab,0.0);
            StudentPane.setLeftAnchor(studentTab,0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void CalendarView(){
        try {
            StackPane calendarTab = FXMLLoader.load(getClass().getResource("/main/views/CalendarScreen.fxml"));
            CalendarPane.getChildren().addAll(calendarTab);
            CalendarPane.setTopAnchor(calendarTab,0.0);
            CalendarPane.setBottomAnchor(calendarTab,0.0);
            CalendarPane.setRightAnchor(calendarTab,0.0);
            CalendarPane.setLeftAnchor(calendarTab,0.0);
//            return CalendarPane;
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return null;
    }

//    -------------------------

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        mathsGraph = new MyGraph(lineGraph, 10);
        areaMathsGraph = new MyGraph(areaGraph, 10);

//        -----------------
        CalendarView();
        StudentView();
//        -----------------
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
