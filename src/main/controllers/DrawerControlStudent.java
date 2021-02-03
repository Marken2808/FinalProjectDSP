package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.models.Student;
import main.models.StudentDAO;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DrawerControlStudent implements Initializable {

    @FXML
    private JFXMasonryPane m1;

    @FXML
    private JFXMasonryPane m2;

    private HashMap<String, String> test = new HashMap<>();

    private JFXTextField textField;

    private int id = TabStudent.id;

    public void declareStudent(){

        Student students = new StudentDAO().retrieveStudentByID(id);
        test.put("Student ID", String.valueOf(students.getStudentId()));
        test.put("Student Name", students.getStudentName());
    }

    public void setTextField (String input, boolean edit){
        textField = new JFXTextField();
        textField.setPromptText(input);
        textField.setLabelFloat(true);
        textField.setText(test.get(input));
        textField.setEditable(edit);
        m1.getChildren().add(textField);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        declareStudent();

        setTextField("Student ID", false);
        setTextField("Student Name", true);

        JFXButton btnUpdate = new JFXButton("Update");
        btnUpdate.setOnMouseClicked(event -> {
            new StudentDAO().update(id, textField.getText());
            TabStudent.getInstance().refresh();
        });

        m1.getChildren().add(btnUpdate);
    }
}
