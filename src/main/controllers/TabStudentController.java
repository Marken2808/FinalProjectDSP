package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.models.Student;
import main.utils.CircleChart;
import main.utils.DBbean;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class TabStudentController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXDrawer drawerPane;

    @FXML
    private TableView<Student> tableSTUDENT;

    @FXML
    private TableColumn<Student, Integer> colSID;

    @FXML
    private TableColumn<Student, String> colSNAME;

    public static TabStudentController instance;
    public TabStudentController(){
        instance = this;
    }
    public static TabStudentController getInstance() {
        if(instance == null){
            instance = new TabStudentController();
        }
        return instance;
    }

    ObservableList<Student> studentLists = FXCollections.observableArrayList();

    @FXML
    void clickOnTable(MouseEvent event) {
        Student selectedStudent = tableSTUDENT.getSelectionModel().getSelectedItem();
        if(!tableSTUDENT.getSelectionModel().isEmpty()){
            if(event.getClickCount() == 2){
                System.out.println(selectedStudent.getSid()+","+selectedStudent.getSname());
                this.displayDrawer();
            }else{
                tableSTUDENT.getSelectionModel().clearSelection(tableSTUDENT.getSelectionModel().getSelectedIndex());
            }
        }
    }

    public void displayDrawer() {

        try {
            AnchorPane sBottom = FXMLLoader.load(getClass().getResource("/main/views/DrawerStudent.fxml"));
            drawerPane.setSidePane(sBottom);
        } catch (IOException e) {
            e.printStackTrace();
        }


        anchorPane.setRightAnchor(drawerPane,0.0);
        anchorPane.setLeftAnchor(drawerPane, 0.0);

        if (drawerPane.isClosed() || drawerPane.isClosing()) {
//            System.out.println("open" + drawerPane.isClosed());
            drawerPane.open();
        } else {
//            System.out.println("close" + drawerPane.isOpened());
            drawerPane.close();
//            this.anchorPane.setBottomAnchor(this.drawerPane, -500.0);
            anchorPane.setBottomAnchor(drawerPane,0.0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colSID.setCellValueFactory(new PropertyValueFactory<Student, Integer>("Sid"));
        colSNAME.setCellValueFactory(new PropertyValueFactory<Student, String>("Sname"));

        studentLists = DBbean.getStudentData();
        tableSTUDENT.setItems(studentLists);



    }



}
