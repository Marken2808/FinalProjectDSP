package main.controllers;

import com.jfoenix.controls.*;
import javafx.beans.binding.Bindings;
import javafx.css.Size;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.models.*;
import main.models.Module;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DrawerControlStudent implements Initializable {

    @FXML
    private VBox vBox;

    private JFXMasonryPane[] masonryPane  = new JFXMasonryPane[3];

    private JFXButton btnUpdate = new JFXButton("Update");

    private HashMap<String, Object> map = new HashMap<>();

    private JFXTextField textField;

    private int id = TabStudent.id;

    public void declareStudent(){

        Student students = new StudentDAO().retrieveStudentByID(id);
        map.put("Student ID", students.getStudentId());
        map.put("Student Name", students.getStudentName());

    }

    public void declareSubject(){
        Subject[] subjectData = new ModuleDAO().retrieveSubjectData(id);
        for ( Subject subject : subjectData ) {
            map.put(subject.getSubjectName(), subject.getSubjectMark());
        }
    }

    public StackPane setCard(String dataKey, double size, boolean edit){
        StackPane stackPane = new StackPane();
        stackPane.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.DOTTED, new CornerRadii(5), null)));
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        stackPane.setPadding(new Insets(0,0,10,0));
        stackPane.getChildren().add(setTextField(dataKey, size, edit));

        return stackPane;
    }
    public JFXTextField setTextField (String dataKey, double size, boolean edit){
        textField = new JFXTextField();
        textField.setPromptText(dataKey);
        textField.setLabelFloat(true);
        textField.setText(String.valueOf(map.get(dataKey)).toUpperCase());
        textField.setFont(new Font(15));
        textField.setPrefWidth(size);
        textField.setEditable(edit);
        textField.setAlignment(Pos.CENTER);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            map.put(dataKey, newValue);

        });
        return textField;

    }



    public void displayPane(){

        for(int i=0; i<masonryPane.length; i++){
            masonryPane[i] = new JFXMasonryPane();
            masonryPane[i].setPadding(new Insets(0,20,0,20));
        }
        masonryPane[0].getChildren().addAll(
                setCard("Student ID",    40,false),
                setCard("Student Name", 160,true)
        );

        masonryPane[1].getChildren().addAll(
                setCard("Math",     40,true),
                setCard("Physics",  40,true),
                setCard("Chemistry",40,true),
                setCard("English",  40,true),
                setCard("History",  40,true),
                setCard("Biology",  40,true),
                setCard("Geography",40,true)
        );

        StackPane calendarTab = null;
        try {
            calendarTab = FXMLLoader.load(getClass().getResource("/main/views/CalendarScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        StackPane stackPane = new StackPane();
//        stackPane.getChildren().add(calendarTab);



        btnUpdate.setOnMouseClicked(event -> {
            new StudentDAO().update(id, map);
            new ModuleDAO().update(id, map);

            refresh();
            TabStudent.getInstance().refresh();
        });

        vBox.setVgrow(calendarTab, Priority.ALWAYS);
        vBox.setVgrow(masonryPane[0], Priority.ALWAYS);
        vBox.setVgrow(masonryPane[1], Priority.ALWAYS);
        vBox.setPadding(new Insets(0,0,10,0));
        vBox.getChildren().addAll(calendarTab, masonryPane[0], masonryPane[1] , btnUpdate);

    }

    public void refresh(){

        vBox.getChildren().clear();
        displayPane();

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        declareStudent();
        declareSubject();

        displayPane();


    }
}
