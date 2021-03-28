package controllers;

import com.jfoenix.controls.JFXTabPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import utils.MyGraph;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;



public class ScreenDashboard implements Initializable {


    @FXML
    private JFXTabPane DashboardTab = new JFXTabPane();

    @FXML
    private LineChart<Double, Double> lineGraph;

    @FXML
    private AreaChart<Double, Double> areaGraph;

    @FXML
    private AnchorPane CalendarPane;

    private MyGraph mathsGraph;
    private MyGraph areaMathsGraph;


    public Node StudentView(){
        try {
            AnchorPane studentTab = FXMLLoader.load(getClass().getResource("/views/TabStudent.fxml"));
            return studentTab;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Node TeacherView(){
        try {
            AnchorPane teacherTab = FXMLLoader.load(getClass().getResource("/views/TabTeacher.fxml"));
            return teacherTab;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Node UserView(){
        try {
            AnchorPane userTab = FXMLLoader.load(getClass().getResource("/views/TabUser.fxml"));
            return userTab;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void CalendarView(){
        try {
            AnchorPane calendarTab = FXMLLoader.load(getClass().getResource("/views/ComponentCalendar.fxml"));
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

        Tab studentTab = new Tab("Student", StudentView());
        Tab teacherTab = new Tab("Teacher", TeacherView());
        Tab userTab    = new Tab("User", UserView());
        DashboardTab.getTabs().addAll(studentTab, teacherTab, userTab);

        mathsGraph = new MyGraph(lineGraph, 10);
        areaMathsGraph = new MyGraph(areaGraph, 10);

//        -----------------
        CalendarView();
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
