package controllers;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import models.Attendance;

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

    private Calendar currentMonth;

    private int id = TabStudent.id;

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

    public void drawCalendar() {
        drawHeader();
        drawBody();
    }

    private void drawHeader() {
        String monthString = getMonthName(currentMonth.get(Calendar.MONTH));
        String yearString = String.valueOf(currentMonth.get(Calendar.YEAR));
        labelTitle.setText(monthString + ", " + yearString);
    }

    public Node displayCell_Basement (String value) {
        String showDay = value.split("\\ ")[0];
        Label text = new Label(showDay);
        text.setDisable(true);

        return text;
    }

    public Node displayCell_Ground (String value) {
        String showDay = value.split("\\ ")[0];
        Label text = new Label(showDay);
        text.setFont(Font.font("System",FontWeight.BOLD,12));

        return text;

    }

    public Border handleBorder(boolean isCurDate) {
        return new Border(new BorderStroke(
                isCurDate?Color.BLACK : Color.valueOf("#b3b3b3"),
                isCurDate?BorderStrokeStyle.DOTTED : BorderStrokeStyle.SOLID,
                isCurDate?null : new CornerRadii(20),
                isCurDate?new BorderWidths(1) : new BorderWidths(0.5)
        ));
    }

    public Background handleBackground(String status, boolean isPress){

        Color color = null;
        switch (status) {
            case "P":
                color = isPress ? Color.rgb(186, 227, 186, 0.15) : Color.rgb(186, 227, 186, 0.3);
                break;
            case "A":
                color = isPress ? Color.rgb(255, 199, 199, 0.15) : Color.rgb(255, 199, 199, 0.3);
                break;
            case "O":
                color = isPress ? Color.rgb(203, 204, 205, 0.15) : Color.rgb(203, 204, 205, 0.3);
                break;
        }

        return new Background(new BackgroundFill(
                color, new CornerRadii(20), new Insets(3)
        ));
    }

    public Node displayCell_Floor (String value) {

        String showDay = value.split("\\ ")[0];
        Label text = new Label(showDay);
        text.setFont(new Font(15));
        text.setMaxWidth(MAX_VALUE);
        text.setMaxHeight(MAX_VALUE);
        text.setAlignment(Pos.CENTER);

        text.borderProperty().bind(Bindings.when(text.hoverProperty())
                .then(handleBorder(false)).otherwise(Border.EMPTY)
        );

        text.cursorProperty().bind(Bindings.when(text.pressedProperty())
                .then(Cursor.WAIT).otherwise(Cursor.HAND)
        );
        
        ArrayList<LocalDate> allDate = new ArrayList<>();
        try {
            Date sqlDate = new Date(new SimpleDateFormat("d M yyyy").parse(value).getTime());
            allDate.add(sqlDate.toLocalDate());
            ArrayList<Attendance> testAll= new Attendance().getAllDate(id, allDate);

            for (Attendance a : testAll) {
                if (sqlDate.equals(a.getAttDate())) {
                    text.backgroundProperty().bind(Bindings.when(text.pressedProperty())
                            .then( handleBackground(a.getAttStatus(), true))
                            .otherwise( handleBackground(a.getAttStatus(), false))
                    );

                    text.setOnMouseClicked(event -> {
                        String AttendanceScreen = "/main/views/ComponentAttendStats.fxml";
                        ScreenPrimary.getInstance().displayPopup(AttendanceScreen,true);
                    });

                }
            }

            if(sqlDate.toLocalDate().equals(LocalDate.now())){
                text.borderProperty().bind(Bindings.when(text.hoverProperty())
                        .then(new Border(new BorderStroke(null,BorderStrokeStyle.DASHED,null,null)))
                        .otherwise(handleBorder(true))
                );
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void drawBody() {

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


            gpBody.add(displayCell_Floor(value), dayOfWeek - 1, row);
            currentDay++;
            dayOfWeek++;
        }

        drawDay_OfWeek();
        drawDay_PreviousMonth(currentDay);
        drawDay_NextMonth(row);

    }

    public void drawDay_OfWeek() {
        // Draw days of the week
        for (int day = 1; day <= 7; day++) {
            gpBody.add(displayCell_Ground(getDayName(day)), day - 1, 0);
        }
    }

    public void drawDay_PreviousMonth(int currentDay) {
        // Draw previous month days

        int dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        if (currentDay != 1) {
            Calendar prevMonth = getPreviousMonth(currentMonth);
            int daysInPrevMonth = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = dayOfWeek - 2; i >= 0; i--) {

                String value = daysInPrevMonth+" "+(prevMonth.get(Calendar.MONTH)+1)+" "+prevMonth.get(Calendar.YEAR);
//                System.out.println("Prev: "+sqlDate);

                gpBody.add(displayCell_Basement(value), i, 1);

                daysInPrevMonth--;
            }
        }
    }

    public void drawDay_NextMonth(int row) {
        // Draw next month days
        currentMonth.set(Calendar.DAY_OF_MONTH, currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH));

        int dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != 7) {
            Calendar nextMonth = getNextMonth(currentMonth);
            int day = 1;
            for (int i = dayOfWeek; i < 7; i++) {

                String value = day+" "+(nextMonth.get(Calendar.MONTH)+1)+" "+nextMonth.get(Calendar.YEAR);
//                System.out.println("Next: "+sqlDate);

                gpBody.add(displayCell_Basement(value), i, row);
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
    void next(ActionEvent event) {
        gpBody.getChildren().clear();
        currentMonth = getNextMonth(currentMonth);
        drawCalendar();

    }

    @FXML
    void previous(ActionEvent event) {
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

        drawCalendar();
    }
}