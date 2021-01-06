package main.controllers;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.sun.javafx.scene.control.LabeledText;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

    private String currentDate = new SimpleDateFormat("d/M/yyyy").format(new Date());

    int selectMonth;
    int selectYear ;
    int selectDay=0;
    String selectDate;


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

    public Node buttonCell(String value, int level){

        String active    = "-fx-background-color: rgba(219,219,245,0.25)";
        String inactive  = "-fx-background-color: rgba(232,232,222,0.25)";
        String clickable = "-fx-background-color: rgba(174,253,171,0.25)";
        String hightlight= "-fx-background-color: rgba(253,252,171,0.25)";

        Label text = new Label(value);

        text.setPadding(new Insets(3));
        text.setMaxWidth(MAX_VALUE);
        text.setMaxHeight(MAX_VALUE);
        text.setAlignment(Pos.CENTER);


        switch (level) {
            case 0:     //inactive

                text.setStyle(inactive);
                text.setFont(new Font(15));
                text.setDisable(true);

                break;
            case 1:     //active

                text.setStyle(active);
                text.setFont(new Font(15));
                text.setDisable(false);
                ImageView img = new ImageView();
                img.setImage(new Image("/resources/images/icon/airplay.png"));
                text.setGraphic(img);
                text.setContentDisplay(ContentDisplay.BOTTOM);

                text.setOnMousePressed(event -> text.setStyle(clickable));
                text.setOnMouseReleased(event -> text.setStyle(active));

                String buildDate = value+"/"+(currentMonth.get(Calendar.MONTH)+1)+"/"+(currentMonth.get(Calendar.YEAR));
                if(currentDate.equals(buildDate)){

//                    System.out.println("Test"+Arrays.toString(test));
//                    System.out.println("Date"+Arrays.toString(date));
//                    System.out.println("DAY: " + date[0].equals(test[0]));
//                    System.out.println("MON: " + date[1].equals(test[1]));
//                    System.out.println("YEA: " + date[2].equals(test[2]));

                    text.setBorder(new Border(new BorderStroke(
                            Color.BLACK,
                            BorderStrokeStyle.SOLID,
                            null,
                            new BorderWidths(1)
                    )));
                }

                break;
            default:
                text.setDisable(false);
                text.setFont(new Font(18));
                break;
        }

        text.setOnMouseClicked(event -> {
            System.out.println("Popup ");
            String AttendanceScreen = "/main/views/AttendanceScreen.fxml";
            MainController.getInstance().popUp(AttendanceScreen,true);
        });


//        btn = new JFXButton(value);
//        btn.setMaxWidth(MAX_VALUE);
//        btn.setMaxHeight(MAX_VALUE);
//        btn.setDisable(!active);
        return text;
//        vBox.getChildren().addAll(text);
//        return vBox;
    }

    private void drawBody() {


        // Draw days of the week
        for (int day = 1; day <= 7; day++) {
            gpBody.add(buttonCell(getDayName(day), 2), day - 1, 0);
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
            gpBody.add(buttonCell(String.valueOf(currentDay),1), dayOfWeek - 1, row);
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
                gpBody.add(buttonCell(String.valueOf(daysInPrevMonth),0), i, 1);

                daysInPrevMonth--;
            }
        }

        // Draw next month days
        currentMonth.set(Calendar.DAY_OF_MONTH, currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != 7) {
            int day = 1;
            for (int i = dayOfWeek; i < 7; i++) {

                gpBody.add(buttonCell(String.valueOf(day),0), i, row);
//                gpBody.add(buttonCell(String.valueOf(day),false), i, row);
                day++;
            }
        }

    }

    private void drawFooter() {
    }


    @FXML
    void testGrid(MouseEvent event) {
//        System.out.println(event.getTarget().toString());
        String selectClass = event.getTarget().toString().split("[@\\[]")[0];

        if(selectClass.equals("Label")){
            try{
                this.selectDay = Integer.parseInt(((Label) event.getTarget()).getText());
            } catch (Exception e){
                System.out.println("cannot convert string");
            }

        } else if( selectClass.equals("Text")){
            try {
                this.selectDay = Integer.parseInt(((Text) event.getTarget()).getText());
            } catch (Exception e){
                System.out.println("cannot convert string");
            }

        }


        selectDate = selectDay+"/"+selectMonth+"/"+selectYear;

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

        selectMonth = (currentMonth.get(Calendar.MONTH)+1);
        selectYear = (currentMonth.get(Calendar.YEAR));

        drawCalendar();


    }
}
