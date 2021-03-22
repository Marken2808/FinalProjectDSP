package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import models.Student;
import models.Teacher;
import models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreenProfile implements Initializable {

    @FXML
    private JFXTextField fieldName;

    private User user = PopupSignIn.authUser;

    private int accessedID;

    @FXML
    void onUpdate(MouseEvent event) {
        System.out.println(accessedID);
    }

    public String accessedName(){
        switch (user.getRole()){
            case "Teacher" :
                accessedID = user.getTeacher().getTeacherId();
                return user.getTeacher().getTeacherName();
            case "Student" :
                accessedID = user.getStudent().getStudentId();
                return user.getStudent().getStudentName();
            case "Admin" :
                return user.getRole();
        }

        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        fieldName.setText( accessedName() );

    }
}
