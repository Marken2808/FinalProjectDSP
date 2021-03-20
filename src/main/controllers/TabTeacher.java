package main.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Cell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import main.models.Student;
import main.models.StudentDAO;
import main.models.Teacher;
import main.models.TeacherDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class TabTeacher implements Initializable {

    @FXML
    private TableView<Teacher> tableTEACHER;

    @FXML
    private TableColumn<Teacher, Integer> colTID;

    @FXML
    private TableColumn<Teacher, String> colTNAME;

    ObservableList<Teacher> teacherLists = FXCollections.observableArrayList();

    public String test = null;

    public void callbackCell_TeacherID(){
        colTID.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                if (!empty) {
                    setText(String.valueOf(param.getTableView().getItems().get(getIndex()).getTeacherID()));
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    public void callbackCell_TeacherName(){

//        colTNAME.setCellFactory(param -> new TableCell<>() {
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                if (!empty) {
//
//                    super.updateItem(item,empty);
//                    setText(String.valueOf(param.getTableView().getItems().get(getIndex()).getTeacherName()));
//                    setAlignment(Pos.CENTER);
//
//                }
//            }
//        });

        System.out.println("Before: " + test);

        colTNAME.setCellValueFactory(new PropertyValueFactory<Teacher, String>("TeacherName"));
        colTNAME.setCellFactory(TextFieldTableCell.forTableColumn());
        colTNAME.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, String> event) {
                event.getRowValue().setTeacherName(event.getNewValue());
                test = event.getNewValue();
            }
        });

        System.out.println("After: " + test);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        teacherLists = new TeacherDAO().showTeacherTable();
        tableTEACHER.setItems(teacherLists);
        callbackCell_TeacherID();
        callbackCell_TeacherName();

    }
}
