package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import main.models.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TabUser implements Initializable {

    @FXML
    private TableView<User> tableUSER;

    @FXML
    private TableColumn<User, Integer> colUID;

    @FXML
    private TableColumn<User, String> colUUSERNAME;

    @FXML
    private TableColumn<User, String> colUPASSWORD;

    @FXML
    private TableColumn<User, String> colUSTATUS;

    @FXML
    private TableColumn<User, String> colUROLE;


    ObservableList<User> userLists = FXCollections.observableArrayList();

    public void callbackCell_ID(){
        colUID.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                if (!empty) {
                    setText(String.valueOf(param.getTableView().getItems().get(getIndex()).getId()));
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    public void callbackCell_USERNAME(){
        colUUSERNAME.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                if (!empty) {
                    setText(param.getTableView().getItems().get(getIndex()).getUsername());
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    public void callbackCell_PASSWORD(){
        colUPASSWORD.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                if (!empty) {
                    setText(param.getTableView().getItems().get(getIndex()).getPassword());
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    public void callbackCell_STATUS(){
        colUSTATUS.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                if (!empty) {
                    setText(param.getTableView().getItems().get(getIndex()).getStatus());
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    public void callbackCell_ROLE(){
        colUROLE.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                if (!empty) {
                    setText(param.getTableView().getItems().get(getIndex()).getRole());
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

//    public void callbackCell_TeacherName(){
//
////        colTNAME.setCellFactory(param -> new TableCell<>() {
////            @Override
////            protected void updateItem(String item, boolean empty) {
////                if (!empty) {
////
////                    super.updateItem(item,empty);
////                    setText(String.valueOf(param.getTableView().getItems().get(getIndex()).getTeacherName()));
////                    setAlignment(Pos.CENTER);
////
////                }
////            }
////        });
//
//        colTNAME.setCellValueFactory(new PropertyValueFactory<Teacher, String>("TeacherName"));
//        colTNAME.setCellFactory(TextFieldTableCell.forTableColumn());
//        colTNAME.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Teacher, String> event) {
//                event.getRowValue().setTeacherName(event.getNewValue());
//            }
//        });
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userLists = new UserDAO().showUserTable();
        tableUSER.setItems(userLists);
        callbackCell_ID();
        callbackCell_USERNAME();
        callbackCell_PASSWORD();
        callbackCell_STATUS();
        callbackCell_ROLE();


        tableUSER.setOnMouseClicked( e-> {
            User selectedUser = tableUSER.getSelectionModel().getSelectedItem();
            if(!tableUSER.getSelectionModel().isEmpty()){
                System.out.println(selectedUser.getId());
                System.out.println(selectedUser.getUsername());
            }
        });

    }
}
