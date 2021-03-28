package controllers;

import com.jfoenix.animation.alert.CenterTransition;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import models.*;
import utils.CircleChart;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ScreenOverview implements Initializable {

    @FXML
    private JFXTextField fieldCode;

    @FXML
    private StackPane calendarPane;

    @FXML
    private StackPane totalPane;

    @FXML
    private AreaChart<?, ?> areaChart;

    boolean isAbsent  = false;
    int all = new StudentDAO().showStudentTable().size();
    int total;

    public void init(){
        for (int i = 0; i <= all ; i++) {
            new AttendanceDAO().insert(new Attendance("A", i));
        }
    }

    public Map<LocalDate, Integer> dup(ArrayList<LocalDate> testDate) {
        Set<LocalDate> set = new HashSet<>();
        Map<LocalDate, Integer> map = new HashMap<>();

        for (LocalDate d : testDate) {
            if (!set.add(d)) {
                if (map.containsKey(d)){
                    map.put(d,map.get(d)+1);
                }
            } else {
                map.put(d,1);
            }
        }

        return map;
    }

    public ArrayList<LocalDate> numOfDays(int range) {
        ArrayList<LocalDate> arrDays = new ArrayList<>();
        arrDays.add(LocalDate.now());
        for (int i = 1; i < range; i++) {
            arrDays.add(arrDays.get(i - 1).minusDays(1));
        }
        Collections.sort(arrDays);
        return arrDays;
    }

    public XYChart.Series areaPresent(){
        Map<String, XYChart.Series> presentMap = getData();
        return presentMap.get("P");
    }

    public XYChart.Series areaAbsent(){
        Map<String, XYChart.Series> absentMap = getData();
        return absentMap.get("A");
    }

    public Map<String, XYChart.Series> getData(){
        ArrayList<Attendance> temp = new AttendanceDAO().retrieveAttendance();
        ArrayList<LocalDate> arrDaysPresent = new ArrayList<>();
        ArrayList<LocalDate> arrDaysAbsent = new ArrayList<>();
        Map<String, XYChart.Series> type = new HashMap<>();
        XYChart.Series present = new XYChart.Series();
        XYChart.Series absent = new XYChart.Series();

        for (int i = 0; i < temp.size(); i++) {
            if(temp.get(i).getAttStatus().equals("P")){
                arrDaysPresent.add(temp.get(i).getAttDate().toLocalDate());
            } else {
                arrDaysAbsent.add(temp.get(i).getAttDate().toLocalDate());
            }
        }

        for (int i=0; i<numOfDays(20).size(); i++) {

            if (arrDaysPresent.contains(numOfDays(20).get(i))) {
                total = dup(arrDaysPresent).get(numOfDays(20).get(i));     //total Present
                present.getData().add(new XYChart.Data(numOfDays(20).get(i).toString(), total));
                type.put("P",present);
            } else {                                                          //all off
                present.getData().add(new XYChart.Data(numOfDays(20).get(i).toString(), 0));
                type.put("P",present);
            }

            if (arrDaysAbsent.contains(numOfDays(20).get(i))) {
                total = dup(arrDaysAbsent).get(numOfDays(20).get(i));     //total Present
                absent.getData().add(new XYChart.Data(numOfDays(20).get(i).toString(), total));
                type.put("A",absent);
            } else {                                                          //all off
                absent.getData().add(new XYChart.Data(numOfDays(20).get(i).toString(), 0));
                type.put("A",absent);
            }
        }

        return type;
    }

    public String getAlphaNumeric(int amount)
    {

        // chose a Character random from this String
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // create StringBuffer size of AlphaNumericString
        StringBuilder strCode = new StringBuilder(amount);

        for (int i = 0; i < amount; i++) {

            int index = (int)(s.length()* Math.random());

            // add Character one by one in end of sb
            if(i%2==0 && i!=0) {
                strCode.append("-");
            }
            strCode.append(s.charAt(index));
        }
        return strCode.toString();
    }

    @FXML
    void onToggleAbsent(MouseEvent event) {
        if (!isAbsent){
            areaChart.getData().add(areaAbsent());
            isAbsent = true;
        } else {
            areaChart.getData().remove(1);
            isAbsent = false;
        }
    }

    @FXML
    void onGenerate(MouseEvent event) {
        fieldCode.setText(getAlphaNumeric(6));
    }


    public void setSemiCircleChart(){
        StackPane stackPane = new StackPane();
        JFXButton btnPane = new JFXButton();
        VBox vBox = new VBox();

        ObservableList<CircleChart.Data> dataList = FXCollections.observableArrayList(
                new CircleChart.Data(total, "Present : "+total, Color.LIMEGREEN),
                new CircleChart.Data(all-total, "Total", Color.LIGHTGRAY)
        );

        CircleChart chart = new CircleChart(180, dataList, 0, 0, 100,90,0);

//        Label labelName = new Label(name);
//        Label labelAchieve = new Label(achieve+"");

//        vBox.getChildren().addAll(labelName, labelAchieve);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
//        labelName.setFont(new Font(15));
//        stackPane.setMargin(vBox,new Insets(0,0,0,0));
        stackPane.getChildren().addAll(chart, vBox);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        btnPane.setGraphic(stackPane);
        totalPane.getChildren().add(btnPane);
    }

//    public void drawSemiCircleChart(){
//
//        Subject[] subjectData = new ModuleDAO().retrieveSubjectData(id);
//
//        for(Subject s: subjectData) {
//            setSemiCircleChart(s.getSubjectName(), s.getSubjectMark());
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();

        areaChart.getData().add(areaPresent());
        try {
            AnchorPane calendarTab = FXMLLoader.load(getClass().getResource("/views/ComponentCalendar.fxml"));
            calendarPane.getChildren().add(calendarTab);
            calendarPane.setAlignment(Pos.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setSemiCircleChart();


    }
}
