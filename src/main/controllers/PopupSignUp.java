package main.controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
//import resources.mySQLconnection;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

//import static resources.controllers.functions.duplicatedForms.*;

public class PopupSignUp implements Initializable {

    @FXML
    private JFXTextField fieldUsername;

    @FXML
    private Label textInfor;

    @FXML
    private ImageView picInfor;

    @FXML
    private JFXPasswordField fieldPassword;

    @FXML
    private JFXPasswordField fieldConfirm;

    @FXML
    private JFXDatePicker pickDate;

    @FXML
    private JFXSlider sliderAge;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    void checkingAge(ActionEvent event) {
        LocalDate date = pickDate.getValue();
        LocalDate today = LocalDate.now();
        Period p = Period.between(date,today);
        double age = p.getYears();
        sliderAge.setValue(age);
        sliderAge.setShowTickLabels(true);
    }

    @FXML
    void makeRegister(ActionEvent event) {
//        ResultSet rs = null;
//        Connection connection = mySQLconnection.ConnectDataBase();
//        String query = "insert into users (username,password) values (?,?)";
//        try{
//            PreparedStatement pst = connection.prepareStatement(query);
//            pst.setString(1,new_user.getText());
//            pst.setString(2,new_pass.getText());
//            pst.execute();
//            JOptionPane.showMessageDialog(null,"Saved");
//        }
//        catch ( Exception e){
//            //JOptionPane.showMessageDialog(null,e);
//        }
        ScreenPrimary.dialog.close();
        ScreenPrimary.getInstance().displaySignIn();
    }

    @FXML
    void isEmpty(KeyEvent event) {
//        Boolean isEmptyUser = isEmptyField(new_user,userWarning,userWarningImg);
//        Boolean isEmptyPass = isEmptyField(new_pass,passWarning,passWarningImg);
//        Boolean isConfirmPass = isConfirmRight(new_pass,check_pass,confirmWarning,confirmWarningImg);
//        Boolean loadButton = isEmptyUser && isEmptyPass && isConfirmPass;
//        isAllDone(loadButton,signUp);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}