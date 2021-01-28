package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.models.Student;
import main.utils.DBbean;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class TabStudent implements Initializable {

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


    public static TabStudent instance;
    public TabStudent(){
        instance = this;
    }
    public static TabStudent getInstance() {
        if(instance == null){
            instance = new TabStudent();
        }
        return instance;
    }

    ObservableList<Student> studentLists = FXCollections.observableArrayList();

    static int id;
    String DrawerViewStudent = "/main/views/DrawerViewStudent.fxml";
    String DrawerControlStudent = "/main/views/DrawerControlStudent.fxml";

    @FXML
    void clickOnTable(MouseEvent event) {
        closeViewPane(event);
        closeControlPane(event);


        Student selectedStudent = tableSTUDENT.getSelectionModel().getSelectedItem();

        if(!tableSTUDENT.getSelectionModel().isEmpty()){

            id = selectedStudent.getStudentId();
            if(DBbean.isIdMark(id)){

                isDrawerClick(event.getButton().equals(MouseButton.SECONDARY));

            }
            tableSTUDENT.getSelectionModel().clearSelection(tableSTUDENT.getSelectionModel().getSelectedIndex());
        }

    }

    public void isDrawerClick(boolean check) {
        if(check) { // right click on selected row

            setOpenDrawer(
                    DrawerControlStudent,
                    drawerControlPane,
                    closeControlBtn,
                    new double[]{10, 5, 0, 5}
                    );
            setCloseDrawer(
                    DrawerViewStudent,
                    drawerViewPane,
                    closeViewBtn,
                    new double[]{0, 0, 0, -anchorPane.getHeight()}
                    );

        } else {    // left click on selected row
            setOpenDrawer(
                    DrawerViewStudent,
                    drawerViewPane,
                    closeViewBtn,
                    new double[]{10, 5, 10, 0}
                    );
            setCloseDrawer(
                    DrawerControlStudent,
                    drawerControlPane,
                    closeControlBtn,
                    new double[]{0, 0, -anchorPane.getWidth(), 0}
                    );
        }

    }

    @FXML
    void closeViewPane(MouseEvent event) {
//        System.out.println("View clicked");
        setCloseDrawer(
                DrawerViewStudent,
                drawerViewPane,
                closeViewBtn,
                new double[]{0, 0, 0, -anchorPane.getHeight()}
                );
    }

    @FXML
    void closeControlPane(MouseEvent event) {
//        System.out.println("Control clicked");
        setCloseDrawer(
                DrawerControlStudent,
                drawerControlPane,
                closeControlBtn,
                new double[]{0, 0,-anchorPane.getWidth(), 0});
    }


    public void setOpenDrawer(String scene, JFXDrawer pane, JFXButton btn, double[] sides) {
        setDrawer(scene, pane, sides);
        pane.open();
        pane.setOnDrawerOpened(jfxDrawerEvent -> {
            pane.setVisible(true);
            btn.setVisible(true);
        });
    }




    public void setCloseDrawer(String scene, JFXDrawer pane, JFXButton btn, double[] sides) {
        setDrawer(scene, pane, sides);
        pane.close();
        pane.setOnDrawerClosing(jfxDrawerEvent -> {
            btn.setVisible(false);
            pane.setVisible(false);
        });
    }


    public void setDrawer(String scene, JFXDrawer pane, double[] sides) {
        try {

            anchorPane.setLeftAnchor(pane, sides[0]);
            anchorPane.setTopAnchor(pane, sides[1]);
            anchorPane.setRightAnchor(pane, sides[2]);
            anchorPane.setBottomAnchor(pane, sides[3]);

            AnchorPane sBottom = FXMLLoader.load(getClass().getResource(scene));
            pane.setSidePane(sBottom);

            if (scene.equals(DrawerControlStudent)) {
                pane.setDefaultDrawerSize(anchorPane.getWidth()*0.6);
//                pane.prefWidthProperty().bind(anchorPane.widthProperty().multiply(80/100));
            } else {
                pane.setDefaultDrawerSize(anchorPane.getHeight());
//                pane.prefHeightProperty().bind(anchorPane.heightProperty().multiply(80/100));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        anchorPane.clearConstraints(drawerViewPane);    //init clear drawer

        colSID.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        colSNAME.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        colMID.setCellValueFactory(new PropertyValueFactory<>("StudentMarked"));

        studentLists = DBbean.getStudentData();

        tableSTUDENT.setItems(studentLists);


    }



}
