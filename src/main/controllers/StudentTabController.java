package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.models.Student;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentTabController implements Initializable {

    @FXML
    private TableView<Student> tableSTUDENT;

    @FXML
    private TableColumn<Student, Integer> colSID;

    @FXML
    private TableColumn<Student, String> colSNAME;

    public ObservableList<Student> getStudentLists() {

        ObservableList<Student> list = FXCollections.observableArrayList();
        Student s1 = new Student(1,"qwe");
        Student s2 = new Student(2,"asd");
        list.addAll(s1,s2);
        return list;
    }

    ObservableList<Student> studentLists = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colSID.setCellValueFactory(new PropertyValueFactory<Student, Integer>("Sid"));
        colSNAME.setCellValueFactory(new PropertyValueFactory<Student, String>("Sname"));

        studentLists = getStudentLists();
        tableSTUDENT.setItems(studentLists);

    }



}
