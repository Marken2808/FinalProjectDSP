package controllers;

import com.jfoenix.animation.alert.CenterTransition;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import models.Attendance;
import models.AttendanceDAO;

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
        present.setName("Total Attendance");

        ArrayList<Attendance> temp = new AttendanceDAO().retrieveAttendance();
        ArrayList<LocalDate> arrDays = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if(temp.get(i).getAttStatus().equals("P")){
                arrDays.add(temp.get(i).getAttDate().toLocalDate());
            }
        }

        for (int i=0; i<numOfDays(20).size(); i++){
            int total = dup(arrDays).get(numOfDays(20).get(i));
            present.getData().add(new XYChart.Data(numOfDays(20).get(i).toString(), total));
        }

        return present;
    }

    public String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // create StringBuffer size of AlphaNumericString
        StringBuilder strCode = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

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
    void onGenerate(MouseEvent event) {
        fieldCode.setText(getAlphaNumericString(6));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        areaChart.getData().add(dataChart());

        try {
            AnchorPane calendarTab = FXMLLoader.load(getClass().getResource("/views/ComponentCalendar.fxml"));
            calendarPane.getChildren().add(calendarTab);
            calendarPane.setAlignment(Pos.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
