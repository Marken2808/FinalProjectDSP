package main.controllers;

import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.lang.Integer.MAX_VALUE;

public class CalendarController implements Initializable{


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gpBody;

    @FXML
    private HBox hbFooter;

    @FXML
    private Text tHeader;

    @FXML
    private JFXButton btNext;

    @FXML
    private JFXButton btPrev;

    private Calendar currentMonth;

    private JFXButton btn;

    private void drawCalendar() {

        drawHeader();
        drawBody();
        drawFooter();
    }

    private void drawHeader() {
        String monthString = getMonthName(currentMonth.get(Calendar.MONTH));
        String yearString = String.valueOf(currentMonth.get(Calendar.YEAR));
        tHeader.setText(monthString + ", " + yearString);
    }

    public Node buttonCell(String value, boolean active){

        Label text = new Label(value);
        text.setFont(new Font(30));
        btn = new JFXButton(text.getText());
        btn.setMaxWidth(MAX_VALUE);
        btn.setMaxHeight(MAX_VALUE);
        btn.setDisable(!active);
        return btn;
    }

    private void drawBody() {


        // Draw days of the week
        for (int day = 1; day <= 7; day++) {
            gpBody.add(buttonCell(getDayName(day),true), day - 1, 0);
//            gpBody.add(new Text(getDayName(day)), day - 1, 0);
        }


        // Draw days in month
        int currentDay = currentMonth.get(Calendar.DAY_OF_MONTH);
        int daysInMonth = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        int row = 1;
        for (int i = currentDay; i <= daysInMonth; i++) {
            if (dayOfWeek == 8) {
                dayOfWeek = 1;
                row++;
            }
            gpBody.add(buttonCell(String.valueOf(currentDay),true), dayOfWeek - 1, row);
//            gpBody.add(new Text(String.valueOf(currentDay)), dayOfWeek - 1, row);
            currentDay++;
            dayOfWeek++;
        }

        // Draw previous month days
        dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        if (currentDay != 1) {
            Calendar prevMonth = getPreviousMonth(currentMonth);
            int daysInPrevMonth = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = dayOfWeek - 2; i >= 0; i--) {
//                Text tDate = new Text(String.valueOf(daysInPrevMonth));
//                tDate.setFill(Color.GRAY);
                gpBody.add(buttonCell(String.valueOf(daysInPrevMonth),false), i, 1);
//                gpBody.add(new Text(String.valueOf(daysInPrevMonth)), i, 1);

                daysInPrevMonth--;
            }
        }

        // Draw next month days
        currentMonth.set(Calendar.DAY_OF_MONTH, currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != 7) {
            int day = 1;
            for (int i = dayOfWeek; i < 7; i++) {
//                Text tDate = new Text(String.valueOf(day));
//                tDate.setFill(Color.GRAY);
//                gpBody.add(new Text(String.valueOf(day)), i, row);
                gpBody.add(buttonCell(String.valueOf(day),false), i, row);
                day++;
            }
        }

    }

    private void drawFooter() {
    }


    @FXML
    void testGrid(MouseEvent event) {
//        System.out.println(((Text) event.getTarget()).getText());
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
                sb.append("Sunday");
                break;
            case 2:
                sb.append("Monday");
                break;
            case 3:
                sb.append("Tuesday");
                break;
            case 4:
                sb.append("Wednesday");
                break;
            case 5:
                sb.append("Thursday");
                break;
            case 6:
                sb.append("Friday");
                break;
            case 7:
                sb.append("Saturday");
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
