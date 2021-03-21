package main.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DrawerMenu implements Initializable {

    @FXML
    private VBox drawerPane;

    @FXML
    private ImageView picAvatar;

    @FXML
    private Label labelName;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnCamera;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnSignOut;

    @FXML
    void isHomeClicked(MouseEvent event) {
        //System.out.println("Home click");
    }

    @FXML
    void makeLogOut(MouseEvent event) {

        btnSignOut.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/views/ScreenPrimary.fxml"));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelName.setText(PopupSignIn.name);
    }
}
