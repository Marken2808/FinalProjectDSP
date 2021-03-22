package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import models.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TabUser implements Initializable {

    @FXML
    private TableView<User> tableUSER;

    public static TableView<User> tableUserClone;

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

    @FXML
    private TableColumn<User, Button> colUACTION;

    public static ObservableList<User> userLists = FXCollections.observableArrayList();

    public void callbackCell_ID() {
        colUID.setCellValueFactory(new PropertyValueFactory<User, Integer>("UserID"));
    }

    public void callbackCell_USERNAME() {
        colUUSERNAME.setCellValueFactory(new PropertyValueFactory<User, String>("Username"));
        colUUSERNAME.setCellFactory(TextFieldTableCell.forTableColumn());
        colUUSERNAME.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setUsername(event.getNewValue());
        });
    }

    public void callbackCell_PASSWORD() {
        colUPASSWORD.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
        colUPASSWORD.setCellFactory(TextFieldTableCell.forTableColumn());
        colUPASSWORD.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassword(event.getNewValue());
        });
    }

    public void callbackCell_STATUS() {
        colUSTATUS.setCellValueFactory(new PropertyValueFactory<User, String>("Status"));
//        colUSTATUS.setCellFactory(ChoiceBoxTableCell.forTableColumn(list));
//        colUSTATUS.setOnEditCommit(event -> {
//            event.getTableView().getItems().get(event.getTablePosition().getRow()).setStatus(event.getNewValue());
//        });
    }

    public void callbackCell_ROLE() {

        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Guest","Teacher", "Student");

        colUROLE.setCellValueFactory(new PropertyValueFactory<User, String>("Role"));
        colUROLE.setCellFactory(ChoiceBoxTableCell.forTableColumn(list));
        colUROLE.setOnEditCommit(event -> {

            User selected = event.getTableView().getItems().get(event.getTablePosition().getRow());
            selected.setRole(event.getNewValue());
            if (selected.getRole() != "Guest") {
                selected.setStatus("Active");
            } else {
                selected.setStatus("Inactive");
            }
        });
    }

    public void callbackCell_ACTION() {
        colUACTION.setCellValueFactory(new PropertyValueFactory<User, Button>("Action"));
    }

    @FXML
    void onAddUser(MouseEvent event) {
        new UserDAO().insert(new User("null","null"));
        updateTable();
    }

    public static void updateTable() {
        // refresh
        tableUserClone.refresh();
        userLists = new UserDAO().showUserTable();
        tableUserClone.setItems(userLists);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userLists = new UserDAO().showUserTable();
        tableUSER.setItems(userLists);
        tableUserClone = tableUSER;

        callbackCell_ID();
        callbackCell_USERNAME();
        callbackCell_PASSWORD();
        callbackCell_STATUS();
        callbackCell_ROLE();
        callbackCell_ACTION();


//        System.out.println("outside: "+tableUSER.getSelectionModel().getSelectedItem().getRole());



    }
}
