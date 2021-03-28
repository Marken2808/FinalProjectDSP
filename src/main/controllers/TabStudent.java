package controllers;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import models.Attendance;
import models.Student;
import models.StudentDAO;
import utils.DBbean;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class TabStudent implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXTextField fieldSearch;

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
    FilteredList<Student> filterData;

    public static int id;
    String DrawerViewStudent = "/views/DrawerViewStudent.fxml";
    String DrawerControlStudent = "/views/DrawerControlStudent.fxml";


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

        setCloseDrawer(drawerControlPane);
        setCloseDrawer(drawerViewPane);

        Student selectedStudent = tableSTUDENT.getSelectionModel().getSelectedItem();
        if(!tableSTUDENT.getSelectionModel().isEmpty()){
            id = selectedStudent.getStudentId();
            if(DBbean.isIdMark(id)){
                isDrawerClick(event.getButton().equals(MouseButton.SECONDARY));
            }
            tableSTUDENT.getSelectionModel().clearSelection(tableSTUDENT.getSelectionModel().getSelectedIndex());
//            updateTable();
        }
    }

    public void updateTable(){
        // refresh
        tableSTUDENT.refresh();
        studentLists = new StudentDAO().showStudentTable();
        tableSTUDENT.setItems(studentLists);

        filterData = new FilteredList<>(studentLists, e-> true);
        tableSTUDENT.setItems(filterData);
    }

    public void isDrawerClick(boolean check) {
        if(check) { // right click on selected row
            setOpenDrawer( DrawerControlStudent, drawerControlPane );
        } else {    // left click on selected row
            setOpenDrawer( DrawerViewStudent, drawerViewPane );
        }
    }

    public void setOpenDrawer(String scene, JFXDrawer pane) {
        setDrawer(scene, pane);
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

    public void setDrawer(String scene, JFXDrawer pane) {
        try {
            anchorPane.setLeftAnchor(pane, 0.0);
            anchorPane.setTopAnchor(pane, 0.0);
            anchorPane.setRightAnchor(pane, 0.0);
            anchorPane.setBottomAnchor(pane, 0.0);

            AnchorPane sBottom = FXMLLoader.load(getClass().getResource(scene));
            pane.setSidePane(sBottom);

            if (scene.equals(DrawerControlStudent)) {
                pane.setDefaultDrawerSize(anchorPane.getWidth()*0.5);
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

                    Color color;
                    switch (a.getAttStatus()){
                        case "P":
                            color = (Color.FORESTGREEN);
                            break;
                        case "A":
                            color = (Color.ORANGERED);
                            break;
                        default:
                            color = (Color.GRAY);
                            break;
                    }

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

    @FXML
    void onClear(MouseEvent event) {
        fieldSearch.clear();
        tableSTUDENT.refresh();
    }

    @FXML
    void onSearchStudent(KeyEvent event) {

        fieldSearch.textProperty().addListener((obsVal, oldVal, newVal) -> {

            filterData.setPredicate((Student s) -> {

                return      s.getStudentName().toUpperCase().contains(newVal.toUpperCase())
                        ||  String.valueOf(s.getStudentId()).contains(newVal.toUpperCase()) ;
            });

            tableSTUDENT.refresh();
        });

    }

    @FXML
    void onSuggestStudent(KeyEvent event) {

        JFXAutoCompletePopup<Student> auto = new JFXAutoCompletePopup<>();
        auto.setSelectionHandler(e -> fieldSearch.setText(e.getObject().getStudentName().toUpperCase()));
        auto.getSuggestions().addAll(studentLists);

        fieldSearch.textProperty().addListener((obsVal, oldVal, newVal) ->{
            auto.filter(s -> s.getStudentName().toUpperCase().contains(newVal.toUpperCase())
                          || String.valueOf(s.getStudentId()).contains(newVal.toUpperCase())
            );
            if(auto.getFilteredSuggestions().isEmpty() || newVal.isBlank()){ auto.hide(); }
            else{ auto.show(fieldSearch); }
        });
//        System.out.println(fieldSearch.getText().isBlank());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        callbackCell_StudentID();
        callbackCell_StudentName();
        callbackCell_StudentMarked();
        callbackCell_Last5Days();

        updateTable();

    }



}
