package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
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

    public JFXTextField setTextField (String dataKey, boolean edit){
        textField = new JFXTextField();
        textField.setPromptText(dataKey);
        textField.setLabelFloat(true);
        textField.setText(String.valueOf(map.get(dataKey)).toUpperCase());
        textField.setFont(new Font(15));
        textField.setPrefHeight(10);
        textField.setPrefWidth(80);
        textField.setEditable(edit);
        textField.setAlignment(Pos.CENTER);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            map.put(dataKey, newValue);
        });

        return textField;

    }

    @FXML
    void updateClick(MouseEvent event) {
        new StudentDAO().update(id, map);
        new ModuleDAO().update(id, map);

        TabStudent.getInstance().refresh();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        declareStudent();
        declareSubject();

        m1.getChildren().addAll(
                setTextField("Student ID", false),
                setTextField("Student Name", true)
        );

        m2.getChildren().addAll(
                setTextField("Math",true),
                setTextField("Physics",true),
                setTextField("Chemistry",true),
                setTextField("English",true),
                setTextField("History",true),
                setTextField("Biology",true),
                setTextField("Geography",true)

        );



    }
}
