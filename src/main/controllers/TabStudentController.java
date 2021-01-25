package main.controllers;

import com.jfoenix.controls.JFXDrawer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import main.models.Student;
import main.utils.DBbean;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class TabStudentController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXDrawer drawerViewPane;

    @FXML
    private TableView<Student> tableSTUDENT;

    @FXML
    private TableColumn<Student, Integer> colSID;

    @FXML
    private TableColumn<Student, String> colSNAME;

    @FXML
    private TableColumn<Student, Integer> colMID;

    @FXML
    private ImageView closeBtn;


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

    static int id;
    boolean marked;
    String DrawerStudentView = "/main/views/DrawerStudentView.fxml";

    @FXML
    void clickOnTable(MouseEvent event) {
        Student selectedStudent = tableSTUDENT.getSelectionModel().getSelectedItem();
        if(!tableSTUDENT.getSelectionModel().isEmpty()){
            if(event.getClickCount() == 2){
                id = selectedStudent.getStudentId();
//                System.out.println(selectedStudent.getSid()+","+selectedStudent.getSname());
                if(marked = DBbean.isIdMark(id)){
                    setOpenDrawer();

                } else {
                    System.out.println("No record , Not fulfill");
                }
            }else{
                setCloseDrawer();
                tableSTUDENT.getSelectionModel().clearSelection(tableSTUDENT.getSelectionModel().getSelectedIndex());
            }
        }

    }

    @FXML
    void closeViewPane(MouseEvent event) {
        setCloseDrawer();
    }

    public void setOpenDrawer() {

        drawerViewPane.setVisible(true);
        if (drawerViewPane.isClosing() || drawerViewPane.isClosed()) {
            setDrawer(DrawerStudentView, drawerViewPane, 0.0, null, 0.0, 0.0);
            drawerViewPane.open();
            drawerViewPane.setOnDrawerOpened(jfxDrawerEvent -> {
                closeBtn.setVisible(true);
            });
        }
    }



    public void setCloseDrawer() {
        if(drawerViewPane.isOpening() || drawerViewPane.isOpened()){
            setDrawer(DrawerStudentView, drawerViewPane, 0.0, null, 0.0, -500.0);
            drawerViewPane.close();
            closeBtn.setVisible(false);
        }
    }

    public void setDrawer(String scene, JFXDrawer pane, Double left, Double top, Double right, Double bottom) {
        try {

            anchorPane.setLeftAnchor(pane, left);
            anchorPane.setTopAnchor(pane, top);
            anchorPane.setRightAnchor(pane,right);
            anchorPane.setBottomAnchor(pane, bottom);

            AnchorPane sBottom = FXMLLoader.load(getClass().getResource(scene));
            pane.setSidePane(sBottom);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        anchorPane.clearConstraints(drawerViewPane);    //init clear drawer

        colSID.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        colSNAME.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        colMID.setCellValueFactory(new PropertyValueFactory<>("StudentMarked"));

        studentLists = DBbean.getStudentData();

        tableSTUDENT.setItems(studentLists);




    }



}
