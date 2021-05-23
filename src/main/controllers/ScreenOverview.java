package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import models.Attendance;
import models.AttendanceDAO;
import models.Student;
import models.StudentDAO;
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
    ObservableList<Student> studentTemp = new StudentDAO().showStudentTable();
    int all = studentTemp.size();
    int total;

    public void init(){
        for (int i = 0; i < studentTemp.size() ; i++) {
            new AttendanceDAO().insert(new Attendance("A", studentTemp.get(i).getStudentId()));
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

    public XYChart.Series getTotal (ArrayList<LocalDate> arrDays){

        XYChart.Series data = new XYChart.Series();

        for (int i=0; i<numOfDays(20).size(); i++) {
            if (arrDays.contains(numOfDays(20).get(i))) {
                total = dup(arrDays).get(numOfDays(20).get(i));     //total Present
                data.getData().add(new XYChart.Data(numOfDays(20).get(i).toString(), total));
            } else {                                                          //all off
                total = 0;
                data.getData().add(new XYChart.Data(numOfDays(20).get(i).toString(), total));
            }
        }
        return data;
    }

    public Map<String, XYChart.Series> getData(){
        ArrayList<Attendance> arrDB = new AttendanceDAO().retrieveAttendance();
        ArrayList<LocalDate> arrDaysPresent = new ArrayList<>();
        ArrayList<LocalDate> arrDaysAbsent = new ArrayList<>();
        Map<String, XYChart.Series> type = new HashMap<>();

        for (int i = 0; i < arrDB.size(); i++) {
            if(arrDB.get(i).getAttStatus().equals("P")){
                arrDaysPresent.add(arrDB.get(i).getAttDate().toLocalDate());
                type.put("P",getTotal(arrDaysPresent));
            } else {
                arrDaysAbsent.add(arrDB.get(i).getAttDate().toLocalDate());
                type.put("A",getTotal(arrDaysAbsent));
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

        ObservableList<CircleChart.Data> dataList = FXCollections.observableArrayList(
                new CircleChart.Data(all-total, "Present : " + (all-total), Color.LIMEGREEN),
                new CircleChart.Data(all, "Total: " + all, Color.LIGHTGRAY)
        );

        CircleChart chart = new CircleChart(180, dataList, 0, 0, 100,90,0);

        Label labelON = new Label((all-total)+"");
        labelON.setGraphic(new ImageView(new Image("/images/icon/users.png")));
        labelON.setContentDisplay(ContentDisplay.RIGHT);
        labelON.setFont(new Font("Times New Roman",36));

        stackPane.getChildren().addAll(chart , labelON);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        stackPane.setPadding(new Insets(10));
        totalPane.getChildren().add(stackPane);
    }

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
