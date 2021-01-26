package main.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DrawerMenu implements Initializable {

    @FXML
    private VBox drawerPane;

    @FXML
    private ImageView picAvatar;

    @FXML
    private Label lableName;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnSignOut;

    @FXML
    void isHomeClicked(MouseEvent event) {
        //System.out.println("Home click");
    }

    @FXML
    void makeLogOut(MouseEvent event) throws IOException {
//        letMake(signOut, "SignInScreen.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Users.checkProfileName(typeUser);
//        if (Users.checkRole().equals("Admin")) {
//            dashboard.setVisible(true);
//        } else {
//            dashboard.setVisible(false);
//        }
    }
}
