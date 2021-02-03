package main.controllers;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.models.Student;
import main.models.StudentDAO;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DrawerControlStudent implements Initializable {

    @FXML
    private JFXMasonryPane m1;

    @FXML
    private JFXMasonryPane m2;

    private JFXTextField[] sField = new JFXTextField[2];
    private JFXTextField aField = new JFXTextField();
    private JFXTextField mField = new JFXTextField();
    private JFXTextField fField = new JFXTextField();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int id = TabStudent.id;
        Student students = new StudentDAO().retrieveStudentByID(id);
        sField[0].setPromptText("Student ID");
        sField[1].setPromptText("Student Name");

        m1.getChildren().addAll(sField);
    }
}
