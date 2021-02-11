package main.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Integer.MAX_VALUE;

public class Calendar implements Initializable{



    @FXML
    private GridPane gpBody;

    @FXML
    private Label labelTitle;

    private java.util.Calendar currentMonth;

    private String currentDate = new SimpleDateFormat("d/M/yyyy").format(new Date());

    String active    = "-fx-background-color: rgba(204,242,255,0.25)";
    String inactive  = "-fx-background-color: rgba(242,242,242,1)";
    String clickable = "-fx-background-color: rgba(48,173,255,0.5)";
    String whiteBase = "-fx-background-color: rgba(255,255,255,1)";


    public static Calendar instance;
    public Calendar(){
        instance = this;
    }
    public static Calendar getInstance() {
        if(instance == null){
            instance = new Calendar();
        }
        return instance;
    }

    private void drawCalendar() throws IOException, ParseException {
        drawHeader();
        drawBody();
    }

    private void drawHeader() {
        String monthString = getMonthName(currentMonth.get(java.util.Calendar.MONTH));
        String yearString = String.valueOf(currentMonth.get(java.util.Calendar.YEAR));
        labelTitle.setText(monthString + ", " + yearString);
    }

    public void displayCell_Text (Label text){
//        text.setPadding(new Insets(2));
        text.setMaxWidth(MAX_VALUE);
        text.setMaxHeight(MAX_VALUE);
        text.setAlignment(Pos.CENTER);
        text.setFont(new Font(12));
    }

    public void displayCell_Preview (Label text) throws IOException {
//        AnchorPane an = FXMLLoader.load(getClass().getResource("/main/views/PreviewScreen.fxml"));
//        text.setGraphic(an);
//        text.setContentDisplay(ContentDisplay.BOTTOM);
//
//        Random rand = new Random();
//        labelTotal.setText("10");   //change total here
//        int total = Integer.parseInt(labelTotal.getText());
//        int on = (rand.nextInt(total+1));
//        Preview.getInstance().showAbsence(on,total);
    }

    public void displayCell_Level (Label text, String level){

        text.setStyle(level);

        if(level.equals(inactive)){
            text.setDisable(true);
        } else {
            text.setDisable(false);
        }

    }

    public void displayCell_Presented (Label text, String buildDate) throws ParseException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        if(!sdf.parse(buildDate).after(sdf.parse(currentDate))) {
            displayCell_Action(text);
            displayCell_Preview(text);
        }
    }

    public void displayCell_Action (Label text){
        text.setCursor(Cursor.HAND);
        text.setOnMousePressed(event ->{
            text.setCursor(Cursor.WAIT);
            text.setStyle(clickable);
        });
        text.setOnMouseReleased(event -> {
            text.setCursor(Cursor.HAND);
            text.setStyle(active);
        });
        text.setOnMouseClicked(event -> {
            String AttendanceScreen = "/main/views/AttendanceScreen.fxml";
            Primary.getInstance().popUp(AttendanceScreen,true);
        });
    }

    public void displayCell_CurrentDate (Label text, String buildDate){
        if(currentDate.equals(buildDate)){
            text.setBorder(new Border(new BorderStroke(
                    Color.BLACK,
                    BorderStrokeStyle.DOTTED,
                    null,
                    new BorderWidths(1)
            )));
        }
    }

    public Node displayCell (String value, String level) throws IOException, ParseException {

        Label text = new Label(value);
        displayCell_Text(text);
        switch (level) {
            case "inactive":
                displayCell_Level(text, inactive);
                break;
            case "active":
                String buildDate = value+"/"+(currentMonth.get(java.util.Calendar.MONTH)+1)+"/"+(currentMonth.get(java.util.Calendar.YEAR));
                displayCell_Level(text, active);
                displayCell_CurrentDate(text, buildDate);
                displayCell_Presented(text, buildDate);
                break;
            default:
                displayCell_Level(text, whiteBase);
                text.setFont(Font.font("System",FontWeight.BOLD,10));
                break;
        }
        return text;
    }

    public void drawBody() throws IOException, ParseException {

//        draw current month
        int currentDay = currentMonth.get(java.util.Calendar.DAY_OF_MONTH);
        int daysInMonth = currentMonth.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        int dayOfWeek = currentMonth.get(java.util.Calendar.DAY_OF_WEEK);
        int row = 1;
        for (int i = currentDay; i <= daysInMonth; i++) {
            if (dayOfWeek == 8) {
                dayOfWeek = 1;
                row++;
            }
            gpBody.add(displayCell(String.valueOf(currentDay),"active"), dayOfWeek - 1, row);
//            gpBody.add(new Text(String.valueOf(currentDay)), dayOfWeek - 1, row);
            currentDay++;
            dayOfWeek++;

        }

        drawDay_OfWeek();
        drawDay_PreviousMonth(currentDay);
        drawDay_NextMonth(row);

    }

    public void drawDay_OfWeek() throws IOException, ParseException {
        // Draw days of the week
        for (int day = 1; day <= 7; day++) {
            gpBody.add(displayCell(getDayName(day), "header"), day - 1, 0);
        }
    }

    public void drawDay_PreviousMonth(int currentDay) throws IOException, ParseException {
        // Draw previous month days
        int dayOfWeek = currentMonth.get(java.util.Calendar.DAY_OF_WEEK);
        if (currentDay != 1) {
            java.util.Calendar prevMonth = getPreviousMonth(currentMonth);
            int daysInPrevMonth = prevMonth.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
            for (int i = dayOfWeek - 2; i >= 0; i--) {
                gpBody.add(displayCell(String.valueOf(daysInPrevMonth),"inactive"), i, 1);

                daysInPrevMonth--;
            }
        }
    }

    public void drawDay_NextMonth(int row) throws IOException, ParseException {
        // Draw next month days
        currentMonth.set(java.util.Calendar.DAY_OF_MONTH, currentMonth.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        int dayOfWeek = currentMonth.get(java.util.Calendar.DAY_OF_WEEK);
        if (dayOfWeek != 7) {
            int day = 1;
            for (int i = dayOfWeek; i < 7; i++) {

                gpBody.add(displayCell(String.valueOf(day),"inactive"), i, row);
                day++;
            }
        }
    }


    @FXML
    void testGrid(MouseEvent event) {
//        System.out.println(event.getTarget().toString());
        String selectClass = event.getTarget().toString().split("[@\\[]")[0];
        int selectMonth = (currentMonth.get(java.util.Calendar.MONTH)+1);
        int selectYear = (currentMonth.get(java.util.Calendar.YEAR));
        int selectDay = 0;

        try{
            if(selectClass.equals("Label")) {
                selectDay = Integer.parseInt(((Label) event.getTarget()).getText());
            } else if( selectClass.equals("Text")){
                selectDay = Integer.parseInt(((Text) event.getTarget()).getText());
            }
        } catch (Exception e){
            System.out.println("Cannot convert String");
        }


        String selectDate = selectDay+"/"+selectMonth+"/"+selectYear;

        System.out.println("Date: "+ selectDate);
    }

    @FXML
    void next(ActionEvent event) throws IOException, ParseException {
        gpBody.getChildren().clear();
        currentMonth = getNextMonth(currentMonth);
        drawCalendar();

    }

    @FXML
    void previous(ActionEvent event) throws IOException, ParseException {
        gpBody.getChildren().clear();
        currentMonth = getPreviousMonth(currentMonth);
        drawCalendar();
    }

    private GregorianCalendar getPreviousMonth(java.util.Calendar cal) {
        int cMonth = cal.get(java.util.Calendar.MONTH);
        int pMonth = cMonth == 0 ? 11 : cMonth - 1;
        int pYear = cMonth == 0 ? cal.get(java.util.Calendar.YEAR) - 1 : cal.get(java.util.Calendar.YEAR);
        return new GregorianCalendar(pYear, pMonth, 1);
    }

    private GregorianCalendar getNextMonth(java.util.Calendar cal) {
        int cMonth = cal.get(java.util.Calendar.MONTH);
        int pMonth = cMonth == 11 ? 0 : cMonth + 1;
        int pYear = cMonth == 11 ? cal.get(java.util.Calendar.YEAR) + 1 : cal.get(java.util.Calendar.YEAR);
        return new GregorianCalendar(pYear, pMonth, 1);
    }

    private String getDayName(int n) {
        StringBuilder sb = new StringBuilder();
        switch (n) {
            case 1:
                sb.append("Sun");
                break;
            case 2:
                sb.append("Mon");
                break;
            case 3:
                sb.append("Tue");
                break;
            case 4:
                sb.append("Wed");
                break;
            case 5:
                sb.append("Thu");
                break;
            case 6:
                sb.append("Fri");
                break;
            case 7:
                sb.append("Sat");
        }
        return sb.toString();
    }

    private String getMonthName(int n) {
        String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        return monthNames[n];
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentMonth = new GregorianCalendar();
        currentMonth.set(java.util.Calendar.DAY_OF_MONTH, 1);

        try {
            drawCalendar();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}