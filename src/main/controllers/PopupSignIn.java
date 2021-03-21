package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import main.models.*;
//import resources.mySQLconnection;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
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

    private String SignUpScreen     = "/main/views/PopupSignUp.fxml";

    public static User authUser;
    public static String name;
    @FXML
    void autoFill(MouseEvent event) {
        name = "Admin";
        ScreenPrimary.dialog.close();
        ScreenPrimary.getInstance().header.setVisible(true);
        ScreenPrimary.getInstance().displayScreen("Overview","/main/views/ScreenOverview.fxml");
    }



    @FXML
    void onLogin(MouseEvent event) {
//
//        System.out.println("username: "+fieldUsername.getText());
//        System.out.println("password: "+fieldPassword.getText());

        authUser = new UserDAO().authenticate(new User(fieldUsername.getText(), fieldPassword.getText()));

        if(authUser != null){
            autoFill(event);
            System.out.println("Successful");
            System.out.println("ID: "+authUser.getUserID());

            switch (authUser.getRole()){
                case "Teacher" :
                    Teacher teacher = new TeacherDAO().retrieve(authUser.getUserID());
                    name = teacher.getTeacherName();
                    break;
                case "Student" :

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