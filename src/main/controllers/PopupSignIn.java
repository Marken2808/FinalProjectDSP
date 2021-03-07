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
import main.models.Teacher;
import main.models.TeacherDAO;
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

    @FXML
    void autoFill(MouseEvent event) {

        ScreenPrimary.dialog.close();
        ScreenPrimary.getInstance().header.setVisible(true);
        ScreenPrimary.getInstance().displayScreen("Overview","/main/views/ScreenOverview.fxml");
    }

    @FXML
    void makeLogin(ActionEvent event) {

        System.out.println("username: "+fieldUsername.getText());
        System.out.println("password: "+fieldPassword.getText());

        boolean verify = new TeacherDAO().authenticate(new Teacher(fieldUsername.getText(), fieldPassword.getText()));

        if(verify){
            ScreenPrimary.dialog.close();
            ScreenPrimary.getInstance().header.setVisible(true);
        } else {
            textInfor.setDisable(false);
        }

    }

    @FXML
    void letRegister(ActionEvent event) {
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