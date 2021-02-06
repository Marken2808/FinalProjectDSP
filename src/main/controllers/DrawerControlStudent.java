package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.models.*;
import main.models.Module;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DrawerControlStudent implements Initializable {

    @FXML
    private JFXMasonryPane m1;

    @FXML
    private JFXMasonryPane m2;

    @FXML
    private JFXButton btnUpdate;




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
        stackPane.getChildren().add(setTextField(dataKey, size, edit));
//        stackPane.setPrefHeight(40);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        stackPane.setPadding(new Insets(10,0,5,0));
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
//        textField.setStyle("-fx-background-color: red");
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            map.put(dataKey, newValue);
        });

        return textField;

    }

    public void load(){


        m1.getChildren().clear();
        m2.getChildren().clear();


        m1.getChildren().addAll(
                setCard("Student ID",    40,false),
                setCard("Student Name", 160,true)
        );

        m2.getChildren().addAll(
                setCard("Math",     40,true),
                setCard("Physics",  40,true),
                setCard("Chemistry",40,true),
                setCard("English",  40,true),
                setCard("History",  40,true),
                setCard("Biology",  40,true),
                setCard("Geography",40,true)

        );
    }

    @FXML
    void updateClick(MouseEvent event) {
        new StudentDAO().update(id, map);
        new ModuleDAO().update(id, map);

        load();
        TabStudent.getInstance().refresh();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        declareStudent();
        declareSubject();

        load();


    }
}
