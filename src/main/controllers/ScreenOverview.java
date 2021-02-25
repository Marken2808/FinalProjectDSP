package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import main.models.Attendance;
import main.models.AttendanceDAO;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ScreenOverview implements Initializable {


    @FXML
    private StackPane CalendarSide;

    @FXML
    private AreaChart<?, ?> areaChart;


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

    public XYChart.Series dataChart(){
        XYChart.Series present = new XYChart.Series();
        ArrayList<Attendance> temp = new AttendanceDAO().retrieveAttendance();
        ArrayList<LocalDate> arrDays = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if(temp.get(i).getAttStatus().equals("P")){
                arrDays.add(temp.get(i).getAttDate().toLocalDate());
            }
        }


        for (int i=0; i<numOfDays(8).size(); i++){
            int total = dup(arrDays).get(numOfDays(8).get(i));
            present.getData().add(new XYChart.Data(numOfDays(8).get(i).toString(), total));
        }

        return present;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        areaChart.getData().add(dataChart());

        try {
            AnchorPane calendarTab = FXMLLoader.load(getClass().getResource("/main/views/ComponentCalendar.fxml"));
            CalendarSide.getChildren().add(calendarTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
