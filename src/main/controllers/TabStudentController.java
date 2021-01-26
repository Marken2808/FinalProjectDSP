package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.models.Student;
import main.utils.DBbean;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;



public class TabStudentController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXDrawer drawerViewPane;

    @FXML
    private JFXDrawer drawerControlPane;

    @FXML
    private TableView<Student> tableSTUDENT;

    @FXML
    private TableColumn<Student, Integer> colSID;

    @FXML
    private TableColumn<Student, String> colSNAME;

    @FXML
    private TableColumn<Student, Integer> colMID;

    @FXML
    private JFXButton closeViewBtn;

    @FXML
    private JFXButton closeControlBtn;


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
    String DrawerViewStudent = "/main/views/DrawerViewStudent.fxml";
    String DrawerControlStudent = "/main/views/DrawerControlStudent.fxml";

    @FXML
    void clickOnTable(MouseEvent event) {
        Student selectedStudent = tableSTUDENT.getSelectionModel().getSelectedItem();

        if(!tableSTUDENT.getSelectionModel().isEmpty()){
            closeAllDrawer();
            id = selectedStudent.getStudentId();
            if(DBbean.isIdMark(id)){

                isDrawerClick(event.getButton().equals(MouseButton.SECONDARY));

            }
            tableSTUDENT.getSelectionModel().clearSelection(tableSTUDENT.getSelectionModel().getSelectedIndex());
        }

    }

    public void isDrawerClick(boolean check) {
        if(check) { // right click on selected row
            setOpenDrawer(DrawerControlStudent, drawerControlPane, new Double[]{null,0.0,0.0,0.0});
            setCloseDrawer(DrawerViewStudent, drawerViewPane, new Double[]{0.0, null, 0.0, -600.0});
        } else {    // left click on selected row
            setOpenDrawer(DrawerViewStudent, drawerViewPane, new Double[]{0.0, null, 0.0, 0.0});
            setCloseDrawer(DrawerControlStudent, drawerControlPane, new Double[]{null,0.0,-800.0,0.0});
        }

    }

    public void closeAllDrawer() {
        setCloseDrawer(DrawerViewStudent, drawerViewPane, new Double[]{0.0, null, 0.0, -600.0});
        setCloseDrawer(DrawerControlStudent, drawerControlPane, new Double[]{null,0.0,-800.0,0.0});
    }

    @FXML
    void closeViewPane(MouseEvent event) {
//        System.out.println("View clicked");
        setCloseDrawer(DrawerViewStudent, drawerViewPane, new Double[]{0.0, null, 0.0, -600.0});
    }

    @FXML
    void closeControlPane(MouseEvent event) {
//        System.out.println("Control clicked");
        setCloseDrawer(DrawerControlStudent, drawerControlPane, new Double[]{null,0.0,-800.0,0.0});
    }


    public void setOpenDrawer(String scene, JFXDrawer pane, Double[] sides) {

        pane.setVisible(true);
        setDrawer(scene, pane, sides);
        pane.open();


        pane.setOnDrawerOpened(jfxDrawerEvent -> {
            if (scene.equals(DrawerControlStudent)) {
                closeControlBtn.setGraphic(new ImageView(new Image("/resources/images/icon/line.png")));
                closeControlBtn.setVisible(true);
            } else {
                closeViewBtn.setGraphic(new ImageView(new Image("/resources/images/icon/minus.png")));
                closeViewBtn.setVisible(true);
            }

        });


    }




    public void setCloseDrawer(String scene, JFXDrawer pane, Double[] sides) {
//        if(pane.isOpening() || pane.isOpened()){
        pane.setVisible(false);
        setDrawer(scene, pane, sides);
        pane.close();

        pane.setOnDrawerClosing(jfxDrawerEvent -> {
            if (scene.equals(DrawerControlStudent)) {
                closeControlBtn.setVisible(false);
                anchorPane.clearConstraints(drawerControlPane);
            } else {
                closeViewBtn.setVisible(false);
                anchorPane.clearConstraints(drawerViewPane);
            }

        });

    }


    public void setDrawer(String scene, JFXDrawer pane, Double[] sides) {
        try {

            anchorPane.setLeftAnchor(pane, sides[0]);
            anchorPane.setTopAnchor(pane, sides[1]);
            anchorPane.setRightAnchor(pane, sides[2]);
            anchorPane.setBottomAnchor(pane, sides[3]);

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
