package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import javafx.util.Duration;
import main.models.Attendance;
import main.models.AttendanceDAO;
import main.models.Student;
import main.models.StudentDAO;
import main.utils.DBbean;

import javax.naming.Binding;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


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
    private TableColumn<Student, Boolean> colMarked;

    @FXML
    private TableColumn<Student, ArrayList<Attendance> > colLast5Days;

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
    void closeViewPane(MouseEvent event) {
//        System.out.println("View clicked");
        setCloseDrawer(
                drawerViewPane
        );
    }

    @FXML
    void closeControlPane(MouseEvent event) {
//        System.out.println("Control clicked");
        setCloseDrawer(
                drawerControlPane
        );
    }

    @FXML
    void testMove(MouseEvent event) {
        if(event.getTarget().toString().contains("null")){
            tableSTUDENT.setCursor(Cursor.DEFAULT);
        } else {
            tableSTUDENT.setCursor(Cursor.HAND);
        }
    }

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

            refresh();
        }
    }

    public void refresh(){
        // refresh
        tableSTUDENT.refresh();
        studentLists = new StudentDAO().showStudentTable();
        tableSTUDENT.setItems(studentLists);
    }

    public void isDrawerClick(boolean check) {
        if(check) { // right click on selected row

            setOpenDrawer(
                    DrawerControlStudent,
                    drawerControlPane,
                    new double[]{5, 5, 0, 5}
                    );
            setCloseDrawer(
                    drawerViewPane
                    );

        } else {    // left click on selected row
            setOpenDrawer(
                    DrawerViewStudent,
                    drawerViewPane,
                    new double[]{5, 5, 5, 0}
                    );
            setCloseDrawer(
                    drawerControlPane
                    );
        }

    }

    public void setOpenDrawer(String scene, JFXDrawer pane, double[] sides) {
        setDrawer(scene, pane, sides);
        pane.open();
        pane.setOnDrawerOpened(jfxDrawerEvent -> {
            pane.setVisible(true);
        });

    }

    public void setCloseDrawer(JFXDrawer pane) {
        pane.close();
        pane.setOnDrawerClosing(jfxDrawerEvent -> {
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
                pane.setDefaultDrawerSize(anchorPane.getWidth()*0.4);
//                pane.prefWidthProperty().bind(anchorPane.widthProperty().multiply(80/100));
            } else {
                pane.setDefaultDrawerSize(anchorPane.getHeight()*0.8);
//                pane.prefHeightProperty().bind(anchorPane.heightProperty().multiply(80/100));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void callbackCell_StudentID(){
        colSID.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                if (!empty) {
                    setText(String.valueOf(param.getTableView().getItems().get(getIndex()).getStudentId()));
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    public void callbackCell_StudentName(){
        colSNAME.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                if (!empty) {
                    setText(param.getTableView().getItems().get(getIndex()).getStudentName().toUpperCase());
                    setAlignment(Pos.CENTER);
//                    setFont(Font.font("Arial", FontWeight.NORMAL, 15));
                }
            }
        });
    }

    public void callbackCell_StudentMarked(){
        colMarked.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                if (!empty) {
                    setText(String.valueOf(param.getTableView().getItems().get(getIndex()).isStudentMarked()));
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    public void hoverBtn(JFXButton btn, Color color, CornerRadii corn){
        btn.backgroundProperty().bind(Bindings.when(btn.hoverProperty())
                .then(new Background(new BackgroundFill(color, corn, Insets.EMPTY)))
                .otherwise(new Background(new BackgroundFill(null, null, Insets.EMPTY)))
        );
        btn.textFillProperty().bind(Bindings.when(btn.hoverProperty())
                .then(Color.WHITE)
                .otherwise(Color.BLACK)
        );
    }
    
    public void callbackCell_Last5Days(){
        colLast5Days.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(ArrayList<Attendance> item, boolean empty) {
            if (!empty) {
                Student indexStudent = param.getTableView().getItems().get(getIndex());
                ArrayList<Attendance> last5Days = indexStudent.getStudentLast5Days();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");
                HBox hBox = new HBox();
                CornerRadii corn = new CornerRadii(8);
                for (Attendance a : last5Days){

                    Tooltip tooltip = new Tooltip();
                    tooltip.setShowDelay(Duration.ZERO);
                    tooltip.setText(dateFormat.format(a.getAttDate()));

                    Color color = a.getAttStatus().equals("P") ? (Color.FORESTGREEN) : (Color.ORANGERED);
                    JFXButton btn = new JFXButton(a.getAttStatus());
                    btn.setBorder(new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, corn, BorderWidths.DEFAULT)));
                    btn.setTooltip(tooltip);
                    hoverBtn(btn, color, corn);
                    
                    hBox.setSpacing(5);
                    hBox.setAlignment(Pos.CENTER);
                    hBox.getChildren().add(btn);
                }

                setGraphic(hBox);
            }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        callbackCell_StudentID();
        callbackCell_StudentName();
        callbackCell_StudentMarked();
        callbackCell_Last5Days();

        refresh();


//        new AttendanceDAO().insertAttendance(new Attendance("Z", 5));

    }



}
