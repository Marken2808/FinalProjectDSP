package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
//import resources.mySQLconnection;

import java.net.URL;
import java.util.ResourceBundle;


public class PopupSignIn implements Initializable {

    @FXML
    private JFXTextField filedUsername;

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
        btnSignIn.setDisable(false);
    }

    @FXML
    void makeLogin(ActionEvent event) {
        ScreenPrimary.dialog.close();
        ScreenPrimary.getInstance().header.setVisible(true);

    }

    @FXML
    void letRegister(ActionEvent event) {
        ScreenPrimary.dialog.close();
        ScreenPrimary.getInstance().popUp(SignUpScreen,false);
    }

    @FXML
    void isEmpty(KeyEvent event) {
//        Boolean isEmptyUser = isEmptyField(user,userWarning,userWarningImg);
//        Boolean isEmptyPass = isEmptyField(pass,passWarning,passWarningImg);
//        Boolean loadButton = isEmptyUser && isEmptyPass;
//        isAllDone(loadButton,signIn);
    }


//    public ArrayList<String> createConnectionWithRole(){
//        String userDB = user.getText() ;
//        String passDB = pass.getText() ;
//
//        Connection connection = mySQLconnection.ConnectDataBase();
//        String query = "Select * from users where username = ? and password = ?";
//        try{
//            PreparedStatement pst = connection.prepareStatement(query);
//            pst.setString(1,userDB);
//            pst.setString(2,passDB);
//            ResultSet rs = pst.executeQuery();
//            if(rs.next()) {
//                ArrayList<String> objToken = new ArrayList<>();
//
//                objToken.add(rs.getString("role"));
//                objToken.add(rs.getString("username"));
//                objToken.add(rs.getString("password"));
//                objToken.add(rs.getString("profilename"));
//
//                return objToken;
//            }
//        }
//        catch ( Exception e){
//            //JOptionPane.showMessageDialog(null,e);
//        }
//        return null;
//    }
//
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}