package main.models;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import main.controllers.TabTeacher;
import main.controllers.TabUser;

import java.awt.*;
import java.sql.SQLException;

public class User {

    private int userID;
    private String username;
    private String password;
    private String status;
    private String role;

    private Teacher teacher;
    private Student student;
    private HBox action = new HBox();

    public User() {
    }

    public User(int userID, String username, String password, String status, String role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;

        initAction();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int userID){
        this.userID = userID;
    }

//    -----------------------------------------

    public void initAction(){

        action.setAlignment(Pos.CENTER);
        action.setSpacing(5);
        action.getChildren().addAll(updateAction(), resetAction(), deleteAction());
    }

    public JFXButton updateAction(){
        ImageView imgUpdate = new ImageView(new Image("/resources/images/icon/check.png"));
        imgUpdate.setFitWidth(15);
        imgUpdate.setFitHeight(15);

        JFXButton update = new JFXButton();
        update.setGraphic(imgUpdate);
        update.setBackground(new Background(new BackgroundFill(Color.SPRINGGREEN,new CornerRadii(5), Insets.EMPTY)));

        update.setOnMouseClicked(e-> {
            User user = TabUser.tableUserClone.getSelectionModel().getSelectedItem();
            if(user.getAction() == action){
//                ========================
                new UserDAO().update(user);
                TabUser.updateTable();
//                ========================
                switch (user.getRole()){
                    case "Teacher":
                        Teacher teacher = new Teacher();
                        teacher.setUserID(user.getUserID());
//                        ->SQL
                        new TeacherDAO().insert(teacher);
                        TabTeacher.updateTable();
                        break;
                    case "Student":
                        break;
                }
            }
        });

        return update;
    }

    public JFXButton deleteAction(){
        ImageView imgDelete = new ImageView(new Image("/resources/images/icon/x.png"));
        imgDelete.setFitWidth(15);
        imgDelete.setFitHeight(15);

        JFXButton delete = new JFXButton();
        delete.setGraphic(imgDelete);
        delete.setBackground(new Background(new BackgroundFill(Color.RED,new CornerRadii(5), Insets.EMPTY)));

        delete.setOnAction(e->{
            User user = TabUser.tableUserClone.getSelectionModel().getSelectedItem();
            if(user.getAction() == action){
                new UserDAO().delete(user);
                TabUser.updateTable();
            }
        });
        return delete;
    }

    public JFXButton resetAction(){
        ImageView imgReset = new ImageView(new Image("/resources/images/icon/rotate-ccw.png"));
        imgReset.setFitWidth(15);
        imgReset.setFitHeight(15);


        JFXButton reset = new JFXButton();
        reset.setGraphic(imgReset);
        reset.setBackground(new Background(new BackgroundFill(Color.ORANGE,new CornerRadii(5), Insets.EMPTY)));

        return reset;
    }

//    ---------------------------------------
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public HBox getAction() {
        return action;
    }

    public void setAction(HBox action) {
        this.action = action;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    //    ------------------------------------------------
    public void insertTeacherData(){

    }

    public void insertStudentData(Student student, Face face) throws SQLException {

        new StudentDAO().insert(student);
        new FaceDAO().insert(face);
        new ModuleDAO().insert(student);
        //temp
//        new AttendanceDAO().insert(new Attendance("P",student.getStudentId()));
    }

}
