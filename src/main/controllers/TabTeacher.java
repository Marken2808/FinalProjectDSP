package controllers;

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
import models.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TabTeacher implements Initializable {

    @FXML
    private TableView<Teacher> tableTEACHER;

    public static TableView<Teacher> tableTeacherClone;

    @FXML
    private TableColumn<Teacher, Integer> colTID;

    @FXML
    private TableColumn<Teacher, String> colTNAME;

    @FXML
    private TableColumn<User, Integer> colTUID;

    public static ObservableList<Teacher> teacherLists = FXCollections.observableArrayList();

    public void callbackCell_TeacherID(){
        colTID.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                if (!empty) {
                    setText(String.valueOf(param.getTableView().getItems().get(getIndex()).getTeacherId()));
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    public void callbackCell_TeacherName(){

        colTNAME.setCellValueFactory(new PropertyValueFactory<Teacher, String>("TeacherName"));
        colTNAME.setCellFactory(TextFieldTableCell.forTableColumn());
        colTNAME.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, String> event) {
                event.getRowValue().setTeacherName(event.getNewValue());
            }
        });
    }

    public void callbackCell_TeacherUserID(){
        colTUID.setCellValueFactory(new PropertyValueFactory<User, Integer>("UserID"));
    }

    public static void updateTable() {
        // refresh
        tableTeacherClone.refresh();
        teacherLists = new TeacherDAO().showTeacherTable();
        tableTeacherClone.setItems(teacherLists);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        teacherLists = new TeacherDAO().showTeacherTable();
        tableTEACHER.setItems(teacherLists);
        tableTeacherClone = tableTEACHER;

        callbackCell_TeacherID();
        callbackCell_TeacherName();
        callbackCell_TeacherUserID();

    }
}
