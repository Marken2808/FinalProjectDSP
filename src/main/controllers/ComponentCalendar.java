package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

import static java.lang.Integer.MAX_VALUE;

public class ComponentCalendar implements Initializable{



    @FXML
    private GridPane gpBody;

    @FXML
    private Label labelTitle;

    Label labelTotal = new Label();

    private Calendar currentMonth;

    private String currentDate = new SimpleDateFormat("dd M yyyy").format(new java.util.Date());

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    String active    = "-fx-background-color: rgba(204,242,255,0.25)";
    String inactive  = "-fx-background-color: rgba(242,242,242,1)";
    String absent    = "-fx-background-color: red";
    String present   = "-fx-background-color: green";
    String clickable = "-fx-background-color: rgba(48,173,255,0.5)";
    String whiteBase = "-fx-background-color: rgba(255,255,255,1)";


    public static ComponentCalendar instance;
    public ComponentCalendar(){
        instance = this;
    }
    public static ComponentCalendar getInstance() {
        if(instance == null){
            instance = new ComponentCalendar();
        }
        return instance;
    }

    private void drawCalendar() throws IOException, ParseException {
        drawHeader();
        drawBody();
    }

    private void drawHeader() {
        String monthString = getMonthName(currentMonth.get(Calendar.MONTH));
        String yearString = String.valueOf(currentMonth.get(Calendar.YEAR));
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd M yyyy");
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

//        Date sqlDate= new Date(formatter.parse(value).getTime());

        String showDay = value.split("\\ ")[0];
        Label text = new Label(showDay);
        displayCell_Text(text);
        switch (level) {
            case "inactive":
                displayCell_Level(text, inactive);
                break;
            case "active":
                displayCell_Level(text, active);
                displayCell_CurrentDate(text, value);
                displayCell_Presented(text, value);
                break;
            case "absent":
                displayCell_Level(text, absent);
                break;
            case "present":
                displayCell_Level(text, present);
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
        int currentDay = currentMonth.get(Calendar.DAY_OF_MONTH);
        int daysInMonth = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        int row = 1;
        for (int i = currentDay; i <= daysInMonth; i++) {
            if (dayOfWeek == 8) {
                dayOfWeek = 1;
                row++;
            }

            String value = currentDay+" "+(currentMonth.get(Calendar.MONTH)+1)+" "+currentMonth.get(Calendar.YEAR);
//            System.out.println("current: "+sqlDate);


            gpBody.add(displayCell(value,"active"), dayOfWeek - 1, row);
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

        int dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        if (currentDay != 1) {
            Calendar prevMonth = getPreviousMonth(currentMonth);
            int daysInPrevMonth = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = dayOfWeek - 2; i >= 0; i--) {

                String value = daysInPrevMonth+" "+(prevMonth.get(Calendar.MONTH)+1)+" "+prevMonth.get(Calendar.YEAR);
//                System.out.println("Prev: "+sqlDate);

                gpBody.add(displayCell(value,"inactive"), i, 1);

                daysInPrevMonth--;
            }
        }
    }

    public void drawDay_NextMonth(int row) throws IOException, ParseException {
        // Draw next month days
        currentMonth.set(Calendar.DAY_OF_MONTH, currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH));

        int dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != 7) {
            Calendar nextMonth = getNextMonth(currentMonth);
            int day = 1;
            for (int i = dayOfWeek; i < 7; i++) {

                String value = day+" "+(nextMonth.get(Calendar.MONTH)+1)+" "+nextMonth.get(Calendar.YEAR);
//                System.out.println("Next: "+sqlDate);

                gpBody.add(displayCell(value,"inactive"), i, row);
                day++;
            }
        }
    }


    @FXML
    void testGrid(MouseEvent event) {
//        System.out.println(event.getTarget().toString());
        String selectClass = event.getTarget().toString().split("[@\\[]")[0];
        int selectMonth = (currentMonth.get(Calendar.MONTH)+1);
        int selectYear = (currentMonth.get(Calendar.YEAR));
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

    private GregorianCalendar getPreviousMonth(Calendar cal) {

        int cMonth = cal.get(Calendar.MONTH);
        int pMonth = cMonth == 0 ? 11 : cMonth - 1;
        int pYear = cMonth == 0 ? cal.get(Calendar.YEAR) - 1 : cal.get(Calendar.YEAR);
        return new GregorianCalendar(pYear, pMonth, 1);
    }

    private GregorianCalendar getNextMonth(Calendar cal) {
        int cMonth = cal.get(Calendar.MONTH);
        int pMonth = cMonth == 11 ? 0 : cMonth + 1;
        int pYear = cMonth == 11 ? cal.get(Calendar.YEAR) + 1 : cal.get(Calendar.YEAR);
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
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);

        try {
            drawCalendar();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}