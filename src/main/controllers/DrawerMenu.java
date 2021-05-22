package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.User;

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

    private static User user;

    @FXML
    void isHomeClicked(MouseEvent event) {
        //System.out.println("Home click");
    }

    @FXML
    void makeLogOut(MouseEvent event) {

        btnSignOut.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ScreenPrimary.fxml"));

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

    public String accessedName(){
        switch (user.getRole()){
            case "Teacher" :
                return user.getTeacher().getTeacherName().toUpperCase();
            case "Student" :
                return user.getStudent().getStudentName().toUpperCase();
            case "Admin" :
                return user.getRole();
        }
        return null;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        user = PopupSignIn.userData();
        labelName.setText(accessedName());

        ObservableList<Node> DrawerBoxes = ((VBox) drawerPane.getChildren().get(1)).getChildren();

        for (Node node : DrawerBoxes) {
            if (!node.getAccessibleText().equals("Profile") &&  (user.getRole().equals("Student"))) {
                node.setVisible(false);
            } else {
                node.setVisible(true);
            }
        }
    }
}
