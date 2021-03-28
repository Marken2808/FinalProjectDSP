package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Teacher;
import models.TeacherDAO;
import models.User;
import models.UserDAO;

import java.net.URL;
import java.util.ResourceBundle;


public class PopupSignIn implements Initializable {

    @FXML
    private JFXTextField fieldUsername;

    @FXML
    private Label textInfor;

    @FXML
    private ImageView picInfor;

    @FXML
    private JFXPasswordField fieldPassword;

    @FXML
    private JFXToggleButton btnAuto;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXButton btnSignUp;

    private String SignUpScreen     = "/views/PopupSignUp.fxml";

    public static User authUser;

    @FXML
    void autoFill(MouseEvent event) {
//        authUser = new UserDAO().authenticate(new User("admin","admin"));
        ScreenPrimary.dialog.close();
        ScreenPrimary.getInstance().header.setVisible(true);
        ScreenPrimary.getInstance().displayScreen("Overview","/views/ScreenOverview.fxml");
    }

    @FXML
    void onLogin(MouseEvent event) {
//
        authUser = new UserDAO().authenticate(new User(fieldUsername.getText(), fieldPassword.getText()));

        if(authUser != null){
            autoFill(event);
            System.out.println("Successful");
            System.out.println("ID: "+authUser.getUserID());

            switch (authUser.getRole()){
                case "Teacher" :
                    Teacher imTeacher = new TeacherDAO().retrieve(authUser.getUserID());
                    authUser.setTeacher(imTeacher);
                    break;
                case "Student" :
                    break;
                case "Admin" :
                    authUser.setRole("Admin");
                    break;

            }
        } else {
            System.out.println("Fail");
        }


    }

    @FXML
    void onRegister(MouseEvent event) {
        ScreenPrimary.dialog.close();
        ScreenPrimary.getInstance().displayPopup(SignUpScreen,false);
    }


    void isEmpty() {
//        Boolean isEmptyUser = isEmptyField(user,userWarning,userWarningImg);
//        Boolean isEmptyPass = isEmptyField(pass,passWarning,passWarningImg);
//        Boolean loadButton = isEmptyUser && isEmptyPass;
//        isAllDone(loadButton,signIn);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}